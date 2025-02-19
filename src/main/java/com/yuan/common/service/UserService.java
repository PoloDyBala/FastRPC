package com.yuan.common.service;

import com.yuan.common.pojo.User;

public interface UserService {
    User getUserByUserId(Integer id);

    Integer insertUserId(User user);
}
