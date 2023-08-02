package org.origami.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.origami.boot.mybatisplus.mapper.BaseMapper;
import org.origami.system.dto.LoginUser;
import org.origami.system.entity.SysUserDO;

/**
 * @author origami
 * @date 2022/1/11 23:00
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    /**
     * 通过用户名获取权限用户
     *
     * @param username 用户名
     * @return {@code LoginUser}
     */
    LoginUser selectLoginUserByUsername(String username);

    /**
     * 通过手机号码获取权限用户
     *
     * @param phone 电话
     * @return {@code LoginUser}
     */
    LoginUser selectLoginUserByPhone(String phone);

    /**
     * 通过手机号码查询
     *
     * @param phone 手机号码
     * @return 用户
     */
    SysUserDO getUserByPhone(@Param("phone") String phone);
}
