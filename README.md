# Bowling Kata

An example of a bowling class respect this specifications:

# Bowling Rules
The game consists of 10 frames. In each frame the player has two rolls to knock down 10 pins.
The score for the frame is the total number of pins knocked down, plus bonuses for strikes and spares.

- A spare is when the player knocks down all 10 pins in two rolls.
The bonus for that frame is the number of pins knocked down by the next roll.

- A strike is when the player knocks down all 10 pins on his first roll.
The frame is then completed with a single roll.
The bonus for that frame is the value of the next two rolls.

- In the tenth frame a player who rolls a spare or strike is allowed to roll the extra balls to complete the frame.
However no more than three balls can be rolled in tenth frame.

# Requirements
Write a class Game that has two methods

- void roll(int) is called each time the player rolls a ball. The argument is the number of pins knocked down.

- int score() returns the total score for that game.
