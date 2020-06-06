package org.apeak.base.interceptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apeak.base.concurrent.annotation.ApeakConcurrentAnnotation;
import org.apeak.base.config.ApeakJobConfig;

import java.lang.reflect.Method;

/**
 * @Author: HZF
 * @Date: 2020/6/6 22:58
 */
public class ApeakMethodInterceptor implements MethodInterceptor {
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        ApeakConcurrentAnnotation apeakConcurrentAnnotation = method.getAnnotation(ApeakConcurrentAnnotation.class);

        if(null != apeakConcurrentAnnotation) {
            int maxNumber = apeakConcurrentAnnotation.maxNumber();
            long timeWait = apeakConcurrentAnnotation.timeWait();
            ApeakJobConfig.lock(o, maxNumber, timeWait);
        }
        methodProxy.invokeSuper(o, objects);

        if(null != apeakConcurrentAnnotation) {
            ApeakJobConfig.unlock(o);
        }

        return null;
    }
}
