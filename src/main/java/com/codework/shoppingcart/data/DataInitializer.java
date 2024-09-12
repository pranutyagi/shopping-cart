package com.codework.shoppingcart.data;

import com.codework.shoppingcart.model.User;
import com.codework.shoppingcart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent>
{
    private final UserRepository userRepository;

    private void createDefaultUserIfNotExists()
    {
        for (int i = 0; i<=5 ; i++)
        {
            String defaultEmail = "user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail))
            {
                continue;
            }
            User user = new User();
            user.setFirstName("The user");
            user.setLastName("user"+i);
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created successfully.");

        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();
    }

//    @Override
//    public boolean supportsAsyncExecution() {
//        return ApplicationListener.super.supportsAsyncExecution();
//    }
}
