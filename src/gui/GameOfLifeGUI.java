package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import javax.swing.DefaultComboBoxModel;

import model.Figure;
import model.Game;
import model.Grid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Christian Berger
 * Die Klasse ist fuer die graphische Darstellung des Game of Life zustaendig.
 * Das Fenster besteht aus verschieden Komponenten. Auch die Aktionen, die durch
 * diese ausgeloest werden, werden hier verwaltet.
 */
public class GameOfLifeGUI {

    // Die verschiedenen Groessen der Zellen
    public static final int SIZE_BIG = 26;
    public static final int SIZE_MEDIUM = 18;
    public static final int SIZE_SMALL = 10;
 
    //Die verschiedenen Geschwindigkeiten
    public static final int SPEED_SLOW = 300;
    public static final int SPEED_MEDIUM = 150;
    public static final int SPEED_FAST = 50;

    /**
     * Die Spiellogik
     */
    private Grid model;

    // Die Zeichenelemente
    private GridJPanel gridJPanel;
    private JFrame frame = new JFrame("Game of Life it-talents.de competition");
    private JPanel panel = new JPanel();
    private JComboBox<Figure> shapeComboBox = new JComboBox<Figure>();
    private JButton btnStart = new JButton("Start");
    private JButton btnNext = new JButton("Next");
    private JComboBox<String> speedComboBox = new JComboBox<String>();
    private JComboBox<String> sizeComboBox = new JComboBox<String>();
    private JLabel genLabel = new JLabel("Generation: 0");

    /**
     * Speichert die momentane Geschwindigkeit. <br>
     * Voreinstellung: Langsam
     */
    private int currentSpeed = SPEED_SLOW;

    /**
     * Speichert Zustand "lebend" einer Zelle bei Klickevent <br>
     * <b>False</b> : Dann agiert der Mauszeiger als "Hebamme" <br>
     * <b>True</b> : Dann agiert der Mauzeiger als "Killer"
     */
    private boolean alive;

    /**
     * Main Methode. Erzeugt ein neues Game of Life Spiel
     * 
     * @param args
     *            startparameter unbenutzt
     */
    public static void main(String[] args) {
        new GameOfLifeGUI(new Game(0, 0));
    }

    /**
     * Erzeugt ein neues Fenster.
     * 
     * @param model
     *            das Modell (die Spiellogik)
     */
    public GameOfLifeGUI(Grid model) {

        // Das gridJPanel modelliert die Zellen, dazu braucht es die Spiellogik
        // um zu wissen, was es anzeigen soll.
        this.model = model;
        gridJPanel = new GridJPanel(model);

        adjustFrame();
        addItemsToComboBoxes();
        addJElementstoPanel();

        // Fuegt das gridJPanel zum Fenster hinzu
        frame.getContentPane().add(gridJPanel);

        // Fuegt die Listener hinzu
        addListener();

        // Macht die GUI sichtbar
        frame.setVisible(true);
    }

    /**
     * Passt das Fenster an
     */
    private void adjustFrame() {
        // Legt Eigenschaften des Fensters fest:
        frame.setMinimumSize(new Dimension(605, 385));
        frame.setBounds(new Rectangle(0, 0, 605, 385));
        frame.setBounds(100, 100, 605, 385);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
    }

    /**
     * Fuegt die Auswahlmoeglichkeiten zu den Comboboxes hinzu
     */
    private void addItemsToComboBoxes() {
        // Fuegt die Figurenauswahl zur entsprechenden Combobox hinzu
        shapeComboBox
                .setModel(new DefaultComboBoxModel<Figure>(Figure.values()));

        // Auswahlmoeglichkeiten der anderen Comboboxes:
        String[] size = {"big", "medium", "small" };
        String[] speed = {"slow", "medium", "fast" };

        // Fuegt all diese an:
        for (int i = 0; i < size.length; i++) {
            sizeComboBox.addItem(size[i]);
        }
        for (int i = 0; i < speed.length; i++) {
            speedComboBox.addItem(speed[i]);
        }
    }

    /**
     * Fuegt die graphischen Elemente zum Panel hinzu
     */
    private void addJElementstoPanel() {
        panel.add(shapeComboBox);
        panel.add(btnNext);
        panel.add(btnStart);
        panel.add(speedComboBox);
        panel.add(sizeComboBox);
        panel.add(genLabel);
    }

    /**
     * Erzeugt und fuegt die Listener zum Fenster hinzu, macht das Fenster
     * sichtbar
     */
    private void addListener() {
        btnNext.addActionListener(new NextButtonListener());
        btnStart.addActionListener(new StartButtonListener());
        shapeComboBox.addActionListener(new ShapeComboBoxListener());
        speedComboBox.addActionListener(new SpeedComboBoxListener());
        sizeComboBox.addActionListener(new SizeComboBoxListener());
        gridJPanel.addComponentListener(new GridJPanelResizeListener());
        gridJPanel.addMouseListener(new GridJPanelMouseListener());
        gridJPanel.addMouseMotionListener(new GridJPanelMouseMotionListener());

        // Macht die GUI sichtbar
        frame.setVisible(true);
    }

