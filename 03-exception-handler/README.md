# SOLID and Exceptions

## Purpose
Learn how to write different exception handling strategies so that the corresponding try-catch block does not have to be modified every time there is a need to handle an exceptional situation in a new way.

## Description
Let's assume that all the commands are in some queue. Queue processing consists of reading the next command and the head of the queue and calling the execute() method of the extracted command. The execute() method can throw any arbitrary exception.

## Tasks:
- Implement a command that records information about the thrown exception in the log.
- Implement an exception handler that puts the Command writing to the log in the Command queue.
- Implement a Command that repeats the Command that threw the exception.
- Implement an exception handler that queues the repeater Command of the command that threw the exception.
- Implement the following exception handling - repeat the command when the exception is thrown for the first time, and write the information to the log when the exception is thrown again.
- Implement an exception handling strategy - repeat twice, then write to the log

## Task

To build project to jar and execute tests use command `../mvnw package`