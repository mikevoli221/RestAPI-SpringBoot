package com.ez2pay.business.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Tag(name = "Authentication API", description = "API to authenticate users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(com.ez2pay.business.user.UserController.class);
    private final UserServices services;

    @Operation(summary = "Sign up a user", description = "Sign up new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid input user information", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PostMapping(value = "/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity signUp(@Parameter(description = "Username and Password") @Valid @RequestBody UserDTO userDTO) {
        services.signUp(userDTO);
        return ResponseEntity.created(null).build();
    }


    @Operation(summary = "Authenticate a user", description = "Authenticate a user by credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated"),
            @ApiResponse(responseCode = "400", description = "Bad Credentials", content = @Content),
    })
    @PostMapping(value = "/signIn")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO signIn(@Parameter(description = "Username and Password. If authenticated, will receive the access token") @Valid @RequestBody UserDTO userDTO) {
        UserDTO userWithToken = services.signIn(userDTO);
        return userWithToken;
    }
}
