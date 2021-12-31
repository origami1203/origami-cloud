package org.origami.upm.controller;

import org.origami.common.log.annotation.SysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author origami
 * @date 2021/12/31 12:17
 */
@RestController
@RequestMapping("/test")
public class TestController {
    
    @SysLog
    @GetMapping("/test1")
    public String test() {
        return "success";
    }
}
