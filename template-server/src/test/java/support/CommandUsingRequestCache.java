package support;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @Author: luweihong
 * @Date: 2019/1/9
 * web filter
 * https://blog.csdn.net/zhuchuangang/article/details/74566185
 */
public class CommandUsingRequestCache extends HystrixCommand<Boolean> {

    private final int value;

    public CommandUsingRequestCache(int value) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.value = value;
    }

    @Override
    protected Boolean run() throws Exception {
        System.out.println("doing");
        return value == 0 || value % 2 == 0;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(value);
    }

    @Override
    public Boolean getFallback(){
        return Boolean.FALSE;
    }
}
