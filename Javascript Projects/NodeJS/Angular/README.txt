Shane Hagen
CMPSC 421
Problem Set 4

Required node modules:
- angular/cli
- howler

Notes:
- App uses howler.js for playing 7 different songs
- App component is just a navbar for moving between home and library directories with router link
- Home is just a welcome page, nothing special happening
- Library implements a sticky footer with controls for the music


Known issues:
- I wanted to find a way to grab metadata from the mp3 files to load song information,
	but there was some problem with mp3 files in the angular asset folder, it wouldn't recognize
	that they exist. I think it has to do with the files being non-static? Not sure.
- If you play a song then switch to the home page the song still plays. 