package com.yuan.Server;

import com.yuan.Server.provider.ServiceProvider;
import com.yuan.Server.server.RpcServer;
import com.yuan.Server.server.impl.NettyRPCRPCServer;
import com.yuan.common.service.Impl.UserServiceImpl;
import com.yuan.common.service.UserService;

public class TestServer {
    public static void main(String[] args) {
        UserService userService=new UserServiceImpl();

        ServiceProvider serviceProvider=new ServiceProvider("127.0.0.1",9999);
        serviceProvider.provideServiceInterface(userService);

        RpcServer rpcServer=new NettyRPCRPCServer(serviceProvider);
        rpcServer.start(9999);
    }
}
