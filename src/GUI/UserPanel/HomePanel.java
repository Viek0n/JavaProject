package GUI.UserPanel;

import BLL.ExamBLL;
import BLL.ExamStructBLL;
import GUI.MakeColor.*;
import MICS.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomePanel extends JPanel {
    private JPanel menuPanel;
    private JLabel homeTitle;
    private RoundedTextField maBaiKT;
    private JLabel textintheBox;
    private MainFrame mainFrame; // Tham chiếu đến MainFrame

    public HomePanel(MainFrame mainFrame) {
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

        JLabel profile = AddImage.createImageLabel(Connect.img + "profile-user.png", 50, 50, 100, 100);
        menuPanel.add(profile);

        JLabel test = AddImage.createImageLabel(Connect.img + "test.png", 80, 280, 80, 80);
        menuPanel.add(test);
        JLabel analysis = AddImage.createImageLabel(Connect.img + "analysis.png", 80, 450, 80, 80);
        menuPanel.add(analysis);

        JButton analysisButton = ButtonFactory.createClearButton(mainFrame, 80, 450, 80, 80, e -> {
            // Chuyển sang AnalysisPanel
            
            mainFrame.showPanel("ScorePanel");
        });
        menuPanel.add(analysisButton);

        homeTitle = new JLabel("Stress Portal", SwingConstants.CENTER);
        homeTitle.setFont(new Font("Arial", Font.BOLD, 128));
        homeTitle.setBounds(525, 160, 808, 155);

        // Nhập mã bài kiểm tra
        textintheBox = new JLabel("Nhập mã bài kiểm tra", SwingConstants.CENTER);
        textintheBox.setFont(new Font("Arial", Font.BOLD, 32));

        maBaiKT = new RoundedTextField(45); // Bo tròn với bán kính 30
        maBaiKT.setFont(new Font("Arial", Font.PLAIN, 50));
        maBaiKT.setBounds(418, 462, 1000, 100);
        maBaiKT.setBackground(Color.white);
        maBaiKT.setForeground(Color.black);
        maBaiKT.setCaretColor(Color.black);
        maBaiKT.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        maBaiKT.setOpaque(false); // Để vẽ nền tùy chỉnh

        JButton confirmButton = ButtonFactory.createConfirmButton(mainFrame,42,717,648,400,100,Ulti.BananaLeaf, e  -> {
            String testCode = maBaiKT.getText().trim();
            if (testCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã bài kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                mainFrame.addPanel(new TakeExam(new ExamBLL().gen(mainFrame.userBLL.getCurrent().getLoginName(), new ExamStructBLL().get(testCode)),mainFrame), "takeExam");
                mainFrame.showPanel("takeExam");
            }
            
        });
        confirmButton.setText("Xác Nhận");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 38));
        confirmButton.setForeground(Color.BLACK);

        JLabel logout = AddImage.createImageLabel(Connect.img+"logout.png", 57, 750, 100, 100);
        add(logout);
        // Nút quay lại LoginPanel
        JButton logoutButton = ButtonFactory.createLogoutButton(mainFrame);

        add(logoutButton);
        add(confirmButton);
        add(logout);
        add(homeTitle);
        add(textintheBox);
        add(maBaiKT);
        add(menuPanel);
    }
}