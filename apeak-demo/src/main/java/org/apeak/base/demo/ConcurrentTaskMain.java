package org.apeak.base.demo;

import net.sf.cglib.proxy.Enhancer;
import org.apeak.base.demo.service.StudentService;
import org.apeak.base.interceptor.ApeakMethodInterceptor;

/**
 * @Author: HZF
 * @Date: 2020/6/6 23:27
 */
public class ConcurrentTaskMain {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(StudentService.class);
        enhancer.setCallback(new ApeakMethodInterceptor());
        StudentService service = (StudentService) enhancer.create();

        for(int i = 0; i < 20; i ++) {
            new Thread(() -> {
                service.startClass();
            }).start();
        }
    }
}
