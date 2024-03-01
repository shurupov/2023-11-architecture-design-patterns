# State

## Purpose
Apply a State pattern to change the behavior of Command handlers
## Description
In [homework #7](../07-concurrent-command-execution), multithreaded processing of the command queue was implemented. Two modes of stopping this queue were offered - hard and soft.

However, there may be many more completion options and processing modes. In this homework, it is necessary to implement the possibility of changing the command processing mode in the stream, starting with the next Command.

To do this, it is proposed to use the State pattern. Each state will have its own command processing mode. The handle method returns a reference to the next state.

It is necessary to implement the following states of the machine:

- Ordinary

  In this state, the next command is retrieved from the queue and executed. By default, a reference to the same instance of the state is returned.
  
  Processing the HardStop command causes a "null reference" to the next state to be returned, which corresponds to the termination of the thread.
  
  Processing the MoveToCommand command causes a reference to the moveTo state to be returned

- MoveTo is the state in which commands are retrieved from the queue and redirected to another queue. This state can be useful if you want to unload the server before shutting it down.
  
  Processing the HardStop command causes a "null reference" to the next state to be returned, which corresponds to the termination of the thread.
  
  Processing the runCommand command causes a reference to the "normal" state to be returned.
