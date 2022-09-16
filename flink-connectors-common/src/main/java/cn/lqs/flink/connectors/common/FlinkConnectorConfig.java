package cn.lqs.flink.connectors.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2022/9/16 10:55
 * created by @lqs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlinkConnectorConfig {

    private Sink sink;
    private Source source;
    private String jobName;

}
