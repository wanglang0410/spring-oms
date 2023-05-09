package com.oms.api.controller;

import com.oms.api.common.annotation.ResponseResult;
import com.oms.api.entity.request.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ResponseResult
public class LoginController {
    @GetMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest loginRequest) {
        Map<String, String> map = new HashMap<>();
        map.put("token", loginRequest.getUsername());
        return map;
    }
}
