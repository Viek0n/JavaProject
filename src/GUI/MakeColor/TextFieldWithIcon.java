package GUI.MakeColor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.ImageIcon;

public class TextFieldWithIcon extends RoundedTextField {
    private ImageIcon icon;

    public TextFieldWithIcon(int cornerRadius, ImageIcon icon) {
        super(cornerRadius);
        this.icon = icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (icon != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            int iconX = 5;
            int iconY = (getHeight() - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g2, iconX, iconY);

            int lineX = iconX + icon.getIconWidth() + 5;
            int topPadding = 6;
            int bottomPadding = 6;

            g2.setColor(Color.BLACK); 
            g2.drawLine(lineX, topPadding, lineX, getHeight() - bottomPadding);

            g2.dispose();
        }
    }

    @Override
    public Insets getInsets() {
        Insets insets = super.getInsets();
        int iconWidth = (icon != null) ? icon.getIconWidth() + 15 : 0; // Space for icon + line
        return new Insets(insets.top, insets.left + iconWidth, insets.bottom, insets.right);
    }
}
