package platform.config;

import config.ModuleConfig;
import org.springframework.stereotype.Service;

/**
 * 模块配置信息
 */
@Service
public class ModuleConfigImpl implements ModuleConfig {
    @Override
    public String getName() {
        return "pay";
    }
}