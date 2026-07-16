package nl.novi.eindopdrachtbackendlibrary.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreResponseDto;
import nl.novi.eindopdrachtbackendlibrary.services.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;


    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreResponseDto>> getAllGenres(){
        List<GenreResponseDto> genres = genreService.findAllGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDto> getGenreById(@PathVariable Long id){
        GenreResponseDto genre = genreService.findGenreById(id);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenreResponseDto> createGenre(@RequestBody @Valid GenreRequestDto genreRequestDto) {
        GenreResponseDto newGenre = genreService.createGenre(genreRequestDto);
        return ResponseEntity.created();
    }
    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDto> updateGenre(@PathVariable Long id, @RequestBody @Valid GenreRequestDto genreRequestDto) {
        GenreResponseDto updatedGenre = genreService.updateGenre(id, genreRequestDto);
        return new ResponseEntity<>(updatedGenre, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
