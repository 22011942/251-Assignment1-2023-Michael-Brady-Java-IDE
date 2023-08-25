import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.fife.ui.rtextarea.*;
public class SearchWord extends JFrame implements ActionListener {
    private JTextField searchField;
    public SearchWord() {

        JPanel cp = new JPanel(new BorderLayout());

        // Create a toolbar
        JToolBar toolBar = new JToolBar();
        searchField = new JTextField(30);
        final JButton nextButton = new JButton("Find Next");
        nextButton.setActionCommand("FindNext");
        nextButton.addActionListener(this);
        toolBar.add(searchField);
        toolBar.add(nextButton);
        searchField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextButton.doClick(0);
            }
        });

        JButton prevButton = new JButton("Find Previous");
        prevButton.setActionCommand("FindPrev");
        prevButton.addActionListener(this);
        toolBar.add(prevButton);
        cp.add(toolBar, BorderLayout.NORTH);

        setContentPane(cp);
        setTitle("Word Search");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        boolean forward = "FindNext".equals(command);

        // Make search parameters
        String text = searchField.getText();
        if (text.length() == 0) {
            return;
        }

        SearchContext context = new SearchContext();
        context.setSearchFor(text);
        context.setSearchForward(forward);
        context.setSearchWrap(true);
        context.setWholeWord(false);

        boolean found = SearchEngine.find(Gui.textArea, context).wasFound();
        if (!found) {
            JOptionPane.showMessageDialog(this, "Text not found");
        }
    }
}
