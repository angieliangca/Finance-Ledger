package gui;

import exceptions.DuplicateItemException;
import persistence.Reader;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

// Represents the file chooser in which user can load data
public class FileChooser extends JFrame {

    private JFileChooser fc;
    private static final String LEDGER_FILE = "./data/myLedger.txt";

    // EFFECTS: constructs the file chooser
    public FileChooser() {
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setCurrentDirectory(new File(LEDGER_FILE));

        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                LedgerApp.myLedger = Reader.readLedger(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(new JFrame("Message"), "Please select a .txt file.",
                        "Load Failed", JOptionPane.PLAIN_MESSAGE);
                new FileChooser();
            } catch (DuplicateItemException ex) {
                ex.printStackTrace();
            }
        }
    }
}
