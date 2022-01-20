package org.origami.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.origami.upm.api.dto.SysUserDTO;
import org.origami.upm.api.entity.SysUser;

/**
 * @author origami
 * @date 2022/1/11 23:00
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    SysUserDTO getUserDTOByUsername(String username);
    
    /**
     * 通过手机号码查询
     *
     * @param phone 手机号码
     * @return 用户
     */
    SysUser getUserByPhone(@Param("phone") String phone);
}
