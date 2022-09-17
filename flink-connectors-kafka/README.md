## Flink - Kafka

#### Quick Start
```java
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
```