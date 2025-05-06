package GUI.UserPanel;


import GUI.MakeColor.*;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class TestList  extends  JPanel{
        private JPanel menuPanel;
    private MainFrame mainFrame; // Tham chiếu đến MainFrame
    public TestList(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        init();
    }
    private void init(){
        setLayout(null);
        setBackground(Ulti.MainColor);

        menuPanel = new JPanel();
        menuPanel.setBackground(Color.white);
        menuPanel.setBounds(0, 0, 214, 900);
        menuPanel.setLayout(null);

        // Tạo nút "Quản Lý Câu Hỏi"
        JLabel questionB = new JLabel("Quản Lý Câu Hỏi", JLabel.CENTER);
        questionB.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        questionB.setBounds(7, 50, 200, 100);
        questionB.setOpaque(false);
        JButton questionButton = ButtonFactory.createConfirmButton(mainFrame,32, 7, 50, 200, 100,Ulti.BananaLeaf, e -> {
            // Chuyển sang QuestionListPanel
            mainFrame.showPanel("QuestionListPanel");
        });

        // Tạo nút "Quản Lý Bài kiểm tra"
        JLabel testB = new JLabel("Quản Lý Bài kiểm tra", JLabel.CENTER);
        testB.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        testB.setBounds(7, 170, 200, 100);
        testB.setOpaque(false);

        JButton testButton = ButtonFactory.createConfirmButton(mainFrame,32, 7,170, 200, 100,Ulti.BananaLeaf, e -> {
            // Chuyển sang QuestionListPanel
            mainFrame.showPanel("TestListPanel");
        });


            // Tạo nút "Quản Lý Người dùng"
        JLabel userB = new JLabel("Quản Lý Người dùng", JLabel.CENTER);
        userB.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        userB.setBounds(7, 290, 200, 100);
        userB.setOpaque(false);
        JButton userButton = ButtonFactory.createConfirmButton(mainFrame,32, 7, 290, 200, 100,Ulti.BananaLeaf, e -> {
            // Chuyển sang QuestionListPanel
            mainFrame.showPanel("UserListPanel");
        });
        userButton.setBackground(Color.green);

        add(questionB);
        add(questionButton);
        add(testB);
        add(testButton);
        add(userB);
        add(userButton);
        
        add(menuPanel);
    }
}
