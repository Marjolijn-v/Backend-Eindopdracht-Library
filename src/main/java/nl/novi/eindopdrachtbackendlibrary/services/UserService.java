package nl.novi.eindopdrachtbackendlibrary.services;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackendlibrary.dtos.user.UserRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.user.UserResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.UserEntity;
import nl.novi.eindopdrachtbackendlibrary.exeptions.RecordNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.mappers.UserDtoMapper;
import nl.novi.eindopdrachtbackendlibrary.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;


    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public List<UserResponseDto> findAllUsers() {
        return userDtoMapper.mapToDto(userRepository.findAll());
    }

    @Transactional
    public UserResponseDto findUserById(Long id) {
        UserEntity userEntity = getUserEntity(id);
        return userDtoMapper.mapToDto(userEntity);
    }

    private UserEntity getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new RecordNotFoundException("Gebruiker " + id + " niet gevonden."));
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto userModel) {
        UserEntity userEntity = userDtoMapper.mapToEntity(userModel);
        userEntity = userRepository.save(userEntity);
        return userDtoMapper.mapToDto(userEntity);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userModel) {
        UserEntity existingUserEntity = getUserEntity(id);

        existingUserEntity.setName(userModel.getName());
        existingUserEntity.setEmail(userModel.getEmail());
        existingUserEntity.setPhoneNumber(userModel.getPhoneNumber());
        existingUserEntity.setDob(userModel.getDob());

        return userDtoMapper.mapToDto(existingUserEntity);

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
