package com.example.demo.domain.mapper;

import com.example.demo.data.model.User;
import com.example.demo.domain.dto.CreateUserRequest;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class UserEditMapper {

    public abstract User create(CreateUserRequest request);

}
