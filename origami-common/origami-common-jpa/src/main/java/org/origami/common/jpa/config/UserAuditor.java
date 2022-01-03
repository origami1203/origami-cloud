package org.origami.common.jpa.config;

import org.origami.common.core.utils.UserContextUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 自动设置创建人，更新人
 *
 * @author origami
 * @date 2022/1/3 12:57
 */
public class UserAuditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(UserContextUtil.getUsername());
    }
}
