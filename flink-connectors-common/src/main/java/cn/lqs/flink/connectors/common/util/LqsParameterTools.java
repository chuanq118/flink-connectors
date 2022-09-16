package cn.lqs.flink.connectors.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.utils.ParameterTool;

import java.io.File;
import java.io.IOException;

/**
 * 2022/9/16 11:01
 * created by @lqs
 */
@Slf4j
public class LqsParameterTools {

    public static void findCfg(String[] args) {
        ParameterTool paras = ParameterTool.fromArgs(args);
        String cfgPt = paras.get("config");
        if (cfgPt == null || "".equals(cfgPt)) {
            log.warn("未发现可用的配置文件路径...");
            return;
        }
        try {
            FlinkConnectorConfigs.parseOfJson(new File(cfgPt));
        } catch (IOException e) {
            log.error("读取配置文件失败", e);
        }
    }

}
