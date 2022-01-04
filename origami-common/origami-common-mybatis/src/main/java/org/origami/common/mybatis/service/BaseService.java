package org.origami.common.mybatis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.origami.common.mybatis.condition.impl.PageQueryCondition;
import org.origami.common.mybatis.condition.impl.QueryCondition;
import org.origami.common.mybatis.utils.WrapperUtil;

import java.util.List;

/**
 * 自定义基类service
 *
 * @author origami
 * @date 2022/1/3 17:00
 */
public interface BaseService<T> extends IService<T> {
    
    @SuppressWarnings("rawtypes")
    default List<T> list(QueryCondition<?> condition) {
        return getBaseMapper().selectList(WrapperUtil.getWrapper(condition.getParams()));
    }
    
    IPage<T> page(PageQueryCondition<?> condition);
    
    long count(QueryCondition<?> condition);
}
