import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;




public class Gui {
    private final JFrame menu = new JFrame("Text editor");
    public static RSyntaxTextArea textArea = new RSyntaxTextArea();
    private final RTextScrollPane  scrollPane = new RTextScrollPane (textArea);
    private final JMenuBar buttonMenu = new JMenuBar();
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

        FileMenu.add(NewItem);
        FileMenu.add(OpenItem);
        FileMenu.add(SaveItem);
        FileMenu.add(ExitItem);
        Manage.add(PrintItem);
        View.add(AboutItem);

        AboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null ,"Developers: Michael, Brady\nAbout: This is Java program is a IDE text editor which fulfills most of the functions of a conventional IDE", "About", JOptionPane.INFORMATION_MESSAGE));

        //This is the print feature it uses the Printable and PrinterJob libraries
        PrintItem.addActionListener(e -> {
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
        });
        //This the save function when checks if you are just saving as .txt or as a .pdf
        SaveItem.addActionListener(e -> {
            JFileChooser saveFile = new JFileChooser();
            saveFile.setCurrentDirectory(new File("C:"));
            int answer = saveFile.showSaveDialog(null);

            if (answer == JFileChooser.APPROVE_OPTION) {
                File savedFile;
                PrintWriter output = null;
                savedFile = new File(saveFile.getSelectedFile().getAbsolutePath());
                //Here it check whether you are trying to save the file as a pdf
                if (savedFile.getName().toLowerCase().endsWith(".pdf")) {
                    savedFile = new File(savedFile.getAbsolutePath() + ".pdf");
                    try {
                        PDDocument document = new PDDocument();
                        PDPage page = new PDPage();
                        document.addPage(page);
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.COURIER, 12);
                        contentStream.newLineAtOffset(50, 750);
                        contentStream.setLeading(14);
                        //Cleans up all the texts and changes tabs into spaces so the font can comprehend it
                        String cleanedText = textArea.getText().replace("\t", "    ");
                        //Loop that gets each line from the text and inserts it into the pdf
                        for (String line : cleanedText.split("\\n")) {
                            contentStream.showText(line);
                            contentStream.newLine();
                        }

                        contentStream.endText();
                        contentStream.close();
                        document.save(savedFile);
                        document.close();

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        //this is the normal output of the save when you just do .txt
                        output = new PrintWriter(savedFile);
                        output.println(textArea.getText());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } finally {
                        Objects.requireNonNull(output).close();
                    }
                }
            }
        });

        //Open button action listener
        OpenItem.addActionListener(e -> {
            OpenFile openFile = new OpenFile();
            openFile.openFile();
        });

        //Search
        Search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Start all Swing applications
                SwingUtilities.invokeLater(() -> {
                    try {
                        String laf = UIManager.getSystemLookAndFeelClassName();
                        UIManager.setLookAndFeel(laf);
                    } catch (Exception e12) { /* never happens */ }
                    SearchWord searchWord = new SearchWord();
                    searchWord.setVisible(true);
                    textArea.requestFocusInWindow();
                });
            }
        });

        //This is the new button, when pressed it opens a new window
        NewItem.addActionListener(e -> {
            Gui call = new Gui();
            call.textEditor();
        });
        //This is the exit button, when pressed it closes all windows
        ExitItem.addActionListener(e -> System.exit(0));
        buttonMenu.add(FileMenu);
        buttonMenu.add(Search);
        buttonMenu.add(View);
        buttonMenu.add(Manage);
        return buttonMenu;
    }
    //Gets the local date and time
    private String time() {
        LocalDateTime timeAndDate = LocalDateTime.now();
        DateTimeFormatter formatTimeAndDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return timeAndDate.format(formatTimeAndDate);
    }
    //Gets the System name and version
    private String OSData() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        return "//" + osName + " " + osVersion;
    }



   // The gui which displays the text editor
    public void textEditor() {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);

        textArea.setMarkOccurrences(true);
        textArea.setMarkOccurrencesDelay(1);

        LanguageSupportFactory.get().register(textArea);

        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        menu.add(buttonMenu(), BorderLayout.NORTH);
        String data = OSData() + "    " + time();
        textArea.insert(data, 0);
        menu.add(scrollPane, BorderLayout.CENTER);
        menu.setSize(800,600);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
