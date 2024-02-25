# Microservice Architecture

## Purpose
Develop a microservice architecture for the game
## Description
Tank/spaceship battles can only be conducted by registered users. Tank/spaceship battles are held as part of tournaments, or between any users by agreement.

You can view the list of future tournaments, apply for participation in the tournament, and view the results of ongoing tournaments that have already passed.

The participants of the battles receive a notification of the invitation to the tournament, a decision on the application for participation in the tournament, the end of the tank battle, and the imminent start of the starting battle.

A participant in a tank battle can watch the last battle. Participants receive rating points for places in the tournament.

Each user can organize their own tournament. Tournaments receive a rating, which is calculated based on the ratings of its participants. The tournament can be regular, then its rating accumulates.

The player participates in a tank battle through a program that is loaded into a special Agent application.

To successfully solve the problem, it is necessary:

- Define a set of microservices and web applications and display them on a diagram/set of diagrams.
- Define messaging directions and Endpoints for each microservice and application.
- Identify bottlenecks and potential application scaling issues and how to solve them.
- Identify the components to which the requirements and the ways to perform OCP for them will most often change.
- Make text explanations to the created solution.

[Solution in english](SOLUTION.md)

[Solution in russian](SOLUTION_RU.md)
