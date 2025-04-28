package com.codapayments.loadbalancerapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codapayments.loadbalancerapi.contracts.GameService;
import com.codapayments.loadbalancerapi.exceptions.NoServerAvailableException;
import com.codapayments.loadbalancerapi.models.PlayGameRequestModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/games")
@Tag(name = "Game Load Balancing Controller", description = "Controller for managing game load balancing.")
public class GameLoadBalancingController {
    private final GameService gameService;
    private final Logger logger = LoggerFactory.getLogger(GameLoadBalancingController.class);

    public GameLoadBalancingController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Start a new game.", description = "Start a new game with the required information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully started the game."),
            @ApiResponse(responseCode = "400", description = "Bad request - The provided values are not correct.")
    })
    @PostMapping("/play-game")
    public ResponseEntity<?> play(@RequestBody @Valid PlayGameRequestModel playGameModel, BindingResult result)
            throws JsonProcessingException, NoServerAvailableException {
        if (result.hasErrors()) {
            logger.error("Validation errors occurred: " + result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        ResponseEntity<?> response = gameService.playGame(playGameModel);

        return response;
    }
}
