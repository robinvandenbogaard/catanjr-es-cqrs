# Catan Junior with ES, CQRS, SSE and HTMX

Catan Junior is a game I play often with my kid. Professionally I'm a software engineer who really likes messaging
systems.
This project brings two fun things together!

## Quickstart

- Start an axon server. See `src/main/docker/docker-compose.yaml`
- Run the application from class `CatanjrApplication`
- Make a post request `curl -X POST http://localhost:8080/game` (this will start a fixed new game)
- Goto http://localhost:8080/ to play a game. You control both players.

To reset the game, reset axon by clicking on the reset event store button at http://localhost:8024/ and creating a new game with the post request. I'll add some buttons to make this easier eventually.

## Event Sourcing (ES)

This project sets out to use ES for the games that are being played. Allowing for any future metrics based on events
from the past. Usecases I can't imagine to need right now.
We'll be using Axon Server for the datastore and message broker.

## Command Query Responsibility Segregation (CQRS)

It will be leveraging CQRS to seperate the reads from the writes. A rest endpoint will be available to dispatch commands
to alter a game. And Queries to read the state from a projection.

## Server Side Events (SSE)

Using SSE we can update the client anytime the projection has changed.

## HTMX

We'll be using another rest endpoint to serve html when changes occur to the aggregate.
