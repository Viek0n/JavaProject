package GUI;
import BLL.UserBLL;
import MICS.Connect;
import MICS.Enums;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
public class LoginPanel{
    private int FrameWidth = 1600;
    private int FrameHeight = 900;


    Color MainColor = Ulti.MainColor;
    Color BananaLeaf = Ulti.BananaLeaf;
    JTextField mssvField;
    // JLabel mssvTitle;
    JTextField passField;
    RoundedButton confirmButton;
    JLabel buttonTitle;
    JLabel buttonTitle2;
    JLabel backIcon;
    JButton backbutton;

    JLabel loginTitle;
    RoundedPanel loginPanel;
    JFrame MainFrame;
    JPanel mainPanel;
    public LoginPanel() {
        init();
    }
    
    

    private void init(){
        UserBLL userBLL = new UserBLL();
        MainFrame = new JFrame("Stress Portal");
        MainFrame.setSize(FrameWidth,FrameHeight);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.setLocationRelativeTo(null);
        MainFrame.setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setBackground(MainColor);
        mainPanel.setLayout(null);

    //khung trắng
        loginPanel = new RoundedPanel(100);
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBounds(300, 211, 1000, 500);
        
        loginTitle = new JLabel("Đăng Nhập",SwingConstants.CENTER);
        loginTitle.setFont(new Font("Inter",Font.BOLD,50));
        loginTitle.setBounds(330,10,340,60);

        mssvField = new RoundedTextField(45); // Bo tròn với bán kính 30
        mssvField.setFont(new Font("Arial", Font.PLAIN, 28));
        mssvField.setBounds(70, 180, 858, 56);
        mssvField.setBackground(Color.white);
        mssvField.setForeground(Color.black);
        mssvField.setCaretColor(Color.black); // Màu con trỏ
        mssvField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        mssvField.setOpaque(false);

       
        /* 
        mssvTitle = new JLabel("MSSV",SwingConstants.LEFT);
        mssvTitle.setFont(new Font("Inter",Font.ITALIC,32));
        mssvTitle.setBounds(10,10,240,40);
        mssvField.add(mssvTitle);
        */

        passField = new RoundedTextField(45);
        passField.setFont(new Font("Arial", Font.PLAIN, 28));
        passField.setBounds(70, 267, 858, 56);
        passField.setBackground(Color.white);
        passField.setForeground(Color.black);
        passField.setCaretColor(Color.black); // Màu con trỏ
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passField.setOpaque(false);

        confirmButton = new RoundedButton("",20);
        confirmButton.setBounds(375,356,250,75);
        confirmButton.setBackground(BananaLeaf);
        confirmButton.addActionListener(e -> {
            
            
            String mssv = mssvField.getText().trim();
            String password = passField.getText().trim();

                // Kiểm tra nếu các trường để trống
            if (mssv.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(MainFrame, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else{
                confirmButton.setBackground(new Color(255,0,0,100));
                buttonTitle.setVisible(false);
                buttonTitle2.setVisible(true);
                //MainFrame.setVisible(false);
                if(userBLL.login(mssv, password) == Enums.UserError.NORMAL){
                    // Nếu đăng nhập thành công, chuyển đến trang chính
                    MainFrame.setVisible(false);
                    new HomePanel();
                }else
                        System.out.print("DMM");
            }
        });
        
        buttonTitle = new JLabel("Xác Nhận",SwingConstants.CENTER);
        buttonTitle.setFont(new Font("Arial",Font.BOLD,38));
        buttonTitle.setBounds(375,355,250,75);
        
        buttonTitle2 = new JLabel("Đang Xử Lý...",SwingConstants.CENTER);
        buttonTitle2.setFont(new Font("Arial",Font.BOLD,38));
        buttonTitle2.setBounds(375,355,250,75);
        buttonTitle2.setVisible(false);
        
        JLabel arrow = AddImage.createImageLabel(Connect.img+"arrow.png", 20, 20, 70, 70);
        loginPanel.add(arrow);

        backbutton = new JButton();
        backbutton.setBounds(20, 20, 70, 70);
        backbutton.setOpaque(false);
        backbutton.setContentAreaFilled(false);
        backbutton.setBorderPainted(false);
        backbutton.addActionListener(b -> {
           
            MainFrame.setVisible(false);
            new LoginPanel(); // Chuyển đến trang đăng nhập
        });
       
        
        JLabel graduation = AddImage.createImageLabel(Connect.img+"graduation.png",860, 0, 105, 105);
        loginPanel.add(graduation);
        

        
        //loginPanel
        loginPanel.add(backbutton);
        loginPanel.add(buttonTitle2);
        loginPanel.add(loginTitle);
        loginPanel.add(buttonTitle); 
        loginPanel.add(mssvField);
        loginPanel.add(passField);
        loginPanel.add(confirmButton);
        
        MainFrame.add(loginPanel);
        MainFrame.add(mainPanel);
        MainFrame.setVisible(true);
    }
}
