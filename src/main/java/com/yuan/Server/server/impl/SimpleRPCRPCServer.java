package com.yuan.Server.server.impl;

import com.yuan.Server.provider.ServiceProvider;
import com.yuan.Server.server.RpcServer;
import com.yuan.Server.server.work.WorkThread;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@AllArgsConstructor
public class SimpleRPCRPCServer implements RpcServer {

    private ServiceProvider serviceProvider;

    @Override
    public void start(int port){
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务启动了");
            while(true) {
                Socket socket = serverSocket.accept();
                new Thread(new WorkThread(socket, serviceProvider)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
    }
}
