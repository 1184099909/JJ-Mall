package com.ithanlei.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext1)throws BeansException {
        if(null == this.applicationContext){
            this.applicationContext = applicationContext1;
        }
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 根据name获取
     * @return
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 根据类型获取
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);

    }
    public static <T> T getBean(String name, Class<T> clazz){
            return getApplicationContext().getBean(name, clazz);
    }


}
