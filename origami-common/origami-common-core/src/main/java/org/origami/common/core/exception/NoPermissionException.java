package org.origami.common.core.exception;

import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.exception.base.BaseException;

/**
 * 没有权限异常
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-30 16:36
 */
public class NoPermissionException extends BaseException {

    private static final long serialVersionUID = 5616582759408356958L;

    public NoPermissionException() {
        super(CodeEnum.NO_PERMISSION);
    }


    public NoPermissionException(String message) {
        super(CodeEnum.NO_PERMISSION.getCode(), message);
    }
}
