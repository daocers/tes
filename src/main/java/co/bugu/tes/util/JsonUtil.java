package co.bugu.tes.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.util.Date;

/**
 * Created by daocers on 2016/7/26.
 */
public class JsonUtil {
    private static SerializeConfig config = new SerializeConfig();

    static {
        config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * 包装fastjson原有的转json串的方法，根据需要，需求格式化的一些配置
     * @param object
     * @return
     */
    public static String toJsonString(Object object){
        return JSON.toJSONString(object, config);
    }

    public static String getData(String errCode, Object errMsg){
        JSONObject json = new JSONObject();
        json.put("errCode", errCode);
        json.put("errMsg", errMsg);
        return json.toJSONString();
    }
}
