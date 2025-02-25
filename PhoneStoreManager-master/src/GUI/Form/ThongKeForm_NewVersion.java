/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Form;

import GUI.Form.MyCheckDate;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.PhoneStoreManager.BackEnd.PriceFormatter;
import DTO.ChiTietHoaDon;
import BUS.QuanlychitiethoadonBUS;
import DTO.ChiTietPhieuNhap;
import BUS.QuanlychitietphieunhapBUS;
import DTO.HoaDon;
import BUS.QuanlyhoadonBUS;
import DTO.KhachHang;
import BUS.QuanlykhachhangBUS;
import DTO.NCC;
import BUS.QuanlyNCCBUS;
import DTO.NhanVien;
import BUS.QuanlynhanvienBUS;
import DTO.PhieuNhap;
import BUS.QuanlyphieunhapBUS;
import DTO.SanPham;
import BUS.QuanlysanphamBUS;
import DTO.LoaiSanPham;
import BUS.QuanlyloaisanphamBUS;
import GUI.GiaoDien.Table;
import GUI.GiaoDien.Table;
import GUI.Button.DateButton;
import GUI.Button.MoreButton;
import GUI.Button.RefreshButton;
import GUI.Form.FormChon;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author DELL
 */
public class ThongKeForm_NewVersion extends JPanel {

    QuanlyhoadonBUS qlhdBUS = new QuanlyhoadonBUS();
    QuanlysanphamBUS qlspBUS = new QuanlysanphamBUS();
    QuanlynhanvienBUS qlnvBUS = new QuanlynhanvienBUS();
    QuanlykhachhangBUS qlkhBUS = new QuanlykhachhangBUS();
    QuanlyphieunhapBUS qlpnBUS = new QuanlyphieunhapBUS();
    QuanlyNCCBUS qlnccBUS = new QuanlyNCCBUS();
    QuanlychitiethoadonBUS qlcthdBUS = new QuanlychitiethoadonBUS();
    QuanlychitietphieunhapBUS qlctpnBUS = new QuanlychitietphieunhapBUS();
    QuanlyloaisanphamBUS qllspBUS = new QuanlyloaisanphamBUS();

    JTextField txNgay1 = new JTextField(7);
    JTextField txNgay2 = new JTextField(7);
    JTextField txNhanVien = new JTextField(10);
    JTextField txKhachHang = new JTextField(10);
    JTextField txNhaCC = new JTextField(10);
    JTextField txSanPham = new JTextField(10);

    JPanel plSanPham, plNhanVien, plKhachHang, plNhaCC;

    DatePicker dPicker1;
    DatePicker dPicker2;
    MoreButton btnChonNhanVien = new MoreButton();
    MoreButton btnChonKhachHang = new MoreButton();
    MoreButton btnChonNhaCC = new MoreButton();
    MoreButton btnChonSanPham = new MoreButton();
    RefreshButton btnRefresh = new RefreshButton();

    JCheckBox chbNhanVien = new JCheckBox();
    JCheckBox chbKhachHang = new JCheckBox();
    JCheckBox chbNhaCC = new JCheckBox();
    JCheckBox chbSanPham = new JCheckBox();

    JComboBox<String> cbChonLoaiThongKe = new JComboBox<>(new String[]{"Bán ra", "Nhập vào"});

    Table tb = new Table();
    Table tbTong = new Table();

