package support;

import com.netflix.hystrix.*;

/**
 * @Author: luweihong
 * @Date: 2019/1/9
 */
public class HelloCommand extends HystrixCommand<String> {

    public HelloCommand(String groupName) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(groupName))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(groupName))
                .andCommandPropertiesDefaults(  // 配置信号量隔离
                        HystrixCommandProperties.Setter()
                                // 用于设置信号量 或者 thread
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                .withExecutionIsolationSemaphoreMaxConcurrentRequests(3)
                                .withFallbackIsolationSemaphoreMaxConcurrentRequests(1)
                                // 超时设置为1500
                                .withExecutionTimeoutInMilliseconds(1500)
                                .withRequestCacheEnabled(true)
                ));
    }

    @Override
    protected String run() throws Exception {
        System.out.println("mainThread=" + Thread.currentThread().getName());
//        Thread.sleep(1000);
        return "hello world";
    }

    @Override
    protected String getFallback() {
        System.out.println("fall back");
        return "fall back";
    }
}
