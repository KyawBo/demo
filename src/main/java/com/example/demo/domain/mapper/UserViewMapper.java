package com.example.demo.domain.mapper;

import com.example.demo.data.model.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.domain.dto.UserView;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    @Autowired
    private UserRepository userRepo;

    public abstract UserView toUserView(User user);

    public abstract List<UserView> toUserView(List<User> users);

    public UserView toUserViewById(Long id) {
        if (id == null) {
            return null;
        }
        return toUserView(userRepo.getById(id));
    }

}
