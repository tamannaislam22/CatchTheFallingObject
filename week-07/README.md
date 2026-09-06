# Week 7: Miss System (Lives)

## Objective

The objective of Week 7 is to add a Miss System (Lives) to the Catch the Falling Objects game.

The player starts with 3 lives. When a falling object is missed, one life is reduced. The remaining lives are displayed on the game screen.


## Tasks Completed

### 1. Add 3 Lives

A lives variable was added to store the player's remaining lives.

Code:
```java
private int lives = 3;
````
The game starts with 3 lives.


### 2. Reduce Life When Object Is Missed

When a falling object reaches the bottom of the game screen without being caught, one life is reduced.

Code:
```java
if (objectY[i] >= getHeight()) {
    if (lives > 0) {
        lives--;
    }

}
```
After losing a life, the missed object is reset to the top with a new random position and speed.

This allows the game to continue while keeping track of the player's remaining lives.


### 3. Show Lives on Screen

The remaining lives are displayed at the top-right of the game screen.

Code:
```java
g.drawString("Lives: " + lives, 470, 30);
```
For example:

Score: 5 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Lives: 2

Here, 470 is the x-coordinate that places the Lives text near the right side of the screen.

30 is the y-coordinate that keeps the Lives text aligned with the Score.


## Technologies Used

- Java
- Java Swing
- JPanel
- Graphics
- Timer
- KeyListener


## Output

- The game starts with 3 lives.
- Catching an object increases the score.
- Missing an object decreases one life.
- The remaining lives are displayed on the screen.
- Missed objects are reset and continue falling.


## Week 7 Features

- Add 3 lives - Completed
- Reduce life when object is missed - Completed
- Show lives on screen - Completed


## Status

Week 7: Completed


## Note

Game Over and Restart functionality are not included in Week 7.

These features are planned for Week 9: Game Over + Restart.
