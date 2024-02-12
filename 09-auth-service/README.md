# Authorization Microservice

## Purpose
Create a microservice User authorization
## Description
It is supposed to implement an authorization microservice using a jwt token.
The algorithm of interaction between the authorization service and the Game server is as follows:

- One of the users organizes a tank battle and determines the list of participants (there may be more than 2 of them).
  A request is sent to the authorization server stating that a tank battle is being organized and a list of its participants is being sent. The server returns the tank battle id in response.
- An authenticated user sends a request for the issuance of a jwt token, which authorizes the right of this user to participate in a tank battle.
  To do this, he must specify the tank battle id in the request.
  If the user was listed in the list of participants in the tank battle, then he gives the user a jwt token, which contains the game id.
- When sending messages to the Game Server, the user attaches the jwt token issued to the messages, and the server, upon receiving the message, makes sure that the token was issued by the authorization server (the hash of the jwt token will be checked) and verifies that the user has requested an operation in the game in which he can perform this operation.
