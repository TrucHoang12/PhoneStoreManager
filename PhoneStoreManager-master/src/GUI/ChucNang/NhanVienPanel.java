package GUI.ChucNang;

import BUS.QuanlychitietquyenBUS;
import BUS.QuanlynhanvienBUS;
import DTO.ChiTietQuyen;
import DTO.NhanVien;
import DTO.TaiKhoan;
import EXEL.DocExcel;
import EXEL.XuatExcel;
import GUI.GiaoDien.GiaoDienGUI;
import GUI.GiaoDien.LoginGUI_V1;
import GUI.GiaoDien.Table;
import GUI.Form.FormThemSuaNhanVien;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static GUI.GiaoDien.GiaoDienGUI.panelMain;

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

public class NhanVienPanel extends JPanel {

    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXuatExcel;
    private JButton btnNhapExcel;
    private JButton btnLamMoi;
    private Table tb;
    private QuanlynhanvienBUS qlnvBUS;
    private JPanel searchPanel;
    private JComboBox<String> comboBoxSearch;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnXem;

    public static int tb_y = 0;
    public static int tb_x = 0;
    public static int tb_w = 0;
    public static int tb_h = 0;

    public NhanVienPanel(int width, int height) {
        qlnvBUS = new QuanlynhanvienBUS();
        int x = 0, y = 0, w = 150, h = 50;

        x = (panelMain.getSize().width - w * 6) / 2;

        setLayout(new BorderLayout());
        setBackground(Color.white);
        setSize(width, height);

        themChucNangButton();
        initSearchPanel();

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
        qlnvBUS = new QuanlynhanvienBUS();
        btnThem = createButton("Thêm", "/com/PhoneStoreManager/image/signs-1.png");
        btnXoa = createButton("Đuổi làm", "/com/PhoneStoreManager/image/button-delete.png");
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
        tb.setHeaders(qlnvBUS.getHeaders);

        int i = 0;
        for (NhanVien nv : qlnvBUS.getDSNV()) {
            ++i;
            String tt = (nv.getTrangThai() == 1) ? "Còn làm" : "Hết làm";
            tb.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSDT(), tt});
        }
        tb.resizeColumnWidth();

        btnThem.addActionListener(e -> themKM());
        btnXoa.addActionListener(e -> xoaKM());
        btnSua.addActionListener(e -> suaKM());
        btnXem.addActionListener(e -> suaKM());

        btnXuatExcel.addActionListener(e -> xuatExcelKM());
        btnNhapExcel.addActionListener(e -> nhapExcelKM());
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

    private void initSearchPanel() {
        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        txtSearch = new JTextField(20);
        comboBoxSearch = new JComboBox<>(qlnvBUS.getComboboxSearch);
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(e -> EventSearchNhanVienforGiaoDien());

        Font font = new Font("Tahoma", Font.BOLD, 14);
        txtSearch.setFont(font);
        comboBoxSearch.setFont(font);
        btnSearch.setFont(font);
        txtSearch.setPreferredSize(new Dimension(200, 30));
        comboBoxSearch.setPreferredSize(new Dimension(150, 30));
        btnSearch.setPreferredSize(new Dimension(120, 40));

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(new JLabel("Loại:"));
        searchPanel.add(comboBoxSearch);
        searchPanel.add(btnSearch);

        int x = (panelMain.getSize().width - 800 - 300) / 2;
        int y = 50;
        searchPanel.setBounds(x, y, 800, 50);
    }

    private void EventSearchNhanVienforGiaoDien() {
        String s = txtSearch.getText().trim();
        String type = comboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<NhanVien> list = qlnvBUS.SearchNhanVien(s, type);
        tb.clear();

        if (list != null && !list.isEmpty()) {
            int i = 0;
            for (NhanVien nv : list) {
                ++i;
                String tt = (nv.getTrangThai() == 1) ? "Còn làm" : "Hết làm";
                tb.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSDT(), tt});
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

    private void themKM() {
        String MaNV = qlnvBUS.MaNVnotHave();
        new FormThemSuaNhanVien(MaNV).setVisible(true);
    }

    private void suaKM() {
        int i = tb.getSelectedRow();
        if (i >= 0) {
            String MaNV = tb.LayMaNVTaiTable(i).toString();
            NhanVien nv = qlnvBUS.getNhanVien(MaNV);
            new FormThemSuaNhanVien(nv, Integer.parseInt(tb.getValueSTT(i).toString())).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhân viên cần xóa");
        }
    }

    private void xoaKM() {
        int i = tb.getSelectedRow();
        if (i >= 0) {
            int k = JOptionPane.showConfirmDialog(null, "Bạn có chắc đuổi nhân viên này không?");
            if (k != JOptionPane.YES_OPTION) {
                return;
            }
            String MaNV = tb.LayMaNVTaiTable(i).toString();
            qlnvBUS.duoiNhanVien(MaNV);
            tb.setValueTrangThai(MaNV, "Hết làm");
//                    tb.setValueAt("Hết làm", i, 6);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhân viên cần xóa");
        }
    }

    private void xuatExcelKM() {
        new XuatExcel().xuatFileExcelNhanVien();
    }

    private void nhapExcelKM() {
        new DocExcel().DocExcelNhanVien();
        btnLamMoi.doClick();
    }

    private void lamMoi() {
        panelMain.remove(tb);
        tb = new Table();
        tb.setBound(tb_x, tb_y, tb_w, tb_h);
        tb.setHeaders(qlnvBUS.getHeaders);
        String tt = "";
        int i = 0;
        for (NhanVien nv : qlnvBUS.getDSNV()) {
            ++i;
            if (nv.getTrangThai() == 1) {
                tt = "Còn làm";
            } else {
                tt = "Hết làm";
            }
            tb.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSDT(), tt});
        }
        tb.setRowHeigth(35);
        tb.resizeColumnWidth();
        panelMain.add(tb);
        panelMain.validate();
    }

    private void loadAndDisplayPermissions() {
        String maQuyen = LoginGUI_V1.currentChiTietQuyen.getMaQuyen();

        QuanlychitietquyenBUS quanlychitietquyenBUS = new QuanlychitietquyenBUS();
        ArrayList<ChiTietQuyen> actions = quanlychitietquyenBUS.getChiTietQuyenByMaQuyen(maQuyen);

        System.out.println("meo ac  " + actions);

        for (ChiTietQuyen action : actions) {
            if (action != null && action.getMaChucNang().equals("NhanVien")) {
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
