package com.oms.api.controller;

import com.oms.api.common.annotation.ResponseResult;
import com.oms.api.entity.request.LoginRequest;
import com.oms.api.service.impl.UserLoginImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ResponseResult
public class LoginController {

    @Autowired
    private UserLoginImpl loginService;

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return loginService.login(loginRequest);
    }
}
