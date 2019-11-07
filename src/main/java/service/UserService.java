package service;

import persistence.model.User;
import web.dto.UserDto;

public interface UserService {
    User registerNewUserAccount(UserDto accountDto)
            throws RuntimeException;
}
