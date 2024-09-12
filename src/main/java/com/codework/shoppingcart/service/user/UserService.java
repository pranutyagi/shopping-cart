package com.codework.shoppingcart.service.user;

import com.codework.shoppingcart.dto.UserDto;
import com.codework.shoppingcart.exceptions.AlreadyExistsException;
import com.codework.shoppingcart.exceptions.ResourceNotFoundException;
import com.codework.shoppingcart.model.User;
import com.codework.shoppingcart.repository.UserRepository;
import com.codework.shoppingcart.request.CreateUserRequest;
import com.codework.shoppingcart.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId)
    {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new AlreadyExistsException("Oops!" +request.getEmail() +" already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId)
    {
        return userRepository.findById(userId).map(existingUser ->
        {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    @Override
    public void deleteUser(Long userId)
    {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () ->
        {
            throw new ResourceNotFoundException("User not found!");
        });
    }

    @Override
    public UserDto convertUserToDto(User user)
    {
        return modelMapper.map(user, UserDto.class);
    }
}
