package org.origami.common.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * 拓展sql注入器,批量插入
 *
 * @author origami
 * @date 2022/11/5 14:40
 */
public class ExtendSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        InsertBatchSomeColumn insertBatchSomeColumn = new InsertBatchSomeColumn();
        methodList.add(insertBatchSomeColumn);
        return methodList;
    }
}
