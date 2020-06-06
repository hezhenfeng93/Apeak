package org.apeak.base.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apeak.base.concurrent.annotation.ApeakConcurrentAnnotation;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HZF
 * @Date: 2020/6/6 23:27
 */
public class StudentService {

    private static Logger LOGGER = LogManager.getLogger(StudentService.class);

    @ApeakConcurrentAnnotation(maxNumber = 5, timeWait = 2000)
    public void startClass() {
        LOGGER.info("开始上课");
        try {
            int timeWait = new Random().nextInt(15);
            timeWait = timeWait < 0 ? -timeWait : timeWait;
            LOGGER.info("上课时间：{}秒", timeWait);
            TimeUnit.SECONDS.sleep(timeWait);
        } catch (InterruptedException e) {
            LOGGER.error("", e);
        }
        LOGGER.info("结束时间");
    }
}
