package com.codapayments.loadbalancerapi.services;

import org.slf4j.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codapayments.loadbalancerapi.contracts.GameService;
import com.codapayments.loadbalancerapi.contracts.LoadBalancer;
import com.codapayments.loadbalancerapi.exceptions.NoServerAvailableException;
import com.codapayments.loadbalancerapi.models.PlayGameRequestModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GameServiceImpl implements GameService {
    private LoadBalancer loadBalancer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    public GameServiceImpl(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public ResponseEntity<?> playGame(PlayGameRequestModel playGameModel)
            throws JsonProcessingException, NoServerAvailableException {
        try {
            // Convert to JSON string
            String jsonString = mapper.writeValueAsString(playGameModel);
            return loadBalancer.forwardRequest("/api/v1/games/play-game", HttpMethod.POST, jsonString);
        } catch (Exception exception) {
            logger.error("Play game failed due to: " + exception.getMessage(), exception);
            throw exception;
        }
    }
}
