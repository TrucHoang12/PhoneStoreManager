
package GUI.Form;

import BUS.QuanlychitiethoadonBUS;
import BUS.QuanlyhoadonBUS;
import BUS.QuanlykhachhangBUS;
import BUS.QuanlykhuyenmaiBUS;
import BUS.QuanlyloaisanphamBUS;
import BUS.QuanlynhanvienBUS;
import BUS.QuanlysanphamBUS;
import DTO.ChiTietHoaDon;
import DTO.HoaDon;
import DTO.KhachHang;
import DTO.KhuyenMai;
import DTO.NhanVien;
import DTO.SanPham;
import GUI.Button.RefreshButton;
import GUI.Button.SuaButton;
import GUI.Button.ThemButton;
import GUI.Button.XoaButton;
import GUI.GiaoDien.LoginGUI_V1;
import com.PhoneStoreManager.BackEnd.PriceFormatter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class BanHangForm extends JPanel {
    
    private JPanel panelLeft, panelRight;
    
    QuanlysanphamBUS qlspBUS = new QuanlysanphamBUS();
    QuanlyloaisanphamBUS qllspBUS = new QuanlyloaisanphamBUS();
    QuanlykhachhangBUS qlkhBUS = new QuanlykhachhangBUS();
    QuanlynhanvienBUS qlnvBUS = new QuanlynhanvienBUS();
    QuanlykhuyenmaiBUS qlkmBUS = new QuanlykhuyenmaiBUS();
    QuanlyhoadonBUS qlhdBUS = new QuanlyhoadonBUS();
    QuanlychitiethoadonBUS qlcthd = new QuanlychitiethoadonBUS();
    
    ThemButton btnThem = new ThemButton();
    XoaButton btnXoa = new XoaButton();
    SuaButton btnSua = new SuaButton();
    RefreshButton btnRefresh = new RefreshButton();
    
    JButton btnLamMoi;
    JButton btnThanhToan = new JButton("Thanh toán");
    JButton btnHuy = new JButton("Hủy");
    
    private DefaultTableModel model1, model2;

    //Panel Left
    private JPanel SanPhamPanel;
    private JPanel ChiTietSPPanel;
    
    private JLabel lbImage;
    
    private JTextField txtMaSP;
    private JTextField txtMaLSP;
    private JTextField txtTenSP;
    private JTextField txtDonGia;
    private JTextField txtSoLuong;
    
    private JTable tableSanPham;
    
    private JTextField txtTimKiem;
    
    private ArrayList<SanPham> listSP;

    
    //Panel Right
    private JPanel ThongTinPanel;
    private JPanel ChiTietPanel;
    private JPanel ThanhToanPanel;
    
    private JTextField txtMaHoaDon = new JTextField(20);
    private JTextField txtNhanVien = new JTextField(17);
    private JTextField txtNgayLap = new JTextField(9);
    private JTextField txtGioLap = new JTextField(9);
    private JTextField txtKhachHang = new JTextField(17);
    private JTextField txtTongTien = new JTextField(20);
    private JTextField txtKhuyenMai = new JTextField(17);
    
    private JComboBox<String> btnChonNhanVien = new JComboBox();
    private JComboBox<String> btnChonKhachHang = new JComboBox();
    private JComboBox<String> btnChonKhuyenMai = new JComboBox();
    
    private ArrayList<ChiTietHoaDon> dscthd = new ArrayList<>();
    private JTable tableChiTietHD;
    
    NhanVien nhanVien;
    KhachHang khachHang;
    KhuyenMai khuyenMai;
    long tongThanhToan;
    

    public BanHangForm(int width, int height) {
        setLayout(new FlowLayout(0, 0, 0));
        setSize(width, height);
        initComponent(width, height);
//        ChonSanPhamPanel cspbh = new ChonSanPhamPanel(0, 0, width - 555 , height);
//        this.add(cspbh);

//            ChonSanPhamForm gd = new ChonSanPhamForm(0, 0, width - 555, height);
//            this.add(gd);
        
//        Test Comments t ogit

//        HoaDonBanHang hdbh = new HoaDonBanHang(width - 550, 0, 550, height );
//        this.add(hdbh);

//        hdbh.setTarget(gd);
//        gd.setTarget(hdbh);
    }
    
    public void initComponent(int width, int height){

        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        
        // ====== Panel Left - Panel chọn sản phẩm ======
        int sizePanelLeft = width - 555;
        panelLeft = new JPanel(new BorderLayout());
//        panelLeft.setBounds(0, 0, width - 555, height);
        panelLeft.setPreferredSize(new Dimension(sizePanelLeft, height));
        panelLeft.setBackground(Color.red);
        
        //========================================
        JPanel TimKiemPanel = new JPanel();
        txtTimKiem = new JTextField(20);
        txtTimKiem.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txtTimKiem.setHorizontalAlignment(JLabel.CENTER);
        addDocumentListener(txtTimKiem);
        
//        btnRefresh.addActionListener((ae) -> {
//            refreshTable();
//        });
        btnLamMoi = createButton("Làm mới", "/com/PhoneStoreManager/image/internet.png");
        btnLamMoi.addActionListener((ActionEvent e) -> {
           refreshTable();
        });
        
        TimKiemPanel.add(txtTimKiem);
        TimKiemPanel.add(btnLamMoi);
        
        SanPhamPanel = new JPanel(new BorderLayout());
        SanPhamPanel.setBackground(new Color(49, 49, 49));
        
        String[] columnNamesModel1 = {"Mã", "Loại", "Tên", "Đơn giá", "Số lượng"};
        model1 = new DefaultTableModel(columnNamesModel1, 0);
        
        // Thêm dữ liệu từ danhSachSanPham vào tableModel
        listSP = qlspBUS.getDSSP();
        for (SanPham sp : listSP) {
            Object[] row = {sp.getMaSP(), sp.getMaLSP(), sp.getTenSP(), sp.getDonGia(), sp.getSoLuong()};
            model1.addRow(row);
        }
        
        //Tạo JTable
        tableSanPham = new JTable(model1);
        tableSanPham.setRowHeight(25);
        
        JTableHeader tableHeader1 = tableSanPham.getTableHeader();
        tableHeader1.setFont(f); // Đặt font cho tiêu đề
        
        tableSanPham.setTableHeader(tableHeader1);
        
        JScrollPane scrollPane1 = new JScrollPane(tableSanPham);
        scrollPane1.setBackground(Color.white);
        
        SanPhamPanel.add(TimKiemPanel, BorderLayout.NORTH);
        SanPhamPanel.add(scrollPane1, BorderLayout.CENTER);
        
        tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                // Lấy mã sản phẩm được chọn từ hàng đầu tiên trong JTable
                String masp = getSelectedSanPham(tableSanPham.getSelectedRow());

                if (masp != null) {
                    // Hiển thị thông tin của sản phẩm (ví dụ: showInfo là phương thức hiển thị thông tin sản phẩm)
                    showInfo(masp, 1);
                }
            }
        });
        
        //==========================
        ChiTietSPPanel = new JPanel(new BorderLayout());
        ChiTietSPPanel.setBackground(new Color(240, 240, 240));
        ChiTietSPPanel.setPreferredSize(new Dimension(sizePanelLeft, 350));
        
        lbImage = new JLabel();
        lbImage.setPreferredSize(new Dimension(200, 200));
        
        JPanel plTextField = new JPanel();
        plTextField.setLayout(new GridLayout(4,2));
        plTextField.setBackground(new Color(240, 240, 240));
        
        txtMaSP = new JTextField(15);
        txtMaLSP = new JTextField(15);
        txtTenSP = new JTextField(15);
        txtDonGia = new JTextField(15);
        txtSoLuong = new JTextField(15);

        
        // border
        txtMaSP.setBorder(BorderFactory.createTitledBorder("Mã sản phẩm"));
        txtMaLSP.setBorder(BorderFactory.createTitledBorder("Loại sản phẩm"));
        txtTenSP.setBorder(BorderFactory.createTitledBorder("Tên sản phẩm"));
        txtDonGia.setBorder(BorderFactory.createTitledBorder("Đơn giá"));
        txtSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));
        // disable
        txtMaSP.setEditable(false);
        txtMaLSP.setEditable(false);
        txtTenSP.setEditable(false);
        txtDonGia.setEditable(false);
        //Set size
        txtMaSP.setPreferredSize(new Dimension(20,10));
        txtMaLSP.setPreferredSize(new Dimension(20,10));
        txtTenSP.setPreferredSize(new Dimension(20,10));
        txtDonGia.setPreferredSize(new Dimension(20,10));
        txtSoLuong.setPreferredSize(new Dimension(20,10));

        //
        txtMaSP.setHorizontalAlignment(JLabel.LEFT);
        txtMaLSP.setHorizontalAlignment(JLabel.LEFT);
        txtTenSP.setHorizontalAlignment(JLabel.LEFT);
        txtDonGia.setHorizontalAlignment(JLabel.LEFT);
        txtSoLuong.setHorizontalAlignment(JLabel.LEFT);
        //set Font
        txtMaSP.setFont(f);
        txtMaLSP.setFont(f);
        txtTenSP.setFont(f);
        txtDonGia.setFont(f);
        txtSoLuong.setFont(f);
        // add to panel
        plTextField.add(txtMaSP);
        plTextField.add(txtMaLSP);
        plTextField.add(txtTenSP);
        plTextField.add(txtDonGia);
        plTextField.add(txtSoLuong);        
        
        btnThem.addActionListener((ae) -> {
            try {
                String masp = txtMaSP.getText();
                int soluong = Integer.parseInt(txtSoLuong.getText());
                if (soluong > 0) {
                    addChiTiet(masp, soluong);
                    System.out.println(masp);
                    System.out.println(soluong);
                } else {
                    JOptionPane.showMessageDialog(txtSoLuong, "Số lượng phải là số dương!");
                    txtSoLuong.requestFocus();
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(txtSoLuong, "Số lượng phải là số nguyên!");
                txtSoLuong.requestFocus();
            }
        });
        
        ChiTietSPPanel.add(lbImage, BorderLayout.WEST);
        ChiTietSPPanel.add(plTextField, BorderLayout.CENTER);
        ChiTietSPPanel.add(btnThem, BorderLayout.PAGE_END);
        
        //===========================
        panelLeft.add(SanPhamPanel, BorderLayout.CENTER);
        panelLeft.add(ChiTietSPPanel, BorderLayout.SOUTH);
        
        
        // ====== Panel Right - Panel hiện hóa đơn ======
       int sizePanelRight = width - sizePanelLeft;
        panelRight = new JPanel();
//        panelRight.setBounds(width - 550, 0, 550, height);
        panelRight.setPreferredSize(new Dimension(sizePanelRight, height));
        panelRight.setBackground(new Color(59, 68, 75));
        BoxLayout boxLayout = new BoxLayout(panelRight, BoxLayout.Y_AXIS);
        panelRight.setLayout(boxLayout);
        
        //=========================================
        int ThongTinHeight = height/3 + 2*height/9 - 5;
        ThongTinPanel = new JPanel();
        ThongTinPanel.setPreferredSize(new Dimension(sizePanelRight, ThongTinHeight));
        ThongTinPanel.setBackground(new Color(240, 240, 240));
        
        //comboBox
        btnChonKhachHang.setPreferredSize(new Dimension(80, 30));
        qlkhBUS.getDSKH().forEach((t) -> {
                btnChonKhachHang.addItem(t.getMaKH());
            });
                
        btnChonKhachHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 txtKhachHang.setText(btnChonKhachHang.getSelectedItem().toString());
            }
        });
        
        txtNhanVien.setText(LoginGUI_V1.currentNhanVien.getMaNV()+" - "+ LoginGUI_V1.currentNhanVien.getTenNV());
        
        btnChonKhuyenMai.setPreferredSize(new Dimension(80, 30));
        qlkmBUS.getDSKM().forEach((t) -> {
                btnChonKhuyenMai.addItem(t.getMaKM());
            });
        
        btnChonKhuyenMai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtKhuyenMai.setText(btnChonKhuyenMai.getSelectedItem().toString());
                btnChonKhuyenMai.setSelectedItem(btnChonKhuyenMai.getSelectedItem());
            }
        });
        
        txtMaHoaDon.setText(qlhdBUS.MaHDnotHave());
        
        new Timer().scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
            txtNgayLap.setText(LocalDate.now().toString());
            txtGioLap.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            if (txtNhanVien.getText().equals("")
                || txtKhachHang.getText().equals("")
                || txtKhuyenMai.getText().equals("")
                || dscthd.isEmpty()) {
                  btnThanhToan.setEnabled(false);
            } else {
                btnThanhToan.setEnabled(true);
            }
          }
        }, 0, 2000);
        
        
        // set border
        txtMaHoaDon.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn:"));
        txtNhanVien.setBorder(BorderFactory.createTitledBorder("Nhân viên:"));
        txtNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập:"));
        txtGioLap.setBorder(BorderFactory.createTitledBorder("Giờ lập:"));
        txtKhachHang.setBorder(BorderFactory.createTitledBorder("Khách hàng:"));
        txtTongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền:"));
        txtKhuyenMai.setBorder(BorderFactory.createTitledBorder("Khuyến mãi:"));
        
        //set font
        txtMaHoaDon.setFont(f);
        txtNhanVien.setFont(f);
        txtNgayLap.setFont(f);
        txtGioLap.setFont(f);
        txtKhachHang.setFont(f);
        txtMaHoaDon.setFont(f);
        txtTongTien.setFont(f);
        txtKhuyenMai.setFont(f);
        
        //set editable
        txtMaHoaDon.setEditable(false);
        txtNhanVien.setEditable(false);
        txtKhachHang.setEditable(false);
        txtNgayLap.setEditable(false);
        txtGioLap.setEditable(false);
        txtTongTien.setEditable(false);
        txtKhuyenMai.setEditable(false);
        
        // Thêm vào panel
        ThongTinPanel.add(txtMaHoaDon);
        ThongTinPanel.add(txtTongTien);
        ThongTinPanel.add(txtKhachHang);
        ThongTinPanel.add(btnChonKhachHang);
        ThongTinPanel.add(txtNhanVien);
        ThongTinPanel.add(txtNgayLap);
        ThongTinPanel.add(txtGioLap);
        ThongTinPanel.add(txtKhuyenMai);
        ThongTinPanel.add(btnChonKhuyenMai);
        
        //==========================================
        int ChiTietHeight = height/3 + 10;
        ChiTietPanel = new JPanel();
        ChiTietPanel.setPreferredSize(new Dimension(sizePanelRight, ChiTietHeight));
        ChiTietPanel.setBackground(new Color(240, 240, 240));
        ChiTietPanel.setLayout(new BorderLayout());
        
        String[] columnNamesModel2 = {"STT", "Mã", "Tên", "Số lượng", "Đơn giá", "Thành tiền"};
        model1 = new DefaultTableModel(columnNamesModel2, 0);
        tableChiTietHD = new JTable(model1);
        tableChiTietHD.setRowHeight(25);
        tableChiTietHD.setPreferredSize(new Dimension(sizePanelRight - 5, ChiTietHeight - 50));
                
        JTableHeader tableHeader2 = tableChiTietHD.getTableHeader();
        tableHeader2.setFont(f); // Đặt font cho tiêu đề
        
        tableChiTietHD.setTableHeader(tableHeader2);
        
        JScrollPane scrollPane2 = new JScrollPane(tableChiTietHD);
        scrollPane2.setBackground(new Color(240, 240, 240));
        
        JPanel ChiTietButton = new JPanel(new FlowLayout(1, 5, 5));
        ChiTietButton.setPreferredSize(new Dimension(sizePanelRight - 5, 50));
        
        ChiTietButton.add(btnXoa);
        ChiTietButton.add(btnSua);
        ChiTietButton.add(btnRefresh);
        
        ChiTietPanel.add(scrollPane2, BorderLayout.CENTER);
        ChiTietPanel.add(ChiTietButton, BorderLayout.SOUTH);
        
        //===========================================
        int ThanhToanHeight = height - ThongTinHeight - ChiTietHeight;
        ThanhToanPanel = new JPanel(new FlowLayout(2, 5, 5));
        ThanhToanPanel.setPreferredSize(new Dimension(sizePanelRight, ThanhToanHeight));
        ThanhToanPanel.setBackground(new Color(240, 240, 240));
        
        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/com/PhoneStoreManager/image/icons8_cancel_30px_1.png")));
        btnThanhToan.setIcon(new ImageIcon(this.getClass().getResource("/com/PhoneStoreManager/image/icons8_us_dollar_30px.png")));
        
        btnHuy.addActionListener((ae) -> {
            btnHuyOnClick();
        });
        btnThanhToan.addActionListener((ae) -> {
            btnThanhToanOnClick();
        });
        
        ThanhToanPanel.add(btnHuy);
        ThanhToanPanel.add(btnThanhToan);
        
        //============================================
        panelRight.add(ThongTinPanel);
        panelRight.add(ChiTietPanel);
        panelRight.add(ThanhToanPanel);
        
        
        // ====== Thêm vào Panel chính =======
        this.add(panelLeft);
        this.add(panelRight);
    }
    
        public String getSelectedSanPham(int rowIndex) {
            if (rowIndex != -1) { // Kiểm tra nếu có hàng được chọn
                // Lấy giá trị của ô trong cột đầu tiên (giả sử mã sản phẩm ở cột đầu tiên)
                Object value = tableSanPham.getValueAt(rowIndex, 0);
                if (value != null) {
                    return value.toString(); // Trả về mã sản phẩm dưới dạng String
                }
            }
            return null; // Trả về null nếu không có hàng nào được chọn hoặc giá trị là null
        }
        
    public void showInfo(String masp, int soluong){
        if (masp != null){
            //show hinh 
            for (SanPham sp : qlspBUS.getDSSP()) {
                if (sp.getMaSP().equals(masp)) {
                    int w = lbImage.getWidth();
                    int h = lbImage.getHeight();
                    ImageIcon img = new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/product/" + sp.getHinhAnh()));
                    Image imgScaled = img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
                    lbImage.setIcon(new ImageIcon(imgScaled));

                    // show info
                    String loai = qllspBUS.getLSP(sp.getMaLSP()).getTenLSP();
                    txtMaSP.setText(sp.getMaSP());
                    txtTenSP.setText(sp.getTenSP());
                    txtMaLSP.setText(loai + " - " + sp.getMaLSP());
                    txtDonGia.setText(PriceFormatter.format(sp.getDonGia()));
                    txtSoLuong.setText(String.valueOf(soluong));
                    return;
                }
            }
        }
    }
    
    public void setDataToTableHoaDon(ArrayList<ChiTietHoaDon> arr, JTable t) {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        model.setRowCount(0); // Xóa tất cả các dòng trong bảng

        long tongTien = 0;
        int stt = 1;

        for (ChiTietHoaDon cthd : arr) {
            String maSP = cthd.getMaSP();
            SanPham sp = qlspBUS.getSanPham(maSP);
            String tenSP = sp.getTenSP();
            int soLuong = cthd.getSoLuong();
            long donGia = cthd.getDonGia();
            long thanhTien = soLuong * donGia;

            Object[] rowData = {
                stt,
                maSP,
                tenSP,
                soLuong,
                PriceFormatter.format(donGia),
                PriceFormatter.format(thanhTien)
            };
            model.addRow(rowData); // Thêm dòng mới vào bảng
            stt++;
            tongTien += thanhTien;
        }

        // Thêm dòng trống vào bảng
        model.addRow(new Object[]{"", "", "", "", "", ""});

        // Thêm dòng tổng tiền vào bảng
        model.addRow(new Object[]{"", "", "", "", "Tổng tiền", PriceFormatter.format(tongTien)});

        // Kiểm tra và thêm thông tin khuyến mãi (nếu có)
        if (khuyenMai != null && khuyenMai.getPhanTramKM() > 0 && khuyenMai.getDieuKienKM() <= tongTien) {
            long giaTriKhuyenMai = tongTien * khuyenMai.getPhanTramKM() / 100;
            long tongTienSauKhuyenMai = tongTien - giaTriKhuyenMai;

            // Thêm dòng khuyến mãi vào bảng
            model.addRow(new Object[]{"", "", "", "", "Khuyến mãi", PriceFormatter.format(-giaTriKhuyenMai)});

            // Thêm dòng còn lại sau khuyến mãi vào bảng
            model.addRow(new Object[]{"", "", "", "", "Còn lại", PriceFormatter.format(tongTienSauKhuyenMai)});

            // Cập nhật tổng thanh toán
            tongThanhToan = tongTienSauKhuyenMai;
            txtTongTien.setText(PriceFormatter.format(tongTienSauKhuyenMai));
        } else {
            // Không có khuyến mãi hoặc không đủ điều kiện khuyến mãi
            tongThanhToan = tongTien;
            txtTongTien.setText(PriceFormatter.format(tongTien));
        }

        // Cập nhật mô hình của bảng
        t.setModel(model);
    }
    
    public void addChiTiet(String masp, int soluong){
        SanPham sp = qlspBUS.getSanPham(masp);

        Boolean daCo = false; // check xem trong danh sách chi tiết hóa đơn đã có sản phẩm này chưa
        for (ChiTietHoaDon cthd : dscthd) {
            if (cthd.getMaSP().equals(sp.getMaSP())) {
                int tongSoLuong = soluong + cthd.getSoLuong();
                if (tongSoLuong > sp.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, "Số lượng sản phẩm trong kho không đủ (" + sp.getSoLuong() + ")");
                    return;
                }
                cthd.setSoLuong(tongSoLuong); // có rồi thì thay đổi số lượng
                daCo = true;
                break;
            }
        }

        if (!daCo) { // nếu chưa có thì thêm mới
            if (soluong > sp.getSoLuong()) {
                JOptionPane.showMessageDialog(this, "Số lượng sản phẩm trong kho không đủ (" + sp.getSoLuong() + ")");
                return;
            }
            ChiTietHoaDon cthd = new ChiTietHoaDon(qlhdBUS.MaHDnotHave(), masp, soluong, sp.getDonGia());
            dscthd.add(cthd);
        }
        
        setDataToTableHoaDon(dscthd, tableChiTietHD);
    }
    public void setDataToTableSanPham(ArrayList<SanPham> data, JTable table) {
        // Xóa hết dữ liệu cũ trong table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa hết các dòng trong bảng

        // Duyệt qua danh sách sản phẩm và thêm vào bảng nếu thỏa điều kiện
        for (SanPham sp : data) {
            if (sp.getTrangThai() == 1) {
                Object[] rowData = {
                    sp.getMaSP(),
                    sp.getMaLSP(),
                    sp.getTenSP(),
                    PriceFormatter.format(sp.getDonGia()),
                    sp.getSoLuong()
                };
                model.addRow(rowData); // Thêm dòng mới vào bảng
            }
        }

        // Cập nhật lại table để hiển thị dữ liệu mới
        table.setModel(model);
    }
     public void clear() {
        txtKhachHang.setText("");
        txtTongTien.setText("");
        txtKhuyenMai.setText("");
        dscthd.clear();
        setDataToTableHoaDon(dscthd, tableChiTietHD);
    }
        
    public void refreshTable() {
        qlspBUS.getDSSP();
        setDataToTableSanPham(qlspBUS.search("", "Tất cả", -1, -1, -1, -1, 1), tableSanPham);
        
    }
    
        public void refreshAll() {
        refreshTable();
        txtMaSP.setText("");
        txtMaLSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        lbImage.setIcon(null);
    }
    
    private void btnHuyOnClick() {
        clear();
    }
    
    private void btnThanhToanOnClick() {
        HoaDon hd = new HoaDon(
                txtMaHoaDon.getText(),
                LoginGUI_V1.currentNhanVien.getMaNV(),
                txtKhachHang.getText(),
                btnChonKhuyenMai.getSelectedItem().toString(),
                LocalDate.parse(txtNgayLap.getText()),
                LocalTime.parse(txtGioLap.getText()),
                tongThanhToan);
        qlhdBUS.addHoaDon(hd);

        for (ChiTietHoaDon ct : dscthd) {
            qlcthd.addChiTietHoaDon(ct);
        }
        txtMaHoaDon.setText(qlhdBUS.MaHDnotHave());
        clear();
        refreshAll();
    }
    
        private void addDocumentListener(JTextField tx) {
        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        tx.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                txSearchOnChange();
            }
        });
    }

    public void txSearchOnChange() {
        setDataToTableSanPham(qlspBUS.search(txtTimKiem.getText(), "Tất cả", -1, -1, -1, -1, 1), tableSanPham);
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

}
