package com.itcast.travel.service;

import com.itcast.travel.domain.ResultInfo;
import com.itcast.travel.domain.User;

public interface UserService {
    Boolean regist(User user);

    Boolean active(String code);

   ResultInfo login(User user);
}
