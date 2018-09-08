package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.user.UserCreateDto;
import com.vankata.cardealer.domain.dto.user.UserDto;
import com.vankata.cardealer.domain.entity.User;
import com.vankata.cardealer.domain.mapper.ModelParser;
import com.vankata.cardealer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelParser modelParser;

    public UserServiceImpl(UserRepository userRepository, ModelParser modelParser) {
        this.userRepository = userRepository;
        this.modelParser = modelParser;
    }

    @Override
    public UserDto register(UserCreateDto userCreateDto) {
        User user = this.modelParser.convert(userCreateDto, User.class);
        this.userRepository.save(user);
        return this.modelParser.convert(user, UserDto.class);
    }

    @Override
    public UserDto findByUserId(String userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return this.modelParser.convert(optionalUser.get(), UserDto.class);
        }

        return null;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user != null) {
            return this.modelParser.convert(user, UserDto.class);
        }

        return null;
    }
}
