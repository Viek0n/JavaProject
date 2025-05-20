package GUI.UserPanel;

import GUI.MakeColor.RoundedPanel;
import GUI.MakeColor.Ulti;
import MICS.Connect;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class HomePage extends JPanel {
    private JPanel menuPanel;
    private JLabel homeTitle, descLabel;
    private MainFrame mainFrame;

    public HomePage(MainFrame mainFrame, JPanel menuPanel) {
        this.mainFrame = mainFrame;
        this.menuPanel = menuPanel;
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        int panelWidth = 800;
        int panelHeight = 300;
        
        JPanel textPanel = new RoundedPanel(200);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setSize(panelWidth, panelHeight);
        centerPanel(textPanel);

        ImageIcon logoIcon = new ImageIcon(Connect.img + "logo.png"); // Adjust path if needed
        Image scaledImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        logoLabel.setAlignmentX(CENTER_ALIGNMENT);

        descLabel = new JLabel("Nơi áp lực thi cử được giải tỏa, không phải gia tăng!");
        descLabel.setFont(new Font("Arial", Font.BOLD, 26));
        descLabel.setForeground(Color.BLACK);
        descLabel.setAlignmentX(CENTER_ALIGNMENT);

        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(logoLabel);
        textPanel.add(Box.createVerticalStrut(15));
        textPanel.add(descLabel);
        textPanel.add(Box.createVerticalGlue());

        add(menuPanel);
        add(textPanel);

        // Recenter when window resizes
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerPanel(textPanel);
            }
        });
    }


    private void centerPanel(JPanel panel) {
        int menuWidth = 214;
        int remainingWidth = getWidth() - menuWidth;
        int x = menuWidth + (remainingWidth - panel.getWidth()) / 2;
        int y = (getHeight() - panel.getHeight()) / 2;
        panel.setLocation(x, y);
    }
}
