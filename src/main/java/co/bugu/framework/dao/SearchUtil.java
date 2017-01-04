package co.bugu.framework.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by user on 2017/1/4.
 */
public class SearchUtil {

    private static Logger logger = LoggerFactory.getLogger(SearchUtil.class);

    public static void process(Map<String, Object> param) {
        Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isNoneEmpty(key)) {
//              获取操作类型
                String opr = key.substring(key.length() - 1);
                String field = key.substring(0, key.length() - 1);


            }

        }
    }

}

/**
 * 参数类型
 */
enum ParamType {
    I(Integer.class), S(String.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class), C(BigDecimal.class);

    private Class<?> type;


    ParamType(Class<?> clazz) {
        this.type = clazz;
    }

    public Class<?> getValue() {
        return type;
    }


}