package org.origami.common.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;

/**
 * jackson工具类
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-04 10:38
 */
@UtilityClass
public class JacksonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static {

        objectMapper
                // 日期格式
                .setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN))
                // 序列化,字段对应的值为null也显示出来
                .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                // json中有实体类没有的字段时,不报错
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 忽略空bean转json错误
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                // 格式化速进控制台输出
                .enable(SerializationFeature.INDENT_OUTPUT);

    }

    /**
     * 对象转json
     *
     * @param src
     * @return
     */
    public String toJson(Object src) {
        if (src == null) {
            return null;
        }

        try {
            return src instanceof String ? ((String) src) : objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 转换为json串
     * @param src
     * @param throwException
     * @return
     */
    public String toJson(Object src,boolean throwException) {
        if (src == null) {
            return null;
        }

        try {
            return src instanceof String ? ((String) src) : objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 返回美化json
     *
     * @param src
     * @return
     */
    public String toJsonPretty(Object src) {
        if (src == null) {
            return null;
        }

        try {
            return src instanceof String ? ((String) src) : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(src);
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    public <T> T fromJson(String json, Class<T> clazz) {
        return objectMapper.
    }
}
