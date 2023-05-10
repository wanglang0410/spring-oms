package com.oms.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.oms.api.entity.LoginUser;
import com.oms.api.entity.User;
import com.oms.api.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名查询用户数据
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername, username);
        User user = userMapper.selectOne(lambdaQueryWrapper);

        // 如果查询不到数据，说明用户名或者密码错误，直接抛出异常
        if (user == null) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }

        // 将查询到的对象转换成Spring Security所需要的UserDetails对象
        return new LoginUser(user);
    }
}
