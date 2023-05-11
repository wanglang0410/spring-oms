package com.oms.api.service;

import com.oms.api.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MenuService {
    /**
     * 查询某一个用户的权限信息
     *
     * @param userId
     * @return
     */
    public abstract List<String> findUserMenuById(Integer userId);
}
