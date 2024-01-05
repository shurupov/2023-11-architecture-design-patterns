# Adapter

## Purpose
Implement Adapter Generated from interface during runtime.
## Description
The game Space Battle has a set of operations on game objects: moving in a straight line, turning, shooting. However, the content of these commands may differ for different games, depending on which rules of the game were chosen by the users. For example, users can limit the power reserve of each ship to a certain amount of fuel, and in another game prohibit ships from turning clockwise, etc.

The IoC can help in this case by hiding the details in the dependency resolution strategy.

## Tasks:
- You have an interface. You should generate, compile and deploy class that implements the interface. Like the following
    ```java
      public class MovableE6rvV implements Movable {
      
        private final IoC ioc;
        private final Map<String, Object> object;
      
        public MovableE6rvV(IoC ioc, Map<String, Object> object) {
          this.ioc = ioc;
          this.object = object;
        }
      
        public void move(Velocity arg0) {
          ioc.resolve("Movable.Move", object, arg0);
        }
      
        public Position getPosition() {
          return ioc.resolve("Movable.Position.Get", object);
        }
      
        public Velocity getVelocity() {
          return ioc.resolve("Movable.Velocity.Get", object);
        }
      }
    ```
- Factories like the following should be resolved and added to IoC container
    ```java
      ioc.resolve("Movable.Position.Get", object);
    ```
- To generate adapter you should use the following code, where `Movable` is an interface.
    ```java
      ioc.resolve("Adapter.Create", Movable.class, object)
    ```
- Generator should work correctly even if methods return `void` or don't need arguments.
