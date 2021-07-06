After completing first 3 levels, the user reaches the multiplayer level, which requires another player to play the game.

## Important Notes
* Host plays first! Before host player plays, the connected player cannot play. Host must always play first. 


* Although we have bluffing for Level3,unfortunately; we could not implement bluffing for Multiplayer level. Actually our protocol class supports that and we came to a point where the host could bluff, however there were some minor bugs but we could not fix them in time. So, we did not commit them.

## How to Play
After reaching level 4, you will be placed in a queue for match-making. Following a match, your game will begin in 3 seconds. The rules and playing system is exactly the same as previous levels, however this time, you will be playing against a human! If you're the host of this game, you will be the first player; however if you're not the host, you will wait until the host plays.

## Development Methodology
While developing the multiplayer level, we have used Java Sockets for communication. When a user reaches the multiplayer level, a request is made to the server for match-making. If there is a player in the queue, a match is made. If there is noone in the queue, the user is put into the queue for the first next player. Following a match, the IP addresses and the ports of the users are exchanged and a socket connection created.

When a card gets played, it is transferred through the socket and it is displayed to the other player. The implementation of this view update was achieved through using the previously designed Strategy pattern for the AI models. Instead of playing the game, the AI strategy simply fetches the moves from the socket and applies them on the game view. Then, host player passes the new game state to other player. With that, we were able to achieve multiplayer functionality with minimal updates to the game logic. We created a class which creates the game state and preprocesses the data before sending it to other player. In other words, when a player receives the game state over the socket, the player can use the state without doing any modifications on it. We create objects from this class and pass them over the socket to pass the state to the other player.



