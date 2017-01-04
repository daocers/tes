package co.bugu.framework.dao;

import java.util.Map;

/**
 * Created by user on 2017/1/4.
 */
public class ThreadLocalUtil {
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(Map<String, Object> map){
        threadLocal.set(map);
    }

    public static Map<String, Object> get(){
        return threadLocal.get();
    }
}