    public ThongKeForm_NewVersion() {
        setLayout(new BorderLayout());

        // panel chon ngay
        DatePickerSettings ds = new DatePickerSettings();
        ds.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(ds);
        dPicker2 = new DatePicker(ds.copySettings());
        dPicker1.addDateChangeListener((dce) -> {
            txNgay1.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2.addDateChangeListener((dce) -> {
            txNgay2.setText(dPicker2.getDateStringOrEmptyString());
        });
        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        txNgay1.setBorder(BorderFactory.createTitledBorder("Từ"));
        txNgay2.setBorder(BorderFactory.createTitledBorder("Đến"));

        JPanel plChonNgay = new JPanel();
        plChonNgay.setBorder(BorderFactory.createTitledBorder("Khoảng ngày"));

        addDocumentListener(txNgay1);
        addDocumentListener(txNgay2);
        plChonNgay.add(txNgay1);
        plChonNgay.add(dPicker1);
        plChonNgay.add(txNgay2);
        plChonNgay.add(dPicker2);
        // ============== End panel chon ngay ======================
//        tb.setHeaders(new String[]{"Hóa đơn"});

        // event
//        btnChonSanPham.addActionListener((ae) -> {
//            ChonSanPhamForm cnv = new ChonSanPhamForm(txSanPham, null, null, null, null);
//        });
//        btnChonNhanVien.addActionListener((ae) -> {
//            ChonNhanVienForm cnv = new ChonNhanVienForm(txNhanVien);
//        });
//        btnChonKhachHang.addActionListener((ae) -> {
//            ChonKhachHangForm ckh = new ChonKhachHangForm(txKhachHang);
//        });
//        btnChonNhaCC.addActionListener((ae) -> {
//            ChonNhaCungCapForm cnv = new ChonNhaCungCapForm(txNhaCC);
//        });
//        btnRefresh.addActionListener((ae) -> {
////            refresh();
//        });
        // ================= End Event ====================

        plNhanVien = getPanelTieuChi("Nhân viên", chbNhanVien, txNhanVien, btnChonNhanVien);
        plSanPham = getPanelTieuChi("Sản phẩm", chbSanPham, txSanPham, btnChonSanPham);
        plKhachHang = getPanelTieuChi("Khách hàng", chbKhachHang, txKhachHang, btnChonKhachHang);
        plNhaCC = getPanelTieuChi("Nhà cung cấp", chbNhaCC, txNhaCC, btnChonNhaCC);

        JPanel plLoaiThongKe = new JPanel();
        plLoaiThongKe.setBorder(BorderFactory.createTitledBorder("Loại thống kê"));
        plLoaiThongKe.add(cbChonLoaiThongKe);

        plNhaCC.setVisible(false); // khởi đầu là thống kê bán ra nên chưa cần khung chọn ncc
        cbChonLoaiThongKe.addActionListener((ae) -> {
            calculate();
            switch (cbChonLoaiThongKe.getSelectedItem().toString()) {
                case "Bán ra":
                    plKhachHang.setVisible(true);
                    plNhaCC.setVisible(false);
                    break;
                case "Nhập vào":
                    plKhachHang.setVisible(false);
                    plNhaCC.setVisible(true);
                    break;
            }
        });

        // add
        // panel chon tieu chi
        JPanel plChonTieuChi = new JPanel();
        plChonTieuChi.setPreferredSize(new Dimension(300, 800));
        plChonTieuChi.add(plLoaiThongKe);
        plChonTieuChi.add(plChonNgay);
        plChonTieuChi.add(plNhanVien);
        plChonTieuChi.add(plSanPham);
        plChonTieuChi.add(plKhachHang);
        plChonTieuChi.add(plNhaCC);
        plChonTieuChi.add(btnRefresh);

        tbTong.setPreferredSize(new Dimension(tbTong.getPreferredSize().width, 70));

        JPanel plContent = new JPanel();
        plContent.setLayout(new BorderLayout());
        plContent.add(tb, BorderLayout.CENTER);
        plContent.add(tbTong, BorderLayout.SOUTH);

        this.add(plChonTieuChi, BorderLayout.WEST);
        this.add(plContent, BorderLayout.CENTER);
    }

    private JPanel getPanelTieuChi(String name, JCheckBox chb, JTextField tx, MoreButton b) {
        JPanel result = new JPanel();
        result.setBorder(BorderFactory.createTitledBorder(name));
        tx.setBorder(BorderFactory.createTitledBorder(" "));

        addDocumentListener(tx);

        if (chb != null) {
            tx.setEnabled(false);
            b.setEnabled(false);

            chb.addActionListener((ae) -> {
                tx.setEnabled(chb.isSelected());
                b.setEnabled(chb.isSelected());
                calculate();
            });
            result.add(chb);
        }

        result.add(tx);
        result.add(b);

        return result;
    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                calculate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }
        });
    }

    private void calculate() {
        String loaiThongKe = cbChonLoaiThongKe.getSelectedItem().toString();

        switch (loaiThongKe) {
            case "Bán ra":
                calculateBanRa();
                break;
            case "Nhập vào":
                calculateNhapVao();
                break;
        }
    }

    private void calculateBanRa() {
        //NhanVienBanRa();
        SanPhamBanRa();
    }

    private void NhanVienBanRa() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã", "Tên", "Số lượng bán ra", "Tổng tiền"});
