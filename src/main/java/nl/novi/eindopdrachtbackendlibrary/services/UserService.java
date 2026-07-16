package nl.novi.eindopdrachtbackendlibrary.services;

import nl.novi.eindopdrachtbackendlibrary.mappers.UserDtoMapper;
import nl.novi.eindopdrachtbackendlibrary.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;


    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }


}
