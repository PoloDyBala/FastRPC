package com.yuan.Client.rpcClient;

import com.yuan.common.Message.RpcRequest;
import com.yuan.common.Message.RpcResponse;

public interface   RpcClient {

    //定义底层通信的方法
    RpcResponse sendRequest(RpcRequest request);
}
