package org.origami.upm.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.origami.upm.api.dto.SysUserDTO;
import org.origami.upm.api.entity.SysUser;

/**
 * @author origami
 * @date 2022/1/11 23:00
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    SysUserDTO getUserDTOByUsername(String username);
}
