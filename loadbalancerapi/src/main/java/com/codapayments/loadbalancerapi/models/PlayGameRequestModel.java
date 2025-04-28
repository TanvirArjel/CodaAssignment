package com.codapayments.loadbalancerapi.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PlayGameRequestModel {
    @NotNull(message = "Game ID cannot be null")
    @NotEmpty(message = "Game ID cannot be empty")
    @Size(min = 1, max = 50, message = "Game ID must be between 1 and 50 characters")
    private String gameId;

    @NotEmpty(message = "Player ID cannot be empty")
    @NotNull(message = "Player ID cannot be null")
    @Size(min = 1, max = 50, message = "Player ID must be between 1 and 50 characters")
    private String playerId;

    @Min(value = 1, message = "Points must be at least 1")
    @Max(value = 1000, message = "Points can be at most 1000")
    private int points;

    public PlayGameRequestModel(String gameId, String playerId, int points) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.points = points;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
