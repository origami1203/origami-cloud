package org.origami.common.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.exception.base.BaseException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    static {
        MAPPER
                // 日期格式
                .setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN))
                // 序列化,字段对应的值为null也显示出来
                .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                // json中有实体类没有的字段时,不报错
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 忽略空bean转json错误
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        // LocalDateTime转换
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT_PATTERN);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class,
                                       new LocalDateTimeDeserializer(formatter));
        javaTimeModule.addSerializer(LocalDateTime.class,
                                     new LocalDateTimeSerializer(formatter));
        // long转string，防止js精度丢失
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, new ToStringSerializer());
        simpleModule.addSerializer(Long.TYPE, new ToStringSerializer());
        MAPPER.registerModules(javaTimeModule, simpleModule);
        
    }
    
    /**
     * 获取mapper
     *
     * @return objectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return MAPPER;
    }
    
    /**
     * 对象转json
     *
     * @param src bean
     * @return json, 不会抛异常
     */
    public String toJson(Object src) {
        return toJson(src, false);
    }
    
    /**
     * 转换为json串，根据配置决定是否报异常
     *
     * @param src            bean
     * @param throwException 是否抛异常
     * @return 解析失败抛异常
     */
    public String toJson(Object src, boolean throwException) {
        if (src == null) {
            return null;
        }
        
        try {
            return src instanceof String ? ((String) src)
                                         : MAPPER.writeValueAsString(src);
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
     * @param bean bean
     * @return 美化的json
     */
    public String toJsonPretty(Object bean) {
        if (bean == null) {
            return null;
        }
        
        try {
            return bean instanceof String ? ((String) bean)
                                          : MAPPER.writerWithDefaultPrettyPrinter()
                                                  .writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    
    /**
     * json转bean
     *
     * @param json           json
     * @param beanType       bean的类型
     * @param throwException 解析失败是否抛出异常
     * @param <T>            类型
     * @return bean实例
     */
    public <T> T fromJson(String json, Class<T> beanType, boolean throwException) {
        if (json == null || beanType == null) {
            return null;
        }
        
        try {
            return MAPPER.readValue(json, beanType);
        } catch (JsonProcessingException e) {
            if (throwException) {
                throw new BaseException("json转换错误");
            }
            log.error("json转换错误");
            return null;
        }
    }
    
    /**
     * json解析为bean
     *
     * @param json     json串
     * @param beanType 类型
     * @param <T>      类型
     * @return bean实例
     */
    public <T> T fromJson(String json, Class<T> beanType) {
        return fromJson(json, beanType, false);
    }
    
    /**
     * json转泛型list
     *
     * @param json           json串
     * @param beanType       类型
     * @param throwException 解析失败是否抛出异常
     * @param <T>            泛型
     * @return 泛型list实例
     */
    public <T> List<T> listFromJson(String json, Class<T> beanType, boolean throwException) {
        if (json == null || beanType == null) {
            return null;
        }
        
        try {
            return MAPPER.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (JsonProcessingException e) {
            if (throwException) {
                throw new BaseException("json转换错误");
            }
            log.error("json转换错误");
            return null;
        }
    }
    
    /**
     * json转泛型list,解析失败不抛异常
     *
     * @param json     json串
     * @param beanType 类型
     * @param <T>      泛型
     * @return 泛型list实例
     */
    public <T> List<T> listFromJson(String json, Class<T> beanType) {
        return listFromJson(json, beanType, false);
    }
}
