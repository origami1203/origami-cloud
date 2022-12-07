package org.origami.common.core.exception;

import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.exception.base.BaseException;

/**
 * 未找到数据异常
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-30 13:45
 */
public class NotFoundException extends BaseException {

    private static final long serialVersionUID = -683097078247553918L;

    public NotFoundException() {
        super(CodeEnum.RESOURCE_NOT_FOUND);
    }


    public NotFoundException(String message) {
        super(CodeEnum.RESOURCE_NOT_FOUND.getCode(), message);
    }
}
