package nl.novi.eindopdrachtbackendlibrary.mappers;

import nl.novi.eindopdrachtbackendlibrary.dtos.user.UserRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.user.UserResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper implements DtoMapper<UserResponseDto, UserRequestDto, UserEntity>{


    @Override
    public UserResponseDto mapToDto(UserEntity model) {
        var result = new UserResponseDto();
        result.setId(model.getId());
        result.setName(model.getName());
        result.setEmail(model.getEmail());
        result.setPhoneNumber(model.getPhoneNumber());
        result.setDob(model.getDob());
        return result;
    }

    @Override
    public List<UserResponseDto> mapToDto(List<UserEntity> models) {
        return models.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserEntity mapToEntity(UserRequestDto userModel) {
        var result = new UserEntity();
        result.setName(userModel.getName());
        result.setEmail(userModel.getEmail());
        result.setPhoneNumber(userModel.getPhoneNumber());
        result.setDob(userModel.getDob());
        return result;
    }
}
