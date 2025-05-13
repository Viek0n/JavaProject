package GUI.UserPanel;

import GUI.MakeColor.Ulti;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomePage extends JPanel {
    private JPanel menuPanel;
    private JLabel homeTitle, descLabel;
    private MainFrame mainFrame; // Tham chiếu đến MainFrame
    private int posx, posy;

    public HomePage(MainFrame mainFrame, JPanel menuPanel) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        this.menuPanel = menuPanel;
        posx = 525;
        posy = 300;
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        homeTitle = new JLabel("Stress Portal", SwingConstants.CENTER);
        homeTitle.setFont(new Font("Arial", Font.BOLD, 60));
        homeTitle.setForeground(Color.BLACK);
        homeTitle.setBounds(posx, posy, 808, 155);

        descLabel = new JLabel("Stress Portal", SwingConstants.CENTER);
        descLabel.setFont(new Font("Arial", Font.BOLD, 30));
        descLabel.setForeground(Color.BLACK);
        descLabel.setBounds(posx, posy+50, 808, 155);

        add(homeTitle);
        add(descLabel);
        add(menuPanel);
    }
}
