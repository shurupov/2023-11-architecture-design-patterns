# Endpoint for Receiving Incoming Messages

## Purpose
Write an endpoint for receiving incoming messages by the Game server.
## Description
The game server is an application on which the status of spaceship battles is calculated, that is, all those commands that were considered in previous classes are executed.
The agent is a special application on which the player runs his algorithm for managing his team of tanks.
For the full implementation of the game, it is necessary to ensure two-way data exchange between the Game server and Agents. An Endpoint for receiving incoming messages by the Game Server will be part of such an exchange.
As a result of the DZ execution, a code based on the patterns of the messaging system for receiving incoming messages from Agents will be obtained.
The goal: to apply the skills of building an architecture based on a messaging system.

## Tasks:
- Determine the format of the messages that the Agent sends to the game server.
  
  Note: the rules in all games can be very different from each other, so we need to think about a format that does not depend on the current implementation of specific commands, that is, so that endpoint does not have to be modified every time we develop a new game rule.
- Define the endpoint that receives the incoming message and converts it to the Interpret Command.
  
  Endpoint must identify the game to which the message is addressed, create an InterpretCommand command, and place this command in the command queue of this game.
  The InterpretCommand command gets all the information about the operation to be performed, the parameters, and the object on which this operation will be performed.
- The task of the InetrpretCommand command based on the IoC container is to create a command for the desired object, which corresponds to the order contained in the message and placing this command in the queue of the game commands.
  For example, if the message indicates that an object with id 548 should start moving, then the InterpretCommand result can be described with the following pseudocode
  ```java
  var obj = ioc.resolve("Game objects", "548"); // "548" received from the incoming message
  ioc.resolve("Set the initial speed value", obj, 2); // value 2 received from the args transmitted in the message
  var cmd = ioc.resolve("Moving in a straight line", obj); // The decision that you need to perform a straight line movement is received from the message
  // note that the value "Moving in a straight line" itself cannot be read directly from the message,
  // to avoid injection when the user tries to perform an action that He is not allowed to perform
  ioc.resolve("Command Queue", cmd); // Execute the command that will place the cmd command in the queue of game commands.
  ```
  
