package com.yuan.consumer;


import com.yuan.config.FastRpcConfig;
import common.util.ConfigUtil;

/**
 * @ClassName ConsumerTestConfig
 * @Description 测试配置顶
 * @Author Tong
 * @LastChangeDate 2024-12-05 11:29
 * @Version v1.0
 */
public class ConsumerTestConfig {
    public static void main(String[] args) {
        FastRpcConfig rpc = ConfigUtil.loadConfig(FastRpcConfig.class, "rpc");
        System.out.println(rpc);
    }

}
