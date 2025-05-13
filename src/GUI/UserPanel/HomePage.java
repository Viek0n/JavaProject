package GUI.UserPanel;

import GUI.MakeColor.RoundedPanel;
import GUI.MakeColor.Ulti;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends JPanel {
    private JPanel menuPanel;
    private JLabel homeTitle, descLabel;
    private MainFrame mainFrame; // Tham chiếu đến MainFrame
    private int posx, posy;

    public HomePage(MainFrame mainFrame, JPanel menuPanel) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        this.menuPanel = menuPanel;
        posx = 525;
        posy = 300;
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        // Tạo panel trắng chứa chữ
        JPanel textPanel = new RoundedPanel(100);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBounds(posx, posy, 800, 200); // Panel trắng

        // Tiêu đề
        homeTitle = new JLabel("Stress Portal");
        homeTitle.setFont(new Font("Arial", Font.BOLD, 60));
        homeTitle.setForeground(Color.BLACK);
        homeTitle.setAlignmentX(CENTER_ALIGNMENT); // Căn giữa theo trục X

        // Mô tả
        descLabel = new JLabel("Nơi áp lực thi cử được giải tỏa, không phải gia tăng!");
        descLabel.setFont(new Font("Arial", Font.BOLD, 30));
        descLabel.setForeground(Color.BLACK);
        descLabel.setAlignmentX(CENTER_ALIGNMENT); // Căn giữa theo trục X

        // Khoảng cách giữa 2 dòng
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(homeTitle);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(descLabel);

        add(textPanel); // Thêm panel trắng vào màn hình
        add(menuPanel); // Giữ nguyên menuPanel
    }

}
