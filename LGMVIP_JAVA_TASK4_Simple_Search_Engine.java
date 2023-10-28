import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class Main extends JFrame implements ActionListener, HyperlinkListener {
    private JTextField addressBar;
    private JLabel label;
    private JEditorPane pane;

    Main() {
        super("Simple Search Engine");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        label = new JLabel();
        label.setText("Type full link here:-");

        addressBar = new JTextField();
        addressBar.setPreferredSize(new Dimension(600, addressBar.getPreferredSize().height));
        addressBar.addActionListener(this);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(label);
        topPanel.add(addressBar);
        topPanel.add(searchButton);

        pane = new JEditorPane();
        pane.setEditable(false);
        pane.addHyperlinkListener(this);

        pane.setPreferredSize(new Dimension(800, 600));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(pane), BorderLayout.CENTER);

        add(mainPanel);
        setSize(new Dimension(660, 490));
    }

    public void actionPerformed(ActionEvent evt) {
        String url = addressBar.getText();

        try {
            pane.setPage(url);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void hyperlinkUpdate(HyperlinkEvent evt) {
        if (evt.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
            return;
        }

        if (evt instanceof HTMLFrameHyperlinkEvent) {
            HTMLDocument doc = (HTMLDocument) pane.getDocument();
            doc.processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent) evt);
        } else {
            String url = evt.getURL().toString();
            addressBar.setText(url);

            try {
                pane.setPage(url);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        new Main().setVisible(true);
    }
}