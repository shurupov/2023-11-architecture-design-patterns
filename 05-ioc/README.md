# Implement IoC container

## Purpose
Implement your own IoC container that is resistant to changing requirements.
## Description
The game Space Battle has a set of operations on game objects: moving in a straight line, turning, shooting. However, the content of these commands may differ for different games, depending on which rules of the game were chosen by the users. For example, users can limit the power reserve of each ship to a certain amount of fuel, and in another game prohibit ships from turning clockwise, etc.

The IoC can help in this case by hiding the details in the dependency resolution strategy.

## Tasks:
- Implement an IoC container that resolves dependencies using a method with the following signature:
    ```java
      public <T> T resolve(String key, Object ...args)
    ```
- Dependencies are also registered using the `resolve` method
    ```java
      ioc.resolve("IoC.Register", "name", "value");
      ioc.resolve("IoC.Register", "sum", (args) -> args[0] + args[1]); //The recording is incomplete. Find more in the tests
    ```
- Dependencies can be registered in different scopes
    ```java
      ioc.resolve("Scope.Add", "scope1");
      ioc.resolve("Scope.Select", "scope1");
    ```
- IoC should be resistant to adding functionality
