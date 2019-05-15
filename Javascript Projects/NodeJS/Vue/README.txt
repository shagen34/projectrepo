Shane Hagen
CMPSC 421
Problem Set 3 - 2048

Required node modules:
- vue 

Notes:
- The game logic works about 99% with one known issue discussed below
- The only animation is when a new tile is introduced it gets faded in

Known issues:
- The function that calls the firestore hiscores won't let me store the data into an array
	to compare it with the current users score. Probably an issue with the async behavior
	but I tried a few things and couldn't get it to work. For this reason I didn't finish
	the end of game prompt so it tells the user in the console.
- If you make a move while the board is full that doesn't cause any tiles to move the console will tell you 
	you've lost, but there could still be moves to make
- On some moves the program will do a "double combine", e.g a sequence of 4, 4, 8 will
	combine to 16, which doesnt happen in the original game. Doesn't happen at each
	instance so not sure where the issue is in the logic