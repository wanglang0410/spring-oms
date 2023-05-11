package com.oms.api.service.impl;

import com.oms.api.mapper.MenuMapper;
import com.oms.api.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> findUserMenuById(Integer userId) {
        return menuMapper.findUserMenuById(userId);
    }
}
