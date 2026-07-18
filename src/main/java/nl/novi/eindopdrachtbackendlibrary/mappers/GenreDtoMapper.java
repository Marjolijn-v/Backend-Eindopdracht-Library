package nl.novi.eindopdrachtbackendlibrary.mappers;

import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.GenreEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreDtoMapper implements DtoMapper<GenreResponseDto, GenreRequestDto, GenreEntity> {

    @Override
    public GenreResponseDto mapToDto(GenreEntity model) {
        var result = new GenreResponseDto();
        result.setId(model.getId());
        result.setName(model.getName());
        result.setDescription(model.getDescription());
        return result;
    }

    @Override
    public List<GenreResponseDto> mapToDto(List<GenreEntity> models) {
        return models.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public GenreEntity mapToEntity(GenreRequestDto genreModel) {
        var result = new GenreEntity();
        result.setName(genreModel.getName());
        result.setDescription(genreModel.getDescription());
        return result;
    }


}
