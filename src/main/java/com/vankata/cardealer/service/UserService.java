package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.user.UserCreateDto;
import com.vankata.cardealer.domain.dto.user.UserDto;

public interface UserService {

    UserDto register(UserCreateDto userCreateDto);

    UserDto findByUserId(String userId);

    UserDto findByUsername(String username);
}
