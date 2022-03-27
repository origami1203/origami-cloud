package org.origami.common.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.exception.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private static final String DEFAULT_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER
                // 日期格式
                .setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_PATTERN))
                // 序列化,字段对应的值为null也显示出来
                .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                // json中有实体类没有的字段时,不报错
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 忽略空bean转json错误
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 若只有getName()方法,但没有name属性和set方法,不显示name字段
        // .configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS, true);

        // LocalDateTime转换,javaTimeModule中已经添加,但格式是iso格式,我们添加自定义格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT_PATTERN);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT_PATTERN);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class,
                                       new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDateTime.class,
                                     new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
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
        return MAPPER.copy();
    }


    /**
     * 对象转换为json串
     *
     * @param src bean
     * @return 对象不能为空，解析失败抛异常
     */
    public String toJson(Object src) {

        Assert.nonNull(src);

        try {
            return src instanceof String ? ((String) src)
                    : MAPPER.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            log.error("json解析失败");
            throw new ParseException("json解析失败：" + e.getMessage());
        }
    }

    /**
     * 返回美化json
     *
     * @param bean bean
     * @return 美化的json
     */
    public String toJsonPretty(Object bean) {

        Assert.nonNull(bean);

        try {
            return bean instanceof String ? ((String) bean)
                    : MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            log.error("json解析失败");
            throw new ParseException("json解析失败：" + e.getMessage());
        }
    }

    /**
     * json转bean
     *
     * @param json     json
     * @param beanType bean的类型
     * @param <T>      类型
     * @return bean实例
     */
    public <T> T fromJson(String json, Class<T> beanType) {

        Assert.nonNull(json, "json字符串不能为null");
        Assert.nonNull(beanType, "bean类型不能为null");

        try {
            return MAPPER.readValue(json, beanType);
        } catch (JsonProcessingException e) {
            log.error("json解析失败");
            throw new ParseException("json解析失败：" + e.getMessage());
        }
    }

    /**
     * json转泛型list
     *
     * @param json     json串
     * @param beanType 类型
     * @param <T>      泛型
     * @return 泛型list实例
     */
    public <T> List<T> listFromJson(String json, Class<T> beanType) {

        Assert.nonNull(json, "json字符串不能为null");
        Assert.nonNull(beanType, "bean类型不能为null");

        JavaType javaType =
                MAPPER.getTypeFactory().constructCollectionType(List.class, beanType);

        try {
            return MAPPER.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error("json解析失败");
            throw new ParseException("json解析失败：" + e.getMessage());
        }


    }


    /**
     * json转泛型类
     *
     * @param json        json串
     * @param beanTypeRef 泛型类型
     * @param <T>         泛型
     * @return 泛型list实例
     */
    public <T> T genericsFromJson(String json, TypeReference<T> beanTypeRef) {

        Assert.nonNull(json, "json字符串不能为null");
        Assert.nonNull(beanTypeRef, "bean泛型类型不能为null");

        try {
            return MAPPER.readValue(json, beanTypeRef);
        } catch (JsonProcessingException e) {
            log.error("json解析失败");
            throw new ParseException("json解析失败：" + e.getMessage());
        }
    }

}
