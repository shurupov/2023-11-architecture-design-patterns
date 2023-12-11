# Command and Macrocommand

## Purpose
Learn how to handle situations from a SOLID perspective when you need to refine existing behavior without modifying existing code.
## Description
Let's assume that we have already written the MoveCommand and RotateCommand commands. Now there is a new requirement: the user can set a rule in the game - fuel is consumed during movement, it is possible to move only if there is fuel.

## Tasks:
- Implement the CheckFuelComamnd class and the tests for it.
- Implement the BurnFuelCommand class and tests for it.
- Implement the simplest macro and tests for it. Here, the simplest one means that when an exception is thrown, the entire command sequence suspends its execution, and the macro throws a CommandException.
- Implement the command to drive in a straight line with fuel consumption using the commands from the previous steps.
- Implement a command to modify the instantaneous velocity vector when turning. It should be noted that not every unfolding object is moving.
- Implement a rotation command that also changes the vector of instantaneous velocity, if any.
