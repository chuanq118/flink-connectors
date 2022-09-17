package cn.lqs.flink.conn.kafka;

import cn.lqs.flink.conn.kafka.util.LqsFlinkKafkaSourceUtil;
import cn.lqs.flink.connectors.common.util.FlinkConnectorConfigs;
import cn.lqs.flink.connectors.common.util.LqsLocalFlinkApplication;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;

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

        // 只需要调用此 api 即可创建一个可用的 kafka source
        KafkaSource<String> kafkaSource
                = LqsFlinkKafkaSourceUtil.<String>createSourceOfKfk(args);

        // 启动本地测试
        DataStreamSource<String> ds = LqsLocalFlinkApplication.run(8081, 2)
                .fromSource(kafkaSource,
                WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(5)),
                FlinkConnectorConfigs.getConnectorCfg().getSource().getName(), Types.STRING);

        // todo 此处可以添加 ds 流处理的自定义逻辑

        // 直接执行即可
        LqsLocalFlinkApplication.<String>doExecute(ds);

    }
}
