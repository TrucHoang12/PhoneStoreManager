package GUI.GiaoDien;

import DTO.NCC;
import BUS.QuanlyNCCBUS;
import BUS.QuanlychitiethoadonBUS;
import BUS.QuanlychitietphieunhapBUS;
import DTO.HoaDon;
import BUS.QuanlyhoadonBUS;
import DTO.KhachHang;
import BUS.QuanlykhachhangBUS;
import DTO.KhuyenMai;
import BUS.QuanlykhuyenmaiBUS;
import DTO.NhanVien;
import BUS.QuanlynhanvienBUS;
import DTO.PhieuNhap;
import BUS.QuanlyphieunhapBUS;
import BUS.QuanlyquyenBUS;
import DTO.Quyen;
import DTO.SanPham;
import BUS.QuanlysanphamBUS;
import DTO.TaiKhoan;
import BUS.XulyTaiKhoanBUS;
import GUI.Form.FormChon;
import GUI.Form.FormThemSuaKhachHang;
import GUI.Form.FormThemSuaKhuyenMai;
import GUI.Form.FormThemSuaLSP;
import GUI.Form.FormThemSuaNCC;
import GUI.Form.FormThemSuaNhanVien;
import GUI.Form.FormThemSuaTaiKhoan;
import GUI.Panel.SearchFromTo;
import GUI.Panel.DateSearch;
import GUI.Panel.DateSearch;
import GUI.Panel.SearchPanel;
import DTO.LoaiSanPham;
import BUS.QuanlyloaisanphamBUS;
import EXEL.DocExcel;
import EXEL.XuatExcel;
import GUI.ChucNang.KhachHangGUI;
import GUI.ChucNang.KhuyenMaiGUI;
import GUI.ChucNang.NhaCungCapGUI;
import GUI.ChucNang.NhanVienPanel;
import GUI.ChucNang.QuyenPanel;
import GUI.ChucNang.SanPhamGUI;
import GUI.ChucNang.XulyTaiKhoanPanel;
import PDF.WritePDF;
import GUI.Form.BanHangForm;
import GUI.Form.NhapHangForm;
import GUI.Form.ThongKeForm;
import GUI.Form.FormSanPham;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import GUI.GiaoDien.LoginGUI_V1;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GiaoDienGUI extends javax.swing.JFrame {

    private int px = 0;
    private int py = 0;

    public static int tb_y = 0;
    public static int tb_x = 0;
    public static int tb_w = 0;
    public static int tb_h = 0;
    public static Table tb;
    private QuanlynhanvienBUS qlnvBUS;
    private XulyTaiKhoanBUS xltkBUS;
    private QuanlyquyenBUS qlquyenBUS;
    private QuanlyNCCBUS qlNCCBUS;
    private QuanlykhachhangBUS qlkhBUS;
    private QuanlyloaisanphamBUS qllspBUS;
    private QuanlykhuyenmaiBUS qlKMBUS;
    private QuanlysanphamBUS qlspBUS;
    private QuanlyhoadonBUS qlhd;
    private QuanlychitiethoadonBUS qlcthd;
    private QuanlychitietphieunhapBUS qlctpn;
    private QuanlyphieunhapBUS qlpn;
    public String z = LoginGUI_V1.currentChiTietQuyen.getMaQuyen();

    public GiaoDienGUI() {
        initComponents();
        this.setLocationRelativeTo(null);

        ImageIcon logo = new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/phone.png"));
        setIconImage(logo.getImage());
        initChucNang();

        JLabel cc = new JLabel("Chào " + LoginGUI_V1.currentNhanVien.getTenNV());
        cc.setFont(new Font("Tahoma", Font.BOLD, 18));
        cc.setBounds(400, 250, 500, 100);
        panelMain.removeAll();
        panelMain.add(cc);
        panelMain.validate();
    }

    private void initChucNang() {
        String s = LoginGUI_V1.currentQuyen.getChiTietQuyen();

        String k = LoginGUI_V1.currentChiTietQuyen.getMaQuyen();

        System.out.println("meo meo meo  " + k);

        s = s.replace(",", " ");

        s += " ThongKe CaiDat";
        String[] words = s.split("\\s");
        int x = 0, y = 0, w = 180, h = 70;

// Set kích thước hiển thị chức năng 
        if (words.length <= 8) {
            jPanel3.setBounds(0, JHeader.getSize().height, w, getHeight() - JHeader.getSize().height);
            panelMain.setBounds(jPanel3.getSize().width, JHeader.getSize().height, getWidth() - jPanel3.getSize().width, getHeight() - JHeader.getSize().height);

        } else {
            jPanel3.setBounds(0, JHeader.getSize().height, w * 2, getHeight() - JHeader.getSize().height);
            panelMain.setBounds(jPanel3.getSize().width, JHeader.getSize().height, getWidth() - jPanel3.getSize().width, getHeight() - JHeader.getSize().height);
        }
        for (int i = 0; i < words.length; i++) {

            initComponent(words[i], x, y, w, h);
            if (i < 7) {
                y += h + 17;
            } else if (i == 7) {
                y = 0;
                x += w;
            } else {
                y += h + 17;
            }
        }
    }

    // Lấy dữ liệu quyền sau khi loại tiền tố => duyệt tồn tại 
    // => khởi tạo , Thêm sự kiện click => thêm vào jPanel3 để hiển thị
    // Set font button chức năng
    private void initComponent(String s, int x, int y, int width, int height) {
        switch (s) {
            case "BanHang": {
                btnBanHang = new javax.swing.JButton();
                btnBanHang.setBounds(x, y, width, height);
                btnBanHang.setBackground(new java.awt.Color(255, 255, 255));
                btnBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/shop1.png"))); // NOI18N

                btnBanHang.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bán hàng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnBanHangMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnBanHangMouseExited(evt);
                    }

                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        btnBanHangMousePressed(evt);
                    }
                });
                jPanel3.add(btnBanHang);
                break;
            }
            case "NhapHang": {
                btnNhapHang = new javax.swing.JButton();
                btnNhapHang.setBounds(x, y, width, height);
                btnNhapHang.setBackground(new java.awt.Color(255, 255, 255));
                btnNhapHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/nhap1.png"))); // NOI18N

                btnNhapHang.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập hàng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnNhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnNhapHangMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnNhapHangMouseExited(evt);
                    }

                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        btnNhapHangMousePressed(evt);
                    }
                });

                jPanel3.add(btnNhapHang);
                break;
            }
            case "SanPham": {
                qlspBUS = new QuanlysanphamBUS();
                btnSanPham = new javax.swing.JButton();
                btnSanPham.setBounds(x, y, width, height);
                btnSanPham.setBackground(new java.awt.Color(255, 255, 255));
                btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/smartphone1.png"))); // NOI18N

                btnSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnSanPhamMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnSanPhamMouseExited(evt);
                    }

                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        btnSanPhamMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnSanPham);
                break;
            }
            case "LoaiSanPham": {
                qllspBUS = new QuanlyloaisanphamBUS();
                btnLoaiSanPham = new javax.swing.JButton();
                btnLoaiSanPham.setBounds(x, y, width, height);
                btnLoaiSanPham.setBackground(new java.awt.Color(255, 255, 255));
                btnLoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/type1.png"))); // NOI18N

                btnLoaiSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Loại sản phẩm", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnLoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnLoaiSanPhamMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnLoaiSanPhamMouseExited(evt);
                    }

                    @Override
                    public void mousePressed(MouseEvent evt) {
                        btnLoaiSanPhamMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnLoaiSanPham);
                break;
            }
            case "HoaDon": {
                qlhd = new QuanlyhoadonBUS();
                qlcthd = new QuanlychitiethoadonBUS();
                btnHoaDon = new javax.swing.JButton();
                btnHoaDon.setBounds(x, y, width, height);
                btnHoaDon.setBackground(new java.awt.Color(255, 255, 255));
                btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/hoadon1.png"))); // NOI18N

                btnHoaDon.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnHoaDonMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnHoaDonMouseExited(evt);
                    }

                    public void mousePressed(MouseEvent evt) {
                        btnHoaDonMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnHoaDon);
                break;

            }
            case "KhuyenMai": {
                qlKMBUS = new QuanlykhuyenmaiBUS();
                btnKhuyenMai = new javax.swing.JButton();
                btnKhuyenMai.setBounds(x, y, width, height);
                btnKhuyenMai.setBackground(new java.awt.Color(255, 255, 255));
                btnKhuyenMai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/gift1.png"))); // NOI18N

                btnKhuyenMai.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Khuyến mãi", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnKhuyenMaiMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnKhuyenMaiMouseExited(evt);
                    }

                    public void mousePressed(MouseEvent evt) {
                        btnKhuyenMaiMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnKhuyenMai);
                break;
            }
            case "NhanVien": {
                qlnvBUS = new QuanlynhanvienBUS();
                btnNhanVien = new javax.swing.JButton();
                btnNhanVien.setBounds(x, y, width, height);
                btnNhanVien.setBackground(new java.awt.Color(255, 255, 255));
                btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/staff1.png"))); // NOI18N

                btnNhanVien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhân viên", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnNhanVienMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnNhanVienMouseExited(evt);
                    }

                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        btnNhanVienMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnNhanVien);
                break;
            }
            case "KhachHang": {
                qlkhBUS = new QuanlykhachhangBUS();
                btnKhachHang = new javax.swing.JButton();
                btnKhachHang.setBounds(x, y, width, height);
                btnKhachHang.setBackground(new java.awt.Color(255, 255, 255));
                btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/customer1.png"))); // NOI18N

                btnKhachHang.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Khách hàng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnKhachHangMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnKhachHangMouseExited(evt);
                    }

                    @Override
                    public void mousePressed(MouseEvent evt) {
                        btnKhachHangMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnKhachHang);
                break;
            }
            case "PhieuNhap": {
                qlpn = new QuanlyphieunhapBUS();
                qlctpn = new QuanlychitietphieunhapBUS();
                btnPhieuNhap = new javax.swing.JButton();
                btnPhieuNhap.setBounds(x, y, width, height);
                btnPhieuNhap.setBackground(new java.awt.Color(255, 255, 255));
                btnPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/phieunhap1.png"))); // NOI18N

                btnPhieuNhap.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Phiếu nhập", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnPhieuNhapMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnPhieuNhapMouseExited(evt);
                    }

                    public void mousePressed(MouseEvent evt) {
                        btnPhieuNhapMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnPhieuNhap);
                break;
            }
            case "NhaCungCap": {
                qlNCCBUS = new QuanlyNCCBUS();
                btnNCC = new javax.swing.JButton();
                btnNCC.setBounds(x, y, width, height);
                btnNCC.setBackground(new java.awt.Color(255, 255, 255));
                btnNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/NCC1.png"))); // NOI18N

                btnNCC.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhà cung cấp", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnNCC.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnNCCMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnNCCMouseExited(evt);
                    }

                    @Override
                    public void mousePressed(MouseEvent evt) {
                        btnNCCMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnNCC);
                break;
            }
            case "TaiKhoan": {
                xltkBUS = new XulyTaiKhoanBUS();
                btnTaiKhoan = new javax.swing.JButton();
                btnTaiKhoan.setBounds(x, y, width, height);
                btnTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
                btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/key1.png"))); // NOI18N

                btnTaiKhoan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tài khoản", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnTaiKhoanMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnTaiKhoanMouseExited(evt);
                    }

                    public void mousePressed(MouseEvent evt) {
                        btnTaiKhoanMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnTaiKhoan);
                break;
            }
            case "Quyen": {
                qlquyenBUS = new QuanlyquyenBUS();
                btnQuyen = new javax.swing.JButton();
                btnQuyen.setBounds(x, y, width, height);
                btnQuyen.setBackground(new java.awt.Color(255, 255, 255));
                btnQuyen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/permission1.png"))); // NOI18N

                btnQuyen.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quyền", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM));
                btnQuyen.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        btnQuyenMouseEntered(evt);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        btnQuyenMouseExited(evt);
                    }

                    public void mousePressed(MouseEvent evt) {
                        btnQuyenMousePressed(evt);
                        initButton();
                    }
                });
                jPanel3.add(btnQuyen);
                break;
            }
            case "ThongKe": {

                break;
            }

            default:
                break;
        }
    }

    /*-------------------------------------------------------------------------------------------------------------------------*/
    public void themChucNangButton() {
        panelMain.removeAll();
        panelMain.invalidate();
        panelMain.repaint();
        Font font = new Font("Tahoma", 1, 12);
        // Nút Thêm
        btnThem = new JButton();
        btnThem.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/signs-1.png")));
        btnThem.setText("Thêm");
        btnThem.setFont(font);

        // Nút Xóa
        btnXoa = new JButton();
        btnXoa.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/button-delete.png")));
        btnXoa.setText("Xóa");
        btnXoa.setFont(font);

        // Nút Sửa
        btnSua = new JButton();
        btnSua.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/edit-tools.png")));
        btnSua.setText("Sửa");
        btnSua.setFont(font);

        // Nút Xuất Excel
        btnXuatExcel = new JButton();
        btnXuatExcel.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/excel.png")));
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.setFont(font);

        // Nút Nhập Excel
        btnNhapExcel = new JButton();
        btnNhapExcel.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/excel.png")));
        btnNhapExcel.setText("Nhập Excel");
        btnNhapExcel.setFont(font);

        // Nút Làm Mới
        btnLamMoi = new JButton();
        btnLamMoi.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/internet.png")));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setFont(font);

        // Thêm Panel Tìm Kiếm
        pnSearch = new SearchPanel("GIAODIEN");

        // Thêm Panel tìm kiếm Ngày Sinh
        pnDateSearch = new DateSearch("GIAODIEN");

        // Thêm Panel tìm tuổi
        pnAgeSearch = new SearchFromTo("GIAODIEN", "Tuổi");

        // Thêm Table
        tb = new Table();
        tb.setRowHeigth(35);
        panelMain.add(new JScrollPane(tb)); // Để Table vào trong JScrollPane trước khi thêm vào panelMain

    }

    /*-------------------------------------------------------------------------------------------------------------------------*/
    private void initComponents() {
        this.setSize(1367, 732);
        this.setTitle("Phone Store Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);

        jPanel3 = new JPanel();

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(BorderFactory.createEtchedBorder(null, new java.awt.Color(102, 102, 102)));
        jPanel3.setPreferredSize(new Dimension(200, 730));

        jPanel3.setLayout(null);

        panelMain = new JPanel();

        panelMain.setBackground(new Color(255, 255, 255));
        panelMain.setLayout(null);
        panelMain.setPreferredSize(new Dimension(1300, 40));

        JHeader = new JPanel();
        JHeader.setBackground(new Color(89, 149, 218));
        JHeader.setBorder(BorderFactory.createEtchedBorder(null, new Color(102, 102, 102)));
        JHeader.setPreferredSize(new Dimension(1367, 50));
        JHeader.setLayout(new BorderLayout());

        labelTitle = new JLabel("Phone Store Manager", JLabel.CENTER);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelTitle.setForeground(Color.WHITE);
        JHeader.add(labelTitle, BorderLayout.CENTER);

        labelBack = new JLabel(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/back.png")));
        labelBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelBack.setBounds(10, 10, 30, 30);

        labelBack.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                labelBackMousePressed(evt);
            }
        });
        JHeader.add(labelBack, BorderLayout.WEST);

        labelZoom = new JLabel(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/zoom.png")));
        labelZoom.setToolTipText("Thu nhỏ");
        labelZoom.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelZoom.setBounds(1265, 10, 30, 30);

        labelZoom.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                labelZoomMouseClicked(evt);
            }
        });
        JHeader.add(labelZoom, BorderLayout.EAST);

        labelPower = new JLabel(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/off.png.png")));
        labelPower.setToolTipText("Thoát");
        labelPower.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelPower.setBounds(1300, 10, 30, 30);

        labelPower.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                labelPowerMouseClicked(evt);
            }
        });
        JHeader.add(labelPower, BorderLayout.LINE_END);

        this.getContentPane().add(JHeader, BorderLayout.NORTH);
        this.getContentPane().add(panelMain, BorderLayout.CENTER);

        this.getContentPane().add(jPanel3, BorderLayout.WEST);
    }

    private void labelPowerMouseClicked(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }

    private void labelZoomMouseClicked(java.awt.event.MouseEvent evt) {
        this.setState(JFrame.ICONIFIED);
    }

    private void labelBackMousePressed(java.awt.event.MouseEvent evt) {
        new LoginGUI_V1().setVisible(true);
        this.dispose();
    }

    private void JHeaderMouseDragged(java.awt.event.MouseEvent evt) {
        setLocation(getLocation().x + evt.getX() - px, getLocation().y + evt.getY() - py);
    }

    private void JHeaderMousePressed(java.awt.event.MouseEvent evt) {
        px = evt.getX();
        py = evt.getY();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Bấm vào chức năng  " Bán Hàng "  khơi tạo hiển thị các nút Chức năng , sự kiện
    private void btnBanHangMouseEntered(java.awt.event.MouseEvent evt) {
        btnBanHang.setBackground(new java.awt.Color(51, 204, 255));
        btnBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/shop2.png")));
    }

    private void btnBanHangMouseExited(java.awt.event.MouseEvent evt) {
        btnBanHang.setBackground(new java.awt.Color(255, 255, 255));
        btnBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/shop1.png")));
        initButton();
    }

    private void btnBanHangMousePressed(MouseEvent evt) {
        panelMain.removeAll();
        panelMain.invalidate();
        panelMain.repaint();
        // Thêm Table
//        tb = new Table();
//        tb.setRowHeigth(35);
        BanHangForm bh = new BanHangForm(panelMain.getWidth(), panelMain.getHeight());

        bh.setVisible(true);
        labelTitle.setText("BÁN HÀNG");

        panelMain.add(bh);
        panelMain.validate();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
 /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Bấm vào chức năng  " Nhập Hàng "  khơi tạo hiển thị các nút Chức năng , sự kiện
    private void btnNhapHangMouseExited(java.awt.event.MouseEvent evt) {
        btnNhapHang.setBackground(new java.awt.Color(255, 255, 255));
        btnNhapHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/nhap1.png")));
        initButton();
    }

    private void btnNhapHangMouseEntered(java.awt.event.MouseEvent evt) {
        btnNhapHang.setBackground(new java.awt.Color(51, 204, 255));
        btnNhapHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/nhap2.png")));
    }

    private void btnNhapHangMousePressed(java.awt.event.MouseEvent evt) {
        panelMain.removeAll();
        panelMain.invalidate();
        panelMain.repaint();
        // Thêm Table
        tb = new Table();
        tb.setRowHeigth(35);
        NhapHangForm nh = new NhapHangForm(panelMain.getWidth(), panelMain.getHeight());

        nh.setVisible(true);
        labelTitle.setText("NHẬP HÀNG");

        panelMain.add(nh);
        panelMain.validate();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
 /*---------------------------------------------------------------------------------------------------------------------------------------*/
    private void btnSanPhamMouseEntered(java.awt.event.MouseEvent evt) {
        btnSanPham.setBackground(new java.awt.Color(51, 204, 255));
        btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/smartphone2.png")));
    }

    private void btnSanPhamMouseExited(java.awt.event.MouseEvent evt) {
        btnSanPham.setBackground(new java.awt.Color(255, 255, 255));
        btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/smartphone1.png")));
        initButton();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Bấm vào chức năng sản phẩm khơi tạo hiển thị các nút thêm xóa sửa
    private void btnSanPhamMousePressed(java.awt.event.MouseEvent evt) {
        panelMain.removeAll();
        panelMain.invalidate();
        panelMain.repaint();
        panelMain.setLayout(new BorderLayout());
        SanPhamGUI sanPhamGUI = new SanPhamGUI();
        panelMain.add(sanPhamGUI, BorderLayout.CENTER);
        panelMain.validate();
    }
//    https://www.programcreek.com/java-api-examples/?class=javax.swing.JPanel&method=removeAll           Example 2
//    private void btnSanPhamMousePressed(java.awt.event.MouseEvent evt) {
//        String dsquyen = LoginGUI_V1.currentQuyen.getChiTietQuyen();
//        themChucNangButton();
//        labelTitle.setText("SẢN PHẨM");
//
//        int x = 0, y = 0, w = 150, h = 50;
//        x = (panelMain.getSize().width - w * 6) / 2;
//
//        // Nút thêm SP
//        btnThem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
//                String MaSP = Quanlysanphambus.MaSPnotHave();
//                new FormSanPham(MaSP).setVisible(true);
//            }
//        });
//        btnThem.setBounds(x, y, w, h);
//
//        x += w;
//
//        // Nút xóa SP
//        btnXoa.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int i = tb.getSelectedRow();
//                if (i >= 0) {
//                    int k = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc xóa sản phẩm này không?");
//                    if (k != JOptionPane.YES_OPTION) {
//                        return;
//                    }
//                    String MaSP = tb.LayMaSPTaiTable(i).toString();
//                    if (qlspBUS.deleteSanPham(MaSP)) {
//                        tb.removeRow(MaSP);
//                        JOptionPane.showMessageDialog(rootPane, "Xóa sản phẩm thành công");
//                    } else {
//                        JOptionPane.showMessageDialog(rootPane, "Xóa sản phẩm thất bại");
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn sản phẩm cần xóa");
//                }
//            }
//        });
//        btnXoa.setBounds(x, y, w, h);
//        x += w;
//
//        // Nút sửa SP
//        btnSua.addActionListener(new ActionListener() {
//            QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int i = tb.getSelectedRow();
//                if (i >= 0) {
//                    String MaSP = tb.LayMaSPTaiTable(i).toString();
//                    SanPham q = Quanlysanphambus.getSanPham(MaSP);
//                    new FormSanPham(q, Integer.parseInt(tb.getValueSTT(i).toString())).setVisible(true);
//                } else {
//                    JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn sản phẩm cần sữa");
//                }
//            }
//        });
//        btnSua.setBounds(x, y, w, h);
//        x += w;
//
//        if (dsquyen.contains("xemSanPham")) {
//            btnThem.setEnabled(false);
//            btnXoa.setEnabled(false);
//            btnSua.setEnabled(false);
//        }
//
//        // Nút Xuất Excel SP
//        btnXuatExcel.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new XuatExcel().xuatFileExcelSanPham();
//            }
//        });
//        btnXuatExcel.setBounds(x, y, w, h);
//        x += w;
//
//        // Nút Nhập Excel SP
//        btnNhapExcel.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new DocExcel().DocExcelSP();
//                btnLamMoi.doClick();
//            }
//        });
//        btnNhapExcel.setBounds(x, y, w, h);
//        x += w;
//
//        // Nút làm mới SP
//        btnLamMoi.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                QuanlysanphamBUS qlspBUS = new QuanlysanphamBUS();
//                QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
//                QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
//
//                panelMain.remove(tb);
//                tb = new Table();
//                tb.setBound(tb_x, tb_y, tb_w, tb_h);
//                tb.setHeaders(qlspBUS.getHeaders);
//                tb.jTable1.getColumn("Hình ảnh").setCellRenderer(new myTableCellRenderer());
//                tb.jTable1.getColumn("Hình ảnh").setMaxWidth(120);
//                tb.jTable1.getColumn("Hình ảnh").setMinWidth(95);
//                int i = 0;
//                for (SanPham q : qlspBUS.getDSSP()) {
//                    ++i;
//                    ImageIcon img = new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/product/" + q.getHinhAnh()));
//                    Image imgScaled = img.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
//                    JLabel label = new JLabel();
//                    label.setIcon(new ImageIcon(imgScaled));
//                    tb.addRow(new Object[]{Integer.toString(i), q.getMaSP(), q.getMaLSP(), q.getTenSP(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia())),
//                        Quanlykhuyenmaibus.ToCurrent(Integer.toString(q.getSoLuong())), label, (q.getTrangThai() == 1) ? "Hiện" : "Ẩn"
//                    });
//                }
//                tb.setRowHeigth(60);
//                tb.resizeColumnWidth();
//                panelMain.add(tb);
//                panelMain.validate();
//            }
//        });
//        btnLamMoi.setBounds(x, y, w, h);
//
//        // Thêm Panel tìm kiếm SP
//        x = (panelMain.getSize().width - 400 - 600) / 2;
//        y = h;
//        pnSearch.setComboBox(qlspBUS.getComboboxSearch);
//        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                pnSoLuongSearch.txtFrom.setText("");
//                pnSoLuongSearch.txtTo.setText("");
//                pnDonGiaSearch.txtFrom.setText("");
//                pnDonGiaSearch.txtFrom.setText("");
//                pnSearch.EventSearchSPforGiaoDien();
//            }
//        });
//        pnSearch.ComboBoxSearch.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                pnSoLuongSearch.txtFrom.setText("");
//                pnSoLuongSearch.txtTo.setText("");
//                pnDonGiaSearch.txtFrom.setText("");
//                pnDonGiaSearch.txtFrom.setText("");
//                pnSearch.EventSearchSPforGiaoDien();
//            }
//        });
//        pnSearch.setSizePanel(x, y);
//        x = x + pnSearch.getSize().width;
//
//        //Them Panel tìm kiếm số lượng sản phẩm
//        pnSoLuongSearch = new SearchFromTo("GIAODIEN", "Số Lượng");
//        pnSoLuongSearch.setSizePanel(x, y);
//        pnSoLuongSearch.btnTim.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                pnDonGiaSearch.txtFrom.setText("");
//                pnDonGiaSearch.txtFrom.setText("");
//                pnSearch.txtSearch.setText("");
//                pnSoLuongSearch.SearchSoLuongSanPham();
//            }
//        });
//        x = x + pnSoLuongSearch.getSize().width;
//
//        // Them Panel tìm kiếm Đơn giá
//        pnDonGiaSearch = new SearchFromTo("GIAODIEN", "Đơn giá");
//        pnDonGiaSearch.setSizePanel(x, y);
//        pnDonGiaSearch.btnTim.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                pnSoLuongSearch.txtFrom.setText("");
//                pnSoLuongSearch.txtTo.setText("");
//                pnSearch.txtSearch.setText("");
//                pnDonGiaSearch.SearchDonGiaSanPham();
//            }
//        });
//
//        // Thêm Table SP
//        tb_x = 0;
//        tb_y = pnSearch.getSize().height + btnThem.getSize().height;
//        tb_w = panelMain.getSize().width;
//        tb_h = panelMain.getSize().height - tb_y;
//        tb.setBound(tb_x, tb_y, tb_w, tb_h);
//        tb.setHeaders(qlspBUS.getHeaders);
//        tb.jTable1.getColumn("Hình ảnh").setCellRenderer(new myTableCellRenderer());
//        tb.jTable1.getColumn("Hình ảnh").setMaxWidth(120);
//        tb.jTable1.getColumn("Hình ảnh").setMinWidth(95);
//        tb.setRowHeigth(60);
//        int i = 0;
//        for (SanPham q : qlspBUS.getDSSP()) {
//            QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
//            ++i;
//            ImageIcon img = new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/product/" + q.getHinhAnh()));
//            Image imgScaled = img.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
//            JLabel label = new JLabel();
//            label.setIcon(new ImageIcon(imgScaled));
//            tb.addRow(new Object[]{Integer.toString(i), q.getMaSP(), q.getMaLSP(), q.getTenSP(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia())),
//                Quanlykhuyenmaibus.ToCurrent(Integer.toString(q.getSoLuong())), label, (q.getTrangThai() == 1) ? "Hiện" : "Ẩn"
//            });
//        }
//
//        /*-----------------------------------------------------------------------------------------------------------*/
////        tb.jTable1.setValueAt(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/product/huawei-y9-2019-blue-400x460.jpg")), 5, 6);
//        tb.resizeColumnWidth();
//        panelMain.add(btnThem);
//        panelMain.add(btnXoa);
//        panelMain.add(btnSua);
//        panelMain.add(btnXuatExcel);
//        panelMain.add(btnNhapExcel);
//        panelMain.add(btnLamMoi);
//        panelMain.add(pnSearch);
//        panelMain.add(pnSoLuongSearch);
//        panelMain.add(pnDonGiaSearch);
//        panelMain.add(tb);
//        panelMain.validate();
//    }

    private void btnLoaiSanPhamMouseEntered(java.awt.event.MouseEvent evt) {
        btnLoaiSanPham.setBackground(new java.awt.Color(51, 204, 255));
        btnLoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/type2.png")));
    }

    private void btnLoaiSanPhamMouseExited(java.awt.event.MouseEvent evt) {
        btnLoaiSanPham.setBackground(new java.awt.Color(255, 255, 255));
        btnLoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/type1.png")));
        initButton();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Bấm vào chức năng Loại sản phẩm khơi tạo hiển thị các nút thêm xóa sửa
/*---------------------------------------------------------------------------------------------------------------------------------------*/
    private void btnLoaiSanPhamMousePressed(MouseEvent evt) {
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        String dsquyen = LoginGUI_V1.currentQuyen.getChiTietQuyen();
        themChucNangButton();
        labelTitle.setText("LOẠI SẢN PHẨM");

        int x = 0, y = 0, w = 150, h = 50;
        x = (panelMain.getSize().width - w * 6) / 2;

        // Nút thêm LSP
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaLSP = Quanlyloaisanphambus.MaLSPnotHave();
                new FormThemSuaLSP(MaLSP).setVisible(true);
            }
        });
        btnThem.setBounds(x, y, w, h);
        x += w;

        // Nút xóa LSP
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = tb.getSelectedRow();
                if (i >= 0) {
                    int k = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc xóa loại sản phẩm này không?");
                    if (k != JOptionPane.YES_OPTION) {
                        return;
                    }
                    String MaLSP = tb.LayMaLSPTaiTable(i).toString();
                    if (qllspBUS.deleteLSP(MaLSP)) {
                        tb.removeRow(MaLSP);
                        JOptionPane.showMessageDialog(rootPane, "Xóa loại sản phẩm thành công");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Xóa loại sản phẩm thất bại");
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn loại sản phẩm cần xóa");
                }
            }
        });
        btnXoa.setBounds(x, y, w, h);
        x += w;

        // Nút sửa LSP
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = tb.getSelectedRow();
                if (i >= 0) {
                    String MaLSP = tb.LayMaLSPTaiTable(i).toString();
                    LoaiSanPham q = qllspBUS.getLSP(MaLSP);
                    new FormThemSuaLSP(q, Integer.parseInt(tb.getValueSTT(i).toString())).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn loại sản phẩm cần sữa");
                }
            }
        });
        btnSua.setBounds(x, y, w, h);
        x += w;

        if (dsquyen.contains("xemLoaiSanPham")) {
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
        }

        // Nút Xuất Excel LSP
        btnXuatExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new XuatExcel().xuatFileExcelLSP();
            }
        });
        btnXuatExcel.setBounds(x, y, w, h);
        x += w;

        // Nút Nhập Excel LSP
        btnNhapExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DocExcel().DocExcelLSP();
                btnLamMoi.doClick();
            }
        });
        btnNhapExcel.setBounds(x, y, w, h);
        x += w;

        // Nút làm mới LSP
        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                qllspBUS = new QuanlyloaisanphamBUS();
                panelMain.remove(tb);
                tb = new Table();
                tb.setBound(tb_x, tb_y, tb_w, tb_h);
                tb.setHeaders(qllspBUS.getHeaders);
                int i = 0;
                for (LoaiSanPham q : qllspBUS.getDSLSP()) {
                    ++i;
                    tb.addRow(new String[]{Integer.toString(i), q.getMaLSP(), q.getTenLSP(), q.getMota()});
                }
                tb.setRowHeigth(35);
                tb.resizeColumnWidth();
                panelMain.add(tb);
                panelMain.validate();
            }
        });
        btnLamMoi.setBounds(x, y, w, h);

        // Thêm Panel tìm kiếm LSP
        x = (panelMain.getSize().width - 400) / 2;
        y = h;
        pnSearch.setComboBox(qllspBUS.getComboboxSearch);
        pnSearch.ComboBoxSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnSearch.EventSearchLSPforGiaoDien();
            }
        });
        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                pnSearch.EventSearchLSPforGiaoDien();
            }
        });
        pnSearch.setSizePanel(x, y);

        // Thêm Table LSP
        tb_x = 0;
        tb_y = pnSearch.getSize().height + btnThem.getSize().height;
        tb_w = panelMain.getSize().width;
        tb_h = panelMain.getSize().height - tb_y - 1;
        tb.setBound(tb_x, tb_y, tb_w, tb_h);
        tb.setHeaders(qllspBUS.getHeaders);
        int i = 0;
        for (LoaiSanPham q : qllspBUS.getDSLSP()) {
            ++i;
            tb.addRow(new String[]{Integer.toString(i), q.getMaLSP(), q.getTenLSP(), q.getMota()});
        }
        tb.resizeColumnWidth();

        panelMain.add(btnThem);
        panelMain.add(btnXoa);
        panelMain.add(btnSua);
        panelMain.add(btnXuatExcel);
        panelMain.add(btnNhapExcel);
        panelMain.add(btnLamMoi);
        panelMain.add(pnSearch);
        panelMain.add(tb);
        panelMain.validate();
    }

    private void btnHoaDonMouseEntered(java.awt.event.MouseEvent evt) {
        btnHoaDon.setBackground(new java.awt.Color(51, 204, 255));
        btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/hoadon2.png")));
    }

    private void btnHoaDonMouseExited(java.awt.event.MouseEvent evt) {
        btnHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/hoadon1.png")));
        initButton();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Bấm vào chức năng  Hóa Đơn khơi tạo hiển thị các nút Chức năng , sự kiện
    private void btnHoaDonMousePressed(MouseEvent evt) {
        panelMain.removeAll();
        panelMain.repaint();
        Font font = new Font("Tahoma", 1, 12);
        labelTitle.setText("HÓA ĐƠN");
        int x = 0, y = 0, w = 150, h = 50;
        x = (panelMain.getSize().width - w * 4) / 2;

        // Nút Xuất Excel
        btnXuatExcel = new JButton();
        btnXuatExcel.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/excel.png")));
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.setFont(font);
        btnXuatExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new XuatExcel().xuatFileExcelHoaDon();
            }
        });
        btnXuatExcel.setBounds(x, y, w, h);
        x += w;

        // Nút InPDF
        btnInPDF = new JButton();
        btnInPDF.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/interface-pdf.png")));
        btnInPDF.setText("In PDF");
        btnInPDF.setFont(font);
        btnInPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuanlyhoadonBUS Quanlyhoadonbus = new QuanlyhoadonBUS();
                int i = tb.getSelectedRow();
                if (i >= 0) {
                    String MaHD = tb.LayMaHoaDonTaiTable(i).toString();
                    HoaDon hd = Quanlyhoadonbus.getHoaDon(MaHD);
                    new WritePDF().writeHoaDon(MaHD);
                }

            }
        });
        btnInPDF.setBounds(x, y, w, h);
        x += w;

        // Nút Xem chi tiết
        btnXemChiTiet = new JButton();
        btnXemChiTiet.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/view.png")));
        btnXemChiTiet.setText("Xem chi tiết");
        btnXemChiTiet.setFont(font);
        btnXemChiTiet.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                QuanlyhoadonBUS Quanlyhoadonbus = new QuanlyhoadonBUS();
                int i = tb.getSelectedRow();
                if (i >= 0) {
                    String MaHD = tb.LayMaHoaDonTaiTable(i).toString();
                    HoaDon hd = Quanlyhoadonbus.getHoaDon(MaHD);
                    new FormChon(hd).setVisible(true);
                }
            }
        });
        btnXemChiTiet.setBounds(x, y, w, h);
        x += w;

        // Nút Làm Mới
        btnLamMoi = new JButton();
        btnLamMoi.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/internet.png")));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setFont(font);
        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();

                qlhd = new QuanlyhoadonBUS();
                panelMain.remove(tb);
                tb = new Table();
                tb.setBound(tb_x, tb_y, tb_w, tb_h);
                tb.setHeaders(qlhd.getHeaders);
                int i = 0;
                for (HoaDon q : qlhd.getDSHD()) {
                    ++i;
                    tb.addRow(new Object[]{Integer.toString(i), q.getMaHD(), q.getMaNV(), q.getMaKH(), q.getMaKM(), q.getNgayLap(), q.getGioLap(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getTongTien()))});
                }
                tb.resizeColumnWidth();
                tb.setRowHeigth(35);
                panelMain.add(tb);
                panelMain.validate();
            }
        });
        btnLamMoi.setBounds(x, y, w, h);
        QuanlyhoadonBUS Quanlyhoadonbus = new QuanlyhoadonBUS();

        x = (panelMain.getSize().width - 1100) / 2;
        y = h;
        // Thêm Panel Tìm Kiếm
        pnSearch = new SearchPanel("GIAODIEN");
        pnSearch.setComboBox(Quanlyhoadonbus.getComboboxSearch);
        pnSearch.ComboBoxSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnDateSearch.dateFrom.setDate(null);
                pnDateSearch.dateTo.setDate(null);
                pnDonGiaSearch.txtFrom.setText("");
                pnDonGiaSearch.txtTo.setText("");
                pnSearch.EventSearchHoaDonforGiaoDien();
            }
        });
        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                pnDateSearch.dateFrom.setDate(null);
                pnDateSearch.dateTo.setDate(null);
                pnDonGiaSearch.txtFrom.setText("");
                pnDonGiaSearch.txtTo.setText("");
                pnSearch.EventSearchHoaDonforGiaoDien();
            }
        });
        pnSearch.setSizePanel(x, y);

        //Them Panel Tìm kiếm ngày nhập
        x += pnSearch.getSize().width;
        pnDateSearch = new DateSearch("GIAODIEN");
        pnDateSearch.btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnSearch.txtSearch.setText("");
                pnDonGiaSearch.txtFrom.setText("");
                pnDonGiaSearch.txtTo.setText("");
                pnDateSearch.SearchDateForHoaDon();
            }
        });
        pnDateSearch.setSizePanel(x, y);

        // Thêm panel Tim kiếm tổng tiền
        x += pnDateSearch.getSize().width;
        pnDonGiaSearch = new SearchFromTo("GIAODIEN", "Tổng tiền");
        pnDonGiaSearch.setSizePanel(x, y);
        pnDonGiaSearch.btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnDateSearch.dateFrom.setDate(null);
                pnDateSearch.dateTo.setDate(null);
                pnSearch.txtSearch.setText("");
                pnDonGiaSearch.SearchTongTienHoaDon();
            }
        });

        // Thêm Table 
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        tb = new Table();
        tb_x = 0;
        tb_y = pnSearch.getSize().height + btnLamMoi.getSize().height;
        tb_w = panelMain.getSize().width;
        tb_h = panelMain.getSize().height - tb_y - 1;
        tb.setBound(tb_x, tb_y, tb_w, tb_h);
        tb.setHeaders(qlhd.getHeaders);
        int i = 0;
        for (HoaDon q : qlhd.getDSHD()) {
            ++i;
            tb.addRow(new Object[]{Integer.toString(i), q.getMaHD(), q.getMaNV(), q.getMaKH(), q.getMaKM(), q.getNgayLap(), q.getGioLap(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getTongTien()))});
        }
        tb.resizeColumnWidth();
        tb.setRowHeigth(35);

        panelMain.add(btnXuatExcel);
