
# Game of Life 


## Getting Started

Just compile the sources and type in the CLI:

```java -jar GameOfLife.jar```

<img src="https://raw.githubusercontent.com/bergerch/GameOfLife/master/gol.png" width="600" height="400" />

## Use

All inputs can be applied using a graphical user interface. A click on cells sets them alive (if dead) or kills them (if alive). You can also draw lines by clicking and hovering over multiple cells. Further features are are cutomizing the size of the cell automata or inserting shapes. You can also choose the speed of the simulation

## Development

The programm is written in Java. All sources are included. We use and recommend IntelliJ for development.

## Known Issues

Resizing the windowd leads to an alternation of the (x,y) model. At runtime, the simulation can become buggy if figures are sliding over the edge of the window (this is actually more a concurrency problem than a bug as resizing (the ui thread) is not synchronized with the actual simulation)


## Complexity

Living cells are stored in a hashmap (the current generation) with access in O(1). The algorithm works in-place and only looks as living cells, hence it scales independently of the size of the (x,y) model. The memory complexity is O(n) with n being the number of living cells, which is optimal, since every living cell needs to be inspected by the algorithm. The algorithm iterates over the hashmap and searches for every cell for its neighbors as a potential candidate for the next generation. Then the number of living neighbours of each candidate will be computed and the Cornway rules will be applied. The run-time complexity is thus O(n) which is optimal.


## Alternative Rule Sets

Der Code is generic to support different rule sets:
```javascript

public class Game implements Grid {
    /*
     * Spielregeln!! Moeglich sind beispielsweise:
     * 
     * ALIVE | DEAD     | World 
     * ------------------------ 
     * 3      | 0145678 | Cornway Original 
     * 0123478| 5       | Anti Cornway 
     * 1357   | 02468   | Copy System 
     * 02468  |1357     | Anti copy system
     * 3      | 014578  | Exploding, similiar zu Conway
     */
    private static final int[] ALIVE_RULE = {3};
    private static final int[] KILL_RULE = {0, 1, 4, 5, 6, 7, 8};
     
```
See class "Game.java"

