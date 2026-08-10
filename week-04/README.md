# Week 4: Multiple Falling Objects
## Objective
The main objective of Week 4 is to improve the game by introducing multiple falling objects instead of a single object. The objects are designed to fall from different random positions and at different speeds to make the gameplay more dynamic. Different colors are also used to clearly distinguish the objects, including the use of the RGB color model for creating custom colors. This week focuses on managing multiple objects using arrays, random values, and Java Swing Timer.
## Task Completed

- Multiple Falling Objects :
  
  Added 5 falling objects using arrays. Each object has its own X and Y position.

- Random Positions :

  Objects start from different random positions, so they do not fall from the same location.

- Different Falling Speeds :

  Each object is assigned a random speed between 1 and 4. As a result, the objects fall at different speeds.

- Different Object Colors :

  Different colors were added to make the falling objects visually distinct. The objects use Red, Ocean Blue, Deep Pink, Yellow, and Orange colors.
  Java's "Color" class is used to define these colors. For custom colors, the RGB (Red, Green, Blue) color model is used, where each color is created by combining different amounts of Red, Green, and Blue values. For example, "new Color(0, 119, 190)" creates the Ocean Blue color, where R = 0, G = 119, and B = 190. Similarly, "new Color(255, 20, 147)" creates the Deep Pink color. This makes it possible to create specific colors instead of using only predefined colors.

- Continuous Falling :

  A Swing "Timer" is used to continuously update the Y-position of all objects and create the falling effect.

- Object Reset :

  When an object reaches the bottom of the screen, it is moved back to the top with a new random X-position and speed.

## Technology Used

- Java
- Java Swing
- JPanel
- Timer
- Graphics
- KeyListener
- Arrays
- Random
- "Color" and RGB Color Model

## Output

The game now displays 5 differently colored objects falling from random positions and at different speeds. The player can move the green catcher using the Left and Right Arrow keys.

## Status

Week 4 has been successfully completed. Multiple falling objects, random positions, different speeds, and RGB-based colors have been implemented successfully to make the game more dynamic and visually appealing.
