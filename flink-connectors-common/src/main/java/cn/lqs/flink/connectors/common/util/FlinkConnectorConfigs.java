package cn.lqs.flink.connectors.common.util;

import cn.lqs.flink.connectors.common.FlinkConnectorConfig;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 2022/9/16 10:48
 * created by @lqs
 */
public class FlinkConnectorConfigs {

    public static FlinkConnectorConfig connectorCfg;

    public static void parseOfJson(File cfgF) throws IOException {
        String cfg = FileUtils.readFileToString(cfgF, StandardCharsets.UTF_8);
        connectorCfg = JSONObject.parseObject(cfg, FlinkConnectorConfig.class);
        // return connectorCfg;
    }

    public static FlinkConnectorConfig getConnectorCfg() {
        return connectorCfg;
    }

    public static boolean hasConfigured() {
        return connectorCfg != null;
    }

}
