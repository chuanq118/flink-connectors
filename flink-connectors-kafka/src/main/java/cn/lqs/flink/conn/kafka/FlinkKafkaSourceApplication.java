package cn.lqs.flink.conn.kafka;

import cn.lqs.flink.conn.kafka.util.LqsFlinkKafkaSourceUtil;
import cn.lqs.flink.connectors.common.util.FlinkConnectorConfigs;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

/**
 * 2022/9/16 11:08
 * created by @lqs
 */
public class FlinkKafkaSourceApplication {

    /**
     * -config D:\Java\workspace\flink-connectors\config\flink_kafka_cfg.json (建议绝对路径)
     * @param args 程序启动参数
     * @throws Exception ex
     */
    public static void main(String[] args) throws Exception {
        Configuration cfg = new Configuration();
        cfg.setInteger(RestOptions.PORT, 8081);
        LocalStreamEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(cfg);

        env.setParallelism(2);

        // 只需要调用此 api 即可创建一个可用的 kafka source
        KafkaSource<String> kafkaSource
                = LqsFlinkKafkaSourceUtil.<String>createSourceOfKfk(args);

        DataStreamSource<String> ds = env.fromSource(kafkaSource,
                WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(5)),
                FlinkConnectorConfigs.getConnectorCfg().getSource().getName(), Types.STRING);

        ds.print();

        env.execute(FlinkConnectorConfigs.getConnectorCfg().getJobName());

    }
}
