package com.ssh1y.paperrec.service;

import com.ssh1y.paperrec.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenweihong
* @description 针对表【Users】的数据库操作Service
* @createDate 2023-03-17 22:42:58
*/
public interface UsersService extends IService<Users> {

    /**
     * 根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    Users selectUserByUserNameAndPwd(String username, String password);

    /**
     * 根据用户id查询用户
     * @param userId 用户id
     * @return 用户
     */
    Users selectUserByUserId(Long userId);
}
