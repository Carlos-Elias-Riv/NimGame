# NimGame Project
## The goal of this project, is to make a program that can be unbeatable at playing the nimgame
_*What is nimgame?*_

NimGame is a game, played by two opponents in which the objective is to manage a way of making the other player stick with the last ball/match/etc. 
The game can be constructed with _n_ rows, each row containing an odd number. You can find some solutions to this game rewarding the _n = 4_ case. However this code, can perform at _n_ in _{1, 2, 3, 4, 5, 6}_ levels. And can be unbeatable at this cases, depending on how many rows has the game. 

Here's an example of Nimgame with n = 4:
 
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/NimGame.svg/1200px-NimGame.svg.png" width="500" height="500"> 

_*How can I play it using this code?*_

First you have to upload every .java file in the same directoryp and declare this package at each file, so that the classes can call up each other. Then, the runnable file is theone called: _PlayNimGame.java_ . The default size for the game is _n_ = 4 and by default you will play against the machine, and once you run up the code, it will ask you (in Spanish) if you want to start or no, if you do type s or S (for Sí in spanish that means Yes). Once the game is over, it will ask you if you want to see the code play against itself.Then it will display the results, showing the best game possible for that _n_. If you want to change the game size, in line 5 you will find ```juego.initializeGame(4)```, all youhave to do is to change that 4 to any integer in range [1, 6]. 

You'll notice that there's no GUI for this game. The logic of playing the game is that it will show you how many balls or matches are in the _i_ position. The code will display an array, that for every _i_ position it represents the amount of balls or matches are left. The way you answer in the game is telling the code how many balls are in the first row, then how many balls are in the second row ... until you get to how many balls are in the _n_ row. If you happen to make an invalid move such as, taking balls from more than one row or not taking any balls at all, a mistake will show up, and (in Spanish) it will ask you to make a valid move. 



__Hope you enjoy! If you have any questions or need any help, contact me: carlos.rivera311202@gmail.com__