//        jPanel4.add(btnNhapExcel);
        panelMain.add(btnInPDF);
        panelMain.add(btnXemChiTiet);
        panelMain.add(btnLamMoi);
        panelMain.add(pnSearch);
        panelMain.add(pnDateSearch);
        panelMain.add(pnDonGiaSearch);
        panelMain.add(tb);
        panelMain.validate();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
    private void btnPhieuNhapMouseEntered(java.awt.event.MouseEvent evt) {
        btnPhieuNhap.setBackground(new java.awt.Color(51, 204, 255));
        btnPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/phieunhap2.png")));
    }

    private void btnPhieuNhapMouseExited(java.awt.event.MouseEvent evt) {
        btnPhieuNhap.setBackground(new java.awt.Color(255, 255, 255));
        btnPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/phieunhap1.png")));
        initButton();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
// Bấm vào chức năng " Phiếu Nhập " khơi tạo hiển thị các nút chức năng , sự kiện
    private void btnPhieuNhapMousePressed(MouseEvent evt) {
        panelMain.removeAll();
        panelMain.repaint();
        Font font = new Font("Tahoma", 1, 12);
        labelTitle.setText("PHIẾU NHẬP");
        int x = 0, y = 0, w = 150, h = 50;
        x = (panelMain.getSize().width - w * 4) / 2;

        // Nút Xuất Excel
        btnXuatExcel = new JButton();
        btnXuatExcel.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/excel.png")));
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.setFont(font);
        btnXuatExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new XuatExcel().xuatFileExcelPhieuNhap();
            }
        });
        btnXuatExcel.setBounds(x, y, w, h);
        x += w;

        // Nút InPDF
        btnInPDF = new JButton();
        btnInPDF.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/interface-pdf.png")));
        btnInPDF.setText("In PDF");
        btnInPDF.setFont(font);
        btnInPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = tb.getSelectedRow();
                if (i >= 0) {
                    String MaPN = tb.LayMaPhieuNhapTaiTable(i).toString();
                    QuanlyphieunhapBUS Quanlyphieunhapbus = new QuanlyphieunhapBUS();
                    PhieuNhap pn = Quanlyphieunhapbus.getPhieuNhap(MaPN);
                    new WritePDF().writePhieuNhap(MaPN);
                }
            }
        });
        btnInPDF.setBounds(x, y, w, h);
        x += w;

        // Nút Xem chi tiết
        btnXemChiTiet = new JButton();
        btnXemChiTiet.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/view.png")));
        btnXemChiTiet.setText("Xem chi tiết");
        btnXemChiTiet.setFont(font);
        btnXemChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = tb.getSelectedRow();
                if (i >= 0) {
                    String MaPN = tb.LayMaPhieuNhapTaiTable(i).toString();
                    QuanlyphieunhapBUS Quanlyphieunhapbus = new QuanlyphieunhapBUS();
                    PhieuNhap pn = Quanlyphieunhapbus.getPhieuNhap(MaPN);
                    new FormChon(pn).setVisible(true);
                }
            }
        });
        btnXemChiTiet.setBounds(x, y, w, h);
        x += w;

        // Nút Làm Mới
        btnLamMoi = new JButton();
        btnLamMoi.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/internet.png")));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setFont(font);
        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();

                qlhd = new QuanlyhoadonBUS();
                panelMain.remove(tb);
                tb = new Table();
                tb.setBound(tb_x, tb_y, tb_w, tb_h);
                tb.setHeaders(qlpn.getHeaders);
                int i = 0;
                for (PhieuNhap q : qlpn.getDSPN()) {
                    ++i;
                    tb.addRow(new Object[]{Integer.toString(i), q.getMaPN(), q.getMaNCC(), q.getMaNV(), q.getNgayNhap(), q.getGioNhap(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getTongTien()))});
                }
                tb.resizeColumnWidth();
                tb.setRowHeigth(35);
                panelMain.add(tb);
                panelMain.validate();
            }
        });
        btnLamMoi.setBounds(x, y, w, h);

        x = (panelMain.getSize().width - 1100) / 2;
        y = h;
        // Thêm Panel Tìm Kiếm
        pnSearch = new SearchPanel("GIAODIEN");
        QuanlyphieunhapBUS Quanlyphieunhapbus = new QuanlyphieunhapBUS();
        pnSearch.setComboBox(Quanlyphieunhapbus.getComboboxSearch);
        pnSearch.ComboBoxSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnDateSearch.dateFrom.setDate(null);
                pnDateSearch.dateTo.setDate(null);
                pnDonGiaSearch.txtFrom.setText("");
                pnDonGiaSearch.txtTo.setText("");
                pnSearch.EventSearchPhieuNhapforGiaoDien();
            }
        });
        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                pnDateSearch.dateFrom.setDate(null);
                pnDateSearch.dateTo.setDate(null);
                pnDonGiaSearch.txtFrom.setText("");
                pnDonGiaSearch.txtTo.setText("");
                pnSearch.EventSearchPhieuNhapforGiaoDien();
            }
        });
        pnSearch.setSizePanel(x, y);

        //Them Panel Tìm kiếm ngày nhập
        x += pnSearch.getSize().width;
        pnDateSearch = new DateSearch("GIAODIEN");
        pnDateSearch.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), "Ngày nhập"));
        pnDateSearch.btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnSearch.txtSearch.setText("");
                pnDonGiaSearch.txtFrom.setText("");
                pnDonGiaSearch.txtTo.setText("");
                pnDateSearch.SearchDateForPhieuNhap();
            }
        });
        pnDateSearch.setSizePanel(x, y);

        // Thêm panel Tim kiếm tổng tiền
        x += pnDateSearch.getSize().width;
        pnDonGiaSearch = new SearchFromTo("GIAODIEN", "Tổng tiền");
        pnDonGiaSearch.setSizePanel(x, y);
        pnDonGiaSearch.btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnDateSearch.dateFrom.setDate(null);
                pnDateSearch.dateTo.setDate(null);
                pnSearch.txtSearch.setText("");
                pnDonGiaSearch.SearchTongTienPhieuNhap();
            }
        });

        // Thêm Table 
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        tb = new Table();
        tb_x = 0;
        tb_y = pnSearch.getSize().height + btnLamMoi.getSize().height;
        tb_w = panelMain.getSize().width;
        tb_h = panelMain.getSize().height - tb_y - 1;
        tb.setBound(tb_x, tb_y, tb_w, tb_h);
        tb.setHeaders(qlpn.getHeaders);
        int i = 0;
        for (PhieuNhap q : qlpn.getDSPN()) {
            ++i;
            tb.addRow(new Object[]{Integer.toString(i), q.getMaPN(), q.getMaNCC(), q.getMaNV(), q.getNgayNhap(), q.getGioNhap(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getTongTien()))});
        }
        tb.resizeColumnWidth();
        tb.setRowHeigth(35);

        panelMain.add(btnXuatExcel);
