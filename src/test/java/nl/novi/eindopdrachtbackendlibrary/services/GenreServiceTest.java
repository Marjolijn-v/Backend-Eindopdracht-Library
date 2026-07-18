package nl.novi.eindopdrachtbackendlibrary.services;

import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.GenreEntity;
import nl.novi.eindopdrachtbackendlibrary.exeptions.RecordNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.mappers.GenreDtoMapper;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreDtoMapper genreDtoMapper;

    @InjectMocks
    private GenreService genreService;

    @Test
    void findAllGenres_shouldReturnListOfGenreResponseDtos() {

        GenreEntity genreEntity1 = new GenreEntity();
        genreEntity1.setName("test 1 name");
        genreEntity1.setDescription("test 1 description");

        GenreEntity genreEntity2 = new GenreEntity();
        genreEntity2.setName("test 2 name");
        genreEntity2.setDescription("test 2 description");

        GenreResponseDto genreResponseDto1 = new GenreResponseDto();
        genreResponseDto1.setName("test dto 1 name");
        genreResponseDto1.setDescription("test dto 1 description");

        GenreResponseDto genreResponseDto2 = new GenreResponseDto();
        genreResponseDto2.setName("test dto 2 name");
        genreResponseDto2.setDescription("test dto 2 description");

        when(genreRepository.findAll()).thenReturn(List.of(genreEntity1, genreEntity2));
        when(genreDtoMapper.mapToDto(List.of(genreEntity1, genreEntity2)))
                .thenReturn(List.of(genreResponseDto1, genreResponseDto2));

        List<GenreResponseDto> result = genreService.findAllGenres();

        assertEquals(2, result.size());
        assertEquals(genreResponseDto1, result.get(0));
        assertEquals(genreResponseDto2, result.get(1));
        verify(genreRepository, times(1)).findAll();
        verify(genreDtoMapper, times(1)).mapToDto(List.of(genreEntity1, genreEntity2));
    }

    @Test
    void findGenreById_shouldReturnGenreResponseDto_whenGenreExists() {
        Long id = 1L;
        GenreResponseDto genreResponseDto = new GenreResponseDto();
        genreResponseDto.setName("New Name");
        genreResponseDto.setDescription("New description");

        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName("old name");
        genreEntity.setDescription("old description");

        when(genreRepository.findById(id)).thenReturn(Optional.of(genreEntity));
        when(genreDtoMapper.mapToDto(genreEntity)).thenReturn(genreResponseDto);

        GenreResponseDto result = genreService.findGenreById(id);

        assertEquals(genreResponseDto, result);
        verify(genreRepository, times(1)).findById(id);
        verify(genreDtoMapper, times(1)).mapToDto(genreEntity);
    }

    @Test
    void findGenreById_shouldThrowRecordNotFoundException_whenGenreDoesNotExist() {
        Long id = 1L;

        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            genreService.findGenreById(id);
        });

        assertEquals("Genre " + id + " niet gevonden", exception.getMessage());
        verify(genreRepository, times(1)).findById(id);
    }

    @Test
    void createGenre_shouldReturnCreatedGenreResponseDto() {
        GenreResponseDto genreResponseDto = new GenreResponseDto();
        genreResponseDto.setName("Name 1");
        genreResponseDto.setDescription("Description 1");

        GenreRequestDto genreRequestDto = new GenreRequestDto();
        genreRequestDto.setName("Name 1");
        genreRequestDto.setDescription("Description 1");

        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName("Old Name");
        genreEntity.setDescription("Old Description");

        when(genreDtoMapper.mapToEntity(genreRequestDto)).thenReturn(genreEntity);
        when(genreRepository.save(genreEntity)).thenReturn(genreEntity);
        when(genreDtoMapper.mapToDto(genreEntity)).thenReturn(genreResponseDto);

        GenreResponseDto result = genreService.createGenre(genreRequestDto);

        assertEquals(genreResponseDto, result);
        verify(genreDtoMapper, times(1)).mapToEntity(genreRequestDto);
        verify(genreRepository, times(1)).save(genreEntity);
        verify(genreDtoMapper, times(1)).mapToDto(genreEntity);

    }

    @Test
    void updateGenre_shouldReturnUpdatedGenreResponseDto_WhenGenreExists() {
        Long id = 1L;
        GenreResponseDto genreResponseDto = new GenreResponseDto();
        genreResponseDto.setName("Updated Name");
        genreResponseDto.setDescription("Updated Description");

        GenreRequestDto genreRequestDto = new GenreRequestDto();
        genreRequestDto.setName("Updated Name");
        genreRequestDto.setDescription("Updated Description");

        GenreEntity existingGenreEntity = new GenreEntity();
        existingGenreEntity.setName("Old Name");
        existingGenreEntity.setDescription("Old Description");

        when(genreRepository.findById(id)).thenReturn(Optional.of(existingGenreEntity));
        when(genreRepository.save(existingGenreEntity)).thenReturn(existingGenreEntity);
        when(genreDtoMapper.mapToDto(existingGenreEntity)).thenReturn(genreResponseDto);

        GenreResponseDto result = genreService.updateGenre(id, genreRequestDto);

        assertEquals(genreResponseDto, result);
        assertEquals("Updated Name", existingGenreEntity.getName());
        assertEquals("Updated Description", existingGenreEntity.getDescription());
        verify(genreRepository, times(1)).findById(id);
        verify(genreRepository, times(1)).save(existingGenreEntity);
        verify(genreDtoMapper, times(1)).mapToDto(existingGenreEntity);

    }

    @Test
    void updateGenre_shouldThrowRecordNotFoundException_whenGenreDoesNotExist() {
        Long id = 1L;
        GenreRequestDto genreRequestDto = new GenreRequestDto();
        genreRequestDto.setName("Updated Name");
        genreRequestDto.setDescription("Updated Description");

        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            genreService.updateGenre(id, genreRequestDto);
        });

        assertEquals("Genre " + id + " niet gevonden", exception.getMessage());
        verify(genreRepository, times(1)).findById(id);
    }

    @Test
    void deleteGenre_withoutBooks_shouldCallDeleteById() {
        Long id = 1L;
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setId(id);
        genreEntity.setName("Book Genre");

        when(genreRepository.findById(id)).thenReturn(Optional.of(genreEntity));
        when(bookRepository.findByGenre(genreEntity)).thenReturn(new ArrayList<>());

        genreService.deleteGenre(id);

        verify(genreRepository, times(1)).deleteById(id);
        verify(bookRepository, times(1)).findByGenre_Id(id);
    }

    @Test
    void deleteGenre_withBooks_shouldCallBookSave() {
        Long id = 1L;
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName("Book Genre");
        genreEntity.setDescription("Book Genre Description");

        BookEntity book1 = new BookEntity();
        BookEntity book2 = new BookEntity();
        BookEntity book3 = new BookEntity();

        List<BookEntity> books = List.of(book1, book2, book3);
        books.forEach(bookEntity -> bookEntity.setGenre(genreEntity));

        when(genreRepository.findById(id)).thenReturn(Optional.of(genreEntity));
        when(bookRepository.findByGenre(genreEntity)).thenReturn(books);
        when(bookRepository.saveAll(books)).thenReturn(books);

        genreService.deleteGenre(id);

        verify(genreRepository, times(1)).deleteById(id);
        verify(bookRepository, times(3)).save(any());
    }
}