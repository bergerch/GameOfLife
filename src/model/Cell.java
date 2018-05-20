package model;
/**
 * @author Christian Berger
 * Modelliert eine Zelle als Tupel zweier int Koordinaten plus einem Zaehler
 * fuer die lebenden Nachbarn. Der Nachbarzaehler wird aber nicht gehasht.
 */
public class Cell {

    private int col;
    private int row;
    private int nbcounter;

    /**
     * Erzeugt eine Zelle
     * 
     * @param col
     *            x-Koordinate
     * @param row
     *            y-Koordinate
     */
    public Cell(int col, int row, int allCols, int allRows) {
        this.col = (col + allCols) % allCols;
        this.row = (row + allRows) % allRows;
    }

    /**
     * Erhoeht die Anzahl der lebenden Nachbarn um 1
     */
    public void addNBC() {
        nbcounter++;
    }

    /**
     * Ruft die Anzahl der lebenden Nachbarn ab
     * 
     * @return Neihbourcounter
     */
    public int getNBC() {
        return nbcounter;
    }

    /**
     * Setzt den nachbarzaehler auf einen neuen Wert.
     * 
     * @param nbc
     *            anzahl der lebenden nachbarszellen
     */
    public void setNBC(int nbc) {
        nbcounter = nbc;
    }

    /**
     * Gibt die X-Koordinate zurueck
     * 
     * @return x-coordinate
     */
    public int getCol() {
        return col;
    }

    /**
     * Gibt die Y-Koordinate zurueck
     * 
     * @return y-coordinate
     */
    public int getRow() {
        return row;
    }

    /**
     * Generiert HashCode <br>
     * Wichtig: nbcounter darf nicht gehasht werden!
     * 
     * @return hashwert
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + col;
        result = prime * result + row;
        return result;
    }

    /**
     * Checkt ob ein 2tes Objekt mit dieser Zelle gleich ist.
     * 
     * @param obj
     *            Vergleichsobjekt
     * @return Ob das Objekt gleich der Zelle ist
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Cell other = (Cell) obj;
        if (col != other.col) {
            return false;
        }
        if (row != other.row) {
            return false;
        }
        return true;
    }

}
