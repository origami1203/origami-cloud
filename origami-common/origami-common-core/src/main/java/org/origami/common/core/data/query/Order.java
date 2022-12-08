package org.origami.common.core.data.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author origami
 * @date 2022/4/12 0:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = -1569885036804250205L;
    
    // 字段名
    private String property;
    // 排序方向
    private Direction direction;
    
    public static enum Direction {
        /**
         * 升序
         */
        ASC,
        /**
         * 降序
         */
        DESC;
        
        /**
         * 是否升序
         *
         * @return boolean
         */
        public boolean isAscending() {
            return this.equals(ASC);
        }
    
        /**
         * 是否降序
         *
         * @return boolean
         */
        public boolean isDescending() {
            return this.equals(DESC);
        }
    }
    
}
