package gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import model.Grid;

/**
 * @author Christian Berger
 * Die Klasse GridJPanel ist eine Zeichenflaeche und fur die Darstellung der
 * Zellen zustaendig. Beim Generationenwechsel wird stets nur die Zellen
 * neugezeichnet, nicht aber das ganze Fenster mit den Buttons und Boxes etc.
 */
public class GridJPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Rot
     */
    public static final Color COLOR_ALIVE = new Color(248, 168, 145);

    /**
     * Weiß
     */
    public static final Color COLOR_DEAD = new Color(255, 255, 255);

    /**
     * Die Laenge einer Zelle. Zellen sind modelliert als Quadrate.
     * Voreinstellung: grosse Zellen
     */
    private int length = GameOfLifeGUI.SIZE_BIG;

    /**
     * Die Spiellogik. Das Panel fragt das Spiel nach seinem Zustand ab, ob z.B
     * Zelle (x,y) lebt oder tot ist, um diese dann entsprechend zeichnen zu
     * koennen.
     */
    private Grid model;

    /**
     * Erzeugt ein neues GridJPanel
     * 
     * @param model
     *            die Spiellogik
     */
    public GridJPanel(Grid model) {
        super();
        this.model = model;
    }

    /**
     * Setzt die Laenge der Zellen
     * 
     * @param l
     *            Laenger der Zellen
     */
    public void setLength(int l) {
        length = l;
        repaint();
    }

    /**
     * Veraendert die Zellenanzahl des Modells
     */
    public void resize() {
        model.resize(getWidth() / (length + 1) + 1, getHeight() / (length + 1)
                + 1);
    }

    /**
     * Gibt die Laenge der Zellen zurueck
     * 
     * @return Laenge der Zelle
     */
    public int getLength() {
        return length;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Durchlaufen des Gitters, markiere Zelle je nach Lebenszustand
        for (int i = 0; i <= this.getWidth() + length + 1; i = i + length + 1) {
            for (int j = 0; j <= this.getHeight() + length + 1; j = j + length
                    + 1) {
                if (model.isAlive(i / (length + 1), j / (length + 1))) {
                    g.setColor(COLOR_ALIVE);
                    g.fillRect(i, j, length, length);
                } else {
                    g.setColor(COLOR_DEAD);
                    g.fillRect(i, j, length, length);
                }
            }
        }
    }

}
