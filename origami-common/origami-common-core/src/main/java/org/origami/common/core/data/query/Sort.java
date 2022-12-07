package org.origami.common.core.data.query;

import java.util.List;

/**
 * 排序
 *
 * @author origami
 * @date 2022/4/11 23:54
 */
public interface Sort {
    /**
     * 字段排序
     *
     * @return 列表
     */
    List<Order> getSort();
}
