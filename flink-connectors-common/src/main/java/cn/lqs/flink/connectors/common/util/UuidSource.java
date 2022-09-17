package cn.lqs.flink.connectors.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 内置测试用 source 每隔指定时间产生随机字符串 到最大值后任务结束
 * 2022/9/17 10:35
 * created by @lqs
 */
@Slf4j
public class UuidSource implements SourceFunction<String> {

    /**
     * 字符串产生间隔时间
     */
    private final long interval;
    /**
     * 最大产生的字符串数量
     */
    private final long maxSize;

    private final AtomicInteger counter = new AtomicInteger(0);

    public UuidSource(long interval, long maxSize) {
        this.interval = interval;
        this.maxSize = maxSize;
    }

    private boolean isRunning = true;

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while (isRunning && counter.getAndIncrement() < maxSize) {
            ctx.collect(UUID.randomUUID().toString());
            TimeUnit.MILLISECONDS.sleep(interval);
        }
    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
