package nl.novi.eindopdrachtbackendlibrary.mappers;

import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.AuthorEntity;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorDtoMapper implements DtoMapper<AuthorResponseDto, AuthorRequestDto, AuthorEntity> {


    @Override
    public AuthorResponseDto mapToDto(AuthorEntity model) {
        var result = new AuthorResponseDto();
        result.setId(model.getId());
        result.setName(model.getName());
        result.setBiography(model.getBiography());
        return result;
    }

    @Override
    public List<AuthorResponseDto> mapToDto(List<AuthorEntity> models) {
        return models.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public AuthorEntity mapToEntity(AuthorRequestDto authorModel) {
        var result = new AuthorEntity();
        result.setName(authorModel.getName());
        result.setBiography(authorModel.getBiography());
        return result;
    }

}
