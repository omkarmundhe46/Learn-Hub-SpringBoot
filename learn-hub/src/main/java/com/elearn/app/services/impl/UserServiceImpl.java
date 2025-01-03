package com.elearn.app.services.impl;

import com.elearn.app.config.AppConstants;
import com.elearn.app.dtos.UserDto;
import com.elearn.app.entities.User;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.UserRepo;
import com.elearn.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto create(UserDto dto) {

        User user = modelMapper.map(dto, User.class);


        user.setUserId(UUID.randomUUID().toString());

        user.setCreateAt(new Date());

        user.setEmailVarified(false);

        user.setSmsVerified(false);

        user.setProfilePath(AppConstants.DEFAULT_PROFILE_PIC_PATH);

        User savedUuser = userRepo.save(user);

        return modelMapper.map(savedUuser, UserDto.class);
    }

    @Override
    public UserDto geById(String userId) {
        return modelMapper.map(userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with give email id ")), UserDto.class);
    }
}