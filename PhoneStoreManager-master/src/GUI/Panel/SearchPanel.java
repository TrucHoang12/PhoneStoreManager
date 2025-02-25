package GUI.Panel;

import DTO.NCC;
import BUS.QuanlyNCCBUS;
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
import BUS.QuanlysanphamBUS;
import DTO.SanPham;
import DTO.TaiKhoan;
import BUS.XulyTaiKhoanBUS;
import GUI.GiaoDien.GiaoDienGUI;
import static GUI.GiaoDien.GiaoDienGUI.tb;
//import static GUI.ChucNang.QuyenPanel;


import GUI.GiaoDien.Table;
import GUI.GiaoDien.myTableCellRenderer;
import DTO.LoaiSanPham;
import BUS.QuanlyloaisanphamBUS;
import GUI.ChucNang.QuyenPanel;
import static GUI.GiaoDien.GiaoDienGUI.panelMain;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {

    public JComboBox<String> ComboBoxSearch;
    public JTextField txtSearch;
    private String type = "";

    public SearchPanel(String type) {
        init();
        this.type = type;
        if (!type.equals("GIAODIEN")) {
            int w = 350;
            int h = 100;
            setSize(w, h);
//            setBackground(new Color(240,240,240));
            float wcom = (float) (0.425 * getSize().width);
            float hcom = (float) (0.34 * getSize().height);
            float ycom = (float) (0.4 * getSize().height);
            ComboBoxSearch.setBounds((int) (0.05 * getSize().width), (int) ycom, (int) wcom, (int) hcom);
            txtSearch.setBounds((int) (0.1 * getSize().width + wcom), (int) ycom, (int) wcom, (int) hcom);
        }
    }

    private void init() {
        ComboBoxSearch = new JComboBox<>();
        txtSearch = new JTextField();
        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel4Layout);
        this.add(ComboBoxSearch);
        this.add(txtSearch);
    }

    public void setSizePanel(int x, int y) {
        int w = 400;
        int h = 100;
        this.setBounds(x, y, w, h);
        float wcom = (float) (0.425 * getSize().width);
        float hcom = (float) (0.34 * getSize().height);
        float ycom = (float) (0.4 * getSize().height);
        ComboBoxSearch.setBounds((int) (0.05 * getSize().width), (int) ycom, (int) wcom, (int) hcom);
        txtSearch.setBounds((int) (0.1 * getSize().width + wcom), (int) ycom, (int) wcom, (int) hcom);

    }
    public void setSizePanel(int x, int y, boolean Change) {
        int w = 400;
        int h = 100;
        this.setBounds(x, y, w, h);
        float wcom = (float) (0.425 * getSize().width);
        float hcom = (float) (0.34 * getSize().height);
        float ycom = (float) (0.4 * getSize().height);
        ComboBoxSearch.setBounds((int) (0.05 * getSize().width), (int) ycom, (int) wcom, (int) hcom);
        txtSearch.setBounds((int) (0.1 * getSize().width + wcom), (int) ycom, Change? (int) wcom - 40 :(int) wcom, (int) hcom);

    }
    public void setComboBox(String[] s) {
        ComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(s));
    }

    // Thiết lập phần chức năng search cho Nhân viên
    public void EventSearchNhanVienforGiaoDien() {
        QuanlynhanvienBUS Quanlynhanvienbus = new QuanlynhanvienBUS();
        String s = txtSearch.getText().trim();
        String kq_timkiem = "-------------------Tìm kiếm: " + s;

        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<NhanVien> searchDSNV = Quanlynhanvienbus.SearchNhanVien(s, type);
        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);
        GiaoDienGUI.tb.setHeaders(Quanlynhanvienbus.getHeaders);
        if (searchDSNV != null) {
            int i = 0;
            for (NhanVien nv : searchDSNV) {
                ++i;
                GiaoDienGUI.tb.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(),
                    nv.getSDT(), (nv.getTrangThai() == 1) ? "Còn làm" : "Hết làm"});
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }
        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    // Thiết lập chức năng search cho Quyền
    
    // Thiết lập chức năng search cho Quyền
public void EventSearchQuyenforGiaoDien() {
    QuyenPanel quyenPanel = new QuyenPanel(WIDTH, HEIGHT);
    QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
    String s = txtSearch.getText().trim();
    String kq_timkiem = "---------- Tìm kiếm: " + s;
    String type = ComboBoxSearch.getSelectedItem().toString().trim();
    ArrayList<Quyen> list = Quanlyquyenbus.SearchQuyen(s, type);

    // Xóa bảng hiện tại trong QuyenPanel (nếu có)
    Component[] components = quyenPanel.getComponents();
    for (Component component : components) {
        if (component instanceof Table) {
            quyenPanel.remove(component);
            break; // Sau khi xóa bảng, bạn có thể thoát vòng lặp vì chỉ cần xóa một bảng duy nhất.
        }
    }

    // Tạo mới bảng và thêm vào QuyenPanel
    Table tb = new Table();
    tb.setHeaders(Quanlyquyenbus.getHeaders);
    tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);

    if (list != null) {
        int i = 0;
        for (Quyen q : list) {
            ++i;
            tb.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
        }
        kq_timkiem += " ------------- Tìm thấy";
    } else {
        kq_timkiem += " ------------- Không tìm thấy";
    }

    tb.setRowHeigth(35);
    tb.resizeColumnWidth();
    quyenPanel.add(tb); // Thêm bảng mới vào QuyenPanel
    quyenPanel.validate(); // Cập nhật giao diện
    System.out.println(kq_timkiem);
}

