package nl.novi.eindopdrachtbackendlibrary.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorResponseDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.user.UserRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.user.UserResponseDto;
import nl.novi.eindopdrachtbackendlibrary.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendlibrary.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UrlHelper urlHelper;

    public UserController(UserService userService, UrlHelper urlHelper) {
        this.userService = userService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<UserResponseDto> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or @userSecurity.isSelf(#id, authentication)")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto user = userService.createUser(userRequestDto);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(user.getId())).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto user = userService.updateUser(id, userRequestDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
