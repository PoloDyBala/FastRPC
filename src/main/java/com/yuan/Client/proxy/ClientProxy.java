package com.yuan.Client.proxy;

import com.yuan.Client.IOClient;
import com.yuan.Client.circuitBreaker.CircuitBreaker;
import com.yuan.Client.circuitBreaker.CircuitBreakerProvider;
import com.yuan.Client.retry.guavaRetry;
import com.yuan.Client.rpcClient.RpcClient;
import com.yuan.Client.rpcClient.impl.NettyRpcClient;
import com.yuan.Client.serverCenter.ServiceCenter;
import com.yuan.Client.serverCenter.ZKServiceCenter;
import com.yuan.common.Message.RpcRequest;
import com.yuan.common.Message.RpcResponse;
import lombok.AllArgsConstructor;

import com.yuan.Client.rpcClient.impl.SimpleSocketRpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClientProxy implements InvocationHandler {
    //传入参数service接口的class对象，反射封装成一个request

    private RpcClient rpcClient;

    private ServiceCenter serviceCenter;

    private CircuitBreakerProvider circuitBreakerProvider;
    public ClientProxy() throws InterruptedException {
        serviceCenter = new ZKServiceCenter();
        rpcClient = new NettyRpcClient();
        circuitBreakerProvider = new CircuitBreakerProvider();
    }
    //jdk动态代理，每一次代理对象调用方法，都会经过此方法增强（反射获取request对象，socket发送到服务端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构建request
        RpcRequest request=RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsType(method.getParameterTypes()).build();
        // 获取熔断器
        CircuitBreaker circuitBreaker = circuitBreakerProvider.getCircuitBreaker(method.getName());
        if(!circuitBreaker.allowRequest()){
            return null;
        }


        //数据传输
        RpcResponse response;
        if(serviceCenter.checkRetry(request.getInterfaceName())){
            response = new guavaRetry().sendServiceWithRetry(request, rpcClient);
        }else {
            response = rpcClient.sendRequest(request);
        }

        // 记录response的状态, 上报给熔断器
        if(response.getCode() == 200){
            circuitBreaker.recordFailure();
        }
        if(response.getCode() == 500){
            circuitBreaker.recordFailure();
        }
        return response.getData();
    }
    public <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