//        jPanel4.add(btnNhapExcel);
        panelMain.add(btnInPDF);
        panelMain.add(btnXemChiTiet);
        panelMain.add(btnLamMoi);
        panelMain.add(pnSearch);
        panelMain.add(pnDateSearch);
        panelMain.add(pnDonGiaSearch);
        panelMain.add(tb);
        panelMain.validate();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
        // Bấm vào chức năng " Khuyến mãi " khơi tạo hiển thị các nút chức năng , sự kiện

    private void btnKhuyenMaiMouseEntered(java.awt.event.MouseEvent evt) {
        btnKhuyenMai.setBackground(new java.awt.Color(51, 204, 255));
        btnKhuyenMai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/gift2.png")));
    }

    private void btnKhuyenMaiMouseExited(java.awt.event.MouseEvent evt) {
        btnKhuyenMai.setBackground(new java.awt.Color(255, 255, 255));
        btnKhuyenMai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/gift1.png")));
        initButton();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
    private void btnKhuyenMaiMousePressed(MouseEvent evt) {

        panelMain.removeAll();

        KhuyenMaiGUI km = new KhuyenMaiGUI(panelMain.getWidth(), panelMain.getHeight());
        km.setVisible(true);
        panelMain.add(km);

        labelTitle.setText("Khuyến Mãi");

        panelMain.validate();

    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
    private void btnNhanVienMouseEntered(java.awt.event.MouseEvent evt) {
        btnNhanVien.setBackground(new java.awt.Color(51, 204, 255));
        btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/staff2.png")));
    }

    private void btnNhanVienMouseExited(java.awt.event.MouseEvent evt) {
        btnNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/staff1.png")));
        initButton();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Bấm vào chức năng " Nhân Viên " khơi tạo hiển thị các nút chức năng , sự kiện
    private void btnNhanVienMousePressed(java.awt.event.MouseEvent evt) {
        
     panelMain.removeAll();

        labelTitle.setText("Nhân Viên");

        NhanVienPanel nv = new NhanVienPanel(panelMain.getWidth(), panelMain.getHeight());
        nv.setVisible(true);

        panelMain.add(nv);

        panelMain.validate();
    }
//        String dsquyen = LoginGUI_V1.currentQuyen.getChiTietQuyen();
//        themChucNangButton();
//        labelTitle.setText("NHÂN VIÊN");
//
//        int x = 110, y = 0, w = 150, h = 50;
//        x = (panelMain.getSize().width - w * 6) / 2;
//        // Nút Thêm Nhân Viên
//        btnThem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String MaNV = qlnvBUS.MaNVnotHave();
//                new FormThemSuaNhanVien(MaNV).setVisible(true);
//            }
//        });
//        btnThem.setBounds(x, y, w, h);
//        x += w;
//
//        // Nút Xóa Nhân Viên
//        btnXoa.setText("Đuổi làm");
//        btnXoa.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int i = tb.getSelectedRow();
//                if (i >= 0) {
//                    int k = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc đuổi nhân viên này không?");
//                    if (k != JOptionPane.YES_OPTION) {
//                        return;
//                    }
//                    String MaNV = tb.LayMaNVTaiTable(i).toString();
//                    qlnvBUS.duoiNhanVien(MaNV);
//                    tb.setValueTrangThai(MaNV, "Hết làm");
////                    tb.setValueAt("Hết làm", i, 6);
//                } else {
//                    JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn nhân viên cần xóa");
//                }
//            }
//        });
//        btnXoa.setBounds(x, y, w, h);
//        x += w;
//
//        // Nút Sửa Nhân Viên
//        btnSua.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int i = tb.getSelectedRow();
//                if (i >= 0) {
//                    String MaNV = tb.LayMaNVTaiTable(i).toString();
//                    NhanVien nv = qlnvBUS.getNhanVien(MaNV);
//                    new FormThemSuaNhanVien(nv, Integer.parseInt(tb.getValueSTT(i).toString())).setVisible(true);
//                } else {
//                    JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn nhân viên cần xóa");
//                }
//            }
//        });
//        btnSua.setBounds(x, y, w, h);
//        x += w;
//        if (dsquyen.contains("xemNhanVien")) {
//            btnThem.setEnabled(false);
//            btnXoa.setEnabled(false);
//            btnSua.setEnabled(false);
//        }
//
//        // Nút Xuất Excel Nhân Viên
//        btnXuatExcel.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new XuatExcel().xuatFileExcelNhanVien();
//            }
//        });
//        btnXuatExcel.setBounds(x, y, w, h);
//        x += w;
//
//        // Nút Nhập Excel Nhân Viên
//        btnNhapExcel.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new DocExcel().DocExcelNhanVien();
//                btnLamMoi.doClick();
//            }
//        });
//        btnNhapExcel.setBounds(x, y, w, h);
//        x += w;
//
//        // Nút Làm Mới Nhân Viên
//        btnLamMoi.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                qlnvBUS = new QuanlynhanvienBUS();
//                LamMoiTableNhanVien();
//            }
//        });
//        btnLamMoi.setBounds(x, y, w, h);
//
//        // Thêm Panel Tìm Kiếm Nhân Viên
//        x = (panelMain.getSize().width - 800 - 300) / 2;
//        y = h;
//        pnSearch.ComboBoxSearch.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                pnDateSearch.dateFrom.setDate(null);
//                pnDateSearch.dateTo.setDate(null);
//                pnAgeSearch.txtFrom.setText("");
//                pnAgeSearch.txtTo.setText("");
//                pnSearch.EventSearchNhanVienforGiaoDien();
//            }
//        });
//        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                pnDateSearch.dateFrom.setDate(null);
//                pnDateSearch.dateTo.setDate(null);
//                pnAgeSearch.txtFrom.setText("");
//                pnAgeSearch.txtTo.setText("");
//                pnSearch.EventSearchNhanVienforGiaoDien();
//            }
//        });
//        pnSearch.setComboBox(qlnvBUS.getComboboxSearch);
//        pnSearch.setSizePanel(x, y);
//
//        // Thêm Panel tìm kiếm Ngày Sinh Nhân Viên
//        x = x + pnSearch.getSize().width;
//        pnDateSearch.btnTim.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                pnSearch.txtSearch.setText("");
//                pnAgeSearch.txtFrom.setText("");
//                pnAgeSearch.txtTo.setText("");
//                pnDateSearch.SearchDateBornNhanVien();
//            }
//        });
//        pnDateSearch.setSizePanel(x, y);
//
//        // Thêm Panel tìm tuổi
//        x += pnDateSearch.getSize().width;
//        pnAgeSearch.btnTim.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                pnSearch.txtSearch.setText("");
//                pnDateSearch.dateFrom.setDate(null);
//                pnDateSearch.dateTo.setDate(null);
//                pnAgeSearch.SearchTuoiNhanVien();
//            }
//        });
//        pnAgeSearch.setSizePanel(x, y);
//
//        // Thêm Table Nhân Viên
//        tb_x = 0;
//        tb_y = pnSearch.getSize().height + btnLamMoi.getSize().height;
//        tb_w = panelMain.getSize().width;
//        tb_h = panelMain.getSize().height - pnSearch.getSize().height - btnLamMoi.getSize().height - 1;
//        tb.setBound(tb_x, tb_y, tb_w, tb_h);
//        tb.setHeaders(qlnvBUS.getHeaders);
//        String tt = "";
//        int i = 0;
//        for (NhanVien nv : qlnvBUS.getDSNV()) {
//            ++i;
//            if (nv.getTrangThai() == 1) {
//                tt = "Còn làm";
//            } else {
//                tt = "Hết làm";
//            }
//            tb.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSDT(), tt});
//        }
//        tb.resizeColumnWidth();
//
//        panelMain.add(tb);
//        panelMain.add(btnThem);
//        panelMain.add(btnXoa);
//        panelMain.add(btnSua);
//        panelMain.add(btnLamMoi);
//        panelMain.add(btnXuatExcel);
//        panelMain.add(btnNhapExcel);
//
//        panelMain.add(pnSearch);
//        panelMain.add(pnDateSearch);
//        panelMain.add(pnAgeSearch);
//        panelMain.validate();
//    }
//
//    private void LamMoiTableNhanVien() {
//        panelMain.remove(tb);
//        tb = new Table();
//        tb.setBound(tb_x, tb_y, tb_w, tb_h);
//        tb.setHeaders(qlnvBUS.getHeaders);
//        String tt = "";
//        int i = 0;
//        for (NhanVien nv : qlnvBUS.getDSNV()) {
//            ++i;
//            if (nv.getTrangThai() == 1) {
//                tt = "Còn làm";
//            } else {
//                tt = "Hết làm";
//            }
//            tb.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSDT(), tt});
//        }
//        tb.setRowHeigth(35);
//        tb.resizeColumnWidth();
//        panelMain.add(tb);
//        panelMain.validate();
//    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
// Bấm vào chức năng " Khách Hàng " khơi tạo hiển thị các nút chức năng , sự kiện
    private void btnKhachHangMouseEntered(java.awt.event.MouseEvent evt) {
        btnKhachHang.setBackground(new java.awt.Color(51, 204, 255));
        btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/customer2.png")));
    }

    private void btnKhachHangMouseExited(java.awt.event.MouseEvent evt) {
        btnKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/customer1.png")));
        initButton();
    }

    private void btnKhachHangMousePressed(MouseEvent evt) {

        panelMain.removeAll();

        KhachHangGUI kh = new KhachHangGUI(panelMain.getWidth(), panelMain.getHeight());
        kh.setVisible(true);
        panelMain.add(kh);

        labelTitle.setText("Khách Hàng");

        panelMain.validate();

    }


    /*---------------------------------------------------------------------------------------------------------------------------------------*/
 /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Bấm vào chức năng " Nhà Cung Cấp " khơi tạo hiển thị các nút chức năng , sự kiện
    private void btnNCCMouseEntered(java.awt.event.MouseEvent evt) {
        btnNCC.setBackground(new java.awt.Color(51, 204, 255));
        btnNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/NCC2.png")));
    }

    private void btnNCCMouseExited(java.awt.event.MouseEvent evt) {
        btnNCC.setBackground(new java.awt.Color(255, 255, 255));
        btnNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/NCC1.png")));
        initButton();
    }

    private void btnNCCMousePressed(MouseEvent evt) {

        panelMain.removeAll();

        NhaCungCapGUI ncc = new NhaCungCapGUI(panelMain.getWidth(), panelMain.getHeight());
        ncc.setVisible(true);
        panelMain.add(ncc);

        labelTitle.setText("Nhà Cung Cấp");

        panelMain.validate();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
 /*---------------------------------------------------------------------------------------------------------------------------------------*/
// Bấm vào chức năng " Tài Khoản " khơi tạo hiển thị các nút chức năng , sự kiện
    private void btnTaiKhoanMouseEntered(java.awt.event.MouseEvent evt) {
        btnTaiKhoan.setBackground(new java.awt.Color(51, 204, 255));
        btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/key2.png")));
    }

    private void btnTaiKhoanMouseExited(java.awt.event.MouseEvent evt) {
        btnTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/key1.png")));
        initButton();
    }

    private void btnTaiKhoanMousePressed(MouseEvent evt) {

        panelMain.removeAll();

        labelTitle.setText("Tài Khoản");

        XulyTaiKhoanPanel tkp = new XulyTaiKhoanPanel(panelMain.getWidth(), panelMain.getHeight());
        tkp.setVisible(true);

        panelMain.add(tkp);

        panelMain.validate();

    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
 /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Bấm vào chức năng " Quyền " khơi tạo hiển thị các nút chức năng , sự kiện
    private void btnQuyenMouseEntered(java.awt.event.MouseEvent evt) {
        btnQuyen.setBackground(new java.awt.Color(51, 204, 255));
        btnQuyen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/permission2.png")));
    }

    private void btnQuyenMouseExited(java.awt.event.MouseEvent evt) {
        btnQuyen.setBackground(new java.awt.Color(255, 255, 255));
        btnQuyen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/permission1.png")));
        initButton();
    }

    /*++++++++++++++++++++++++++++++++  Panel Quyền đã fix    ++++++++++++++++++++++++++++++++++++++++++++*/
    private void btnQuyenMousePressed(MouseEvent evt) {
        panelMain.removeAll();

        QuyenPanel qp = new QuyenPanel(panelMain.getWidth(), panelMain.getHeight());
        qp.setVisible(true);
        panelMain.add(qp);

        labelTitle.setText("QUYỀN");

        panelMain.validate();
    }

    /*++++++++++++++++++++++++++++++++                       ++++++++++++++++++++++++++++++++++++++++++++*/

 /*---------------------------------------------------------------------------------------------------------------------------------------*/
 /*---------------------------------------------------------------------------------------------------------------------------------------*/
 /*---------------------------------------------------------------------------------------------------------------------------------------*/

 /*---------------------------------------------------------------------------------------------------------------------------------------*/
 /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Bấm vào chức năng " Thống Kê " khơi tạo hiển thị các nút chức năng , sự kiện ( Phần này Bỏ ) 
    private void btnThongKeMouseEntered(java.awt.event.MouseEvent evt) {
        btnThongKe.setBackground(new java.awt.Color(51, 204, 255));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/thongke2.png")));
    }

    private void btnThongKeMouseExited(java.awt.event.MouseEvent evt) {
        btnThongKe.setBackground(new java.awt.Color(255, 255, 255));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/thongke1.png")));
        initButton();
    }

    private void btnThongKeMousePressed(MouseEvent evt) {
        panelMain.removeAll();
        panelMain.invalidate();
        panelMain.repaint();
        // Thêm Table
        tb = new Table();
        tb.setRowHeigth(35);
        ThongKeForm tk = new ThongKeForm();
        tk.setBounds(0, 0, panelMain.getWidth(), panelMain.getHeight());
        tk.setVisible(true);
        labelTitle.setText("THỐNG KÊ");

        panelMain.add(tk);
        panelMain.validate();
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
 /*---------------------------------------------------------------------------------------------------------------------------------------*/
    // Quản lý trạng thái hiển thị của các nút Chức năng ( Tô đậm màu )
    private void initButton() {
        String s = labelTitle.getText();
        if (btnBanHang != null) {
            btnBanHang.setBackground(new java.awt.Color(255, 255, 255));
            btnBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/shop1.png")));
        }
        if (btnNhapHang != null) {
            btnNhapHang.setBackground(new java.awt.Color(255, 255, 255));
            btnNhapHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/nhap1.png")));
        }
        if (btnSanPham != null) {
            btnSanPham.setBackground(new java.awt.Color(255, 255, 255));
            btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/smartphone1.png")));
        }
        if (btnLoaiSanPham != null) {
            btnLoaiSanPham.setBackground(new java.awt.Color(255, 255, 255));
            btnLoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/type1.png")));
        }
        if (btnHoaDon != null) {
            btnHoaDon.setBackground(new java.awt.Color(255, 255, 255));
            btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/hoadon1.png")));
        }

        if (btnKhuyenMai != null) {
            btnKhuyenMai.setBackground(new java.awt.Color(255, 255, 255));
            btnKhuyenMai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/gift1.png")));
        }
        if (btnNhanVien != null) {
            btnNhanVien.setBackground(new java.awt.Color(255, 255, 255));
            btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/staff1.png")));
        }
        if (btnKhachHang != null) {
            btnKhachHang.setBackground(new java.awt.Color(255, 255, 255));
            btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/customer1.png")));
        }
        if (btnPhieuNhap != null) {
            btnPhieuNhap.setBackground(new java.awt.Color(255, 255, 255));
            btnPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/phieunhap1.png")));
        }
        if (btnNCC != null) {
            btnNCC.setBackground(new java.awt.Color(255, 255, 255));
            btnNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/NCC1.png")));
        }
        if (btnTaiKhoan != null) {
            btnTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
            btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/key1.png")));
        }
        if (btnQuyen != null) {
            btnQuyen.setBackground(new java.awt.Color(255, 255, 255));
            btnQuyen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/permission1.png")));
        }
        if (btnThongKe != null) {
            btnThongKe.setBackground(new java.awt.Color(255, 255, 255));
            btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/thongke1.png")));
        }
        if (btnCaiDat != null) {
            btnCaiDat.setBackground(new java.awt.Color(255, 255, 255));
            btnCaiDat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/setting1.png")));
        }

        switch (s) {
            case "BÁN HÀNG": {
                if (btnBanHang != null) {
                    btnBanHang.setBackground(new java.awt.Color(51, 204, 255));
                    btnBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/shop2.png")));
                }
                break;
            }
            case "NHẬP HÀNG": {
                if (btnNhapHang != null) {
                    btnNhapHang.setBackground(new java.awt.Color(51, 204, 255));
                    btnNhapHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/nhap2.png")));
                }
                break;
            }
            case "SẢN PHẨM": {
                if (btnSanPham != null) {
                    btnSanPham.setBackground(new java.awt.Color(51, 204, 255));
                    btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/smartphone2.png")));
                }
                break;
            }
            case "LOẠI SẢN PHẨM": {
                if (btnLoaiSanPham != null) {
                    btnLoaiSanPham.setBackground(new java.awt.Color(51, 204, 255));
                    btnLoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/type2.png")));
                }
                break;
            }
            case "HÓA ĐƠN": {
                if (btnHoaDon != null) {
                    btnHoaDon.setBackground(new java.awt.Color(51, 204, 255));
                    btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/hoadon2.png")));
                }
                break;
            }
            case "KHUYẾN MÃI": {
                if (btnKhuyenMai != null) {
                    btnKhuyenMai.setBackground(new java.awt.Color(51, 204, 255));
                    btnKhuyenMai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/gift2.png")));
                }
                break;
            }
            case "NHÂN VIÊN": {
                if (btnNhanVien != null) {
                    btnNhanVien.setBackground(new java.awt.Color(51, 204, 255));
                    btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/staff2.png")));
                }
                break;
            }
            case "KHÁCH HÀNG": {
                if (btnKhachHang != null) {
                    btnKhachHang.setBackground(new java.awt.Color(51, 204, 255));
                    btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/customer2.png")));
                }
                break;
            }
            case "PHIẾU NHẬP": {
                if (btnPhieuNhap != null) {
                    btnPhieuNhap.setBackground(new java.awt.Color(51, 204, 255));
                    btnPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/phieunhap2.png")));
                }
                break;
            }
            case "NHÀ CUNG CẤP": {
                if (btnNCC != null) {
                    btnNCC.setBackground(new java.awt.Color(51, 204, 255));
                    btnNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/NCC2.png")));
                }
                break;
            }
            case "TÀI KHOẢN": {
                if (btnTaiKhoan != null) {
                    btnTaiKhoan.setBackground(new java.awt.Color(51, 204, 255));
                    btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/key2.png")));
                }
                break;
            }
            case "QUYỀN": {
                if (btnQuyen != null) {
                    btnQuyen.setBackground(new java.awt.Color(51, 204, 255));
                    btnQuyen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/permission2.png")));
                }
                break;
            }
            case "THỐNG KÊ": {
                if (btnThongKe != null) {
                    btnThongKe.setBackground(new java.awt.Color(51, 204, 255));
                    btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/thongke2.png")));
                }
                break;
            }
            case "CÀI ĐẶT": {
                if (btnCaiDat != null) {
                    btnCaiDat.setBackground(new java.awt.Color(51, 204, 255));
                    btnCaiDat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/setting2.png")));
                }
                break;
            }
            default:
                break;
        }
    }

    // Thành phần tìm kiếm
    private SearchPanel pnSearch;
    private DateSearch pnDateSearch;
    private SearchFromTo pnAgeSearch;
    private SearchFromTo pnSoLuongSearch;
    private SearchFromTo pnDonGiaSearch;

    // Các button theo danh mục
    private javax.swing.JButton btnXemChiTiet;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JButton btnNhapExcel;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnInPDF;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnKetThuc;

    // Button danh mục
    private javax.swing.JButton btnBanHang;
    private javax.swing.JButton btnCaiDat;
    private javax.swing.JButton btnCongCu;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnKhuyenMai;
    private javax.swing.JButton btnLoaiSanPham;
    private javax.swing.JButton btnNCC;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnNhapHang;
    private javax.swing.JButton btnPhieuNhap;
    private javax.swing.JButton btnQuyen;
    private javax.swing.JButton btnSanPham;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnThongKe;

    // Các panel dc thêm
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JPanel panelMain;
    // Variables declaration - do not modify                     
    private javax.swing.JPanel JHeader;
    private javax.swing.JPanel JMain1;
    private javax.swing.JLabel labelBack;
    private javax.swing.JLabel labelPower;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel labelZoom;
    // End of variables declaration                   
}
