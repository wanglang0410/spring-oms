package com.oms.api.service.impl;

import com.oms.api.entity.LoginUser;
import com.oms.api.entity.request.LoginRequest;
import com.oms.api.exception.BizException;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserLoginImpl {

    @Resource
    private AuthenticationManager authenticationManager;

    public Map<String, String> login(LoginRequest loginRequest) {
        // 创建Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        // 调用AuthenticationManager的authenticate方法进行认证 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        Map<String, String> result = new HashMap<>();
        return result;
    }
}
