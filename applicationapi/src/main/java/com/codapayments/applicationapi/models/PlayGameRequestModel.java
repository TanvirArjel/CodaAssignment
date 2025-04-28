package com.codapayments.applicationapi.models;

public class PlayGameRequestModel extends PlayGameBaseModel {

    // This class extends PlayGameBaseModel and can be used to add additional
    // functionality or properties
    // specific to the PlayGameModel if needed.
    // For now, it just inherits everything from PlayGameBaseModel.
    public PlayGameRequestModel(String gameId, String playerId, int points) {
        super(gameId, playerId, points);
    }
}
