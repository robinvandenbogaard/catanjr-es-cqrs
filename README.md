# Catan Junior with ES, CQRS, SSE and HTMX

Catan Junior is a game I play often with my kid. Professionally I'm a software engineer hoe really likes messaging
systems.
This project brings two fun things together!

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
