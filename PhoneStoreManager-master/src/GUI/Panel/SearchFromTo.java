package GUI.Panel;

import DTO.HoaDon;
import BUS.QuanlyhoadonBUS;
import BUS.QuanlykhachhangBUS;
import BUS.QuanlykhuyenmaiBUS;
import BUS.QuanlyloaisanphamBUS;
import DTO.NhanVien;
import BUS.QuanlynhanvienBUS;
import DTO.PhieuNhap;
import BUS.QuanlyphieunhapBUS;
import BUS.QuanlysanphamBUS;
import DTO.SanPham;
import GUI.GiaoDien.GiaoDienGUI;
import static GUI.GiaoDien.GiaoDienGUI.tb;
import GUI.GiaoDien.Table;
import GUI.GiaoDien.myTableCellRenderer;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchFromTo extends JPanel {

    public JTextField txtFrom;
    public JTextField txtTo;
    public JButton btnTim;

    public SearchFromTo(String type, String Title) {
        init(Title);
        if (!type.equals("GIAODIEN")) {
            setSize(350, 100);
//            setBackground(new Color(240,240,240));
            float xcom = (float) (getSize().width * 0.05);
            float ycom = (float) (getSize().height * 0.3);
            float wcom = (float) (getSize().width * 0.35);
            float hcom = (float) (getSize().height * 0.55);

            txtFrom.setBounds((int) xcom, (int) ycom, (int) wcom, (int) hcom);
            xcom += txtFrom.getSize().width;
            txtTo.setBounds((int) xcom, (int) ycom, (int) wcom, (int) hcom);
            xcom += txtTo.getSize().width;
            btnTim.setBounds((int) xcom, (int) (ycom * 1.45), (int) (wcom * 0.6), (int) (hcom * 0.7));
        }
    }

    private void init(String Title) {
        txtFrom = new JTextField();
        txtFrom.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                char caracter = evt.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != '\b')) {
                    evt.consume();
                }
            }
        });
        txtTo = new JTextField();
        txtTo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                char caracter = evt.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != '\b')) {
                    evt.consume();
                }
            }
        });
        btnTim = new JButton();
        setBackground(Color.white);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), Title));
        txtFrom.setBorder(BorderFactory.createTitledBorder("Từ"));
        txtTo.setBorder(BorderFactory.createTitledBorder("Đến"));
        btnTim.setText("Tìm");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        add(txtFrom);
        add(txtTo);
        add(btnTim);
    }

    public void setSizePanel(int x, int y) {
        setBounds(x, y, 300, 100);

        float xcom = (float) (getSize().width * 0.05);
        float ycom = (float) (getSize().height * 0.3);
        float wcom = (float) (getSize().width * 0.35);
        float hcom = (float) (getSize().height * 0.55);

        txtFrom.setBounds((int) xcom, (int) ycom, (int) wcom, (int) hcom);
        xcom += txtFrom.getSize().width;
        txtTo.setBounds((int) xcom, (int) ycom, (int) wcom, (int) hcom);
        xcom += txtTo.getSize().width;
        btnTim.setBounds((int) xcom, (int) (ycom * 1.45), (int) (wcom * 0.6), (int) (hcom * 0.7));
    }

    public void SearchTuoiNhanVien() {
            QuanlynhanvienBUS Quanlynhanvienbus = new QuanlynhanvienBUS();
        String ag1 = txtFrom.getText().trim();
        String ag2 = txtTo.getText().trim();
        String pa = "[\\d]{1,}";
        ArrayList<NhanVien> list;
        if (Pattern.matches(pa, ag1) && Pattern.matches(pa, ag2)) {
            int age1 = Integer.parseInt(ag1);
            int age2 = Integer.parseInt(ag2);
            list = Quanlynhanvienbus.SearchNVAge(age1, age2);
        } else {
            list = null;
        }

        String kq_timkiem = "----- Age From: " + ag1 + " To " + ag2 + " ------ ";
        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);
        GiaoDienGUI.tb.setHeaders(Quanlynhanvienbus.getHeaders);

        if (list == null) {
            kq_timkiem += "Không tìm thấy";
        } else {
            int i = 0;
            for (NhanVien nv : list) {
                ++i;
                GiaoDienGUI.tb.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(),
                    nv.getSDT(), (nv.getTrangThai() == 1) ? "Còn làm" : "Hết làm"});
            }
            kq_timkiem += "Đã tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    public void SearchSoLuongSanPham() {
        QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        String ag1 = txtFrom.getText().trim();
        String ag2 = txtTo.getText().trim();
        String pa = "[\\d]{1,}";
        ArrayList<SanPham> list;
        if (Pattern.matches(pa, ag1) && Pattern.matches(pa, ag2)) {
            int age1 = Integer.parseInt(ag1);
            int age2 = Integer.parseInt(ag2);
            list = Quanlysanphambus.SearchSoLuongSP(age1, age2);
        } else {
            list = null;
        }

        String kq_timkiem = "----- SoLuongSP From: " + ag1 + " To " + ag2 + " ------ ";
        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);
        GiaoDienGUI.tb.setHeaders(Quanlysanphambus.getHeaders);
        GiaoDienGUI.tb.jTable1.getColumn("Hình ảnh").setCellRenderer(new myTableCellRenderer());
        GiaoDienGUI.tb.jTable1.getColumn("Hình ảnh").setMaxWidth(120);
        tb.jTable1.getColumn("Hình ảnh").setMinWidth(95);

        if (list == null) {
            kq_timkiem += "Không tìm thấy";
        } else {
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
            kq_timkiem += "Đã tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(60);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    public void SearchDonGiaSanPham() {
        QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();

        String ag1 = txtFrom.getText().trim();
        String ag2 = txtTo.getText().trim();
        String pa = "[\\d]{1,}";
        ArrayList<SanPham> list;
        if (Pattern.matches(pa, ag1) && Pattern.matches(pa, ag2)) {
            long age1 = Long.parseLong(ag1);
            long age2 = Long.parseLong(ag2);
            list = Quanlysanphambus.SearchDonGiaSP(age1, age2);
        } else {
            list = null;
        }

        String kq_timkiem = "----- DonGiaSP From: " + ag1 + " To " + ag2 + " ------ ";
        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);
        GiaoDienGUI.tb.setHeaders(Quanlysanphambus.getHeaders);
        GiaoDienGUI.tb.jTable1.getColumn("Hình ảnh").setCellRenderer(new myTableCellRenderer());
        GiaoDienGUI.tb.jTable1.getColumn("Hình ảnh").setMaxWidth(120);
        tb.jTable1.getColumn("Hình ảnh").setMinWidth(95);

        if (list == null) {
            kq_timkiem += "Không tìm thấy";
        } else {
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
            kq_timkiem += "Đã tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(60);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    public void SearchTongTienHoaDon() {
        QuanlyhoadonBUS Quanlyhoadonbus = new QuanlyhoadonBUS();
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        String ag1 = txtFrom.getText().trim();
        String ag2 = txtTo.getText().trim();
        String pa = "[\\d]{1,}";
        ArrayList<HoaDon> list;
        if (Pattern.matches(pa, ag1) && Pattern.matches(pa, ag2)) {
            long age1 = Long.parseLong(ag1);
            long age2 = Long.parseLong(ag2);
            list = Quanlyhoadonbus.SearchHoaDonTongTienFromTo(age1, age2);
        } else {
            list = null;
        }

        String kq_timkiem = "----- TongTien From: " + ag1 + " To " + ag2 + " ------ ";
        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);
        GiaoDienGUI.tb.setHeaders(Quanlyhoadonbus.getHeaders);

        if (list == null) {
            kq_timkiem += "Không tìm thấy";
        } else {
            int i = 0;
            for (HoaDon q : list) {
                ++i;
                tb.addRow(new Object[]{Integer.toString(i), q.getMaHD(), q.getMaNV(), q.getMaKH(), q.getMaKM(), q.getNgayLap(), q.getGioLap(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getTongTien()))
                });
            }
            kq_timkiem += "Đã tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }

    public void SearchTongTienPhieuNhap() {
                    QuanlyphieunhapBUS Quanlyphieunhapbus = new QuanlyphieunhapBUS();
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        String ag1 = txtFrom.getText().trim();
        String ag2 = txtTo.getText().trim();
        String pa = "[\\d]{1,}";
        ArrayList<PhieuNhap> list;
        if (Pattern.matches(pa, ag1) && Pattern.matches(pa, ag2)) {
            long age1 = Long.parseLong(ag1);
            long age2 = Long.parseLong(ag2);
            list = Quanlyphieunhapbus.SearchPhieuNhapTongTienFromTo(age1, age2);
        } else {
            list = null;
        }

        String kq_timkiem = "----- TongTien From: " + ag1 + " To " + ag2 + " ------ ";
        GiaoDienGUI.panelMain.remove(GiaoDienGUI.tb);
        GiaoDienGUI.tb = new Table();
        GiaoDienGUI.tb.setBound(GiaoDienGUI.tb_x, GiaoDienGUI.tb_y, GiaoDienGUI.tb_w, GiaoDienGUI.tb_h);
        GiaoDienGUI.tb.setHeaders(Quanlyphieunhapbus.getHeaders);

        if (list == null) {
            kq_timkiem += "Không tìm thấy";
        } else {
            int i = 0;
            for (PhieuNhap q : list) {
                ++i;
                tb.addRow(new Object[]{Integer.toString(i), q.getMaPN(), q.getMaNCC(), q.getMaNV(), q.getNgayNhap(), q.getGioNhap(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getTongTien()))
                });
            }
            kq_timkiem += "Đã tìm thấy";
        }

        GiaoDienGUI.tb.setRowHeigth(35);
        GiaoDienGUI.tb.resizeColumnWidth();
        GiaoDienGUI.panelMain.add(GiaoDienGUI.tb);
        GiaoDienGUI.panelMain.validate();
        System.out.println(kq_timkiem);
    }
}
