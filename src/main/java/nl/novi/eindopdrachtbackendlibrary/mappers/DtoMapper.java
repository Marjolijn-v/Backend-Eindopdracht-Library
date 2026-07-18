package nl.novi.eindopdrachtbackendlibrary.mappers;

import nl.novi.eindopdrachtbackendlibrary.entities.BaseEntity;

import java.util.List;

public interface DtoMapper <RESPONSE, REQUEST, ENTITY extends BaseEntity>{
    RESPONSE mapToDto(ENTITY model);

    List<RESPONSE> mapToDto(List<ENTITY> models);

    ENTITY mapToEntity(REQUEST genreModel);
}
