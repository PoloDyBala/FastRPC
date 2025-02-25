//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {
    private static final Logger log = LoggerFactory.getLogger(ConfigUtil.class);

    public ConfigUtil() {
    }

    public static <T> T loadConfig(Class<T> targetClass, String prefix) {
        return loadConfig(targetClass, prefix, "");
    }

    public static <T> T loadConfig(Class<T> targetClass, String prefix, String environment) {
        StringBuilder configFileNameBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileNameBuilder.append("-").append(environment);
        }

        configFileNameBuilder.append(".properties");
        Props properties = new Props(configFileNameBuilder.toString());
        if (properties.isEmpty()) {
            log.warn("配置文件 '{}' 为空或加载失败！", configFileNameBuilder.toString());
        } else {
            log.info("加载配置文件: '{}'", configFileNameBuilder.toString());
        }

        try {
            return properties.toBean(targetClass, prefix);
        } catch (Exception var6) {
            log.error("配置转换失败，目标类: {}", targetClass.getName(), var6);
            throw new RuntimeException("配置加载失败", var6);
        }
    }
}
