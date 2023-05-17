package com.oms.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.oms.api.entity.LoginUser;
import com.oms.api.entity.request.LoginRequest;
import com.oms.api.exception.BizException;
import com.oms.api.utils.JwtTokenUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Map<String, String> login(LoginRequest loginRequest) {
        // 创建Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        // 调用AuthenticationManager的authenticate方法进行认证 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        redisTemplate.boundValueOps("login_user:" + userId).set(JSON.toJSONString(loginUser));
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        Map<String, String> result = new HashMap<>();
        result.put("token", jwtTokenUtils.createToken(claims));
        return result;
    }

    public boolean logout(HttpServletRequest request) {
        String token = jwtTokenUtils.getTokenFromRequest(request);
        String userId = jwtTokenUtils.getClaims(token).get("userId").toString();
        redisTemplate.delete("login_user:" + userId);
        return true;
    }
}
