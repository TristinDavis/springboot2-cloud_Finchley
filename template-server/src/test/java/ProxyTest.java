import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import support.UserVO;

import java.lang.reflect.Method;

/**
 * @Author: luweihong
 * @Date: 2018/8/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ProxyTest {

    private Logger LOGGER = LoggerFactory.getLogger(SpringJUnit4ClassRunner.class);

    @Test
    public void cglibTest() {
        Enhancer enhancer = new Enhancer();
        // 设置父类型,并且通过cglib生成的类为被代理的一个子类
        enhancer.setSuperclass(UserVO.class);
        //
        enhancer.setCallback((MethodInterceptor) (o, method, args, methodProxy) -> {
            LOGGER.debug("before method run ...");
            Object result = methodProxy.invokeSuper(o, args);
            LOGGER.debug("after method run ...");
            return result;
        });

        UserVO userVO = (UserVO) enhancer.create();
        userVO.test();
    }

    @Test
    public void testFixedValue() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserVO.class);
        enhancer.setCallback((FixedValue) () -> "Hello cglib");

        UserVO proxy = (UserVO) enhancer.create();
        LOGGER.info("{}", proxy.test(null));
        LOGGER.info("{}", proxy.toString());
        LOGGER.info("{}", proxy.getClass());
        LOGGER.info("{}", proxy.hashCode());
    }

    /**
     * 指定某个方法拦截
     */
    @Test
    public void testInvocationHandler() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserVO.class);
        enhancer.setCallback((InvocationHandler) (proxy, method, args) -> {
            if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                return "hello cglib";
            } else {
                throw new RuntimeException("Do not know what to do");
            }
        });
        UserVO proxy = (UserVO) enhancer.create();
        Assert.assertEquals("hello cglib", proxy.test(null));
        Assert.assertNotEquals("Hello cglib", proxy.toString());
    }

    @Test
    public void testCallbackFilter() {
        Enhancer enhancer = new Enhancer();
        CallbackHelper callbackHelper = new CallbackHelper(UserVO.class, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return (FixedValue) () -> "Hello cglib";
                } else {
                    return NoOp.INSTANCE;
                }
            }
        };
        enhancer.setSuperclass(UserVO.class);
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        UserVO proxy = (UserVO) enhancer.create();
        Assert.assertEquals("Hello cglib", proxy.test(null));
        Assert.assertNotEquals("Hello cglib", proxy.toString());
        System.out.println(proxy.hashCode());
    }

}
