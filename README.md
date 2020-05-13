# DrawingCmd

That's a simple console version of a drawing program. 

## Getting Started

Currently, the functionality of the program quite limited but this might change in the future. 
In a nutshell, the program has following capabilities:
1. Create a new canvas
2. Start drawing on the canvas by issuing various commands (L, R, B, Q)
3. Quit

### Prerequisites

Following software required to run the program:

```
JRE higher or equal to 1.8
Gradle 6.4
Groovy SDK 2.5.10 
```

### Installing

Steps to install program

```
1. Enter application directiory
2. Generate gradle wrapper by running gradle wrapper
3. Generate executble jar by runnging gradle jar
4. Run application by java -jar ./build/libs/DrawingCmd-1.0.jar
```
Available commands:
```
C w h           Should create a new canvas of width w and height h.
L x1 y1 x2 y2   Should create a new line from (x1,y1) to (x2,y2). Currently only
                horizontal or vertical lines are supported. Horizontal and vertical Dlines
                will be drawn using the 'x' character.
R x1 y1 x2 y2   Should create a new rectangle, whose upper left corner is (x1,y1) and
                lower right corner is (x2,y2). Horizontal and vertical lines will be drawn
                using the 'x' character.
B x y c         Should fill the entire area connected to (x,y) with "colour" c. The
                behavior of this is the same as that of the "bucket fill" tool in paint
                programs.
Q               Should quit the program.
```

Execution examples:
```
enter command: C 20 4
----------------------
|                    |
|                    |
|                    |
|                    |	
----------------------

enter command: L 1 2 6 2
----------------------
|                    |
|xxxxxx              |
|                    |
|                    |
----------------------

enter command: L 6 3 6 4
----------------------
|                    |
|xxxxxx              |
|     x              |
|     x              |
----------------------

enter command: R 14 1 18 3
----------------------
|             xxxxx  |
|xxxxxx       x   x  |
|     x       xxxxx  |
|     x              |
----------------------

enter command: B 10 3 o
----------------------
|oooooooooooooxxxxxoo|
|xxxxxxooooooox   xoo|
|     xoooooooxxxxxoo|
|     xoooooooooooooo|
----------------------

enter command: Q
```

## Running the tests

To run tests execute:
./gradlew test

Tests result can be viewed on ./build/reports/tests/test/index.html

## Assumptions
* Figures can be drawn only inside the canvas, if any of coordinates enters canvas boundaries, application notifies user
* New drawing always overrides old one

## Built With

* [Gradle](https://gradle.org) - Dependency Management

## Authors

* [Alexander Makarov](https://www.linkedin.com/in/makarov-alexander/)
