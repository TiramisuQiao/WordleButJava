# Wordle Game

This Java application is a console-based representation of the popular game Wordle.

## Features

* **Play-ability:** At startup, the user is presented with an option to either start the game or quit.

* **The Game:** The main game allows the user to guess a random 5-letter word, with six attempts provided to make the right guess. Every time a guess is made, the game indicates which letters are correct and in the correct position, which letters are correct but in the wrong position, and which letters are wrong.

* **Performance Metrics:** At the end of each game, the program reports on the user's performance, providing the number of tries it took to guess the word and the total duration of the game. 

## Game Logic

The game logic is implemented in the `GameBody` method, which serves up the user interface, handles user inputs, and provides feedback based on each guess. Game progress is tracked in terms of both letters guessed correctly and attempts taken. Feedback is given immediately after every guess to help the user make informed subsequent guesses. Once a game is completed (either the correct word has been guessed or all attempts have been used), the user is presented with the option to play again or quit.

## Requirements
This application requires `Java SDK version 20`.

## How to Run
Compile and run the `Game` class in your favorite Java IDE or from the command line using `javac` and `java` respectively.
