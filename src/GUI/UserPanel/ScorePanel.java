package GUI.UserPanel;

import DTO.UserDTO;
import GUI.MakeColor.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
    private MainFrame mainFrame; // Tham chiếu đến MainFrame
    private UserDTO user;
    private float score;
    private String time;

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
        scoreTitle.setBounds(710, 174, 467, 120);
        scoreTitle.setForeground(Color.BLACK);
        add(scoreTitle);

        JLabel scoreLabel = new JLabel(Float.toString(score));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 100));
        scoreLabel.setBounds(750, 355, 467, 120);
        if(score < 5){
            scoreLabel.setForeground(Color.RED);
        }else if(score < 8){
            scoreLabel.setForeground(Color.yellow);
        }
        else{
            scoreLabel.setForeground(Color.green);
        }
        add(scoreLabel);

        JLabel Timer = new JLabel("Thời Gian: " + time);
        Timer.setFont(new Font("Arial", Font.BOLD, 32));
        Timer.setBounds(700, 525, 500, 88);
        Timer.setForeground(Color.BLACK);
        add(Timer);

        JButton CheckButton = ButtonFactory.createConfirmButton(mainFrame, 20, 700, 609, 250, 75,Ulti.BananaLeaf,e -> {
            mainFrame.addPanel(new HomePage(mainFrame, new MenuPanel(mainFrame)), "HomePanel");
            mainFrame.showPanel("HomePanel");
        });
        CheckButton.setText("Thoát");
        CheckButton.setFont(new Font("Arial", Font.BOLD, 28));
        CheckButton.setForeground(Color.BLACK);
        add(CheckButton);
        add(scorePanel);
    }
}