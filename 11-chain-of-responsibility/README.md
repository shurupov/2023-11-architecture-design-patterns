# Chain of Responsibility

## Purpose
Learn how to apply the chain of responsibility
## Description
Let's assume that we have a function/procedure/method that can determine a collision for two game objects - whether these two objects collided or not. This function/procedure/method does not need to be implemented, since it is quite a big task.

Using this function, we implement an efficient search of all game objects to determine all possible collisions in the game at any given time.

To do this, we divide the playing field into neighborhoods, assuming that objects located in different neighborhoods cannot collide with each other. Only objects from the same neighborhood can collide with each other.

Necessary:

1. Implement a command that
   - determines the neighborhood in which the game object is present,
   - if the object is in a new neighborhood, then removes it from the list of objects in the old neighborhood and adds a list of objects in the new neighborhood.
   - for each object of the new neighborhood and the current moving object, creates a command to check the collision of these two objects. Puts all these commands in a macro and writes this macro to the place of a similar macro for the previous neighborhood.
2. When approaching with neighborhoods, it may turn out that objects located nearby may fall into different neighborhoods, which will not allow you to determine a collision between them. To solve this problem, we will use two neighborhood systems - one neighborhood is shifted relative to the center of the other neighborhoods. To do this, you can use several commands from paragraph 1
3. Using the command from 1. Organize a collision check of game objects using two neighborhood systems.