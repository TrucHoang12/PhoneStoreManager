
import BUS.QuanlychitietquyenBUS;
import BUS.QuanlyquyenBUS;
import DAO.QuanlychitietquyenDAO;
import DTO.ChiTietQuyen;
import DTO.Quyen;
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

    // HashMap to store action checkboxes for each function
    public FormThemSuaQuyen_V5(String MaQuyen) {

        selectedActionsPanelMap = new HashMap<>();

        quyenBUS = new QuanlyquyenBUS();
        setTitle("Thêm Quyền");
        setSize(1400, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        txtMaQuyen.setText(MaQuyen);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/signs-1.png")).getImage());

        initComponents();
        initCheckbox();

    }
    
        public FormThemSuaQuyen_V5(Quyen q, int i) {
            
                    selectedActionsPanelMap = new HashMap<>();

            quyenBUS = new QuanlyquyenBUS();
 setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/edit-tools.png")).getImage());
        btnThemSua.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/edit-tools.png")));
        btnThemSua.setText("Sửa");        setSize(1400, 700);
        int row =1;
      
        
                String s = q.getChiTietQuyen();
                
                        s = s.replace(",", " ");
                        
                        System.out.println(s);

                
                

         txtMaQuyen.setText(q.getMaQuyen());
        txtMaQuyen.setEditable(false);
        txtTenQuyen.setText(q.getTenQuyen());
        
        
        setResizable(false);
        setLocationRelativeTo(null);
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
        add(headerPanel, BorderLayout.NORTH);

        // Setup main panel with checkboxes
        mainPanel = new JPanel(null); // Set layout to null for absolute positioning
        s = new TitledBorder("Chi Tiết Quyền");
        s.setTitleFont(new Font("Arial", Font.PLAIN, 20));

        mainPanel.setBorder(s);

        JLabel labelMaChucNang = new JLabel("Mã Chức Năng");
        labelMaChucNang.setBounds(70, 20, 150, 30);
        mainPanel.add(labelMaChucNang);

        JLabel labelHanhdong = new JLabel("Hành Động");
        labelHanhdong.setBounds(250, 20, 400, 30);
        mainPanel.add(labelHanhdong);

        addCheckBoxes();
//                        initCheckbox();

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Setup south panel
        btnThemSua = new JButton("Thêm Quyền");
        btnThemSua.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/signs-1.png")));
        btnThemSua.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThemSua.addActionListener(e -> ThemSuaQuyen());

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(btnThemSua);
        southPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(southPanel, BorderLayout.SOUTH);
    }

    private void addCheckBoxes() {
        int y = 50; // Initial y position
        int x = 10; // Initial x position
        int width = 200; // Width of each checkbox
        int height = 30; // Height of each checkbox
        int gap = 35; // Gap between checkboxes

        // Initialize and set bounds for each checkbox
        checkboxBanHang = new JCheckBox("Bán hàng");
        checkboxBanHang.setBounds(x, y, width, height);
        actionsPanelBanHang = createActionsPanel(x + width, y, "BanHang");

        mainPanel.add(checkboxBanHang);

        mainPanel.add(actionsPanelBanHang);

        // Initialize and set bounds for each checkbox
        checkboxHoaDon = new JCheckBox("Hóa đơn");
        checkboxHoaDon.setBounds(x, y += gap, width, height);
        actionsPanelHoaDon = createActionsPanel(x + width, y, "HoaDon");

        mainPanel.add(checkboxHoaDon);
        mainPanel.add(actionsPanelHoaDon);

        // Initialize and set bounds for each checkbox
        checkboxKhachHang = new JCheckBox("Khách hàng");
        checkboxKhachHang.setBounds(x, y += gap, width, height);
        actionsPanelKhachHang = createActionsPanel(x + width, y, "KhachHang");
        mainPanel.add(checkboxKhachHang);
        mainPanel.add(actionsPanelKhachHang);

        // Initialize and set bounds for each checkbox
        checkboxKhuyenMai = new JCheckBox("Khuyến mãi");
        checkboxKhuyenMai.setBounds(x, y += gap, width, height);
        actionsPanelKhuyenMai = createActionsPanel(x + width, y, "KhuyenMai");
        mainPanel.add(checkboxKhuyenMai);
        mainPanel.add(actionsPanelKhuyenMai);

        // Initialize and set bounds for each checkbox
        checkboxLoaiSanPham = new JCheckBox("Loại sản phẩm");
        checkboxLoaiSanPham.setBounds(x, y += gap, width, height);
        actionsPanelLoaiSanPham = createActionsPanel(x + width, y, "LoaiSanPham");
        mainPanel.add(checkboxLoaiSanPham);
        mainPanel.add(actionsPanelLoaiSanPham);

        // Initialize and set bounds for each checkbox
        checkboxNCC = new JCheckBox("Nhà cung cấp");
        checkboxNCC.setBounds(x, y += gap, width, height);
        actionsPanelNhaCungCap = createActionsPanel(x + width, y, "NhaCungCap");
        mainPanel.add(checkboxNCC);
        mainPanel.add(actionsPanelNhaCungCap);

        // Initialize and set bounds for each checkbox
        checkboxNhanVien = new JCheckBox("Nhân viên");
        checkboxNhanVien.setBounds(x, y += gap, width, height);
        actionsPanelNhanVien = createActionsPanel(x + width, y, "NhanVien");
        mainPanel.add(checkboxNhanVien);
        mainPanel.add(actionsPanelNhanVien);

        // Initialize and set bounds for each checkbox
        checkboxNhapHang = new JCheckBox("Nhập hàng");
        checkboxNhapHang.setBounds(x, y += gap, width, height);
        actionsPanelNhapHang = createActionsPanel(x + width, y, "NhapHang");
        mainPanel.add(checkboxNhapHang);
        mainPanel.add(actionsPanelNhapHang);

        // Initialize and set bounds for each checkbox
        checkboxPhieuNhap = new JCheckBox("Phiếu nhập");
        checkboxPhieuNhap.setBounds(x, y += gap, width, height);
        actionsPanelPhieuNhap = createActionsPanel(x + width, y, "PhieuNhap");
        mainPanel.add(checkboxPhieuNhap);
        mainPanel.add(actionsPanelPhieuNhap);

        // Initialize and set bounds for each checkbox
        checkboxSanPham = new JCheckBox("Sản phẩm");
        checkboxSanPham.setBounds(x, y += gap, width, height);
        actionsPanelSanPham = createActionsPanel(x + width, y, "SanPham");
        mainPanel.add(checkboxSanPham);
        mainPanel.add(actionsPanelSanPham);

        // Initialize and set bounds for each checkbox
        checkboxTaiKhoan = new JCheckBox("Tài khoản");
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

        // Initialize and set bounds for each checkbox
        JCheckBox checkboxThem = new JCheckBox("Thêm");
        checkboxThem.setBounds(0, 0, width, height);
        actionsPanel.add(checkboxThem);

        JCheckBox checkboxXem = new JCheckBox("Xem");
        checkboxXem.setBounds(width, 0, width, height);
        actionsPanel.add(checkboxXem);

        JCheckBox checkboxSua = new JCheckBox("Sửa");
        checkboxSua.setBounds(2 * width, 0, width, height);
        actionsPanel.add(checkboxSua);

        JCheckBox checkboxXoa = new JCheckBox("Xóa");
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

    private void ThemSuaQuyen() {
//        if(btnThemSua.getText().equals("Thêm")){
        // Lấy thông tin từ các thành phần giao diện
        String maQuyen = txtMaQuyen.getText().trim();
        String tenQuyen = txtTenQuyen.getText().trim();
        // Lấy thông tin chi tiết quyền từ các checkbox và combobox
        String chitietChucNang = getChiTietQuyenFromCheckbox();

//        }
// Tạo danh sách chi tiết quyền từ các hành động được chọn
        ArrayList<ChiTietQuyen> chiTietQuyenList = new ArrayList<>();
        
        // Dùng vòng lặp thêm danh sách hành động vào chiTietQuyenList
        for (String functionName : selectedActionsPanelMap.keySet()) {
            JPanel actionsPanel = selectedActionsPanelMap.get(functionName);

            // Tạo một đối tượng ChiTietQuyen từ thông tin của actionsPanel
            ChiTietQuyen chiTietQuyen = createChiTietQuyenFromActionsPanel(maQuyen, functionName, actionsPanel);

            // Thêm đối tượng ChiTietQuyen vào danh sách
            chiTietQuyenList.add(chiTietQuyen);
        }

        QuanlychitietquyenDAO chitietQuyenDAO = new QuanlychitietquyenDAO();
        QuanlychitietquyenBUS chitietquyenBUS = new QuanlychitietquyenBUS();

        // Thêm quyền mới vào cơ sở dữ liệu và cập nhật giao diện
        if (quyenBUS.addQuyen(new Quyen(maQuyen, tenQuyen, chitietChucNang))) {
            // Thêm thành công
            for (ChiTietQuyen chiTietQuyen : chiTietQuyenList) {
                if (chitietQuyenDAO.addChiTietQuyen(chiTietQuyen)) {
                    // Thêm thành công
//                JOptionPane.showMessageDialog(this, "Thêm chi tiết quyền thành công");
                } else {
                    // Thêm thất bại
                    JOptionPane.showMessageDialog(this, "Thêm chi tiết quyền thất bại");
                }
            }
            JOptionPane.showMessageDialog(this, "Thêm quyền thành công");
        } else {
            // Thêm thất bại
            JOptionPane.showMessageDialog(this, "Thêm quyền thất bại");
        }

    }

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
