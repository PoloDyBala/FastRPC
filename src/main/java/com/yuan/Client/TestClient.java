package com.yuan.Client;

import com.yuan.Client.proxy.ClientProxy;
import com.yuan.common.pojo.User;
import com.yuan.common.service.UserService;

public class TestClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy();
        UserService proxy = clientProxy.getProxy(UserService.class);

        User user = proxy.getUserByUserId(1);
        System.out.println("从服务器得到的user =" + user.toString());

        User u = User.builder().id(100).userName("wxx").sex(true).build();
        Integer id =  proxy.insertUserId(u);
        System.out.println("向服务端插入user的id" + id);
    }
}
