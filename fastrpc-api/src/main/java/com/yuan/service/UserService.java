package com.yuan.service;

import com.yuan.annotation.Retryable;
import com.yuan.pojo.User;

public interface UserService {

    // 查询
    @Retryable
    User getUserByUserId(Integer id);

    // 新增
    @Retryable
    Integer insertUserId(User user);
}
