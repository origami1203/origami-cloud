package org.origami.system.service;

import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageModel;
import org.origami.boot.mybatisplus.service.BaseService;
import org.origami.system.dto.LoginUser;
import org.origami.system.dto.SysUserDTO;
import org.origami.system.dto.CreateUserRequest;
import org.origami.system.entity.SysUserDO;

/**
 * @author origami
 * @date 2022/1/11 23:07
 */
public interface SysUserService extends BaseService<SysUserDO> {

    /**
     * 保存
     *
     * @param form 表单
     * @return {@code Boolean}
     */
    Boolean save(CreateUserRequest form);

    /**
     * 通过id查询
     *
     * @param id id
     * @return {@code SysUserDTO}
     */
    SysUserDTO findById(Long id);

    /**
     * 更新用户信息
     *
     * @param editUser 编辑用户
     * @return {@code Boolean}
     */
    Boolean updateUserDetail(SysUserDTO editUser);

    /**
     * 分页查询
     *
     * @param userDTO 用户dto
     * @return {@code IPage<SysUserDTO>}
     */
    IPage<SysUserDTO> pageByCondition(PageModel<SysUserDTO> userDTO);

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return {@code SysUserDTO}
     */
    SysUserDTO getUserByUsername(String username);

    /**
     * 通过手机号码查询
     *
     * @param phone 电话
     * @return {@code SysUserDTO}
     */
    SysUserDTO getUserByPhone(String phone);

    /**
     * 通过用户名获取用户信息（密码，角色）
     *
     * @param username 用户名
     * @return {@code LoginUser}
     */
    LoginUser getLoginUserByUsername(String username);

    /**
     * 通过email获取认证用户
     *
     * @param email 电子邮件
     * @return {@code LoginUser}
     */
    LoginUser getLoginUserByEmail(String email);

    /**
     * 通过电话号码获取认证用户
     *
     * @param phone 电话
     * @return {@code LoginUser}
     */
    LoginUser getLoginUserByPhone(String phone);

    /**
     * 更新用户密码
     *
     * @param userId 用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updateUserPassword(Long userId, String oldPassword, String newPassword);

}
