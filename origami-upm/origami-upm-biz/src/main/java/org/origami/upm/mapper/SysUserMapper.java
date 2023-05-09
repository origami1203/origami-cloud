package org.origami.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.origami.upm.api.dto.SysAuthUserDTO;
import org.origami.upm.api.entity.SysUserDO;

/**
 * @author origami
 * @date 2022/1/11 23:00
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    SysAuthUserDTO getUserDTOByUsername(String username);

    /**
     * 通过手机号码查询
     *
     * @param phone 手机号码
     * @return 用户
     */
    SysUserDO getUserByPhone(@Param("phone") String phone);
}
