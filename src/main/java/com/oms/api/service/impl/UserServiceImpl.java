package com.oms.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oms.api.entity.User;
import com.oms.api.mapper.UserMapper;
import com.oms.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
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

    @Override
    public Map<String, Object> getList(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("username", "email");
        Page<Map<String, Object>> page = new Page<>(1, 2);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("list", mapIPage.getRecords());
        result.put("totalSize", mapIPage.getTotal());
        result.put("totalPage", mapIPage.getPages());
        result.put("size", mapIPage.getSize());
        result.put("page", mapIPage.getCurrent());
        return result;
    }

    @Override
    public IPage getPageList(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("username", "email");
        Page<Map<String, Object>> page = new Page<>(1, 2);
        return userMapper.selectMapsPage(page, wrapper);
    }
}
