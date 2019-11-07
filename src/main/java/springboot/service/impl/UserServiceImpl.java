package springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.model.User;
import springboot.repository.RoleRepository;
import springboot.repository.UserRepository;
import springboot.service.UserService;
import springboot.web.dto.UserDto;

import java.util.Arrays;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto)
            throws RuntimeException {

        if (loginExists(accountDto.getLogin())) {
            throw new RuntimeException("There is an account with that email address:  + accountDto.getEmail())");
        }
        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setLogin(accountDto.getLogin());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return repository.save(user);
    }

    private boolean loginExists(String login) {
        User user = repository.findByLogin(login);
        return user != null;
    }
}
