package com.example.demo;

import com.example.demo.domain.dto.CreateUserRequest;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final List<String> usernames = List.of(
            "kbbyt",
            "kbb",
            "leo"
    );
    private final List<String> fullNames = List.of(
            "Kyaw Bo Bo Ye Tun",
            "Kyaw Bo Bo",
            "Leo"
    );

    private final String password = "Test12345_";

    private final UserServiceImpl userService;

    public DatabaseInitializer(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        for (int i = 0; i < usernames.size(); ++i) {
            CreateUserRequest request = new CreateUserRequest();
            request.setUsername(usernames.get(i));
            request.setFullName(fullNames.get(i));
            request.setPassword(password);
            request.setRePassword(password);

            userService.create(request);
        }
    }

}
