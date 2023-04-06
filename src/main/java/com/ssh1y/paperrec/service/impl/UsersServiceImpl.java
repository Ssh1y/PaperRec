package com.ssh1y.paperrec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssh1y.paperrec.entity.Users;
import com.ssh1y.paperrec.mapper.UsersMapper;
import com.ssh1y.paperrec.service.UsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenweihong
 * @description 针对表【Users】的数据库操作Service实现
 * @createDate 2023-03-17 22:42:58
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public Users selectUserByUserNameAndPwd(String username, String password) {
        return usersMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, username).eq(Users::getPassword, password));
    }

    @Override
    public Users selectUserByUserId(Long userId) {
        return usersMapper.selectById(userId);
    }

}




