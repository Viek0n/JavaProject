package GUI;

import MICS.Connect;
import java.awt.*;
import javax.swing.*;

public class User_GUI {

    private int FrameWidth = 1600;
    private int FrameHeight = 900;

    JFrame User_frame;
    JPanel Main_panel, Left_panel, Right_panel, Test_panel,dynamicNumberPanel,gridPanel;
    JLabel Test_title, Code_Exam1, Code_Exam2, Test_img;
    JTextField leftField,rightField; 
    JButton Answer1, Answer2, Answer3, Answer4, Submit_Answer;

    public User_GUI() {
        // Tạo frame cho User
        User_frame = new JFrame();
        User_frame.setSize(FrameWidth, FrameHeight);
        User_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        User_frame.setResizable(false);

        // Tạo panel chứa tất cả thành phần
        Main_panel = new JPanel();
        Main_panel.setBackground(Ulti.MainColor);
        Main_panel.setLayout(null);

        // Tạo Left_panel chứa các thành phần về đề thi
        Left_panel = new JPanel();
        Left_panel.setBackground(Color.white);
        Left_panel.setBounds(0, 0, 214, 900);
        Left_panel.setLayout(null);
        Main_panel.add(Left_panel);


        Test_title = new JLabel();
        Test_title.setText("Kiểm tra");
        Test_title.setForeground(Color.BLACK); // Đặt màu chữ
        Test_title.setFont(new Font("Arial", Font.BOLD, 20));
        Test_title.setBounds(21, 31, 131, 45);
        Left_panel.add(Test_title);

        Test_img = new JLabel();
        Test_img = AddImage.createImageLabel(Connect.img+"test.png", 120, 31, 50, 50);
        Left_panel.add(Test_img);


        Code_Exam1 = new JLabel("Mã Bài");
        Code_Exam1.setFont(new Font("Arial", Font.BOLD, 32));
        Code_Exam1.setForeground(Color.BLACK); // Đặt màu chữ
        Code_Exam1.setBounds(55,119,104,39);
        Left_panel.add(Code_Exam1);  
        
        // Tạo JLabel cho Code_Exam2 với góc bo tròn
        Code_Exam2 = new JLabel("ABC", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Ulti.MainColor); // Màu nền
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Góc bo tròn (20px)
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK); // Màu viền
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Góc bo tròn (20px)
            }
        };
        Code_Exam2.setFont(new Font("Arial", Font.BOLD, 32)); // Đặt font chữ
        Code_Exam2.setForeground(Color.BLACK); // Đặt màu chữ
        Code_Exam2.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa nội dung
        Code_Exam2.setBounds(17, 162, 180, 64); // Đặt vị trí và kích thước
        Code_Exam2.setOpaque(false); // Tắt chế độ nền mặc định

        // Thêm Code_Exam2 vào Left_panel
        Left_panel.add(Code_Exam2);

        // Tạo một JPanel để chứa các thành phần của DynamicNumberDisplay
        dynamicNumberPanel = new JPanel();
        dynamicNumberPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Căn giữa các thành phần
        dynamicNumberPanel.setBounds(49, 322, 150, 48); // Đặt vị trí và kích thước
        dynamicNumberPanel.setBackground(Color.white); // Đặt màu nền

        // Tạo các thành phần từ DynamicNumberDisplay
        leftField = new JTextField("4"); // Số bên trái
        leftField.setFont(new Font("Arial", Font.BOLD, 30)); // Cỡ chữ lớn hơn
        leftField.setEditable(false);
        leftField.setFocusable(false);
        leftField.setBorder(null);
        leftField.setPreferredSize(new Dimension(40, 40)); // Đặt kích thước đủ lớn để hiển thị số

        JLabel slashLabel = new JLabel("/"); // Dấu "/"
        slashLabel.setFont(new Font("Arial", Font.BOLD, 30));

        rightField = new JTextField("40"); // Số bên phải
        rightField.setFont(new Font("Arial", Font.BOLD, 30)); // Cỡ chữ lớn hơn
        rightField.setEditable(false);
        rightField.setFocusable(false);
        rightField.setBorder(null);
        rightField.setPreferredSize(new Dimension(60, 40)); // Đặt kích thước đủ lớn để hiển thị số

        // Thêm các thành phần vào dynamicNumberPanel
        dynamicNumberPanel.add(leftField);
        dynamicNumberPanel.add(slashLabel);
        dynamicNumberPanel.add(rightField);

        // Thêm dynamicNumberPanel vào Left_panel
        Left_panel.add(dynamicNumberPanel);   

        //Tạo ô hiển thị các câu đã làm
        gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các ô
        gbc.fill = GridBagConstraints.BOTH;

        // Tạo lưới 5x5 (25 ô vuông)
        int totalCells = 25;
        int columns = 5;

        for (int i = 0; i < totalCells; i++) {
            gbc.gridx = i % columns; // Cột
            gbc.gridy = i / columns; // Hàng

            JPanel cell = new JPanel();
            cell.setPreferredSize(new Dimension(30, 30)); // Kích thước ô vuông (nhỏ hơn để vừa Left_panel)
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Tô màu: 2 ô màu xanh nhạt (0,2), 1 ô màu xanh đậm (3)
            if (i == 0 || i == 2) {
                cell.setBackground(new Color(144, 238, 144)); // Xanh nhạt
            } else if (i == 3) {
                cell.setBackground(new Color(100, 149, 237)); // Xanh đậm
            } else {
                cell.setBackground(Color.LIGHT_GRAY); // Xám
            }

            gridPanel.add(cell, gbc);
        }

        // Đặt vị trí cho gridPanel trong Left_panel
        gridPanel.setBounds(17, 400, 180, 180); // Đặt bên dưới dynamicNumberPanel
        gridPanel.setBackground(Color.white);
        Left_panel.add(gridPanel);


        // Tạo JLabel cho biểu tượng Logout bằng AddImage
        JLabel logoutIconLabel = AddImage.createImageLabel(Connect.img + "logout.png", 65, 700, 100, 100); // Đặt vị trí và kích thước



        // Thêm biểu tượng và nút Logout vào Left_panel
        Left_panel.add(logoutIconLabel);

        
        
     

        // Create Right_panel for questions and answers
        Right_panel = new JPanel();
        Right_panel.setLayout(null);
        Right_panel.setBackground(new Color(173, 216, 230)); // Light blue
        Right_panel.setBounds(214, 0, 1386, 900);
        Main_panel.add(Right_panel);



        // Question number label
        JLabel questionNumberLabel = new JLabel("Câu 4:");
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 32));
        questionNumberLabel.setBounds(115, 90, 153, 47);
        Right_panel.add(questionNumberLabel);

        // Create RoundedPanel for the question
        RoundedPanel questionPanel = new RoundedPanel(50);
        questionPanel.setBackground(Color.WHITE);
        questionPanel.setBounds(115, 150, 1175, 374);
        questionPanel.setLayout(null);

        // Updated question text from the image
        JLabel questionLabel = new JLabel("<html>Đây là câu hỏi</html>");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 32));
        questionLabel.setForeground(Color.BLACK);
        questionLabel.setBounds(20, 20, 1135, 334);
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        questionPanel.add(questionLabel);
        Right_panel.add(questionPanel);

        // Mảng chứa các câu trả lời
        String[] answers = {"A: TIN", "B: VIETKON", "C: VIETKON", "D: HOANG VU"};

        // Tọa độ ban đầu cho các nút
        int startX = 115; // Vị trí X của nút đầu tiên
        int startY = 550; // Vị trí Y của hàng đầu tiên
        int buttonWidth = 550; // Chiều rộng của nút
        int buttonHeight = 80; // Chiều cao của nút
        int gapY = 100; // Khoảng cách giữa các hàng

        // Tạo các nút trả lời bằng vòng lặp
        for (int i = 0; i < answers.length; i++) {
            RoundedButton answerButton = new RoundedButton(answers[i], 30); // Góc bo tròn 30px
            answerButton.setFont(new Font("Arial", Font.BOLD, 24));
            answerButton.setBackground(Color.WHITE);
            answerButton.setForeground(Color.BLACK);

            // Tính toán vị trí của nút
            int x = (i % 2 == 0) ? startX : startX + buttonWidth + 50; // Cột trái hoặc phải
            int y = startY + (i / 2) * gapY; // Hàng trên hoặc dưới

            answerButton.setBounds(x, y, buttonWidth, buttonHeight);

            // Thêm nút vào Right_panel
            Right_panel.add(answerButton);
        }
      
     

        User_frame.add(Main_panel);
        User_frame.setVisible(true);
    }

    public static void main(String[] args) {
        new User_GUI();
    }
}