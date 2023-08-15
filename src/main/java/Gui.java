import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gui {
    private JFrame menu = new JFrame("Text editor");
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private JMenuBar buttonMenu = new JMenuBar();
    // This method returns a panel which holds all the buttons and their respective actions
    public JMenuBar buttonPanel() {
        JMenu FileMenu = new JMenu("File");
        JMenuItem NewItem = new JMenuItem("New");
        JMenuItem OpenItem = new JMenuItem("Open");
        JMenuItem SaveItem = new JMenuItem("Save");
        JMenuItem ExitItem = new JMenuItem("Exit");
        JMenu Search = new JMenu("Search");
        JMenu View = new JMenu("View");
        JMenu Manage = new JMenu("Manage");
        JMenu Help = new JMenu("Help");

        FileMenu.add(NewItem);
        FileMenu.add(OpenItem);
        FileMenu.add(SaveItem);
        FileMenu.add(ExitItem);

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
   // The gui which displays the text editor
    public void textEditor() {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        menu.add(buttonPanel(), BorderLayout.NORTH);
        menu.add(scrollPane, BorderLayout.CENTER);
        menu.setSize(800,600);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
