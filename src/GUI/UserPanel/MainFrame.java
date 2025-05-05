package GUI.UserPanel;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel menuPanel;

    public MainFrame() {
        setTitle("Stress Portal");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);



        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        

        // Thêm các panel vào CardLayout
        mainPanel.add(new LoginPanel(this), "LoginPanel");
        mainPanel.add(new HomePanel(this), "HomePanel");
        mainPanel.add(new ScorePanel(this), "ScorePanel");
        mainPanel.add(new QuestionList(this), "QuestionList");
        add(mainPanel);

        // Hiển thị LoginPanel ban đầu
        cardLayout.show(mainPanel, "LoginPanel");
        
        
        setVisible(true);
    }

    // Phương thức để chuyển đổi giữa các panel
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    
    public void addPanel(JPanel panel, String panelName) {
        mainPanel.add(panel, panelName);
    }
   
}