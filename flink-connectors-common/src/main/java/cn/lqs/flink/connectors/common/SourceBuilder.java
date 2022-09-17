package cn.lqs.flink.connectors.common;

import cn.lqs.flink.connectors.common.util.FlinkConnectorConfigs;
import cn.lqs.flink.connectors.common.util.LqsParameterTools;
import cn.lqs.flink.connectors.common.util.UuidSource;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * 2022/9/17 12:46
 * created by @lqs
 */
public class SourceBuilder {

    public static Source getSourceFromConfig(String[] args) {
        LqsParameterTools.findCfg(args);
        if (!FlinkConnectorConfigs.hasConfigured()) {
            throw new IllegalArgumentException("读取配置文件时发生错误!");
        }
        return FlinkConnectorConfigs.getConnectorCfg().getSource();
    }

    /**
     * 读取配置文件 构建 source function
     * @param args 程序参数
     * @return source func
     * @param <T> 数据类型
     */
    public static <T> SourceFunction<T> buildSourceFunc(String[] args) {
        Source source = getSourceFromConfig(args);
        String sourceFunc = source.getSourceFunc();
        if (sourceFunc != null && !"".equals(sourceFunc)) {
            if (sourceFunc.equals("cn.lqs.flink.connectors.common.util.UuidSource")){
                return (SourceFunction<T>) new UuidSource(Long.parseLong(source.getProps()
                        .getOrDefault("interval", "1000")),
                        Long.parseLong(source.getProps().getOrDefault("max.size", "100")));
            }
        }
        throw new IllegalArgumentException("没有发现支持 source func 配置");
    }
}