//        tb.setAlignment(2, JLabel.CENTER);
//        tb.setAlignment(3, JLabel.RIGHT);

        int tongAllSoLuong = 0;
        float tongAllTien = 0;
        int soluongNV = 0;

        for (NhanVien nv : qlnvBUS.getDSNV()) {
            if (!txNhanVien.getText().equals("") && !nv.getMaNV().equals(txNhanVien.getText())) {
                continue;
            }

            int tongsoluong = 0;
            float tongtien = 0;
            MyCheckDate mcd = new MyCheckDate(txNgay1, txNgay2);
            for (HoaDon hd : qlhdBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                if (hd.getMaNV().equals(nv.getMaNV())) {
                    tongtien += hd.getTongTien();
                    for (ChiTietHoaDon cthd : qlcthdBUS.getChiTietHoaDon(hd.getMaHD())) {
                        tongsoluong += cthd.getSoLuong();
                    }
                }
            }

            tongAllSoLuong += tongsoluong;
            tongAllTien += tongtien;

            tb.addRow(new String[]{
                nv.getMaNV(),
                nv.getTenNV(),
                String.valueOf(tongsoluong),
                PriceFormatter.format(tongtien)
            });

            soluongNV++;
        }

        tbTong.clear();
        tbTong.setHeaders(new String[]{"TỔNG TẤT CẢ", "", "", ""});
        tbTong.addRow(new String[]{"",
            String.valueOf(soluongNV) + " nhân viên",
            String.valueOf(tongAllSoLuong) + " sản phẩm",
            PriceFormatter.format(tongAllTien)
        });
    }

    private void SanPhamBanRa() {
        tb.clear();
        tb.setHeaders(new String[]{"Loại", "Mã sản phẩm", "Tên sản phẩm", "Số lượng bán ra", "Tổng tiền"});
//        tb.setAlignment(3, JLabel.CENTER);
//        tb.setAlignment(4, JLabel.RIGHT);

        int tongAllSoLuong = 0;
        float tongAllTien = 0;

        for (LoaiSanPham lsp : qllspBUS.getDSLSP()) {
            
            tb.addRow(new String[] {lsp.getTenLSP().toUpperCase(), "", "", "", ""});
            
            for (SanPham sp : qlspBUS.getDSSP()) {
                if (!txSanPham.getText().equals("") && !sp.getMaSP().equals(txSanPham.getText())
                        || !sp.getMaLSP().equals(lsp.getMaLSP())) {
                    continue;
                }

                int tongsoluong = 0;
                float tongtien = 0;
                MyCheckDate mcd = new MyCheckDate(txNgay1, txNgay2);
                for (HoaDon hd : qlhdBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                    for (ChiTietHoaDon cthd : qlcthdBUS.getChiTietHoaDon(hd.getMaHD())) {
                        if (cthd.getMaSP().equals(sp.getMaSP())) {
                            tongsoluong += cthd.getSoLuong();
                            tongtien += cthd.getDonGia() * cthd.getSoLuong();
                        }
                    }
                }

                tongAllSoLuong += tongsoluong;
                tongAllTien += tongtien;

                tb.addRow(new String[]{
                    "",
                    sp.getMaSP(),
                    sp.getTenSP(),
                    String.valueOf(tongsoluong),
                    PriceFormatter.format(tongtien)
                });
            }
            
            tb.addRow(new String[] {"", "", "", "", ""});
        }

        tbTong.clear();
        tbTong.setHeaders(new String[]{"TỔNG TẤT CẢ", "", "", "", ""});
        tbTong.addRow(new String[]{"", "", "",
            String.valueOf(tongAllSoLuong) + " sản phẩm",
            PriceFormatter.format(tongAllTien)
        });
    }

    private void calculateNhapVao() {
    }
}
