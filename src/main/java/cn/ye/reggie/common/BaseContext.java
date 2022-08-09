package cn.ye.reggie.common;

import org.springframework.data.annotation.Id;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户的id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long Id) {

    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
