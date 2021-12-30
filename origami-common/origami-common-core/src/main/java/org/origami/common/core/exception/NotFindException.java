package org.origami.common.core.exception;

import org.origami.common.core.base.Code;
import org.origami.common.core.exception.base.BaseException;

/**
 * 未找到数据异常
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-30 13:45
 */
public class NotFindException extends BaseException {

    private static final long serialVersionUID = -683097078247553918L;

    public NotFindException() {
        super(Code.RESOURCE_NOT_FOUND);
    }


    public NotFindException(String message) {
        super(Code.RESOURCE_NOT_FOUND.getCode(), message);
    }
}
