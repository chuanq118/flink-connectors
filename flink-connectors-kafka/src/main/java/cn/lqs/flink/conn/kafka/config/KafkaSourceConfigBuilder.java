package cn.lqs.flink.conn.kafka.config;

import cn.lqs.flink.connectors.common.Source;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.KafkaSourceBuilder;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.lang.reflect.InvocationTargetException;

/**
 * 2022/9/16 13:09
 * created by @lqs
 */
public class KafkaSourceConfigBuilder {

    public static <T> KafkaSource<T> buildFromSource(Source kafkaSourceCfg) {
        // get servers
        String brokers = kafkaSourceCfg.getProps()
                .getOrDefault(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // get group id
        String groupId = kafkaSourceCfg.getProps()
                .getOrDefault(ConsumerConfig.GROUP_ID_CONFIG, "");
        // specify topics
        String topics = kafkaSourceCfg.getProps().get("topics");
        if (topics == null) {
            throw new IllegalArgumentException("kafka source config 缺少 topics 参数");
        }
        // specify deserializer
        String valueDeserializer = kafkaSourceCfg.getProps().getOrDefault("value.deserializer",
                "org.apache.flink.api.common.serialization.SimpleStringSchema");
        // specify offset
        String offset = kafkaSourceCfg.getProps().getOrDefault("start.offset", "earliest");
        OffsetsInitializer offsetsInitializer = OffsetsInitializer.earliest();
        // latest,earliest,committed
        switch (offset){
            case "earliest":
                break;
            case "latest":
                offsetsInitializer = OffsetsInitializer.latest();
                break;
            case "committed":
                offsetsInitializer = OffsetsInitializer.committedOffsets();
                break;
            default:
                throw new IllegalArgumentException("未知的 offset 参数!");
        }

        try {
            Object $valueDeserializer = Class.forName(valueDeserializer).getDeclaredConstructor().newInstance();

            KafkaSourceBuilder<T> sourceBuilder = KafkaSource.<T>builder()
                    .setBootstrapServers(brokers)
                    .setTopics(topics)
                    .setValueOnlyDeserializer((DeserializationSchema<T>) $valueDeserializer)
                    .setStartingOffsets(offsetsInitializer);

            if (!"".equals(groupId)) {
                sourceBuilder.setGroupId(groupId);
            }

            // extract all additional props
            String additional = kafkaSourceCfg.getProps().getOrDefault("additional", null);
            if (additional != null && !"".equals(additional)) {
                for (String sp : additional.split(";")) {
                    String[] kv = sp.trim().split("=");
                    assert kv.length == 2;
                    sourceBuilder.setProperty(kv[0].trim(), kv[1].trim());
                }
            }
            return sourceBuilder.build();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
