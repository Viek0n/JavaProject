package GUI.MakeColor;

import GUI.UserPanel.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ButtonFactory {

    // Tạo nút Logout
    public static JButton createLogoutButton(MainFrame mainFrame) {
        JButton logoutButton = new JButton();
        logoutButton.setText("Đăng xuất");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 15));
        logoutButton.setBounds(0, 770, 214, 100);
        logoutButton.setOpaque(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);  
        logoutButton.addActionListener(e -> {
            // Tạo một LoginPanel mới
            LoginPanel newLoginPanel = new LoginPanel(mainFrame);

            // Thêm LoginPanel mới vào CardLayout
            mainFrame.addPanel(newLoginPanel, "LoginPanel");

            // Chuyển sang LoginPanel mới
            mainFrame.showPanel("LoginPanel");
        });

        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                logoutButton.setCursor(Cursor.getDefaultCursor());
            }
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
        clearButton.setFocusPainted(false);   
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

    public static JButton createClearButton(MainFrame mainFrame, int width,int height, ActionListener onConfirm) {
        JButton clearButton = new JButton();
        clearButton.setPreferredSize(new Dimension(width, height));
        clearButton.setOpaque(false);
        clearButton.setContentAreaFilled(false);
        clearButton.setFocusPainted(false);   
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

    public static JButton createClearButton(MainFrame mainFrame, int width,int height, ImageIcon icon, ActionListener onConfirm) {
        JButton clearButton = new JButton();
        clearButton.setHorizontalAlignment(SwingConstants.LEFT);  
        clearButton.setPreferredSize(new Dimension(width, height));
        clearButton.setOpaque(false);
        clearButton.setIcon(icon);
        clearButton.setContentAreaFilled(false);
        clearButton.setBorderPainted(false);
        clearButton.setFocusPainted(false);   
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

    public static JButton createButton(MainFrame mainFrame, int width,int height, ImageIcon icon, ActionListener onConfirm) {
        JButton clearButton = new JButton();
        clearButton.setHorizontalAlignment(SwingConstants.LEFT);  
        clearButton.setPreferredSize(new Dimension(width, height));
        clearButton.setIcon(icon);
        clearButton.setFocusPainted(false);   
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

    public static JButton createConfirmButton(MainFrame mainFrame,int r,int x,int y,int width,int height,Color color,ActionListener onConfirm) {
        JButton confirmButton = new RoundedButton("", r);
        confirmButton.setBounds(x, y, width, height);
        confirmButton.setBackground(color);
        confirmButton.addActionListener(onConfirm);
        confirmButton.setOpaque(false);
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

    public static JButton createCell(String s,ActionListener onConfirm) {
        JButton cell = new JButton(s);
        cell.addActionListener(onConfirm);
        cell.setFocusPainted(false);   
        cell.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                cell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                cell.setCursor(Cursor.getDefaultCursor());
            }
        });
        return cell;
    }
}