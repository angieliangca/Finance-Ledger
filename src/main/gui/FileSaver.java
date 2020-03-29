package gui;

import persistence.Writer;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

// Represents the file saver in which user can save data
public class FileSaver {

    private static final String LEDGER_FILE = "./data/myLedger.txt";

    // EFFECTS: constructs the file saver
    public FileSaver() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setCurrentDirectory(new File(LEDGER_FILE));

        int returnVal = fc.showSaveDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                Writer writer = new Writer(file);
                writer.write(LedgerApp.myLedger);
                writer.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(new JFrame("Message"), "Please save in a valid path.",
                        "Save Failed", JOptionPane.PLAIN_MESSAGE);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
