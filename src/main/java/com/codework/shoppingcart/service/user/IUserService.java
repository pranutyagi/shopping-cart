package com.codework.shoppingcart.service.user;

import com.codework.shoppingcart.dto.UserDto;
import com.codework.shoppingcart.model.User;
import com.codework.shoppingcart.request.CreateUserRequest;
import com.codework.shoppingcart.request.UserUpdateRequest;

public interface IUserService
{
    User getUserById(Long userId);
    User createUser(CreateUserRequest user);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
