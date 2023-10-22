# S5.2DiceGame
Final project for Cibernarium Java class

This is your final project, an API 100% designed by you where you will
apply everything you know so far to create a complete application, from
database to security. Apply everything you know even what is not asked.

## Level 1

The dice game is played with two dice. If the result of the sum of the two
dice is 7, the game is won, if not lost. A player can see a list of all 
the rolls he/she has made and the percentage of success.

To be able to play the game and make a roll, a user must register with a 
non-repeated name. Upon creation, it is assigned a unique numeric identifier
and a registration date. If the user so wishes, you can not add a name and
it will be called "ANONYMOUS". There can be more than one "ANONYMOUS" 
player.

Each player can see a list of all the rolls they have made, with the value
of each die and whether or not they have won the game. In addition, you 
can know your success rate for all the rolls you have made.

You cannot delete a specific game, but you can delete the entire list of 
runs for a player.

The software must allow listing all the players in the system, the success
percentage of each player and the average success percentage of all the
players in the system.

The software must respect the main design patterns.

NOTES

You should consider the following construction details:

URLs:
- POST: /players: Create a player.
- PUT /players: change the name of the player.
- POST /players/{id}/games/ : A specific player rolls the dice.
- DELETE /players/{id}/games: deletes the player's rolls.
- GET /players/: returns the list of all the players in the system with 
  their average success rate.
- GET /players/{id}/games: returns the list of games for a player.
- GET /players/ranking: returns the average ranking of all players in the
  system. That is, the average percentage of successes.
- GET /players/ranking/loser: returns the player with the worst success 
  rate.
- GET /players/ranking/winner: returns the player with the worst success
  rate.
    
### Phase 1

Persistence: Uses MySQL as a database.

### Phase 2

Change everything you need and use MongoDB to persist data.

### Phase 3

Add Security: Include JWT authentication on all accesses to microservice URLs.

## Level 2

Add unit, component and integration tests to the project with jUnit, 
AssertJ or Hamcrest libraries.
Add Mocks to project testing (Mockito) and Contract Tests 
(https://docs.pact.io/)

## Level 3

Design and modify the project diversifying persistence to use two 
database schemas at the same time, MySQL and Mongo.
