# Interpreter

## Purpose
Learn how to use the interpreter

It is necessary to teach game objects to react to the actions of the players
## Description
Let's assume that the order has the following form:
```json
{
  "ID": "ID of the object to which the order is addressed",
  "action": "action to be performed",
  // some specific parameters for this order
}
```
or
```
player {playerId} accelerate 1 ship shipId
```
This is a command to accelerate ship with id `shipId`.

It is necessary to implement:

1. A command that receives an input order and performs the necessary action using the IoC.
2. It is necessary to prohibit one player from giving orders to another player.

