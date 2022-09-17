package cn.lqs.flink.connectors.common.util;

import cn.lqs.flink.connectors.common.SinksBuilder;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 2022/9/17 11:05
 * created by @lqs
 */
public class LqsLocalFlinkApplication {

    public static StreamExecutionEnvironment env;

    public static StreamExecutionEnvironment run(int port, int parallelism) {
        Configuration cfg = new Configuration();
        cfg.setInteger(RestOptions.PORT, 8081);
        env = StreamExecutionEnvironment.createLocalEnvironment(cfg);
        env.setParallelism(2);
        return env;
    }

    public static <T> void doExecute(DataStreamSource<T> ds) throws Exception {
        // 自动装配相关 sinks
        SinksBuilder.build(FlinkConnectorConfigs.getConnectorCfg().getSinks(), ds);

        env.execute(FlinkConnectorConfigs.getConnectorCfg().getJobName());
    }
}
