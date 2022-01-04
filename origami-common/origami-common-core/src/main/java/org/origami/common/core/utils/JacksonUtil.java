package org.origami.common.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.exception.base.BaseException;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * jackson工具类
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-04 10:38
 */
@Slf4j
@UtilityClass
public class JacksonUtil {
    private static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static ObjectMapper objectMapper = new ObjectMapper();
    
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
    
    public static ObjectMapper getInstance() {
        return objectMapper;
    }
    
    /**
     * 对象转json
     *
     * @param src
     * @return
     */
    public String toJson(Object src) {
        return toJson(src, false);
    }
    
    /**
     * 转换为json串，根据配置决定是否报异常
     *
     * @param src
     * @param throwException
     * @return
     */
    public String toJson(Object src, boolean throwException) {
        if (src == null) {
            return null;
        }
        
        try {
            return src instanceof String ? ((String) src)
                                         : objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            if (throwException) {
                throw new BaseException("json解析失败");
            }
            log.error("json解析失败");
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
            return src instanceof String ? ((String) src)
                                         : objectMapper.writerWithDefaultPrettyPrinter()
                                                       .writeValueAsString(src);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    
    /**
     * json转bean
     *
     * @param json
     * @param beanType
     * @param throwException
     * @param <T>
     * @return
     */
    public <T> T fromJson(String json, Class<T> beanType, boolean throwException) {
        if (json == null || beanType == null) {
            return null;
        }
        
        try {
            return objectMapper.readValue(json, beanType);
        } catch (JsonProcessingException e) {
            if (throwException) {
                throw new BaseException("json转换错误");
            }
            log.error("json转换错误");
            return null;
        }
    }
    
    public <T> T fromJson(String json, Class<T> beanType) {
        return fromJson(json, beanType, false);
    }
    
    public <T> List<T> listFromJson(String json, Class<T> beanType, boolean throwException) {
    
    }
}
