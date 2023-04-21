package asia.huayu.config;

import cn.hutool.core.thread.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author RainZiYu
 * @Date 2023/4/7
 * 自定义线程池，用于自定义的异步任务
 */
@Configuration
public class ExecutorConfig {
    @Bean
    public ExecutorService initExecutorService() {
        // 运行服务器的可用核心数
        int coreCount = Runtime.getRuntime().availableProcessors();
        // 使用Hutool的线程池工具 为线程池命名 并设置为非守护进程   即如果所有jvm在关闭时所有非守护进程执行完毕那么jvm就可以退出 即不管守护进程是否执行完毕
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory("MySelf", false);
        return new ThreadPoolExecutor(coreCount / 2, coreCount, 10,
                TimeUnit.SECONDS,
                // 无容量的阻塞队列，一旦达到最大线程数并仍然有新任务就会抛出RejectedExecutionException，交由饱和策略处理 解决短时高峰期间的任务处理
                new SynchronousQueue(),
                namedThreadFactory,
                // 将提交的任务交由提交的线程处理，如果提交线程已结束就会丢弃任务
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
