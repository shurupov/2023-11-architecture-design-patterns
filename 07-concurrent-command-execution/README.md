# Multithreaded command execution

## Purpose
Development of a multithreaded command execution system
## Description
Suppose we have a set of commands that need to be executed. We organize the execution of commands in several threads.
To do this, we will assume that each thread has its own thread-safe queue.
In order to execute a command, it must be added to the queue. The thread reads the next command from the queue and executes it.
If the execution of the command is interrupted by an exception thrown, the thread must catch it and continue working.
If there are no messages in the queue, then the thread goes to sleep until the queue is empty.

## Tasks:
- Implement code that runs in a separate thread and does the following
  In a loop, it receives a command from a thread-safe queue and runs it.
  Throwing an exception from the command should not interrupt the execution of the thread.
- Write a command that starts the code written in the first point in a separate thread.
- Write a command that stops the cycle of executing commands from point 1 without waiting for them to complete (hard stop).
- Write a command that stops the cycle of executing commands from point 1 only after all commands have completed their work (soft stop).
- Write tests for the command to start and stop the stream.
