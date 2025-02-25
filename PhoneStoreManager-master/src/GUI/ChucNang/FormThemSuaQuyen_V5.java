/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.ChucNang;

import DTO.Quyen;

import BUS.QuanlychitietquyenBUS;
import BUS.QuanlyquyenBUS;
import DAO.QuanlychitietquyenDAO;
import DTO.ChiTietQuyen;
import DTO.Quyen;
import GUI.GiaoDien.GiaoDienGUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FormThemSuaQuyen_V5 extends JFrame {

    private Map<String, JPanel> selectedActionsPanelMap;

    private QuanlyquyenBUS quyenBUS;
    private QuanlychitietquyenBUS chitietQuyenBUS;

    private JButton btnThemSua;
    private JTextField txtMaQuyen = new JTextField();
    private JTextField txtTenQuyen = new JTextField();
    private JPanel mainPanel;
    private JLabel labelMaQuyen;
    private JLabel labelTenQuyen;
    private TitledBorder s;

    // Declare individual checkboxes
    private JCheckBox checkboxBanHang;
    private JCheckBox checkboxHoaDon;
    private JCheckBox checkboxKhachHang;
    private JCheckBox checkboxKhuyenMai;
    private JCheckBox checkboxLoaiSanPham;
    private JCheckBox checkboxNCC;
    private JCheckBox checkboxNhanVien;
    private JCheckBox checkboxNhapHang;
    private JCheckBox checkboxPhieuNhap;
    private JCheckBox checkboxSanPham;
    private JCheckBox checkboxTaiKhoan;
    private JPanel actionsPanelBanHang;
    private JPanel actionsPanelHoaDon;
    private JPanel actionsPanelKhachHang;
    private JPanel actionsPanelKhuyenMai;
    private JPanel actionsPanelLoaiSanPham;
    private JPanel actionsPanelNhaCungCap;
    private JPanel actionsPanelNhanVien;
    private JPanel actionsPanelNhapHang;
    private JPanel actionsPanelPhieuNhap;
    private JPanel actionsPanelSanPham;
    private JPanel actionsPanelTaiKhoan;
    private int row = 0;

    public FormThemSuaQuyen_V5(String MaQuyen) {

        selectedActionsPanelMap = new HashMap<>();

        quyenBUS = new QuanlyquyenBUS();
        setTitle("Thêm Quyền");
        setSize(1000, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        txtMaQuyen.setText(MaQuyen);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/signs-1.png")).getImage());

        initComponents();
        initCheckbox();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public FormThemSuaQuyen_V5(Quyen q, int i, int a) {

        selectedActionsPanelMap = new HashMap<>();

        initComponents();
        initCheckbox();
        quyenBUS = new QuanlyquyenBUS();
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/edit-tools.png")).getImage());
        if (a == 1) {
            btnThemSua.setText("Sửa Quyền");
        } else if (a == 2) {
            btnThemSua.setText("Thoát");
            setIconImage(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/eye.png")).getImage());

            btnThemSua.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/exitt.png")));

            disableEditing();
        }
        setSize(1000, 700);
        int row = 1;
        txtMaQuyen.setText(q.getMaQuyen());

        txtMaQuyen.setEditable(false);
        txtTenQuyen.setText(q.getTenQuyen());

        //-------------------------------------------------------
        // Lấy thông tin chi tiết quyền từ cơ sở dữ liệu thông qua lớp DAO
        QuanlychitietquyenDAO chitietQuyenDAO = new QuanlychitietquyenDAO();
        ArrayList<ChiTietQuyen> chiTietQuyenList = chitietQuyenDAO.getChiTietQuyenByMaQuyen(q.getMaQuyen());

        // Duyệt qua danh sách các chi tiết quyền và đánh dấu các checkbox tương ứng
        for (ChiTietQuyen chiTietQuyen : chiTietQuyenList) {
            String maChucNang = chiTietQuyen.getMaChucNang();
            boolean them = chiTietQuyen.isThem();
            boolean xoa = chiTietQuyen.isXoa();
            boolean sua = chiTietQuyen.isSua();
            boolean xem = chiTietQuyen.isXem();

            switch (maChucNang) {
                case "BanHang": {
                    checkboxBanHang.setSelected(true);
                    break;
                }
                case "NhapHang": {
                    checkboxNhapHang.setSelected(true);
                    break;
                }

                case "SanPham": {
                    checkboxSanPham.setSelected(true);

                    break;
                }
                case "LoaiSanPham": {
                    checkboxLoaiSanPham.setSelected(true);

                    break;
                }
                case "HoaDon": {
                    checkboxHoaDon.setSelected(true);

                    break;
                }
                case "KhuyenMai": {
                    checkboxKhuyenMai.setSelected(true);

                    break;
                }
                case "NhanVien": {
                    checkboxNhanVien.setSelected(true);

                    break;
                }
                case "KhachHang": {
                    checkboxKhachHang.setSelected(true);

                    break;
                }
                case "PhieuNhap": {
                    checkboxPhieuNhap.setSelected(true);

                    break;
                }
                case "NhaCungCap": {
                    checkboxNCC.setSelected(true);

                    break;
                }
                case "TaiKhoan": {
                    checkboxTaiKhoan.setSelected(true);

                    break;
                }
                default:
                    break;
            }
            // Đánh dấu các checkbox hành động tương ứng
            JPanel actionsPanel = getActionsPanelByName(maChucNang);
            if (actionsPanel != null) {
                for (Component component : actionsPanel.getComponents()) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        String action = checkBox.getText();
                        switch (action) {
                            case "Thêm":
                                checkBox.setSelected(them);
                                break;
                            case "Xóa":
                                checkBox.setSelected(xoa);
                                break;
                            case "Sửa":
                                checkBox.setSelected(sua);
                                break;
                            case "Xem":
                                checkBox.setSelected(xem);
                                break;
                        }
                    }
                }
            }
        }

        //---------------------------------------------
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Setup header panel
        JPanel headerPanel = new JPanel(new FlowLayout(30, 20, 20));
        labelMaQuyen = new JLabel("Mã quyền:");
        labelMaQuyen.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMaQuyen.setPreferredSize(new Dimension(100, 40));

        labelTenQuyen = new JLabel("Tên quyền:");
        labelTenQuyen.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTenQuyen.setPreferredSize(new Dimension(100, 40));
//        txtTenQuyen.setText("Administrator");  // Example default value

        headerPanel.add(labelMaQuyen);
        headerPanel.add(txtMaQuyen);
        headerPanel.add(labelTenQuyen);
        headerPanel.add(txtTenQuyen);
        headerPanel.setBorder(new EmptyBorder(10, 10, 5, 5));
        headerPanel.setBackground(Color.white);
        add(headerPanel, BorderLayout.NORTH);

        // ----------------Main Panel
        mainPanel = new JPanel(null);
        s = new TitledBorder("Chi Tiết Quyền");
        s.setTitleFont(new Font("Arial", Font.PLAIN, 20));

        mainPanel.setBorder(s);

        JLabel labelMaChucNang = new JLabel("Mã Chức Năng");
        labelMaChucNang.setFont(new Font("Arial", Font.BOLD, 16));

        labelMaChucNang.setBounds(220, 40, 150, 30);
        mainPanel.add(labelMaChucNang);

        JLabel labelHanhdong = new JLabel("Hành Động");
        labelHanhdong.setBounds(560, 40, 400, 30);
        labelHanhdong.setFont(new Font("Arial", Font.BOLD, 16));

        mainPanel.add(labelHanhdong);

        addCheckBoxes();

        
        mainPanel.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(mainPanel);

        add(scrollPane, BorderLayout.CENTER);

        // -------------------------south panel
        btnThemSua = new JButton();
        btnThemSua.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/signs-1.png")));
        btnThemSua.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThemSua.setText("Thêm Quyền");

        btnThemSua.addActionListener(e -> ThemSuaQuyen());

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(btnThemSua);
        southPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        southPanel.setBackground(Color.white);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void addCheckBoxes() {
        int y = 80;
        int x = 220;
        int width = 200;
        int height = 30;
        int gap = 35;
        setBackground(Color.white);

        // Initialize and set bounds for each checkbox
        checkboxBanHang = new JCheckBox("Bán hàng");
        checkboxBanHang.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxBanHang.setBounds(x, y, width, height);
        actionsPanelBanHang = createActionsPanel(x + width, y, "BanHang");

        mainPanel.add(checkboxBanHang);

        mainPanel.add(actionsPanelBanHang);

        // Initialize and set bounds for each checkbox
        checkboxHoaDon = new JCheckBox("Hóa đơn");
        checkboxHoaDon.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxHoaDon.setBounds(x, y += gap, width, height);
        actionsPanelHoaDon = createActionsPanel(x + width, y, "HoaDon");

        mainPanel.add(checkboxHoaDon);
        mainPanel.add(actionsPanelHoaDon);

        // Initialize and set bounds for each checkbox
        checkboxKhachHang = new JCheckBox("Khách hàng");
        checkboxKhachHang.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxKhachHang.setBounds(x, y += gap, width, height);
        actionsPanelKhachHang = createActionsPanel(x + width, y, "KhachHang");
        mainPanel.add(checkboxKhachHang);
        mainPanel.add(actionsPanelKhachHang);

        // Initialize and set bounds for each checkbox
        checkboxKhuyenMai = new JCheckBox("Khuyến mãi");
        checkboxKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxKhuyenMai.setBounds(x, y += gap, width, height);
        actionsPanelKhuyenMai = createActionsPanel(x + width, y, "KhuyenMai");
        mainPanel.add(checkboxKhuyenMai);
        mainPanel.add(actionsPanelKhuyenMai);

        // Initialize and set bounds for each checkbox
        checkboxLoaiSanPham = new JCheckBox("Loại sản phẩm");
        checkboxLoaiSanPham.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxLoaiSanPham.setBounds(x, y += gap, width, height);
        actionsPanelLoaiSanPham = createActionsPanel(x + width, y, "LoaiSanPham");
        mainPanel.add(checkboxLoaiSanPham);
        mainPanel.add(actionsPanelLoaiSanPham);

        // Initialize and set bounds for each checkbox
        checkboxNCC = new JCheckBox("Nhà cung cấp");
        checkboxNCC.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxNCC.setBounds(x, y += gap, width, height);
        actionsPanelNhaCungCap = createActionsPanel(x + width, y, "NhaCungCap");
        mainPanel.add(checkboxNCC);
        mainPanel.add(actionsPanelNhaCungCap);

        // Initialize and set bounds for each checkbox
        checkboxNhanVien = new JCheckBox("Nhân viên");
        checkboxNhanVien.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxNhanVien.setBounds(x, y += gap, width, height);
        actionsPanelNhanVien = createActionsPanel(x + width, y, "NhanVien");
        mainPanel.add(checkboxNhanVien);
        mainPanel.add(actionsPanelNhanVien);

        // Initialize and set bounds for each checkbox
        checkboxNhapHang = new JCheckBox("Nhập hàng");
        checkboxNhapHang.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxNhapHang.setBounds(x, y += gap, width, height);
        actionsPanelNhapHang = createActionsPanel(x + width, y, "NhapHang");
        mainPanel.add(checkboxNhapHang);
        mainPanel.add(actionsPanelNhapHang);

        // Initialize and set bounds for each checkbox
        checkboxPhieuNhap = new JCheckBox("Phiếu nhập");
        checkboxPhieuNhap.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxPhieuNhap.setBounds(x, y += gap, width, height);
        actionsPanelPhieuNhap = createActionsPanel(x + width, y, "PhieuNhap");
        mainPanel.add(checkboxPhieuNhap);
        mainPanel.add(actionsPanelPhieuNhap);

        // Initialize and set bounds for each checkbox
        checkboxSanPham = new JCheckBox("Sản phẩm");
        checkboxSanPham.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxSanPham.setBounds(x, y += gap, width, height);
        actionsPanelSanPham = createActionsPanel(x + width, y, "SanPham");
        mainPanel.add(checkboxSanPham);
        mainPanel.add(actionsPanelSanPham);

        // Initialize and set bounds for each checkbox
        checkboxTaiKhoan = new JCheckBox("Tài khoản");
        checkboxTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxTaiKhoan.setBounds(x, y += gap, width, height);
        actionsPanelTaiKhoan = createActionsPanel(x + width, y, "TaiKhoan");
        mainPanel.add(checkboxTaiKhoan);
        mainPanel.add(actionsPanelTaiKhoan);

        checkboxBanHang.addItemListener(e -> handleFunctionCheckboxChange("BanHang", e.getStateChange()));
        checkboxHoaDon.addItemListener(e -> handleFunctionCheckboxChange("HoaDon", e.getStateChange()));
        checkboxKhachHang.addItemListener(e -> handleFunctionCheckboxChange("KhachHang", e.getStateChange()));
        checkboxKhuyenMai.addItemListener(e -> handleFunctionCheckboxChange("KhuyenMai", e.getStateChange()));
        checkboxLoaiSanPham.addItemListener(e -> handleFunctionCheckboxChange("LoaiSanPham", e.getStateChange()));
        checkboxNCC.addItemListener(e -> handleFunctionCheckboxChange("NhaCungCap", e.getStateChange()));
        checkboxNhanVien.addItemListener(e -> handleFunctionCheckboxChange("NhanVien", e.getStateChange()));
        checkboxNhapHang.addItemListener(e -> handleFunctionCheckboxChange("NhapHang", e.getStateChange()));
        checkboxPhieuNhap.addItemListener(e -> handleFunctionCheckboxChange("PhieuNhap", e.getStateChange()));
        checkboxSanPham.addItemListener(e -> handleFunctionCheckboxChange("SanPham", e.getStateChange()));
        checkboxTaiKhoan.addItemListener(e -> handleFunctionCheckboxChange("TaiKhoan", e.getStateChange()));
    }

    private JPanel createActionsPanel(int x, int y, String functionName) {
        JPanel actionsPanel = new JPanel(null);
        int width = 100;
        int height = 30;
        int gap = 35;

        // Set layout to null for absolute positioning
        actionsPanel.setLayout(null);
        actionsPanel.setBackground(new Color(231,245,250));

        // Initialize and set bounds for each checkbox
        JCheckBox checkboxThem = new JCheckBox("Thêm");
        checkboxThem.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxThem.setBounds(0, 0, width, height);
        actionsPanel.add(checkboxThem);

        JCheckBox checkboxXem = new JCheckBox("Xem");
        checkboxXem.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxXem.setBounds(width, 0, width, height);
        actionsPanel.add(checkboxXem);

        JCheckBox checkboxSua = new JCheckBox("Sửa");
        checkboxSua.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxSua.setBounds(2 * width, 0, width, height);
        actionsPanel.add(checkboxSua);

        JCheckBox checkboxXoa = new JCheckBox("Xóa");
        checkboxXoa.setFont(new Font("Arial", Font.PLAIN, 16));
        checkboxXoa.setBounds(3 * width, 0, width, height);
        actionsPanel.add(checkboxXoa);

        // Set size for actionsPanel based on the width and height of checkboxes
        actionsPanel.setPreferredSize(new Dimension(4 * width, height));

        // Gọi phương thức xử lý sự kiện cho từng checkbox hành động ở đây
        checkboxThem.addItemListener(e -> handleActionCheckboxChange(functionName, "Thêm", e.getStateChange()));
        checkboxXem.addItemListener(e -> handleActionCheckboxChange(functionName, "Xem", e.getStateChange()));
        checkboxSua.addItemListener(e -> handleActionCheckboxChange(functionName, "Sửa", e.getStateChange()));
        checkboxXoa.addItemListener(e -> handleActionCheckboxChange(functionName, "Xóa", e.getStateChange()));

        // Set position for the actionsPanel
        actionsPanel.setBounds(x, y, 4 * width, height);

        return actionsPanel;

    }

    // Xử lý Checkbox  hành  động 
    private void handleActionCheckboxChange(String functionName, String action, int stateChange) {

    }

    // Xử lý Checkbox chức năng 
    private void handleFunctionCheckboxChange(String functionName, int stateChange) {

        boolean isSelected = (stateChange == ItemEvent.SELECTED);

        // Tìm panel hành động tương ứng dựa trên tên chức năng
        JPanel actionsPanel = getActionsPanelByName(functionName);

        // Hiển thị hoặc ẩn panel hành động tương ứng
        if (actionsPanel != null) {
            actionsPanel.setEnabled(isSelected);

            // Thêm hoặc loại bỏ panel hành động vào selectedActionsPanelMap
            if (isSelected) {
                selectedActionsPanelMap.put(functionName, actionsPanel);
            } else {
                selectedActionsPanelMap.remove(functionName);
            }

            // Điều chỉnh trạng thái của các checkbox hành động
            if (!isSelected) {
                disableActionCheckboxes(actionsPanel);
            } else {
                enableActionCheckboxes(actionsPanel);
            }
        }

        //------------------------------------Lấy dữ liệu checkbox
    }

    private JPanel getActionsPanelByName(String functionName) {
        switch (functionName) {
            case "BanHang":
                return actionsPanelBanHang;
            case "HoaDon":
                return actionsPanelHoaDon;
            case "KhachHang":
                return actionsPanelKhachHang;
            case "KhuyenMai":
                return actionsPanelKhuyenMai;
            case "LoaiSanPham":
                return actionsPanelLoaiSanPham;
            case "NhaCungCap":
                return actionsPanelNhaCungCap;
            case "NhanVien":
                return actionsPanelNhanVien;
            case "NhapHang":
                return actionsPanelNhapHang;
            case "PhieuNhap":
                return actionsPanelPhieuNhap;
            case "SanPham":
                return actionsPanelSanPham;
            case "TaiKhoan":
                return actionsPanelTaiKhoan;
            // Thêm các trường hợp khác nếu cần
            default:
                return null;
        }
    }

    private void disableActionCheckboxes(JPanel actionsPanel) {
        for (Component component : actionsPanel.getComponents()) {
            if (component instanceof JCheckBox) {
                component.setEnabled(false);
            }
        }
    }

    private void enableActionCheckboxes(JPanel actionsPanel) {
        for (Component component : actionsPanel.getComponents()) {
            if (component instanceof JCheckBox) {
                component.setEnabled(true);
            }
        }
    }

    private void disableEditing() {
        txtTenQuyen.setEditable(false);

        for (Component component : this.getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component subComponent : panel.getComponents()) {
                    if (subComponent instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) subComponent;
                        checkBox.setEnabled(false);
                    }
                }
            }
        }
    }

    private void initCheckbox() {
        // BanHang
        disableActionCheckboxes(actionsPanelBanHang);

        // HoaDon
        disableActionCheckboxes(actionsPanelHoaDon);

        // KhachHang
        disableActionCheckboxes(actionsPanelKhachHang);

        // KhuyenMai
        disableActionCheckboxes(actionsPanelKhuyenMai);

        // LoaiSanPham
        disableActionCheckboxes(actionsPanelLoaiSanPham);

        // NhaCungCap
        disableActionCheckboxes(actionsPanelNhaCungCap);

        // NhanVien
        disableActionCheckboxes(actionsPanelNhanVien);

        // NhapHang
        disableActionCheckboxes(actionsPanelNhapHang);

        // PhieuNhap
        disableActionCheckboxes(actionsPanelPhieuNhap);

        // SanPham
        disableActionCheckboxes(actionsPanelSanPham);

        // TaiKhoan
        disableActionCheckboxes(actionsPanelTaiKhoan);
    }

    public static void main(String[] args) {
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
        String MaQuyen = Quanlyquyenbus.MaQuyennotHave();
        new FormThemSuaQuyen_V5(MaQuyen).setVisible(true);
        System.out.println(MaQuyen);

    }

    // -------------------------------Mẫu mới------------------------------
    private void ThemSuaQuyen() {
        // Lấy thông tin từ các thành phần giao diện
        String maQuyen = txtMaQuyen.getText().trim();
        String tenQuyen = txtTenQuyen.getText().trim();
        // Lấy thông tin chi tiết quyền từ các checkbox và combobox
        String chitietChucNang = getChiTietQuyenFromCheckbox();

        // Tạo danh sách chi tiết quyền từ các hành động được chọn
        ArrayList<ChiTietQuyen> chiTietQuyenList = new ArrayList<>();

        // Duyệt qua các panel hành động đã được chọn
        for (String functionName : selectedActionsPanelMap.keySet()) {
            JPanel actionsPanel = selectedActionsPanelMap.get(functionName);

            // Tạo một đối tượng ChiTietQuyen từ thông tin của actionsPanel
            ChiTietQuyen chiTietQuyen = createChiTietQuyenFromActionsPanel(maQuyen, functionName, actionsPanel);

            // Thêm đối tượng ChiTietQuyen vào danh sách
            chiTietQuyenList.add(chiTietQuyen);
        }

        QuanlychitietquyenDAO chitietQuyenDAO = new QuanlychitietquyenDAO();
        QuanlychitietquyenBUS chitietQuyenBUS = new QuanlychitietquyenBUS();

        // Xử lý chức năng "Sửa", "Xem" hoặc "Thêm" tùy thuộc vào văn bản trên nút
        switch (btnThemSua.getText()) {
            case "Sửa Quyền":
                // Kiểm tra hợp lệ của thông tin sửa
                if (tenQuyen.equals("")) {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại. Hãy kiểm tra lại tên quyền.");
                    return;
                }

                // Cập nhật thông tin quyền
                if (quyenBUS.repaireQuyen(maQuyen, tenQuyen, chitietChucNang)) {
                    QuyenPanel.tb.setValueRow(maQuyen, new String[]{Integer.toString(row), maQuyen, tenQuyen, chitietChucNang});

                    // Xóa chi tiết quyền cũ
                    chitietQuyenDAO.deleteChiTietQuyen(maQuyen);

                    for (ChiTietQuyen chiTietQuyen : chiTietQuyenList) {
                        if (chitietQuyenDAO.addChiTietQuyen(chiTietQuyen)) {
                            // Sửa chi tiết quyền thành công
                        } else {
                            // Sửa chi tiết quyền thất bại
                            JOptionPane.showMessageDialog(this, "Sửa chi tiết quyền thất bại");
                            return; // Kết thúc sớm nếu có lỗi
                        }
                    }
                    // Sửa thành công
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại");
                }
                // Đóng cửa sổ sau khi sửa
                this.dispose();
                break;

            case "Thoát":
                // Đóng form
                this.dispose();
                break;

            case "Thêm Quyền":
                // Xử lý chức năng "Thêm"
                // Kiểm tra tính hợp lệ của thông tin mới
                if (maQuyen.equals("") || tenQuyen.equals("")) {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại. Hãy kiểm tra lại mã và tên quyền.");
                    return;
                }
                if (!Pattern.matches("Q[\\d]{1,}", maQuyen)) {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại. Mã quyền không hợp lệ.");
                    return;
                }

                // Kiểm tra mã quyền đã tồn tại chưa
                Quyen q = quyenBUS.getQuyen(maQuyen);
                if (q != null) {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại. Mã quyền đã tồn tại.");
                    return;
                }

                // Thêm quyền mới vào cơ sở dữ liệu và cập nhật giao diện
                if (quyenBUS.addQuyen(new Quyen(maQuyen, tenQuyen, chitietChucNang))) {
                    // Thêm thành công
                    for (ChiTietQuyen chiTietQuyen : chiTietQuyenList) {
                        if (chitietQuyenDAO.addChiTietQuyen(chiTietQuyen)) {
                            // Thêm chi tiết quyền thành công
                        } else {
                            // Thêm chi tiết quyền thất bại
                            JOptionPane.showMessageDialog(this, "Thêm chi tiết quyền thất bại");
                            return; // Kết thúc sớm nếu có lỗi
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Thêm quyền thành công");
                } else {
                    // Thêm thất bại
                    JOptionPane.showMessageDialog(this, "Thêm quyền thất bại");
                }
                break;

            default:
                JOptionPane.showMessageDialog(this, "Chức năng không hợp lệ.");
                break;
        }
    }

    // -------------------------------Mẫu mới------------------------------
    // Phương thức để tạo một đối tượng ChiTietQuyen từ một actionsPanel
    private ChiTietQuyen createChiTietQuyenFromActionsPanel(String maQuyen, String functionName, JPanel actionsPanel) {
        boolean them = false;
        boolean xoa = false;
        boolean sua = false;
        boolean xem = false;

        // Duyệt qua các checkbox hành động trong actionsPanel và cập nhật các cờ tương ứng
        for (Component component : actionsPanel.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.getText().equals("Thêm") && checkBox.isSelected()) {
                    them = true;
                } else if (checkBox.getText().equals("Xóa") && checkBox.isSelected()) {
                    xoa = true;
                } else if (checkBox.getText().equals("Sửa") && checkBox.isSelected()) {
                    sua = true;
                } else if (checkBox.getText().equals("Xem") && checkBox.isSelected()) {
                    xem = true;
                }
            }
        }

        // Tạo một đối tượng ChiTietQuyen từ thông tin thu được
        return new ChiTietQuyen(maQuyen, functionName, them, xoa, sua, xem);
    }

    private String getChiTietQuyenFromCheckbox() {
        String ChiTietQuyen = "";
        if (checkboxBanHang.isSelected()) {
            ChiTietQuyen += "BanHang ";
        }
        if (checkboxNhapHang.isSelected()) {
            ChiTietQuyen += "NhapHang ";
        }
        if (checkboxSanPham.isSelected()) {
            ChiTietQuyen += "SanPham ";
        }
        if (checkboxLoaiSanPham.isSelected()) {
            ChiTietQuyen += "LoaiSanPham ";
        }
        if (checkboxHoaDon.isSelected()) {
            ChiTietQuyen += "HoaDon ";
        }
        if (checkboxKhuyenMai.isSelected()) {
            ChiTietQuyen += "KhuyenMai ";
        }
        if (checkboxNhanVien.isSelected()) {
            ChiTietQuyen += "NhanVien ";
        }
        if (checkboxKhachHang.isSelected()) {
            ChiTietQuyen += "KhachHang ";
        }
        if (checkboxPhieuNhap.isSelected()) {
            ChiTietQuyen += "PhieuNhap ";
        }
        if (checkboxNCC.isSelected()) {
            ChiTietQuyen += "NCC ";
        }
        if (checkboxTaiKhoan.isSelected()) {
            ChiTietQuyen += "TaiKhoan ";
        }

        ChiTietQuyen = ChiTietQuyen.trim();
        return ChiTietQuyen;
        // Code để lấy thông tin chi tiết quyền từ các checkbox và combobox
        // Đảm bảo bạn tạo phương thức phù hợp để thực hiện điều này
    }

    // Các phương thức khác của lớp
}
