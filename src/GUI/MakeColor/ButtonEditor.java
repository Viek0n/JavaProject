package GUI.MakeColor;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellEditor;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button;
    private String label;
    private boolean clicked;

    public ButtonEditor(ActionListener actionListener) {
        button = new JButton();
        button.setOpaque(false);
        button.addActionListener(actionListener); // Gắn ActionListener từ bên ngoài
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label =  "Detail";
        button.setText(label);
        button.putClientProperty("row", row); // Lưu thông tin hàng vào nút
        button.setOpaque(false);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        clicked = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
}