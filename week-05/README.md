# Week 5 : Catch System

## Objective

The main objective of Week 5 is to implement the Catch System in the Catch the Falling Objects game. In this week, the game detects when the player catches a falling object, removes the caught object from the screen, and shows a temporary "+1" effect as simple visual feedback.

## Tasks Completed

- Added collision detection between the player and falling objects.
- Detected when the player successfully catches an object.
- Removed the caught object from its current position.
- Sent the caught object back to the top of the screen.
- Assigned a new random X-position to the object.
- Assigned a new random falling speed after each catch.
- Added a temporary "+1" effect when an object is caught.
- The "+1" effect disappears automatically after a short time.
- Kept all Week 4 features such as multiple objects, different colors, random positions, and different speeds.

## Technology Used

- Java
- Java Swing – for the game interface and JPanel
- Java AWT – for graphics, colors, fonts, and keyboard events
- Timer – for continuous object movement and temporary catch effects
- Random – for random object positions and speeds
- Collision Detection – to detect when the player catches an object

## How the Catch System Works

When a falling object overlaps with the player's catcher area, the game identifies it as a successful catch using collision detection.

After a successful catch:

1. The object is removed from the current screen position.
2. A "+1" message appears near the catcher.
3. The object receives a new random X-position.
4. A new random speed is assigned.
5. The object starts falling again from the top.
6. The "+1" effect disappears after a short time.

## Output

When the player successfully catches a falling object, the object disappears and a temporary "+1" effect is displayed near the catcher.

The other falling objects continue moving normally, so the game remains active with multiple falling objects.

## Status

Status: Completed

The Catch System has been successfully implemented. The player can now catch falling objects, caught objects reset from the top, and a temporary "+1" visual effect provides feedback for each successful catch.
