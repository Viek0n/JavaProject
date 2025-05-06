package GUI.giaodienadmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class ThanhBar extends JPanel implements ActionListener, MouseListener {
    private JButton btnclose, btnminimize, btnmaxnimize;
    final int DEFAUT_WIDTH_JPanel = 180, DEFAUT_HEIGHT_JPanel = 50;

    public ThanhBar() {
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new BorderLayout()); 
        this.setPreferredSize(new Dimension(DEFAUT_WIDTH_JPanel, DEFAUT_HEIGHT_JPanel));
        this.setBackground(Color.decode("#2c3e50"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.setOpaque(false);

       
        btnminimize = createButton("/GUI/giaodienadmin/Icon/minimize.png");
        btnmaxnimize = createButton("/GUI/giaodienadmin/Icon/maximize.png");
        btnclose = createButton("/GUI/giaodienadmin/Icon/close.png");

        buttonPanel.add(btnminimize);
        buttonPanel.add(btnmaxnimize);
        buttonPanel.add(btnclose);

        this.add(buttonPanel, BorderLayout.EAST); 
    }

    private JButton createButton(String iconPath) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(30, 30));
        button.setBackground(Color.decode("#e74c3c"));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#c0392b")));
        
        // Ensure the icon path is correct
        java.net.URL iconURL = getClass().getResource(iconPath);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            button.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        } else {
            System.err.println("Icon not found: " + iconPath);
        }
        
        button.addActionListener(this);
        button.addMouseListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (e.getSource() == btnclose) {
            System.exit(0);
        }
        if (e.getSource() == btnminimize) {
            frame.setState(JFrame.ICONIFIED);
        }
        if (e.getSource() == btnmaxnimize) {
            int state = frame.getExtendedState();
            if ((state & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                frame.setExtendedState(JFrame.NORMAL);
            } else {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == btnclose) {
            btnclose.setBackground(Color.WHITE);
        }
        if (e.getSource() == btnminimize) {
            btnminimize.setBackground(Color.WHITE);
        }
        if (e.getSource() == btnmaxnimize) {
            btnmaxnimize.setBackground(Color.WHITE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == btnclose) {
            btnclose.setBackground(Color.WHITE);
        }
        if (e.getSource() == btnminimize) {
            btnminimize.setBackground(Color.WHITE);
        }
        if (e.getSource() == btnmaxnimize) {
            btnmaxnimize.setBackground(Color.WHITE);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == btnclose || e.getSource() == btnminimize || e.getSource() == btnmaxnimize) {
            ((JButton) e.getSource()).setBackground(Color.decode("#2c3e50"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == btnclose) {
            btnclose.setBackground(Color.decode("#c0392b"));
        }
        if (e.getSource() == btnminimize || e.getSource() == btnmaxnimize) {
            ((JButton) e.getSource()).setBackground(Color.decode("#3498db"));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == btnclose) {
            btnclose.setBackground(Color.decode("#e74c3c"));
        } else if (e.getSource() == btnminimize || e.getSource() == btnmaxnimize) {
            ((JButton) e.getSource()).setBackground(Color.decode("#2c3e50"));
        }
    }
}
