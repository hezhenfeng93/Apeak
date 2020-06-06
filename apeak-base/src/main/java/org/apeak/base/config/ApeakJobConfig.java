package org.apeak.base.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HZF
 * @Date: 2020/6/6 23:04
 */
public class ApeakJobConfig {

    private static Logger LOGGER = LogManager.getLogger(ApeakJobConfig.class);

    private static Map<Object, Integer> taskCache = new ConcurrentHashMap<>();

    private static Integer getAndSetTask(Object object) {
        synchronized (object) {
            Integer maxNumber = taskCache.get(object);
            if(null == maxNumber) {
                maxNumber = 0;
                taskCache.put(object, maxNumber);
            }
            return maxNumber;
        }
    }

    private static void getAndIncrementTask(Object object){
        synchronized (object) {
            Integer maxNumber = getAndSetTask(object);
            taskCache.put(object, maxNumber + 1);
        }
    }

    private static void getAndDecrementTask(Object object){
        synchronized (object) {
            Integer maxNumber = getAndSetTask(object);
            taskCache.put(object, maxNumber - 1);
        }
    }

    public static void lock(Object object, int maxNumber, long timeWait) {
        while(!tryLock(object, maxNumber)) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeWait);
            } catch (InterruptedException e) {
                LOGGER.error("", e);
            }
        }
    }

    public static boolean tryLock(Object object, int maxNumber) {
        synchronized (object) {
            Integer currentNumber = getAndSetTask(object);
            if(currentNumber >= maxNumber) {
                return false;
            } else {
                getAndIncrementTask(object);
                return true;
            }
        }
    }

    public static boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public static void unlock(Object object) {
        synchronized (object) {
            Integer currentNumber = getAndSetTask(object);
            if(currentNumber > 0) {
                getAndDecrementTask(object);
            } else {
                LOGGER.error("current object task number is not consistent.");
            }
        }
    }
}
