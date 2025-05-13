package GUI.MakeColor;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AddImage {
    public static JLabel createImageLabel(String imagePath, int x, int y, int width, int height) {
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(imagePath);

        // Thay đổi kích thước hình ảnh
        Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        imageLabel.setIcon(imageIcon);
        imageLabel.setBounds(x, y, width, height);
        return imageLabel;
    }

    public static ImageIcon createImageIcon(String imagePath, int x, int y, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath);

        // Thay đổi kích thước hình ảnh
        Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        return imageIcon;
    }
    
    public static ImageIcon createImageIcon(String imagePath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath);

        // Thay đổi kích thước hình ảnh
        Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        return imageIcon;
    }
}