    /**
     * Der GridJPanelResizeListener fuegt folgende Funktionalitaet hinzu: Beim
     * Verkleinern oder Vergroessern des Fensters, wird das das Modell angepasst
     */
    class GridJPanelResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent arg0) {
            gridJPanel.resize();
        }
    }

    /**
     * Der NextButtonListener fuegt folgende Funktionalitaet hinzu: Bei Druecken
     * des NextButtons wird eine neue Generation berechnet, der
     * Generationenzaehler aktualisiert sowie das Fenster mit den Zellen neu
     * gezeichnet.
     */
    class NextButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent arg0) {
            model.next();
            genLabel.setText("Generation: " + model.getGenerations());
            gridJPanel.repaint();
        }
    }

    /**
     * Der StartButtonListener fuegt folgende Funktionalitaet hinzu: Es wird
     * zwischen dem Zustand <b>Start</b> und <b>Stop</b> unterschieden: <br>
     * Bei <u> Start </u> werden fortlaufend neue Generationen berechnet, sowie
     * der Generationenzaehler aktualisiert und neu gezeichnet. Die Auswahl von
     * Figuren sowie der Next Button werden deaktiviert<br>
     * Bei <u> Stop </u> wird die Berechnung angehalten, NextButton und Figuren
     * auswahl wieder aktiviert.
     * 
     */
    class StartButtonListener implements ActionListener {

        /**
         * Speichert ob der Starbutton noch gedrueckt ist
         */
        private boolean startPressed;

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // Start Funktionalitaet:
            if (btnStart.getText().equals("Start")) {

                // Deaktivieren anderer Elemente, in den Zustand Stop wechseln:
                startPressed = true;
                btnStart.setText("Stop");
                btnNext.setEnabled(false);
                shapeComboBox.setEnabled(false);

                // Neuen Thread anlegen, der Generationen berechnet und neu
                // zeichnet:
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (startPressed) {
                            model.next();
                            try {
                                Thread.sleep(currentSpeed);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            genLabel.setText("Generation: "
                                    + model.getGenerations());
                            gridJPanel.repaint();
                        }
                    }
                });

                // Thread starten
                t1.start();
            } else {
                // Stop Funktionalitaet:
                // Button wieder in den Zustand "Start" setzen:
                startPressed = false;
                btnStart.setText("Start");
                btnNext.setEnabled(true);
                shapeComboBox.setEnabled(true);
            }
        }
    }

    /**
     * Der ShapeComboBoxListene fuegt folgende Funktionalitaet hinzu: Einfuegen
     * von Figuren (Shapes) in das Spielfeld sowie Fehlermeldung (Popup) wenn
     * das Spielelfd zu klein ist.
     * 
     */
    class ShapeComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String errMsg = "Cannot insert shape, because there are not enough"
                    + " free grids. Solution: enlarge the Window or"
                    + " make the size of the grids smaller!";

            // Suche die entsprechende Figur und fuege sie dann ein:
            for (int i = 0; i < Figure.values().length; i++) {
                if (Figure.values()[i] == shapeComboBox.getSelectedItem()) {
                    if (!Figure.insertFigure(Figure.values()[i], model)) {
                        JOptionPane.showMessageDialog(null, errMsg,
                                "Shape insert failed",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Generationenzaehler zuruecksetzen
                        genLabel.setText("Generation: 0");
                    }
                }
            }
            gridJPanel.repaint();
        }
    }

    /**
     * Der SizeComboBoxListener fuegt folgende Funktionalitaet hinzu: Aendern
     * der Groeße der einzelnen Zellen und Neuzeichnen.
     */
    class SizeComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Unterscheidung je nach ausgewaechlter Option
            switch ((String) sizeComboBox.getSelectedItem()) {
            case "big":
                gridJPanel.setLength(SIZE_BIG);
                break;
            case "medium":
                gridJPanel.setLength(SIZE_MEDIUM);
                break;
            case "small":
                gridJPanel.setLength(SIZE_SMALL);
                break;
            default:
            }
            gridJPanel.resize();
        }
    }

    /**
     * Der SpeedComboBoxListener fuegt folgende Funktionalitaet hinzu: Aendern
     * der Geschwindigkeit der automatischen Berechnung der naechsten
     * Generation.
     * 
     */
    class SpeedComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Unterscheidung je nach ausgewaechlter Option
            switch ((String) speedComboBox.getSelectedItem()) {
            case "fast":
                currentSpeed = SPEED_FAST;
                break;
            case "medium":
                currentSpeed = SPEED_MEDIUM;
                break;
            case "slow":
                currentSpeed = SPEED_SLOW;
                break;
            default:
            }
        }
    }

    /**
     * Der GridJPanelMouseListener fuegt folgende Funktionalitaet hinzu: Die
     * entsprechende Zelle (worauf die Maus zeigt) lebendig oder tot setzen, je
     * nach zuvor gespeichertem Zustand (siehe alive)
     * 
     */
    class GridJPanelMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent arg0) {

            // Koordinaten der Maus umrechnen zu Position der Zelle
            int x = arg0.getX() / (gridJPanel.getLength() + 1);
            int y = arg0.getY() / (gridJPanel.getLength() + 1);

            alive = model.isAlive(x, y);
            model.setAlive(x, y, !alive);
            gridJPanel.repaint();
        }
    }

    /**
     * Der GridJPanelMouseListener fuegt folgende Funktionalitaet hinzu: Durch
     * Bewegung der Maus automatisch mehrere Zellen tot oder lebendig setzen, je
     * nach zuvor gespeichertem Zustand (siehe alive)
     * 
     */
    class GridJPanelMouseMotionListener extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent arg0) {

            // Koordinaten der Maus umrechnen zu Position der Zelle
            int x = arg0.getX() / (gridJPanel.getLength() + 1);
            int y = arg0.getY() / (gridJPanel.getLength() + 1);

            model.setAlive(x, y, !alive);
            gridJPanel.repaint();
        }
    }
    
    

}
