package EXEL;

import GUI.GiaoDien.Table;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.JPanel;

public class MyJOptionPane extends JOptionPane {
    
    JComboBox<String> cb = new JComboBox<>(new String[] {"Ghi đè", "Bỏ qua", "Ghi đè tất cả", "Bỏ qua tất cả các mã trùng"});
    JPanel pl = new JPanel();
    
    public MyJOptionPane(Table mtb, String defaultSelect) {
        cb.setBorder(BorderFactory.createTitledBorder("Hành động:"));
        cb.setSelectedItem(defaultSelect);
        mtb.resizeColumnWidth();
        
        pl.setLayout(new BorderLayout());
        pl.add(mtb, BorderLayout.CENTER);
        pl.add(cb, BorderLayout.SOUTH);
        
        this.showMessageDialog(null, pl, "Trùng mã", QUESTION_MESSAGE);
    }
    
    public String getAnswer() {
        return cb.getSelectedItem().toString();
    }
}
