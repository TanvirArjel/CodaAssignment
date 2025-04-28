package com.codapayments.applicationapi.models;

public class PlayGameResponseModel extends PlayGameBaseModel {
    // This class extends PlayGameBaseModel and adds additional functionality or
    // properties
    // specific to the response model for playing a game.
    public PlayGameResponseModel(String gameId, String playerId, int points) {
        super(gameId, playerId, points);
    }
}
