package com.codapayments.applicationapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codapayments.applicationapi.models.PlayGameRequestModel;
import com.codapayments.applicationapi.models.PlayGameResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/games")
@Tag(name = "Game Controller", description = "Controller for managing game-related operations.")
public class GameController {
    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Operation(summary = "Start a new game.", description = "Start a new game with the required information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully started the game."),
            @ApiResponse(responseCode = "400", description = "Bad request - The provided values are not correct.")
    })
    @PostMapping("/play-game")
    public ResponseEntity<?> play(@RequestBody @Valid PlayGameRequestModel playGameModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors: " + bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        PlayGameResponseModel response = new PlayGameResponseModel(
                playGameModel.getGameId(),
                playGameModel.getPlayerId(),
                playGameModel.getPoints());

        return ResponseEntity.ok(response);
    }
}