//    public void EventSearchQuyenforGiaoDien() {
//        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
//        String s = txtSearch.getText().trim();
//        String kq_timkiem = "---------- Tìm kiếm: " + s;
//        String type = ComboBoxSearch.getSelectedItem().toString().trim();
//        ArrayList<Quyen> list = Quanlyquyenbus.SearchQuyen(s, type);
//
//        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
//        GiaoDienGUI.tb = new Table();
//        GiaoDienGUI.tb.setHeaders(Quanlyquyenbus.getHeaders);
//        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);
//
//        if (list != null) {
//            int i = 0;
//            for (Quyen q : list) {
//                ++i;
//                GiaoDienGUI.tb.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
//            }
//            kq_timkiem += " ------------- Tìm thấy";
//        } else {
//            kq_timkiem += " ------------- Không tìm thấy";
//        }
//
//        GiaoDienGUI.tb.setRowHeigth(35);
//        GiaoDienGUI.tb.resizeColumnWidth();
//        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
//        GiaoDienGUI.panelMain.validate();
//        System.out.println(kq_timkiem);
//    }

    public void EventSearchTaiKhoanforGiaoDien() {

        String s = txtSearch.getText().trim();
        String kq_timkiem = "---------- Tìm kiếm: " + s;
        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        XulyTaiKhoanBUS XulyTaiKhoanbus = new XulyTaiKhoanBUS();
        ArrayList<TaiKhoan> list = XulyTaiKhoanbus.SearchTaiKhoan(s, type);

        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setHeaders(XulyTaiKhoanbus.getHeaders);
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);

        if (list != null) {
            int i = 0;
            for (TaiKhoan tk : list) {
                ++i;
                GiaoDienGUI.tb.addRow(new String[]{Integer.toString(i), tk.getTenTaiKhoan(), tk.getMatKhau(), tk.getMaNV(), tk.getMaQuyen()});
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    // Thiết lập chức năng search cho NCC
    public void EventSearchNCCforGiaoDien() {
        QuanlyNCCBUS QuanlyNCCbus = new QuanlyNCCBUS();

        String s = txtSearch.getText().trim();
        String kq_timkiem = "---------- Tìm kiếm: " + s;
        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<NCC> list = QuanlyNCCbus.SearchNCC(s, type);

        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setHeaders(QuanlyNCCbus.getHeaders);
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);

        if (list != null) {
            int i = 0;
            for (NCC q : list) {
                ++i;
                GiaoDienGUI.tb.addRow(new String[]{Integer.toString(i), q.getMaNCC(), q.getTenNCC(), q.getDiaChi(), q.getSDT(), q.getFax()});
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    // Thiết lập chức năng search cho KhachHang
    public void EventSearchKhachHangforGiaoDien() {
        QuanlykhachhangBUS Quanlykhachhangbus = new QuanlykhachhangBUS();
        String s = txtSearch.getText().trim();
        String kq_timkiem = "---------- Tìm kiếm: " + s;
        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<KhachHang> list = Quanlykhachhangbus.SearchKhachHang(s, type);

        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setHeaders(Quanlykhachhangbus.getHeaders);
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);

        if (list != null) {
            int i = 0;
            for (KhachHang q : list) {
                ++i;
                GiaoDienGUI.tb.addRow(new String[]{Integer.toString(i), q.getMaKH(), q.getTenKH(), q.getDiaChi(), q.getSDT(), (q.getTrangThai() == 1) ? "Hiện" : "Ẩn"});
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    // Thiết lập chức năng search cho LSP
    public void EventSearchLSPforGiaoDien() {
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        String s = txtSearch.getText().trim();
        String kq_timkiem = "---------- Tìm kiếm: " + s;
        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<LoaiSanPham> list = Quanlyloaisanphambus.SearchLSP(s, type);

        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setHeaders(Quanlyloaisanphambus.getHeaders);
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);

        if (list != null) {
            int i = 0;
            for (LoaiSanPham q : list) {
                ++i;
                GiaoDienGUI.tb.addRow(new String[]{Integer.toString(i), q.getMaLSP(), q.getTenLSP(), q.getMota()});
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }
  public void EventSearchLSPforGiaoDien(Table tbSP) {
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        String s = txtSearch.getText().trim();
        String kq_timkiem = "---------- Tìm kiếm: " + s;
        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<LoaiSanPham> list = Quanlyloaisanphambus.SearchLSP(s, type);
        tbSP = new Table();
        tbSP.setHeaders(Quanlyloaisanphambus.getHeaders);

        if (list != null) {
            int i = 0;
            for (LoaiSanPham q : list) {
                ++i;
                tbSP.addRow(new String[]{Integer.toString(i), q.getMaLSP(), q.getTenLSP(), q.getMota()});
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }

        tbSP.setRowHeigth(35);
        tbSP.resizeColumnWidth();
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }
    public void EventSearchKhuyenMaiforGiaoDien() {
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        String s = txtSearch.getText().trim();
        String kq_timkiem = "---------- Tìm kiếm: " + s;
        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<KhuyenMai> list = Quanlykhuyenmaibus.SearchKhuyenMai(s, type);

        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setHeaders(Quanlykhuyenmaibus.getHeaders);
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);

        if (list != null) {
            int i = 0;
            LocalDate today = LocalDate.now();
            String[] day = today.toString().split("-");
            String[] kt;
            String[] bd;

            for (KhuyenMai q : list) {
                ++i;
                kt = q.getNgayKT().toString().split("-");
                bd = q.getNgayBD().toString().split("-");
                String trangthai = "Đang diễn ra";
                if (today.toString().equals(q.getNgayKT().toString())) {
                    trangthai = "Đã kết thúc";
                } else {
                    if (!(Quanlykhuyenmaibus.CompareDate(day[0], day[1], day[2], kt[0], kt[1], kt[2])
                            && Quanlykhuyenmaibus.CompareDate(bd[0], bd[1], bd[2], day[0], day[1], day[2]))) {
                        if (Quanlykhuyenmaibus.CompareDate(day[0], day[1], day[2], bd[0], bd[1], bd[2])) {
                            trangthai = "Chưa diễn ra";
                        } else {
                            trangthai = "Đã kết thúc";
                        }
                    }
                }
                GiaoDienGUI.tb.addRow(new String[]{Integer.toString(i), q.getMaKM(), q.getTenKM(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDieuKienKM())), Integer.toString(q.getPhanTramKM()),
                    q.getNgayBD().toString(), q.getNgayKT().toString(), trangthai});
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    // Thiết lập chức năng search cho SP
    public void EventSearchSPforGiaoDien() {
        QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();

        String s = txtSearch.getText().trim();
        String kq_timkiem = "---------- Tìm kiếm: " + s;
        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<SanPham> list = Quanlysanphambus.SearchSanPham(s, type);

        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setHeaders(Quanlysanphambus.getHeaders);
        GiaoDienGUI.tb.jTable1.getColumn("Hình ảnh").setCellRenderer(new myTableCellRenderer());
        GiaoDienGUI.tb.jTable1.getColumn("Hình ảnh").setMaxWidth(120);
        tb.jTable1.getColumn("Hình ảnh").setMinWidth(95);
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);

        if (list != null) {
            int i = 0;
            for (SanPham q : list) {
                ++i;
                ImageIcon img = new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/product/" + q.getHinhAnh()));
                Image imgScaled = img.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
                JLabel label = new JLabel();
                label.setIcon(new ImageIcon(imgScaled));
                tb.addRow(new Object[]{Integer.toString(i), q.getMaSP(), q.getMaLSP(), q.getTenSP(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia())),
                    Quanlykhuyenmaibus.ToCurrent(Integer.toString(q.getSoLuong())), label, (q.getTrangThai() == 1) ? "Hiện" : "Ẩn"
                });
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(60);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    // Thiết lập chức năng search cho Hóa Đơn
    public void EventSearchHoaDonforGiaoDien() {
        QuanlyhoadonBUS Quanlyhoadonbus = new QuanlyhoadonBUS();
        QuanlykhachhangBUS Quanlykhachhangbus = new QuanlykhachhangBUS();
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        String s = txtSearch.getText().trim();
        String kq_timkiem = "---------- Tìm kiếm: " + s;
        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<HoaDon> list = Quanlyhoadonbus.SearchHoaDon(s, type);

        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setHeaders(Quanlyhoadonbus.getHeaders);
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);

        if (list != null) {
            int i = 0;
            for (HoaDon q : list) {
                ++i;
                tb.addRow(new Object[]{Integer.toString(i), q.getMaHD(), q.getMaNV(), q.getMaKH(), q.getMaKM(), q.getNgayLap(), q.getGioLap(),
                    Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getTongTien()))
                });
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    // Thiết lập chức năng search cho Phiếu Nhập
    public void EventSearchPhieuNhapforGiaoDien() {
        QuanlyphieunhapBUS Quanlyphieunhapbus = new QuanlyphieunhapBUS();
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        String s = txtSearch.getText().trim();
        String kq_timkiem = "---------- Tìm kiếm: " + s;
        String type = ComboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<PhieuNhap> list = Quanlyphieunhapbus.SearchPhieuNhap(s, type);

        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setHeaders(Quanlyphieunhapbus.getHeaders);
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);

        if (list != null) {
            int i = 0;
            for (PhieuNhap q : list) {
                ++i;
                tb.addRow(new Object[]{Integer.toString(i), q.getMaPN(), q.getMaNCC(), q.getMaNV(), q.getNgayNhap(), q.getGioNhap(),
                    Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getTongTien()))
                });
            }
            kq_timkiem += " ------------- Tìm thấy";
        } else {
            kq_timkiem += " ------------- Không tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

}
