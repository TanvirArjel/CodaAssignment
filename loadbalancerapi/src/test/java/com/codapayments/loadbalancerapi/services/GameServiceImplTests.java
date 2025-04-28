package com.codapayments.loadbalancerapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codapayments.loadbalancerapi.contracts.LoadBalancer;
import com.codapayments.loadbalancerapi.exceptions.NoServerAvailableException;
import com.codapayments.loadbalancerapi.models.PlayGameRequestModel;

public class GameServiceImplTests {
    private LoadBalancer loadBalancer;
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        loadBalancer = mock(LoadBalancer.class);
        gameService = new GameServiceImpl(loadBalancer);
    }

    @Test
    void playGame_successfulRequest_returnsResponseEntity() throws Exception {
        // Arrange
        PlayGameRequestModel requestModel = new PlayGameRequestModel("123345", "12234", 100);
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Game played successfully");

        doReturn(mockResponse).when(loadBalancer).forwardRequest(
                eq("/api/v1/games/play-game"),
                eq(HttpMethod.POST),
                anyString());

        // Act
        ResponseEntity<?> response = gameService.playGame(requestModel);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Game played successfully", response.getBody());
        verify(loadBalancer, times(1)).forwardRequest(anyString(), eq(HttpMethod.POST), anyString());
    }

    @Test
    void playGame_loadBalancerThrowsException_propagatesException() throws Exception {
        // Arrange
        PlayGameRequestModel requestModel = new PlayGameRequestModel("123345", "12234", 100);

        when(loadBalancer.forwardRequest(
                anyString(),
                any(HttpMethod.class),
                anyString())).thenThrow(new NoServerAvailableException("No servers available"));

        // Act + Assert
        NoServerAvailableException exception = assertThrows(NoServerAvailableException.class, () -> {
            gameService.playGame(requestModel);
        });

        assertEquals("No servers available", exception.getMessage());
        verify(loadBalancer, times(1)).forwardRequest(anyString(), any(HttpMethod.class), anyString());
    }
}
