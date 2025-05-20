package GUI.UserPanel;

import DTO.UserDTO;
import GUI.MakeColor.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class ScorePanel extends JPanel {
    private MainFrame mainFrame;
    private UserDTO user;
    private float score;
    private String time;
    private JPanel contentPanel;

    public ScorePanel(MainFrame mainFrame, UserDTO user, float score, String time) {
        this.mainFrame = mainFrame;
        this.user = user;
        this.score = score;
        this.time = time;
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        int panelWidth = 600;
        int panelHeight = 500;

        // Main score panel
        RoundedPanel scorePanel = new RoundedPanel(80);
        scorePanel.setLayout(null);
        scorePanel.setBackground(Color.WHITE);
        scorePanel.setSize(panelWidth, panelHeight);
        add(scorePanel);

        // Score Title
        JLabel scoreTitle = new JLabel("Điểm", SwingConstants.CENTER);
        scoreTitle.setFont(new Font("Arial", Font.BOLD, 70));
        scoreTitle.setForeground(Color.BLACK);
        scoreTitle.setBounds(0, 30, panelWidth, 80);
        scorePanel.add(scoreTitle);

        // Score Display
        JLabel scoreLabel = new JLabel(String.format("%.2f", score), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 90));
        scoreLabel.setBounds(0, 120, panelWidth, 100);

        if (score < 5) {
            scoreLabel.setForeground(Color.RED);
        } else if (score < 8) {
            scoreLabel.setForeground(Color.ORANGE);
        } else {
            scoreLabel.setForeground(Color.green); // greenish
        }

        scorePanel.add(scoreLabel);

        // Time Display
        JLabel timer = new JLabel("Thời gian: " + time, SwingConstants.CENTER);
        timer.setFont(new Font("Arial", Font.PLAIN, 30));
        timer.setForeground(Color.DARK_GRAY);
        timer.setBounds(0, 240, panelWidth, 40);
        scorePanel.add(timer);

        // Exit Button
        JButton exitButton = ButtonFactory.createConfirmButton(mainFrame, 0, 0, 0, 0, 0, Ulti.BananaLeaf, e -> {
            mainFrame.addPanel(new HomePage(mainFrame, new MenuPanel(mainFrame)), "HomePanel");
            mainFrame.showPanel("HomePanel");
        });
        exitButton.setText("Thoát");
        exitButton.setFont(new Font("Arial", Font.BOLD, 26));
        exitButton.setForeground(Color.BLACK);
        exitButton.setBounds((panelWidth - 200) / 2, 350, 200, 60);
        scorePanel.add(exitButton);

        // Re-center on resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerPanel(scorePanel);
            }
        });

        centerPanel(scorePanel);
    }

    private void centerPanel(JPanel panel) {
        int x = (getWidth() - panel.getWidth()) / 2;
        int y = (getHeight() - panel.getHeight()) / 2;
        panel.setLocation(x, y);
    }
}
