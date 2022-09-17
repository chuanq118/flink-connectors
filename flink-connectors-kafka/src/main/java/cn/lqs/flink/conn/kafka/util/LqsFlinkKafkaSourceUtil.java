package cn.lqs.flink.conn.kafka.util;

import cn.lqs.flink.conn.kafka.config.KafkaSourceConfigBuilder;
import cn.lqs.flink.connectors.common.Source;
import cn.lqs.flink.connectors.common.SourceBuilder;
import org.apache.flink.connector.kafka.source.KafkaSource;

/**
 * 2022/9/16 13:37
 * created by @lqs
 */
public class LqsFlinkKafkaSourceUtil {

    public static <T> KafkaSource<T> createSourceOfKfk(String[] args) {
        Source kafkaSourceCfg = SourceBuilder.getSourceFromConfig(args);
        return KafkaSourceConfigBuilder.<T>buildFromSource(kafkaSourceCfg);
    }

}
