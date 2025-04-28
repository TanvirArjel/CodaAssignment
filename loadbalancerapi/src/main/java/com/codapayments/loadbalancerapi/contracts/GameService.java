package com.codapayments.loadbalancerapi.contracts;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codapayments.loadbalancerapi.exceptions.NoServerAvailableException;
import com.codapayments.loadbalancerapi.models.PlayGameRequestModel;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public interface GameService {
    /**
     * This method is used to play a game. It takes a PlayGameRequestModel object as
     * input, which contains the necessary information to play the game.
     * 
     * @param playGameModel The model containing the information needed to play the
     *                      game.
     * @return A ResponseEntity containing the result of the game.
     * @throws JsonProcessingException    If there is an error processing JSON data.
     * @throws NoServerAvailableException If no server is available to handle the
     *                                    request.
     * @throws Exception                  If there is any other error during the
     *                                    game play.
     */
    public ResponseEntity<?> playGame(PlayGameRequestModel playGameModel)
            throws JsonProcessingException, NoServerAvailableException;
}