package com.oms.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oms.api.entity.User;
import com.oms.api.mapper.UserMapper;
import com.oms.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int saveUser(User user) {
         userMapper.insert(user);
         return user.getId();
    }

    @Override
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> findAllStudent() {
        return null;
    }

    @Override
    public int deleteUserById(int id) {
        return 0;
    }

    @Override
    public int updateUserById(User User) {
        return userMapper.updateById(User);
    }

    @Override
    public List<Map<String, Object>> findUserByFirstName(User user) {
        List<Map<String, Object>> users;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("username", "email");
        wrapper.likeRight("username", user.getUsername());
        users = userMapper.selectMaps(wrapper);
        return users;
    }
}
