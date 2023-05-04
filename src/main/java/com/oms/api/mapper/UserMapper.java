package com.oms.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oms.api.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
