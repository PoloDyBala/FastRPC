package com.yuan;


import com.yuan.config.FastRpcConfig;
import com.yuan.config.RpcConstant;
import common.util.ConfigUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName RpcApplication
 * @Description 测试配置顶，学习更多参考Dubbo
 * @Author Tong
 * @LastChangeDate 2024-12-05 11:22
 * @Version v5.0
 */
@Slf4j
public class FastRpcApplication {
    private static volatile FastRpcConfig rpcConfigInstance;

    public static void initialize(FastRpcConfig customRpcConfig) {
        rpcConfigInstance = customRpcConfig;
        log.info("RPC 框架初始化，配置 = {}", customRpcConfig);
    }

    public static void initialize() {
        FastRpcConfig customRpcConfig;
        try {
            customRpcConfig = ConfigUtil.loadConfig(FastRpcConfig.class, RpcConstant.CONFIG_FILE_PREFIX);
            log.info("成功加载配置文件，配置文件名称 = {}", RpcConstant.CONFIG_FILE_PREFIX); // 添加成功加载的日志
        } catch (Exception e) {
            // 配置加载失败，使用默认配置
            customRpcConfig = new FastRpcConfig();
            log.warn("配置加载失败，使用默认配置");
        }
        initialize(customRpcConfig);
    }

    public static FastRpcConfig getRpcConfig() {
        if (rpcConfigInstance == null) {
            synchronized (FastRpcApplication.class) {
                if (rpcConfigInstance == null) {
                    initialize();  // 确保在第一次调用时初始化
                }
            }
        }
        return rpcConfigInstance;
    }
}
