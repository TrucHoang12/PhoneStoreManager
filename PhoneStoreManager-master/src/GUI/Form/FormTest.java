package GUI.Form;

import DTO.ChiTietHoaDon;
import BUS.QuanlychitiethoadonBUS;
import DTO.ChiTietPhieuNhap;
import BUS.QuanlychitietphieunhapBUS;
import DTO.HoaDon;
import BUS.QuanlykhuyenmaiBUS;
import DTO.NhanVien;
import BUS.QuanlynhanvienBUS;
import DTO.PhieuNhap;
import BUS.QuanlyquyenBUS;
import DTO.Quyen;
import BUS.QuanlysanphamBUS;
import GUI.Panel.SearchFromTo;
import GUI.Panel.DateSearch;
import GUI.Panel.SearchPanel;
import DTO.LoaiSanPham;
import BUS.QuanlyloaisanphamBUS;
import BUS.QuanlyphieunhapBUS;
import BUS.XulyTaiKhoanBUS;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class FormTest extends JFrame {

//    private JButton btnLamMoi;
    private JButton btnThemSua;
    private JPanel pnmain;
    private JPanel pnSouth;
    private JPanel pnNorth;
    private JTable tb;
    private SearchPanel pnSearch;
    private DateSearch pnDateSearch;
    private SearchFromTo pnAgeSearch;
    private SearchFromTo pnSoLuongSearch;
    private SearchFromTo pnThanhTienSearch;
    private DefaultTableModel model;
    private JScrollPane JS;
    private Object[] s = null;

    public FormTest(String type) {
        setIconImage((new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/signs-1.png"))).getImage());
        Font font = new Font("Tahoma", 1, 12);
        
        // pnSouth Form chon
        btnThemSua = new JButton("Làm mới");
        btnThemSua.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/internet.png")));
        btnThemSua.setFont(font);
       
        pnSouth = new JPanel();
        pnSouth.add(btnThemSua, BorderLayout.CENTER);
        pnSouth.setBackground(Color.white);
        switch (type) {
//            case "ChonNhanVien": {
//                initChonNhanVien();
//                break;
//            }
            case "ChonMaQuyen": {
                initChonMaQuyen();
                break;
            }
           
            default:
                break;
        }

        sort();
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tb.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    int colum = tb.getSelectedColumn();
                    if (colum >= 0) {
                        if (s == null) {
                            s = new Object[]{tb.getValueAt(row, colum), Integer.toString(row), Integer.toString(colum)};
                        } else {
                            tb.setValueAt(s[0], Integer.parseInt(s[1].toString()), Integer.parseInt(s[2].toString()));
                            s = null;
                        }
                    }
                }
            }
        });
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    private void initChonMaQuyen() {

        setTitle("Chọn mã quyền");
        setSize(1200, 600);
        TaoLaiTableMaQuyen();
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
        ArrayList<Quyen> list = Quanlyquyenbus.getDSChucNang();
        int i = 0;
        for (Quyen q : list) {
            ++i;
            model.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
        }
        resizeColumnWidth();

        btnThemSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(JS);
                repaint();
                TaoLaiTableMaQuyen();

                ArrayList<Quyen> list = Quanlyquyenbus.getDSChucNang();
                int i = 0;
                for (Quyen q : list) {
                    ++i;
                    model.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
                }
                resizeColumnWidth();
                sort();
                tb.setFont(new Font("Segoe UI", 0, 16));
                tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
                add(JS, BorderLayout.CENTER);
                validate();
            }
        });

        btnThemSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    FormThemSuaTaiKhoan.txtMaQuyen.setText(LayMaQuyenTrenTable(row));
                    dispose();
                }
            }
        });

        pnSearch = new SearchPanel("CHON");
        pnSearch.setPreferredSize(new Dimension(pnSearch.getSize().width, pnSearch.getSize().height));
        pnSearch.setComboBox(Quanlyquyenbus.getComboboxSearch);
        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                SearchQuyen();
            }
        });
        pnSearch.ComboBoxSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchQuyen();
            }
        });

        pnNorth = new JPanel();
        pnNorth.add(pnSearch, BorderLayout.CENTER);
        pnNorth.setBorder(BorderFactory.createLineBorder(Color.black));
        pnNorth.setBackground(Color.white);

        add(pnSouth, BorderLayout.SOUTH);
        add(JS, BorderLayout.CENTER);
        add(pnNorth, BorderLayout.NORTH);
    }

    private void sort() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tb.setRowSorter(sorter);
    }

   
    private void SearchQuyen() {

        String type = pnSearch.ComboBoxSearch.getSelectedItem().toString().trim();
        String s = pnSearch.txtSearch.getText().trim();
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
        ArrayList<Quyen> list = Quanlyquyenbus.SearchQuyen(s, type);
        remove(JS);
        repaint();
        TaoLaiTableMaQuyen();
        if (list != null) {
            int i = 0;
            for (Quyen q : list) {
                ++i;
                model.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
            }
        }
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        resizeColumnWidth();
        sort();
        add(JS, BorderLayout.CENTER);
        validate();
    }

  

//    private void TaoLaiTableMaQuyen() {
//        tb = new JTable();
//        model = (DefaultTableModel) tb.getModel();
//        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
//        model.setColumnIdentifiers(Quanlyquyenbus.getHeaders);
//        tb.setRowHeight(35);
//        JS = new JScrollPane(tb);
//    }
    
    private void TaoLaiTableMaQuyen() {
    tb = new JTable() {
        @Override
        public Class getColumnClass(int column) {
            if (column == 4) { // Số cột checkbox là 4
                return Boolean.class; // Trả về kiểu dữ liệu là Boolean cho checkbox
            }
            return String.class;
        }
    };
    model = (DefaultTableModel) tb.getModel();
    QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
    model.setColumnIdentifiers(Quanlyquyenbus.getHeaders);
    tb.setRowHeight(35);
    JS = new JScrollPane(tb);
//
//    // Thêm checkbox vào cột cuối cùng
//    TableColumnModel columnModel = tb.getColumnModel();
//    TableColumn checkBoxColumn = columnModel.getColumn(4); // Số cột checkbox là 4
//    checkBoxColumn.setCellEditor(tb.getDefaultEditor(Boolean.class));
//    checkBoxColumn.setCellRenderer(tb.getDefaultRenderer(Boolean.class));
}

    

  

    private String LayMaQuyenTrenTable(int row) {
        for (int i = 0; i < tb.getColumnCount(); ++i) {
            if (tb.getColumnName(i).equals("Mã quyền")) {
                return tb.getValueAt(row, i).toString();
            }
        }
        return null;
    }


//    https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
    public void resizeColumnWidth() {
        final TableColumnModel columnModel = tb.getColumnModel();
        for (int column = 0; column < tb.getColumnCount(); column++) {
            int width = 100; // Min width
            for (int row = 0; row < tb.getRowCount(); row++) {
                TableCellRenderer renderer = tb.getCellRenderer(row, column);
                Component comp = tb.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }

            width += 100;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
//        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
//    
    public static void main(String[] args){
        new FormTest("ChonMaQuyen").setVisible(true);
    }
}
