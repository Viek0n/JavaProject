package GUI.UserPanel;

import BLL.ExamBLL;
import BLL.ExamStructBLL;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.RoundedTextField;
import GUI.MakeColor.Ulti;
import MICS.Connect;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ExamSelect extends JPanel{
    private JPanel menuPanel;
    private RoundedTextField maBaiKT;
    private JLabel textintheBox;
    private MainFrame mainFrame; // Tham chiếu đến MainFrame

    public ExamSelect(MainFrame mainFrame, JPanel menuPanel) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        this.menuPanel = menuPanel;
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        // Nhập mã bài kiểm tra
        textintheBox = new JLabel("Nhập mã bài kiểm tra", SwingConstants.CENTER);
        textintheBox.setFont(new Font("Arial", Font.BOLD, 32));
        textintheBox.setBounds(418, 342, 1000, 100);
        textintheBox.setForeground(Color.black);

        maBaiKT = new RoundedTextField(45); // Bo tròn với bán kính 30
        maBaiKT.setFont(new Font("Arial", Font.PLAIN, 50));
        maBaiKT.setBounds(418, 422, 1000, 100);
        maBaiKT.setBackground(Color.white);
        maBaiKT.setForeground(Color.black);
        maBaiKT.setCaretColor(Color.black);
        maBaiKT.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        maBaiKT.setOpaque(false); // Để vẽ nền tùy chỉnh

        JButton confirmButton = ButtonFactory.createConfirmButton(mainFrame,42,717,558,400,100,Ulti.BananaLeaf, e  -> {
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

        // Nút quay lại LoginPanel
        JButton logoutButton = ButtonFactory.createLogoutButton(mainFrame);
        logoutButton.setIcon(AddImage.createImageIcon(Connect.img+"logout.png", 40, 40));

        add(confirmButton);
        add(textintheBox);
        add(maBaiKT);
        add(menuPanel);
    }
}
