package com.oms.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oms.api.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    /**
     * 保存User
     *
     * @param User
     * @return
     */
    int saveUser(User User);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    User findUserById(int id);

    /**
     * 查找全部
     *
     * @return
     */
    List<User> findAllStudent();

    /**
     * 删除一个
     *
     * @param id
     * @return
     */
    int deleteUserById(int id);

    /**
     * 修改一个
     *
     * @param User
     * @return
     */
    int updateUserById(User User);

    /**
     * 根据姓模糊查询
     *
     * @param firstName
     * @return
     */
    List<Map<String, Object>> findUserByFirstName(User firstName);

    Map<String, Object> getList(User user);

    IPage getPageList(User user);
}
