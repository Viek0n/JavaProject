package GUI.UserPanel;
import GUI.MakeColor.*;
import MICS.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {
    private JTextField mssvField;
    private JTextField passField;
    private MainFrame mainFrame;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        // Scaled panel dimensions
        double scale = 0.75;
        int panelWidth = (int)(1000 * scale);
        int panelHeight = (int)(500 * scale);

        RoundedPanel loginPanel = new RoundedPanel((int)(100 * scale));
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setSize(panelWidth, panelHeight);

        JLabel loginTitle = new JLabel("Đăng Nhập", SwingConstants.CENTER);
        loginTitle.setFont(new Font("Inter", Font.BOLD, (int)(50 * scale)));
        loginTitle.setBounds((int)(330 * scale), (int)(40 * scale), (int)(340 * scale), (int)(60 * scale));
        loginTitle.setForeground(Color.BLACK);
        loginTitle.setIcon(AddImage.createImageIcon(Connect.img + "graduation.png", 
            (int)(20 * scale), (int)(20 * scale), (int)(70 * scale), (int)(70 * scale)));

        mssvField = new TextFieldWithIcon((int)(30 * scale),
            AddImage.createImageIcon(Connect.img + "user.png", (int)(20 * scale), (int)(20 * scale), (int)(45 * scale), (int)(45 * scale)));
        mssvField.setFont(new Font("Arial", Font.PLAIN, (int)(28 * scale)));
        mssvField.setBounds((int)(70 * scale), (int)(150 * scale), (int)(858 * scale), (int)(56 * scale));
        mssvField.setBackground(Color.white);
        mssvField.setForeground(Color.black);
        mssvField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        mssvField.setOpaque(false);

        passField = new TextFieldWithIcon((int)(30 * scale),
            AddImage.createImageIcon(Connect.img + "lock.png", (int)(20 * scale), (int)(20 * scale), (int)(45 * scale), (int)(45 * scale)));
        passField.setFont(new Font("Arial", Font.PLAIN, (int)(28 * scale)));
        passField.setBounds((int)(70 * scale), (int)(267 * scale), (int)(858 * scale), (int)(56 * scale));
        passField.setBackground(Color.white);
        passField.setForeground(Color.black);
        passField.setCaretColor(Color.black);
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passField.setOpaque(false);

        JButton confirmButton = ButtonFactory.createConfirmButton(mainFrame,
            (int)(20 * scale), (int)(375 * scale), (int)(355 * scale), (int)(250 * scale), (int)(75 * scale),
            Ulti.BananaLeaf, a -> {
                String mssv = mssvField.getText();
                String password = passField.getText();

                if (mssv.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    switch(mainFrame.userBLL.login(mssv, password)) {
                        case NORMAL:
                            mainFrame.addPanel(new HomePage(mainFrame, new MenuPanel(mainFrame)), "HomePanel");
                            mainFrame.showPanel("HomePanel");
                            break;
                        case LOCKED:
                            JOptionPane.showMessageDialog(this, "Người dùng đã bị khóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            break;
                        case NOUSER:
                            JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            break;
                        case WRONGPASS:
                            JOptionPane.showMessageDialog(this, "Sai mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                }
            });

        confirmButton.setText("Đăng Nhập");
        confirmButton.setFont(new Font("Arial", Font.BOLD, (int)(38 * scale)));
        confirmButton.setForeground(Color.BLACK);

        loginPanel.add(loginTitle);
        loginPanel.add(mssvField);
        loginPanel.add(passField);
        loginPanel.add(confirmButton);

        add(loginPanel);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int centerX = (getWidth() - panelWidth) / 2;
                int centerY = (getHeight() - panelHeight) / 2;
                loginPanel.setLocation(centerX, centerY);
            }
        });
    }
}
