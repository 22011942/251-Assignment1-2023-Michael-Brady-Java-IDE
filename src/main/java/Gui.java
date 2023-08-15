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
    private JPanel buttonPanel = new JPanel();
    // This method returns a panel which holds all the buttons and their respective actions
    public JPanel buttonPanel() {
        JButton File = new JButton("File");
        JButton New = new JButton("New");
        JButton Open = new JButton("Open");
        JButton Save = new JButton("Save");
        JButton Search = new JButton("Search");
        JButton View = new JButton("View");
        JButton Manage = new JButton("Manage");
        JButton Help = new JButton("Help");
        JPopupMenu dropbox = new JPopupMenu();

        dropbox.add(New);
        dropbox.add(Open);
        dropbox.add(Save);

        //Open button action listener
        Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenFile openFile = new OpenFile();
                openFile.openFile();
                textArea.setText(null);
                textArea.append(openFile.getText());
            }
        });

        File.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropbox.show(File, 0, File.getHeight());
            }
        });
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(File);
        buttonPanel.add(Search);
        buttonPanel.add(View);
        buttonPanel.add(Manage);
        buttonPanel.add(Help);
        return buttonPanel;
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
