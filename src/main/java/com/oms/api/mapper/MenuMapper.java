package com.oms.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oms.api.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuMapper extends BaseMapper<User> {

    @Select("SELECT distinct m.path FROM user u" +
            " left join user_role ur on ur.user_id = u.id" +
            " left join role_menu rm on rm.role_id = ur.role_id" +
            " left join menu m on m.id = rm.menu_id" +
            " WHERE u.id = #{userId}")
    List<String> findUserMenuById(Integer userId);
}
