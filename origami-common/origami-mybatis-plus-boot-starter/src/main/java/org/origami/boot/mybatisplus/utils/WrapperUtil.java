package org.origami.boot.mybatisplus.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.data.query.Order;
import org.origami.common.core.data.query.Query;
import org.origami.common.core.exception.IllegalParamException;
import org.origami.common.core.utils.Assert;

/**
 * 根据实体类获取QueryWrapper
 *
 * @author origami
 * @date 2022/1/3 17:37
 */
public abstract class WrapperUtil {

    public static <T> QueryWrapper<T> getWrapper(Query<T> condition) {
        return Optional.ofNullable(condition).map(query -> {
            Map<String, Object> conditionMap = query.getConditionMap();
            QueryWrapper<T> queryWrapper = new QueryWrapper<>();
            conditionMap.entrySet().stream().filter(entry -> Objects.nonNull(entry.getValue())).forEach(entry -> {
                String column = StrUtil.toUnderlineCase(entry.getKey());
                Object fieldValue = entry.getValue();
                if (fieldValue instanceof String) {
                    queryWrapper.like(column, fieldValue.toString());
                } else {
                    queryWrapper.eq(column, fieldValue);
                }
            });

            List<OrderItem> orderList = getOrderList(condition.getSort());

            orderList.stream().peek(it -> {
                String column = it.getColumn();
                if (!conditionMap.containsKey(column)) {
                    throw new IllegalParamException(CodeEnum.REQUEST_PARAM_ERROR.getCode(),
                        "属性:[" + it.getColumn() + "]不在排序范围内");
                }
            }).forEach(orderItem -> queryWrapper.orderBy(true, orderItem.isAsc(),
                StrUtil.toUnderlineCase(orderItem.getColumn())));

            return queryWrapper;
        }).orElse(Wrappers.emptyWrapper());

    }

    /**
     * 获取排序列表
     *
     * @param orderList 排序列表
     * @return {@code List<OrderItem>}
     */
    public static List<OrderItem> getOrderList(List<Order> orderList) {
        if (orderList.isEmpty()) {
            return Collections.emptyList();
        }
        return orderList.stream().peek(it -> Assert.nonNull(it.getProperty(), "排序字段不能为null"))
            .map(it -> new OrderItem(it.getProperty(), it.getDirection().isAscending())).collect(Collectors.toList());

    }

}
