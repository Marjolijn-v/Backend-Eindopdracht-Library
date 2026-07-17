package nl.novi.eindopdrachtbackendlibrary.services;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.AuthorEntity;
import nl.novi.eindopdrachtbackendlibrary.exeptions.RecordNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.mappers.AuthorDtoMapper;
import nl.novi.eindopdrachtbackendlibrary.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorDtoMapper authorDtoMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorDtoMapper authorDtoMapper) {
        this.authorRepository = authorRepository;
        this.authorDtoMapper = authorDtoMapper;
    }


    @Transactional
    public List<AuthorResponseDto> findAllAuthors() {
        return authorDtoMapper.mapToDto(authorRepository.findAll());
    }

    @Transactional
    public AuthorResponseDto findAuthorById(Long id) {
        AuthorEntity authorEntity = getAuthorEntity(id);
        return  authorDtoMapper.mapToDto(authorEntity);
    }

    private AuthorEntity getAuthorEntity(Long id) {
        return authorRepository.findById(id).orElseThrow(()-> new RecordNotFoundException("Auteur " + id + " niet gevonden."));
    }

    @Transactional
    public AuthorResponseDto createAuthor(Long id, AuthorRequestDto authorModel) {
        AuthorEntity authorEntity = authorDtoMapper.mapToEntity(authorModel);
        authorEntity = authorRepository.save(authorEntity);
        return authorDtoMapper.mapToDto(authorEntity);
    }

    @Transactional
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto authorModel) {
        AuthorEntity existingAuthorEntity = getAuthorEntity(id);

        existingAuthorEntity.setName(authorModel.getName());
        existingAuthorEntity.setBiography(authorModel.getBiography());

        existingAuthorEntity = authorRepository.save(existingAuthorEntity);
        return authorDtoMapper.mapToDto(existingAuthorEntity);

    }

    @Transactional
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public List<AuthorResponseDto> getAuthorForBook(Long albumId) {
        return authorDtoMapper.mapToDto(authorRepository.findAuthorsByBooksId(albumId));
    }

}
