import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gui {
    //TODO fix field errors
    private JFrame menu = new JFrame("Text editor");
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private JMenuBar buttonMenu = new JMenuBar();
    public JMenuBar buttonMenu() {
        JMenu FileMenu = new JMenu("File");
        JMenuItem NewItem = new JMenuItem("New");
        JMenuItem OpenItem = new JMenuItem("Open");
        JMenuItem SaveItem = new JMenuItem("Save");
        JMenuItem ExitItem = new JMenuItem("Exit");
        JMenuItem PrintItem = new JMenuItem("Print");
        JMenuItem AboutItem = new JMenuItem("About");
        JMenu Search = new JMenu("Search");
        JMenu View = new JMenu("View");
        JMenu Manage = new JMenu("Manage");
        JMenu Help = new JMenu("Help");

        FileMenu.add(NewItem);
        FileMenu.add(OpenItem);
        FileMenu.add(SaveItem);
        FileMenu.add(ExitItem);
        Manage.add(PrintItem);
        View.add(AboutItem);

        AboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null ,"Developers: Michael, Brady\nAbout: This is Java program is a IDE text editor which fulfills most of the functions of a conventional IDE", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //This is the print feature it uses the printable and printerjob libraries
        PrintItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrinterJob printJob = PrinterJob.getPrinterJob();
                Printable printable = (graphics, pageFormat, pageIndex) -> {
                    if (pageIndex > 0) {
                        return Printable.NO_SUCH_PAGE;
                    }
                    //Graphics 2d is responsible for the visual aspects of the page
                    Graphics2D graphic2d = (Graphics2D) graphics;
                    graphic2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                    textArea.print(graphic2d);
                    return Printable.PAGE_EXISTS;
                };
                printJob.setPrintable(printable);

                if (printJob.printDialog()) {
                    try {
                        printJob.print();
                    } catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        });
        SaveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser saveFile = new JFileChooser();
                saveFile.setCurrentDirectory(new File("C:"));
                int answer = saveFile.showSaveDialog(null);

                if (answer == JFileChooser.APPROVE_OPTION) {
                    File savedFile;
                    PrintWriter output = null;
                    savedFile = new File(saveFile.getSelectedFile().getAbsolutePath());
                    try {
                        output = new PrintWriter(savedFile);
                        output.println(textArea.getText());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } finally {
                        output.close();
                    }
                }
            }
        });

        //Open button action listener
        OpenItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenFile openFile = new OpenFile();
                openFile.openFile();
                textArea.setText(null);
                textArea.append(openFile.getText());
            }
        });

        //This is the new button, when pressed it opens a new window
        NewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui call = new Gui();
                call.textEditor();
            }
        });
        //This is the exit button, when pressed it closes all windows
        ExitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonMenu.add(FileMenu);
        buttonMenu.add(Search);
        buttonMenu.add(View);
        buttonMenu.add(Manage);
        buttonMenu.add(Help);
        return buttonMenu;
    }

    private String time() {
        LocalDateTime timeAndDate = LocalDateTime.now();
        DateTimeFormatter formatTimeAndDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String data = timeAndDate.format(formatTimeAndDate);

        return data;
    }

    private String OSData() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osData = osName + " " + osVersion;
        return osData;
    }


   // The gui which displays the text editor
    public void textEditor() {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        menu.add(buttonMenu(), BorderLayout.NORTH);
        String data = OSData() + "    " + time();
        textArea.insert(data, 0);
        menu.add(scrollPane, BorderLayout.CENTER);
        menu.setSize(800,600);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
