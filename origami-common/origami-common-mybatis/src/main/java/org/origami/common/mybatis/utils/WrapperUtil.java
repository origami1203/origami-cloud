package org.origami.common.mybatis.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;

/**
 * @author origami
 * @date 2022/1/3 17:37
 */
@UtilityClass
public class WrapperUtil {
    
    public QueryWrapper getWrapper(Map<String, Object> paramsMap) {
        return Optional.ofNullable(paramsMap)
                       .map(param -> {
                           QueryWrapper queryWrapper = new QueryWrapper();
                           param.forEach((fieldName, obj) -> {
            
                               if (obj instanceof String) {
                                   queryWrapper.likeLeft(fieldName, obj.toString());
                               }
            
                               queryWrapper.eq(fieldName, obj);
                           });
                           return queryWrapper;
                       })
                       .orElse(new QueryWrapper());
    }
    
}
