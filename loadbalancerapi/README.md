# Load Balancer API (Routing API)

## Introduction
This is project is actually a load balancer api (Routing API) project where we have implemented round robin approach of load balancing. In future we can add ip hashing, least connection etc method of load balancing if business requires. So this API is easily extensible.

## Major Components
1. #### Load balancing endpoint
    that will receive request and forward to the appropriate application server using round robin load balancer.
2. #### Server endpoints
    that will be used to register application servers automatically to the load balancing (Routing) server pool.
3. ### Server class
    that actually contains the attributes and behavior of the server.
3. #### Server Pool
    is the collection of the healthy servers and server pool behavior.
4. ### Server health check 
    monitoring service that continuously monitor the health of servers in the server and remove if any server found unhealthy and not responding.
5. #### Round Robin load balancer
    that that actually helps to find a server to handle current request in round robin manner.

## How to run
Before the running the application api, load balancer api should be run first so that application api can make request to the load balancer api to be registered to the server pool.

## API Documentation
Visit http://localhost:8081/ after running the application. It will direct to the swagger documentation.
