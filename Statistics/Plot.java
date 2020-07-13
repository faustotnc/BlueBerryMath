package BlueBerryMath.Statistics;

import java.awt.*;
import javax.swing.*;

public class Plot extends JFrame {
    JPanel thePanel = new JPanel();

    public Plot() {
        this.setSize(600, 400);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        // Centers the window in the middle of the screen
        int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2) - (this.getHeight() / 2);
        this.setLocation(xPos, yPos);
        // Sets the title
        this.setTitle("Sample Plot - Histogram");
        // Makes the window non-resizable
        this.setResizable(false);
        // Quits the window when the close button is clicked
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creates the panels/components
        createLabel();
        createButton();

        this.add(thePanel);
        this.setVisible(true); // Shows the window
    }


    public void createLabel() {
        JLabel label1 = new JLabel("Tell me something");

        label1.setText("New Text");
        label1.setToolTipText("Does not do anything...");

        thePanel.add(label1);
    }


    public void createButton() {
        JButton button1 = new JButton("Send");

        button1.setText("New Button");
        button1.setToolTipText("A button");

        thePanel.add(button1);
    }
}
