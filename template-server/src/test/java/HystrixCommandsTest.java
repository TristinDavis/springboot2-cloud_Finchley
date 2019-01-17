import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import support.CommandUsingRequestCache;
import support.HelloCommand;

import static junit.framework.TestCase.assertTrue;

/**
 * @Author: luweihong
 * @Date: 2018/8/17
 */
@RunWith(SpringRunner.class)
public class HystrixCommandsTest {

    /**
     * 简单的hystrixCommand请求
     * 每个实例只能调用一次
     * 再次execute会出现异常
     */
    @Test
    public void hystrixCommandsTest() {
        String syncGroupName = "sync-test-commands";
        HelloCommand helloCommand = new HelloCommand(syncGroupName);
        String result = helloCommand.execute();
        System.out.println("result is :" + result);
    }

    @Test
    public void hystricCacheRequest() {
        HystrixRequestContext cxt = HystrixRequestContext.initializeContext();

    }

    /**
     * 测试缓存的上下文作用域
     */
    @Test
    public void hystrixCacheRequestContext() {
        HystrixRequestContext cxt = HystrixRequestContext.initializeContext();
        try {
            CommandUsingRequestCache command2a = new CommandUsingRequestCache(2);
            CommandUsingRequestCache command2b = new CommandUsingRequestCache(2);

            assertTrue(command2a.execute());
            System.out.println("command2a 是否使用了缓存" + command2a.isResponseFromCache());

            assertTrue(command2b.execute());
            System.out.println("command2b 是否使用了缓存" + command2b.isResponseFromCache());
        } finally {
            cxt.shutdown();
        }

        // 开启一个新的上下文
        cxt = HystrixRequestContext.initializeContext();
        try {
            CommandUsingRequestCache command3b = new CommandUsingRequestCache(2);
            assertTrue(command3b.execute());
            System.out.println(command3b.isResponseFromCache());
        } finally {
            cxt.shutdown();
        }
    }

    @Test
    public void testObserve() {
//        String asyncGroupName = "async-test-commands";
//        Observable<String> helloWorldObservable = new HelloWorldCommand(asyncGroupName).observe();
//        HelloCommand helloCommand = new HelloCommand(asyncGroupName);
//
//        helloObservable.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("调用成功...");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println(throwable);
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("on next is what ? " + s);
//            }
//        });
//
//        // 调用后会触发监听
//        helloCommand.execute();
    }

}
