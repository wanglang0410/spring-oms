package com.oms.api.controller;

import com.oms.api.common.annotation.ResponseResult;
import com.oms.api.entity.User;
import com.oms.api.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserServiceImpl userService;

    @GetMapping("/info")
    public List<Map<String, Object>> user() {
        User user = new User();
        user.setUsername("admin");
        return userService.findUserByFirstName(user);
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
    boolean edit(@RequestBody User user) {
        userService.updateUserById(user);
        return true;
    }
}
