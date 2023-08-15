import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class OpenFile {
    Scanner scanner = null;
    String line = null;
    String text = "";
    public void openFile() {

        //Sets the File Directory Path
        //Filters to only show txt files
        final JFileChooser fileChooser = new JFileChooser("c:");
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Normal Text File (*.txt)", "txt");
        fileChooser.setFileFilter(txtFilter);

        //Opens the file directory allowing the user to select a file to open
        int select = fileChooser.showOpenDialog(null);
        if (select == JFileChooser.APPROVE_OPTION) {

            //Sets file to the selected files path
            File file = fileChooser.getSelectedFile();
            //Reads through the file
            try {
                scanner = new Scanner(new BufferedReader(new FileReader(file)));
                try {
                    while (scanner.hasNext()) {
                        line = scanner.nextLine();

                        // Breaks the loop if there are no more lines in the text file
                        if (line == null) {
                            break;
                        } else {
                            // Merges the text file into one large string, so it can be sent back to Gui class
                            text = text + line + "\n";
                        }
                    }
                    // Stops the scanner
                } finally {
                    if (scanner!= null) {
                        scanner.close();
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getText() {
        return text;
    }
}