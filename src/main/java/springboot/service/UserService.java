package springboot.service;

import springboot.model.User;
import springboot.web.dto.UserDto;

public interface UserService {
    User registerNewUserAccount(UserDto accountDto)
            throws RuntimeException;
}
