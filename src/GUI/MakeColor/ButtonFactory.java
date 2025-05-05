package GUI.MakeColor;

import GUI.UserPanel.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ButtonFactory {

    // Tạo nút Logout
    public static JButton createLogoutButton(MainFrame mainFrame) {
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
        return logoutButton;
    }

    // Tạo nút tàng hình
    public static JButton createClearButton(MainFrame mainFrame,int x, int y,int width,int height,ActionListener onConfirm) {
        JButton clearButton = new JButton();
        clearButton.setBounds(x, y, width, height);
        clearButton.setOpaque(false);
        clearButton.setContentAreaFilled(false);
        clearButton.setBorderPainted(false);
        clearButton.addActionListener(onConfirm);
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                clearButton.setCursor(Cursor.getDefaultCursor());
            }
        });
        return clearButton;
    }

    public static JButton createConfirmButton(MainFrame mainFrame,int r,int x, int y,int width,int height,Color color,ActionListener onConfirm) {
        JButton confirmButton = new RoundedButton("", r);
        confirmButton.setBounds(x, y, width, height);
        confirmButton.setBackground(color);
        confirmButton.addActionListener(onConfirm);
        confirmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                confirmButton.setCursor(Cursor.getDefaultCursor());
            }
        });
        return confirmButton;
    }
}