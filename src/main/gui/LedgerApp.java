package gui;

import model.Ledger;
import persistence.Writer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

//Represents the main window in which the ledger is launched
public class LedgerApp extends JFrame {

    public static final int WIDTH = 850;
    public static final int HEIGHT = 550;
    public static final int BORDER = 10;

    private static final String RECOVER_FILE = "./data/recover.txt";

    public static Ledger myLedger;
    private InputPanel inputPanel;
    private OutputPanel outputPanel;

    // EFFECTS: constructs main window
    public LedgerApp() {
        super("LedgerApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
        setLayout(new BorderLayout());

        myLedger = new Ledger();

        outputPanel = new OutputPanel();
        inputPanel = new InputPanel(outputPanel);
        add(inputPanel, BorderLayout.WEST);
        add(outputPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: launches the app
    public static void main(String[] args) {
        new LedgerApp();
    }

    // EFFECTS: auto saves a recover file when window closes
    @Override
    protected void processWindowEvent(WindowEvent w) {
        if (w.getID() == WindowEvent.WINDOW_CLOSING) {
            try {
                Writer writer = new Writer(new File(RECOVER_FILE));
                writer.write(myLedger);
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            super.processWindowEvent(w);
        }
    }
}
