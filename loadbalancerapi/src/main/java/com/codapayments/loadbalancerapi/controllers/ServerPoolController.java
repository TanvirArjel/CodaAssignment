package com.codapayments.loadbalancerapi.controllers;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codapayments.loadbalancerapi.ServerPool;
import com.codapayments.loadbalancerapi.models.AddServerRequestModel;
import com.codapayments.loadbalancerapi.models.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/servers")
@Tag(name = "Server Pool Controller", description = "Controller for managing server pool.")
public class ServerPoolController {
    @Autowired
    private ServerPool serverPool;
    private static final Logger logger = LoggerFactory.getLogger(ServerPoolController.class);

    @Operation(summary = "Get the list of active servers.", description = "Returns a list of servers in server pool.")
    @GetMapping()
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(serverPool.getServers());
    }

    @Operation(summary = "Add a new server to the server pool.", description = "Add a new server with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Added"),
            @ApiResponse(responseCode = "400", description = "Bad request - The provided values are not correct.")
    })
    @PostMapping()
    public ResponseEntity<?> addServer(@RequestBody @Valid AddServerRequestModel serverModel,
            BindingResult bindingResult)
            throws Exception {
        if (bindingResult.hasErrors()) {
            logger.error("Validation error while adding server to the pool: " + bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Server server = new Server(serverModel.getName(), serverModel.getIpAddress(), serverModel.getPort(),
                serverModel.getBaseUrl());

        serverPool.addServer(server);
        return ResponseEntity.ok().build();
    }
}
