package model;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Collection;

/**
 * @author Christian Berger
 * Modeliert das Spielfeld: Alle lebenden Zellen befinden sich in einer
 * Population (LinkedHashSet). Tote Zellen existieren gar nicht. Die neue
 * Population wird durch die Anwendung der Spielregeln erzeugt.
 */
public class Game implements Grid {

    /*
     * Spielregeln!! Moeglich sind beispielsweise:
     * 
     * GEBURT | TOT     | Welt 
     * ------------------------ 
     * 3      | 0145678 | Cornway Original 
     * 0123478| 5       | Anti Cornway 
     * 1357   | 02468   | Kopiersystem 
     * 02468  |1357     | Antikopiersystem 
     * 3      | 014578  | Explodierend, aehnlich zu Cornway
     */
    private static final int[] GEBURTS_REGEL = {3 };
    private static final int[] TODES_REGEL = {0, 1, 4, 5, 6, 7, 8 };

    private int columns;
    private int rows;
    private int generation;
    private Set<Cell> population;

    /**
     * Erzeugt ein neues Spiel mit bestimmter Hoehe, Breite und einer anfangs
     * leeren Population der 0ten Generation!
     * 
     * @param columns
     *            Spaltenanzahl, sozusagen die Breite
     * @param rows
     *            Reihenanzahl, also die Hoehe
     */
    public Game(int columns, int rows) {
        super();
        this.columns = columns;
        this.rows = rows;
        this.population = new LinkedHashSet<Cell>();
        generation = 0;
    }

    @Override
    public synchronized int getColumns() {
        return columns;
    }

    @Override
    public synchronized int getRows() {
        return rows;
    }

    @Override
    public synchronized int getGenerations() {
        return generation;
    }

    @Override
    public synchronized Collection<Cell> getPopulation() {
        return population;
    }

    @Override
    public synchronized boolean isAlive(int col, int row) {
        // Zelle lebt <=> Zelle befindet sich in der Population
        Cell c = new Cell(col, row, columns, rows);
        return population.contains(c);
    }

    @Override
    public synchronized void setAlive(int col, int row, boolean alive) {


        Cell c = new Cell(col, row, columns, rows);
        if (alive) {
            population.add(c);
        } else {
            population.remove(c);
        }


    }

    @Override
    public synchronized void resize(int cols, int rows) {
        this.columns = cols;
        this.rows = rows;
        // Schaue welche Zellen ausserhalb liegen und loesche sie dann
        Set<Cell> outside = new LinkedHashSet<Cell>();
        for (Cell c : population) {
            if (c.getCol() >= cols || c.getRow() >= rows) {
                outside.add(c);
            }
        }
        population.removeAll(outside);
    }

    @Override
    public String toString() {
        String erg = "";
        // Durchlauft das gesamte Feld
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                // Markiere lebendige Zellen mit X" und tote mit "."
                erg = erg + (population.contains(new Cell(x, y, columns, rows)) ? "X" : ".");
            }
            // Mache nach jeder ausser der letzten Zeile einen Zeilenumbruch
            if (y < rows - 1) {
                erg = erg + "\n";
            }
        }
        return erg;
    }

    @Override
    public synchronized void clear() {
        population = new LinkedHashSet<Cell>();
        generation = 0;
    }

    @Override
    public synchronized void next() {
        /*
         * Fuer alle lebenden Zellen: Setzte den Nachbarschaftszaehler auf 0 und
         * speichere die Zelle sowie ihre Nachbarn in der Hilfsmenge update.
         * update speichert alle fuer die nachste Generation infrage kommenden
         * Zellen
         */
        Set<Cell> update = new LinkedHashSet<Cell>();
        for (Cell c : population) {
            c.setNBC(0); // NBC = NeighBourCounter
            update.add(c);
            saveNeighbours(c, update);
        }
        // Berrechne die Anzahl der lebenden Nachbarn all dieser Zellen
        for (Cell c : update) {
            computeNBC(c);
        }
        // Wende nun die Spielregeln an, sodass die neue Population entsteht!
        for (Cell c : update) {
            applyRules(c);
        }
        generation++;
    }

    /**
     * Speichert die Nachbarn einer Zelle c in die uebergebene Menge update
     * 
     * @param c
     *            current Cell
     * @param update
     *            Set to add c + all of its neighbours
     */
    private void saveNeighbours(Cell c, Set<Cell> update) {
        // Generiert automatisch die Nachbarzellen.
        // Jede for schleife laeuft genau 3 mal
        for (int x = c.getCol() - 1; x <= c.getCol() + 1; x++) {
            for (int y = c.getRow() - 1; y <= c.getRow() + 1; y++) {
                // nicht die identische Zelle
                if (!(x == c.getCol() && y == c.getRow())) {
                    update.add(new Cell(x, y, columns, rows));
                }
            }
        }
    }

    /**
     * Rechnet die Anzahl der lebenden Nachbarn einer Zelle c aus
     * 
     * @param c
     *            current Cell
     */
    private void computeNBC(Cell c) {
        // Generiert automatisch die Nachbarzellen.
        // Jede for schleife laeuft genau 3 mal
        for (int x = c.getCol() - 1; x <= c.getCol() + 1; x++) {
            for (int y = c.getRow() - 1; y <= c.getRow() + 1; y++) {

                // Falls nicht die identische Zelle, sondern Nachbar
                // und dieser Nachbar lebt, so erhoehe den Nachbarzaehler
                if (!(x == c.getCol() && y == c.getRow())
                        && population.contains(new Cell(x, y, columns, rows))) {
                    c.addNBC();
                }
            }
        }
    }

    /**
     * Wendet die Spielregeln an. Diese koennen in den Konstanen TODES_REGEL und
     * GEBURTS_REGEL festgelegt werden! <br>
     * <b> Laufzeit O(1) </b><br>
     * Anmerkung: Todes- und Geburtenregel sind disjunkt
     * 
     * @param c
     *            current Cell
     */
    private void applyRules(Cell c) {
        // Pruefe die Todesregel ab, falls zutreffend, toete die Zelle
        for (int i = 0; i < TODES_REGEL.length; i++) {
            if (c.getNBC() == TODES_REGEL[i]) {
                setAlive(c.getCol(), c.getRow(), false);
            }
        }
        // Pruefe die Geburtenregel ab, falls zutreffend, belebe die Zelle
        for (int i = 0; i < GEBURTS_REGEL.length; i++) {
            if (c.getNBC() == GEBURTS_REGEL[i]) {
                setAlive(c.getCol(), c.getRow(), true);
            }
        }
    }

}
