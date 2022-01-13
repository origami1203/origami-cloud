package org.origami.upm.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.origami.upm.api.entity.SysRole;
import org.origami.upm.api.mapper.SysRoleMapper;
import org.origami.upm.api.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author origami
 * @date 2022/1/13 22:05
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements
                                                                            SysRoleService {
    @Override
    public List<SysRole> getListByUserId(Long userId) {
        return baseMapper.selectListByUserId(userId);
    }
}
