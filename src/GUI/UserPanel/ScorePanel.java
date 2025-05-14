package GUI.UserPanel;

import DTO.UserDTO;
import GUI.MakeColor.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class ScorePanel extends JPanel {
    private MainFrame mainFrame; // Tham chiếu đến MainFrame
    private UserDTO user;
    private float score;
    private String time;
    private JPanel contentPanel;

    public ScorePanel(MainFrame mainFrame, UserDTO user, float score, String time) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        this.user = user;
        this.score = score;
        this.time = time;
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        RoundedPanel scorePanel = new RoundedPanel(109);
        scorePanel.setBounds(400, 25, 850, 800);
        
        
        JLabel scoreTitle = new JLabel("Điểm");
        scoreTitle.setFont(new Font("Arial", Font.BOLD, 100));
        scoreTitle.setBounds(710, 114, 467, 120);
        add(scoreTitle);

        JLabel scoreLabel = new JLabel(Float.toString(score));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 100));
        scoreLabel.setBounds(750, 295, 467, 120);
        add(scoreLabel);

        JLabel underline = new JLabel();
        underline.setBounds(477, 360, 700, 5);
        underline.setBackground(Color.BLACK);
        add(underline); 

        JLabel Timer = new JLabel("Thời Gian: " + time);
        Timer.setFont(new Font("Arial", Font.BOLD, 32));
        Timer.setBounds(700, 595, 500, 88);
        Timer.setForeground(Color.BLACK);
        add(Timer);

        JButton CheckButton = ButtonFactory.createConfirmButton(mainFrame, 20, 700, 679, 250, 75,Ulti.BananaLeaf,e -> {
            mainFrame.addPanel(new HomePage(mainFrame, new MenuPanel(mainFrame,contentPanel)), "HomePanel");
            mainFrame.showPanel("HomePanel");
        });
        CheckButton.setText("Thoát");
        CheckButton.setFont(new Font("Arial", Font.BOLD, 28));
        CheckButton.setForeground(Color.BLACK);
        add(CheckButton);
        add(scorePanel);
    }
}