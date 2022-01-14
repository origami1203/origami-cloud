package org.origami.upm.controller;

import lombok.RequiredArgsConstructor;
import org.origami.common.core.base.Result;
import org.origami.common.core.exception.base.BaseException;
import org.origami.upm.api.entity.SysUser;
import org.origami.upm.api.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-14 11:00
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @PostMapping("")
    public Result<SysUser> add(@RequestBody SysUser user) {
        return Optional.ofNullable(user)
                .map(s -> {
                    sysUserService.save(s);
                    return s;
                })
                .map(Result::ok)
                .orElseThrow(BaseException::new);

    }
}
