package com.oms.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oms.api.common.annotation.ResponseResult;
import com.oms.api.entity.User;
import com.oms.api.exception.BizException;
import com.oms.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@ResponseResult
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('/customer')")
    public Map<String, Object> user() {
        User user = new User();
        user.setUsername("admin");
        return userService.getList(user);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('/customer')")
    public IPage list() {
        User user = new User();
        user.setUsername("admin");
        return userService.getPageList(user);
    }

    /**
     * @param user 实体User
     */
    @PostMapping("/save")
    public int save(@Valid @RequestBody User user, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return userService.saveUser(user);
    }

    @PostMapping("/edit")
    boolean edit(@Valid @RequestBody User user) {
        if (!(userService.updateUserById(user) > 0)) {
            throw new BizException("更新失败");
        }
        return true;
    }
}
