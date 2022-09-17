package cn.lqs.flink.connectors.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 2022/9/16 10:55
 * created by @lqs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlinkConnectorConfig {

    private List<Sink> sinks;
    private Source source;
    private String jobName;

}
