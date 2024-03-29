package org.origami.common.jpa.config;

import lombok.NonNull;
import org.origami.common.core.utils.CurrentUserHolder;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 自动设置创建人，更新人
 *
 * @author origami
 * @date 2022/1/3 12:57
 */
public class UserAuditorAware implements AuditorAware<String> {

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(CurrentUserHolder.getUsername());
    }

}
