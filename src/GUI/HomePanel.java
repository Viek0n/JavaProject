package GUI;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomePanel{

    private int FrameWidth = 1600;
    private int FrameHeight = 900;

    JFrame homeFrame;
    JPanel homePanel;
    JPanel menuPanel;
    JLabel homeTitle;
    RoundedTextField maBaiKT;
    JLabel textintheBox;
    RoundedButton confirmButton;
    JLabel buttonTitle;
    
    // test Place
    JPanel TestPanel;

    public HomePanel(){
        init();
    }
    private void init(){
        homeFrame = new JFrame();
        homeFrame.setSize(FrameWidth,FrameHeight);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setResizable(false);

        homePanel = new JPanel();
        homePanel.setBackground(Ulti.MainColor);
        homePanel.setLayout(null);

        menuPanel = new JPanel();
        menuPanel.setBackground(Color.white);
        menuPanel.setBounds(0,0,214,900);
        menuPanel.setLayout(null);
        
        JLabel graduation = AddImage.createImageLabel("D:\\Project\\JavaProject\\res\\profile-user.png", 50, 50, 100, 100);
        menuPanel.add(graduation);
        JLabel test = AddImage.createImageLabel("D:\\Project\\JavaProject\\res\\test.png", 80, 280, 80, 80);
        menuPanel.add(test);
        JLabel graduationLabel = AddImage.createImageLabel("D:\\Project\\JavaProject\\res\\analysis.png", 80, 450, 80, 80);
        menuPanel.add(graduationLabel);


        homeTitle = new JLabel("Stress Portal",SwingConstants.CENTER);
        homeTitle.setFont(new Font("Arial",Font.BOLD,128));
        homeTitle.setBounds(525,160,808,155);

        //Nhập mã bài kiểm tra
        textintheBox = new JLabel("Nhập mã bài kiểm tra",SwingConstants.CENTER);
        textintheBox.setFont(new Font("Arial",Font.BOLD,32));       

        maBaiKT = new RoundedTextField(45); // Bo tròn với bán kính 30
        maBaiKT.setFont(new Font("Arial", Font.PLAIN, 50));
        maBaiKT.setBounds(418, 462, 1000, 100);
        maBaiKT.setBackground(Color.white);
        maBaiKT.setForeground(Color.black);
        maBaiKT.setCaretColor(Color.black);
        maBaiKT.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        maBaiKT.setOpaque(false); // Để vẽ nền tùy chỉnh

        buttonTitle = new JLabel("Xác Nhận",SwingConstants.CENTER);
        buttonTitle.setFont(new Font("Arial",Font.BOLD,38));
        buttonTitle.setBounds(717,648,400,100);
        
        confirmButton = new RoundedButton("",42);
        confirmButton.setBounds(717,648,400,100);
        confirmButton.setBackground(Ulti.BananaLeaf);

        homePanel.add(buttonTitle);
        homePanel.add(textintheBox);
        homePanel.add(confirmButton);
        homePanel.add(maBaiKT);
        homePanel.add(homeTitle);
        // Test Place
        TestPanel = new RoundedPanel(42);





        homeFrame.add(menuPanel);
        homeFrame.add(homePanel);
        homeFrame.setVisible(true);
    }
    public static void main(String[] args) {
        HomePanel hp = new HomePanel();
    }
}
