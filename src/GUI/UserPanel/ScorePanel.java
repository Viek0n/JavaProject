package GUI.UserPanel;

import GUI.MakeColor.*;
import MICS.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class ScorePanel extends JPanel {
    private JPanel menuPanel;

    private MainFrame mainFrame; // Tham chiếu đến MainFrame

    public ScorePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        menuPanel = new JPanel();
        menuPanel.setBackground(Color.white);
        menuPanel.setBounds(0, 0, 214, 900);
        menuPanel.setLayout(null);

        JLabel graduation = AddImage.createImageLabel(Connect.img + "profile-user.png", 50, 50, 100, 100);
        menuPanel.add(graduation);
        JLabel test = AddImage.createImageLabel(Connect.img + "test.png", 80, 280, 80, 80);
        menuPanel.add(test);
        JLabel graduationLabel = AddImage.createImageLabel(Connect.img + "analysis.png", 80, 450, 80, 80);
        menuPanel.add(graduationLabel);

        JLabel logout = AddImage.createImageLabel(Connect.img+"logout.png", 57, 750, 100, 100);
        add(logout);
        // Nút quay lại LoginPanel
        JButton logoutButton = new JButton();
        logoutButton.setBounds(57, 750, 100, 100);
        logoutButton.setOpaque(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(e -> {
        // Tạo một LoginPanel mới
        LoginPanel newLoginPanel = new LoginPanel(mainFrame);

        // Thêm LoginPanel mới vào CardLayout
        mainFrame.addPanel(newLoginPanel, "LoginPanel");

        // Chuyển sang LoginPanel mới
        mainFrame.showPanel("LoginPanel");
        });

        RoundedPanel score = new RoundedPanel(109);
        score.setBounds(500, 25, 850, 850);
        
        
        JLabel scoreTitle = new JLabel("Score");
        scoreTitle.setFont(new Font("Arial", Font.BOLD, 100));
        scoreTitle.setBounds(755, 94, 467, 120);
        add(scoreTitle);

        JLabel underline = new JLabel();
        underline.setBounds(577, 264, 700, 5);
        underline.setBackground(Color.BLACK);

        add(underline); 

        JLabel Timer = new JLabel("Thời Gian:");
        Timer.setFont(new Font("Arial", Font.BOLD, 28));
        Timer.setBounds(841, 555, 174, 88);
        add(Timer);

        JButton CheckButton = ButtonFactory.createConfirmButton(mainFrame, 20, 800, 679, 250, 75,Ulti.BananaLeaf,e -> {
            mainFrame.showPanel("LoginPanel");
        });
        add(CheckButton);

        add(logoutButton);
        add(logout);
        add(menuPanel);
        add(score);

    }
}