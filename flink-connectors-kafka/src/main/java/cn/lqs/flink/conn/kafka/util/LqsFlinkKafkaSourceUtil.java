package cn.lqs.flink.conn.kafka.util;

import cn.lqs.flink.conn.kafka.config.KafkaSourceConfigBuilder;
import cn.lqs.flink.connectors.common.Source;
import cn.lqs.flink.connectors.common.util.FlinkConnectorConfigs;
import cn.lqs.flink.connectors.common.util.LqsParameterTools;
import org.apache.flink.connector.kafka.source.KafkaSource;

/**
 * 2022/9/16 13:37
 * created by @lqs
 */
public class LqsFlinkKafkaSourceUtil {

    public static <T> KafkaSource<T> createSourceOfKfk(String[] args) {
        LqsParameterTools.findCfg(args);
        if (!FlinkConnectorConfigs.hasConfigured()) {
            throw new IllegalArgumentException("读取配置文件时发生错误!");
        }
        Source kafkaSourceCfg = FlinkConnectorConfigs.getConnectorCfg().getSource();
        return KafkaSourceConfigBuilder.<T>buildFromSource(kafkaSourceCfg);
    }

}
