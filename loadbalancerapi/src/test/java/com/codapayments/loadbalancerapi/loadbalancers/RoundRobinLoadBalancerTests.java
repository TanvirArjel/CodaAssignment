package com.codapayments.loadbalancerapi.loadbalancers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.codapayments.loadbalancerapi.ServerPool;
import com.codapayments.loadbalancerapi.exceptions.NoServerAvailableException;
import com.codapayments.loadbalancerapi.models.Server;

public class RoundRobinLoadBalancerTests {
    private RoundRobinLoadBalancer loadBalancer;

    @Mock
    private ServerPool serverPool;

    @Mock
    private Server server1, server2, server3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ArrayList<Server> servers = new ArrayList<>();
        servers.add(server1);
        servers.add(server2);
        servers.add(server3);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<?> responseEntity = new ResponseEntity<>(
                "Response from server",
                header,
                HttpStatus.OK);

        doReturn(responseEntity).when(server1).handleRequest(anyString(), any(HttpMethod.class), anyString());

        when(serverPool.getServers()).thenReturn(servers);

        this.loadBalancer = new RoundRobinLoadBalancer(serverPool);
    }

    @Test
    void getNextServer_whenServerPoolHasServers_returnsCorrectServer() throws Exception {
        // Arrange
        // Access the private method using reflection
        Method method = RoundRobinLoadBalancer.class.getDeclaredMethod("getNextServer");
        method.setAccessible(true); // Make the method accessible

        // Act and Assert
        // Getting the next server (the private method is tested indirectly)
        Server server = (Server) method.invoke(loadBalancer);
        assertEquals(server1, server);

        server = (Server) method.invoke(loadBalancer);
        assertEquals(server2, server);

        server = (Server) method.invoke(loadBalancer);
        assertEquals(server3, server);

        server = (Server) method.invoke(loadBalancer);
        assertEquals(server1, server); // Should cycle back to the first server
    }

    @Test
    void getNextServer_whenServerPoolIsEmpty_returnsNull() throws Exception {
        // Arrange
        // Mocking the server pool to return an empty list
        when(serverPool.getServers()).thenReturn(new ArrayList<Server>());

        // Access the private method using reflection
        Method method = RoundRobinLoadBalancer.class.getDeclaredMethod("getNextServer");
        method.setAccessible(true); // Make the method accessible

        // Act and Assert
        // Ensure the server is null when no servers are available
        Server server = (Server) method.invoke(loadBalancer);
        assertNull(server);
    }

    @Test
    void getNextServer_whenNextServerThrowsException_returnsNull() throws Exception {
        // Arrange
        // Mocking server pool to throw an exception
        when(serverPool.getServers()).thenThrow(new RuntimeException("Error getting servers"));
        // Access the private method using reflection
        Method method = RoundRobinLoadBalancer.class.getDeclaredMethod("getNextServer");
        method.setAccessible(true); // Make the method accessible

        // Act and Assert
        Server server = (Server) method.invoke(loadBalancer);
        assertNull(server);
    }

    @Test
    void forwardRequest_whenServerPoolIsEmpty_ThrowsNoServerAvailableException() {
        // Arrange
        // Mocking the server pool to return an empty list
        when(serverPool.getServers()).thenReturn(new ArrayList<Server>());

        // Act and Assert
        // Expecting NoServerAvailableException when no server is available
        assertThrows(NoServerAvailableException.class, () -> {
            loadBalancer.forwardRequest("/test", HttpMethod.GET, "body");
        });
    }

    @Test
    void forwardRequest_whenServerPoolHasServers_ReturnsResponseEntity() throws NoServerAvailableException {

        // Act
        ResponseEntity<?> response = loadBalancer.forwardRequest("/test", HttpMethod.GET, "body");

        // Assert
        assertNotNull(response);
        assertEquals("Response from server", response.getBody());
    }
}
