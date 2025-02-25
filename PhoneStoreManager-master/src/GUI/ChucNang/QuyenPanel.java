package GUI.ChucNang;

import BUS.QuanlyquyenBUS;
import DTO.Quyen;
import GUI.Panel.SearchPanel;
import EXEL.DocExcel;
import EXEL.XuatExcel;
import static GUI.GiaoDien.GiaoDienGUI.panelMain;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import GUI.GiaoDien.Table;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;

public class QuyenPanel extends JPanel {

    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXem;

    private JButton btnXuatExcel;
    private JButton btnNhapExcel;
    private JButton btnLamMoi;
    public static Table tb;

    public static int tb_y = 0;
    public static int tb_x = 0;
    public static int tb_w = 0;
    public static int tb_h = 0;

    private JTextField txtSearch;
    private JComboBox<String> comboBoxSearch;
    private JButton btnSearch;
    private QuanlyquyenBUS quanlyquyenbus;
    private JPanel searchPanel;
    private int i = 1;

    public QuyenPanel(int width, int height) {
        quanlyquyenbus = new QuanlyquyenBUS();

        int x = 0, y = 0, w = 150, h = 50;

        x = (panelMain.getSize().width - w * 6) / 2;

        setLayout(new BorderLayout());
        setBackground(Color.white);
        setSize(width, height);

        themChucNangButton();
        initSearch();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXem);
        buttonPanel.add(btnXuatExcel);
        buttonPanel.add(btnNhapExcel);
        buttonPanel.add(btnLamMoi);

        applyButtonHoverEffect(btnThem);
        applyButtonHoverEffect(btnXoa);
        applyButtonHoverEffect(btnSua);
        applyButtonHoverEffect(btnXem);

        applyButtonHoverEffect(btnXuatExcel);
        applyButtonHoverEffect(btnNhapExcel);
        applyButtonHoverEffect(btnLamMoi);

        add(buttonPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(new JScrollPane(tb), BorderLayout.SOUTH);
    }

    private void themChucNangButton() {

        int x = 0, y = 0, w = 150, h = 50;

        x = (panelMain.getSize().width - w * 6) / 2;

        QuanlyquyenBUS qlquyenBUS = new QuanlyquyenBUS();
        btnThem = createButton("Thêm", "/com/PhoneStoreManager/image/signs-1.png");
        btnXoa = createButton("Xóa", "/com/PhoneStoreManager/image/button-delete.png");
        btnSua = createButton("Sửa", "/com/PhoneStoreManager/image/edit-tools.png");
        btnXem = createButton("Xem", "/com/PhoneStoreManager/image/eye.png");

        btnXuatExcel = createButton("Xuất Excel", "/com/PhoneStoreManager/image/excel.png");
        btnNhapExcel = createButton("Nhập Excel", "/com/PhoneStoreManager/image/excel.png");
        btnLamMoi = createButton("Làm mới", "/com/PhoneStoreManager/image/internet.png");

        tb = new Table();
        tb.setRowHeigth(35);
        // Tìm kiếm 

        // Thêm Table quyền
        tb_x = 0;
        tb_y = 100 + btnThem.getSize().height;
        tb_w = panelMain.getSize().width;
        tb_h = panelMain.getSize().height - tb_y - 1;
        tb.setBound(tb_x, tb_y, tb_w, tb_h);
        tb.setHeaders(qlquyenBUS.getHeaders);

        int i = 0;
// ---VIP---------------
        for (Quyen q : qlquyenBUS.getDSChucNang()) {
            ++i;
            tb.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
        }
        tb.resizeColumnWidth();

        btnThem.addActionListener(e -> themQuyen());
        btnXoa.addActionListener(e -> xoaQuyen());
        btnSua.addActionListener(e -> suaQuyen());
        btnXem.addActionListener(e -> xemQuyen());

        btnXuatExcel.addActionListener(e -> xuatExcelQuyen());
        btnNhapExcel.addActionListener(e -> nhapExcelQuyen());
        btnLamMoi.addActionListener(e -> lamMoi());
    }

    private void initSearch() {
        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        txtSearch = new JTextField(20);
        comboBoxSearch = new JComboBox<>(quanlyquyenbus.getComboboxSearch);
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventSearchQuyenforGiaoDien();
            }
        });

        Font font = new Font("Tahoma", Font.BOLD, 14);
        txtSearch.setFont(font);
        comboBoxSearch.setFont(font);
        btnSearch.setFont(font);
        txtSearch.setPreferredSize(new Dimension(200, 30));
        comboBoxSearch.setPreferredSize(new Dimension(150, 30));

        // Thiết lập kích thước và icon cho nút tìm kiếm
        btnSearch.setPreferredSize(new Dimension(120, 40));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(new JLabel("Loại:"));
        searchPanel.add(comboBoxSearch);
        searchPanel.add(btnSearch);

    }

    public void EventSearchQuyenforGiaoDien() {
        String s = txtSearch.getText().trim();
        String type = comboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<Quyen> list = quanlyquyenbus.SearchQuyen(s, type);

        tb.clear();

        if (list != null && !list.isEmpty()) {
            for (Quyen q : list) {
                tb.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(150, 50));

        button.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        button.setText(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setBackground(Color.white);
        return button;
    }

    private void applyButtonHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.cyan);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.white);
            }
        });
    }

    private void themQuyen() {
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
        String MaQuyen = Quanlyquyenbus.MaQuyennotHave();
        new FormThemSuaQuyen_V5(MaQuyen).setVisible(true);
    }

    private void xoaQuyen() {
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();

        int i = tb.getSelectedRow();
        if (i >= 0) {
            int k = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa quyền này không?");
            if (k != JOptionPane.YES_OPTION) {
                return;
            }
            String MaQuyen = tb.LayMaQuyenTaiTable(i).toString();
            if (Quanlyquyenbus.deleteQuyen(MaQuyen)) {
                tb.removeRow(MaQuyen);
                JOptionPane.showMessageDialog(null, "Xóa quyền thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Xóa quyền thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn quyền cần xóa");
        }
    }

    private void suaQuyen() {
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();

        int i = tb.getSelectedRow();
                int a = 1;

        if (i >= 0) {
            String MaQuyen = tb.LayMaQuyenTaiTable(i).toString();
            Quyen q = Quanlyquyenbus.getQuyen(MaQuyen);
            new FormThemSuaQuyen_V5(q, Integer.parseInt(tb.getValueSTT(i).toString()),a).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn quyền cần sữa");
        }
    }
    
    
    private void xemQuyen() {
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();

        int i = tb.getSelectedRow();
        int a = 2;
        if (i >= 0) {
            String MaQuyen = tb.LayMaQuyenTaiTable(i).toString();
            Quyen q = Quanlyquyenbus.getQuyen(MaQuyen);
            new FormThemSuaQuyen_V5(q, Integer.parseInt(tb.getValueSTT(i).toString()),a).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn quyền cần xem");
        }
    }

    private void xuatExcelQuyen() {
        new XuatExcel().xuatFileExcelQuyen();
    }

    private void nhapExcelQuyen() {
        new DocExcel().DocExcelQuyen();
        btnLamMoi.doClick();
    }

    private void lamMoi() {
        // Xóa toàn bộ dữ liệu trong Table
        tb.clear();

        // Thêm lại dữ liệu mới vào Table
        QuanlyquyenBUS qlquyenBUS = new QuanlyquyenBUS();
        int i = 0;// ---VIP---------------

        for (Quyen q : qlquyenBUS.getDSChucNang()) {
            ++i;
            tb.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
        }

        // Cập nhật lại giao diện Table
        tb.resizeColumnWidth();
    }
}
