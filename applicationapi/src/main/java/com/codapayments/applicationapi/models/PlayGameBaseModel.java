package com.codapayments.applicationapi.models;

import jakarta.validation.constraints.*;

public abstract class PlayGameBaseModel {
    @NotNull(message = "Game ID cannot be null")
    @NotEmpty(message = "Game ID cannot be empty")
    protected String gameId;

    @NotNull(message = "Player ID cannot be null")
    protected String playerId;

    @Min(value = 1, message = "Points must be greater than 0")
    @Max(value = 1000, message = "Points must be less than or equal to 100")
    protected int points;

    public PlayGameBaseModel(String gameId, String playerId, int points) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.points = points;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getPoints() {
        return points;
    }
}
