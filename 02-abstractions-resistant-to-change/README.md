# Abstractions resistant to change

## Alternative title: Space Battle Game Server

## Purpose
Developing the skill of applying SOLID principles on the example of the game "Space Battle".

## Description
Two fleets of spaceships met in a distant star system. Ships can move around the entire space of the star system in a straight line, turn counterclockwise and clockwise, shoot photon torpedoes. A photon torpedo hitting the ship puts it out of action.

Three spaceships from each flotilla take part in the battle.

The victory in the battle is won by the flotilla that will be the first to disable all the opponent's ships.

The flotillas are controlled by computer programs for the players (that is, not from the keyboard).

Conceptually, the game consists of three subsystems:

- A game server where all the game logic is implemented.
- Player is a console application that displays a specific battle.
- Agent is an application that runs a tank management program on behalf of the player and sends control commands to the game server.

## Task
Implement the movement of objects on the playing field within the Game Server subsystem.

To build project to jar and execute tests use command `../mvnw package`

To launch application use command `java -jar target/01-unit-testing-1.0-jar-with-dependencies.jar 1 2 3 0.001`