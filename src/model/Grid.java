package model;

import java.util.Collection;


/**
 *  @author Christian Berger
 * Interface fuer das Spiel. Implementiert Methoden zur Manipulation des
 * Spielfelds, sowie Zugriffsmethoden auf das Spielfeld.
 */
public interface Grid {

    /**
     * Prueft ob die Zelle lebt <br>
     * 
     * @param col
     *            x-coordinate
     * @param row
     *            y-coordinate
     * @return ist die Zelle am Leben?
     */
    boolean isAlive(int col, int row);

    /**
     * Setzt eine Zelle auf lebendig oder tot <br>
     * <b>Wichtig:</b> Zellen die nicht auf dem Spielfeld liegen, z.B. wegen
     * negativen Koordinaten oder Koordinaten die die Raender verlassen, werden
     * nicht eingefuegt (oder geloescht, aber ausserhalb des Spielfeldes
     * existieren ja eh keine Zellen). <br>
     * Eine Zelle lebt genau dann wenn sie sich in der Population befindet!
     * 
     * @param col
     *            x-coordinate
     * @param row
     *            y-coordinate
     * @param alive
     *            ob die Zelle leben soll
     * 
     */
    void setAlive(int col, int row, boolean alive);

    /**
     * Aendert die maximale Zeilen und Spaltenanzahl des Spielfelds. Bei einer
     * Verkleinerung werden Zellen, die sich nun ausserhalb befinden geloescht.
     * 
     * @param cols
     *            x-coordinate
     * @param rows
     *            y-coordinate
     */
    void resize(int cols, int rows);

    /**
     * Ruft die Breite des Spielfelds ab
     * 
     * @return x-dimension
     */
    int getColumns();

    /**
     * Ruft die Hoehe des Spielfelds ab
     * 
     * @return y-dimension
     */
    int getRows();

    /**
     * Gibt die vollstaendige Menge aller lebenden Zellen zurueck
     * 
     * @return lebende Zehlen
     */
    Collection<Cell> getPopulation();

    /**
     * Toetet alle Zellen. Setzt Generationszaehler auf 0
     */
    void clear();

    /**
     * Berrechnet die naechste Generation nach den wohldefinierten Regeln
     * (beispielsweise Conrways Regeln). Laufzeit ist O(n).
     */
    void next();

    /**
     * Ruft die Anzahl an vertrichenen Generationen ab
     * 
     * @return generationenzaehler
     */
    int getGenerations();

    @Override
    String toString();

}