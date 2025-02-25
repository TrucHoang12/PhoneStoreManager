
package GUI.ChucNang;

import BUS.QuanlychitietquyenBUS;
import BUS.QuanlykhachhangBUS;
import DTO.ChiTietQuyen;
import DTO.KhachHang;
import EXEL.DocExcel;
import EXEL.XuatExcel;
import GUI.Form.FormThemSuaKhachHang;
//import GUI.Form.FormThemSuaKhachHang2;
import static GUI.GiaoDien.GiaoDienGUI.panelMain;
import GUI.GiaoDien.LoginGUI_V1;
import GUI.GiaoDien.Table;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author MSI
 */
public class KhachHangGUI extends JPanel {
    
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
    private QuanlykhachhangBUS qlkhachhangbus;
    private JPanel searchPanel;
    private int i = 1;

    public KhachHangGUI (int width, int height) {
        qlkhachhangbus = new QuanlykhachhangBUS();
        
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

        qlkhachhangbus = new QuanlykhachhangBUS();
        
        btnThem = createButton("Thêm", "/com/PhoneStoreManager/image/signs-1.png");
        btnXoa = createButton("Xóa", "/com/PhoneStoreManager/image/button-delete.png");
        btnSua = createButton("Sửa", "/com/PhoneStoreManager/image/edit-tools.png");
        btnXem = createButton("Xem", "/com/PhoneStoreManager/image/eye.png");

        btnXuatExcel = createButton("Xuất Excel", "/com/PhoneStoreManager/image/excel.png");
        btnNhapExcel = createButton("Nhập Excel", "/com/PhoneStoreManager/image/excel.png");
        btnLamMoi = createButton("Làm mới", "/com/PhoneStoreManager/image/internet.png");

        tb = new Table();
        tb.setRowHeigth(35);
        
        // Thêm table khách hàng
        tb_x = 0;
        tb_y = 100 + btnThem.getSize().height;
        tb_w = panelMain.getSize().width;
        tb_h = panelMain.getSize().height - tb_y - 1;
        tb.setBound(tb_x, tb_y, tb_w, tb_h);
        tb.setHeaders(qlkhachhangbus.getHeaders);

        int i = 0;
        
        for(KhachHang kh : qlkhachhangbus.getDSKH()) {
            ++i;
            tb.addRow(new String[]{Integer.toString(i), kh.getMaKH(), kh.getTenKH(), kh.getDiaChi(), kh.getSDT()});
        }
        tb.resizeColumnWidth();
        
        btnThem.addActionListener(e -> themKH());
        btnXoa.addActionListener(e -> xoaKH());
        btnSua.addActionListener(e -> suaKH());
        btnXem.addActionListener(e -> suaKH());

        btnXuatExcel.addActionListener(e -> xuatExcelKH());
        btnNhapExcel.addActionListener(e -> nhapExcelKH());
        btnLamMoi.addActionListener(e -> lamMoi());
        
        
         //------------------Ẩn nút hỗ trợ phân quyền 
        btnThem.setVisible(false);
    btnXoa.setVisible(false);
    btnSua.setVisible(false);
    btnXem.setVisible(false);
    
    applyButtonHoverEffect(btnThem);
    applyButtonHoverEffect(btnXoa);
    applyButtonHoverEffect(btnSua);
    applyButtonHoverEffect(btnXem);
    applyButtonHoverEffect(btnXuatExcel);
    applyButtonHoverEffect(btnNhapExcel);
    applyButtonHoverEffect(btnLamMoi);
    
        loadAndDisplayPermissions();
    }
    
    private void initSearch() {
        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        txtSearch = new JTextField(20);
        comboBoxSearch = new JComboBox<>(qlkhachhangbus.getComboboxSearch);
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
        ArrayList<KhachHang> list = qlkhachhangbus.SearchKhachHang(s, type);

        tb.clear();

        if (list != null && !list.isEmpty()) {
            for (KhachHang kh : list) {
                tb.addRow(new String[]{Integer.toString(i), kh.getMaKH(), kh.getTenKH(), kh.getDiaChi(), kh.getSDT()});
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

    private void themKH() {
        qlkhachhangbus = new QuanlykhachhangBUS();
        String MaKH = qlkhachhangbus.MaKHnotHave();
        new FormThemSuaKhachHang(MaKH).setVisible(true);
    }

    private void xoaKH() {
        qlkhachhangbus = new QuanlykhachhangBUS();
        int i = tb.getSelectedRow();
        if (i >= 0) {
            int k = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa khách hàng này không?");
            if (k != JOptionPane.YES_OPTION) {
                return;
            }
            String MaKH = tb.LayMaKHTaiTable(i).toString();
            if (qlkhachhangbus.delete(MaKH)) {
                tb.removeRow(MaKH);
                JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Xóa khách hàng thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn khách hàng cần xóa");
        }
    }

    private void suaKH() {
        qlkhachhangbus = new QuanlykhachhangBUS();
        int i = tb.getSelectedRow();
        if (i >= 0) {
            String MaKH = tb.LayMaKHTaiTable(i).toString();
            KhachHang kh = qlkhachhangbus.getKhachHang(MaKH);
            new FormThemSuaKhachHang(kh, Integer.parseInt(tb.getValueSTT(i).toString())).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn khách hàng cần sữa");
        }
    }

    private void xuatExcelKH() {
        new XuatExcel().xuatFileExcelKhachHang();
    }

    private void nhapExcelKH() {
        new DocExcel().DocExcelKhachHang();
        btnLamMoi.doClick();
    }

    private void lamMoi() {
        // Xóa toàn bộ dữ liệu trong Table
        tb.clear();

        // Thêm lại dữ liệu mới vào Table
        qlkhachhangbus = new QuanlykhachhangBUS();
        int i = 0;
        for (KhachHang kh : qlkhachhangbus.getDSKH()) {
            ++i;
            tb.addRow(new String[]{Integer.toString(i), kh.getMaKH(), kh.getTenKH(), kh.getDiaChi(), kh.getSDT()});
        }

        // Cập nhật lại giao diện Table
        tb.resizeColumnWidth();
    }

   private void loadAndDisplayPermissions() {
               String maQuyen = LoginGUI_V1.currentChiTietQuyen.getMaQuyen();

    QuanlychitietquyenBUS quanlychitietquyenBUS = new QuanlychitietquyenBUS();
    ArrayList<ChiTietQuyen> actions = quanlychitietquyenBUS.getChiTietQuyenByMaQuyen(maQuyen);

            System.out.println("meo ac  " + actions);

   for (ChiTietQuyen action : actions) {
            if (action != null&& action.getMaChucNang().equals("KhachHang")) {
                if (action.isThem()) {
                    btnThem.setVisible(true);
                }
                if (action.isXoa()) {
                    btnXoa.setVisible(true);
                }
                if (action.isSua()) {
                    btnSua.setVisible(true);
                }
                if (action.isXem()) {
                    btnXem.setVisible(true);
                }
            }
}
}

 
}
