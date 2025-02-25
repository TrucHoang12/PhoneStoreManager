/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.ChucNang;

import BUS.QuanlykhuyenmaiBUS;
import BUS.QuanlyloaisanphamBUS;
import BUS.QuanlysanphamBUS;
import DTO.LoaiSanPham;
import DTO.SanPham;
import EXEL.DocExcel;
import EXEL.XuatExcel;
import GUI.Form.FormThemSuaSanPham;
import static GUI.GiaoDien.GiaoDienGUI.panelMain;
import GUI.GiaoDien.LoginGUI_V1;
import GUI.GiaoDien.Table;
import GUI.GiaoDien.myTableCellRenderer;
import GUI.Panel.SearchFromTo;
import GUI.Panel.SearchPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author MSI
 */
public class SanPhamGUI extends JPanel {

    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;

    private JButton btnXuatExcel;
    private JButton btnNhapExcel;
    private JButton btnLamMoi;

    private SearchPanel pnSearch;
    private SearchFromTo pnSoLuongSearch;
    private SearchFromTo pnDonGiaSearch;

    private Table tb;
    
    private JPanel ActionPanel;

    private int i = 1;

    private final QuanlyloaisanphamBUS qllspBUS = new QuanlyloaisanphamBUS();
    private final QuanlysanphamBUS qlspBUS = new QuanlysanphamBUS();

    public SanPhamGUI() {
        setBackground(Color.white);
        setLayout(new BorderLayout());

        ActionPanel = new JPanel();
        ActionPanel.setBackground(Color.white);
        ActionPanel.setLayout(new GridLayout(2, 1, 10, 10));
        ActionPanel.setPreferredSize(new Dimension(getWidth(), 200));
        add(ActionPanel, BorderLayout.NORTH);
        InitButtonAction();
        InitSearchContent();
        InitTable();
        AddAllButtonEvent();
        AddAllSearchEvent();
    }

    private void InitButtonAction() {
        String dsquyen = LoginGUI_V1.currentQuyen.getChiTietQuyen();
        btnThem = createButton("Thêm", "/com/PhoneStoreManager/image/signs-1.png");
        btnXoa = createButton("Xóa", "/com/PhoneStoreManager/image/button-delete.png");
        btnSua = createButton("Sửa", "/com/PhoneStoreManager/image/edit-tools.png");

        btnXuatExcel = createButton("Xuất Excel", "/com/PhoneStoreManager/image/excel.png");
        btnNhapExcel = createButton("Nhập Excel", "/com/PhoneStoreManager/image/excel.png");
        btnLamMoi = createButton("Làm mới", "/com/PhoneStoreManager/image/internet.png");
        
        if (dsquyen.contains("xemSanPham")) {
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
        }
        applyButtonHoverEffect(btnThem);
        applyButtonHoverEffect(btnXoa);
        applyButtonHoverEffect(btnSua);
        applyButtonHoverEffect(btnXuatExcel);
        applyButtonHoverEffect(btnNhapExcel);
        applyButtonHoverEffect(btnLamMoi);
        
        JPanel panelActionTop = new JPanel();
        panelActionTop.setBackground(Color.white);
        panelActionTop.setLayout(new GridLayout(1,6,10,10));
        panelActionTop.setBorder(new EmptyBorder(0, 50, 0, 50));
        panelActionTop.add(btnThem);
        panelActionTop.add(btnXoa);
        panelActionTop.add(btnSua);
        panelActionTop.add(btnXuatExcel);
        panelActionTop.add(btnNhapExcel);
        panelActionTop.add(btnLamMoi);
        ActionPanel.add(panelActionTop);
    }

    private void InitSearchContent() {

        // Thêm Panel tìm kiếm LSP
        pnSearch = new SearchPanel("GIAODIEN");
        pnSearch.setComboBox(qllspBUS.getComboboxSearch);
        
        pnSearch.setSizePanel(0,0, true);
        
        // Them Panel tìm kiếm số lượng 
        pnSoLuongSearch = new SearchFromTo("GIAODIEN", "Số Lượng");
        pnSoLuongSearch.setSizePanel(0, 150);

        // Them Panel tìm kiếm Đơn giá
        pnDonGiaSearch = new SearchFromTo("GIAODIEN", "Đơn giá");
        pnDonGiaSearch.setSizePanel(0, 300);

        JPanel panelActionBottom = new JPanel();
        panelActionBottom.setBackground(Color.white);
        panelActionBottom.setLayout(new GridLayout(1,3,10,10));
        panelActionBottom.setBorder(new EmptyBorder(0, 50, 0, 50));
        panelActionBottom.add(pnSearch);
        panelActionBottom.add(pnSoLuongSearch);
        panelActionBottom.add(pnDonGiaSearch);
        ActionPanel.add(panelActionBottom);
    }

    private void InitTable() {
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        this.tb = new Table();
        this.tb.setHeaders(qlspBUS.getHeaders);
        this.tb.jTable1.getColumn("Hình ảnh").setCellRenderer(new myTableCellRenderer());
        this.tb.jTable1.getColumn("Hình ảnh").setMaxWidth(120);
        this.tb.jTable1.getColumn("Hình ảnh").setMinWidth(95);
        int i1 = 0;
        for (SanPham q : qlspBUS.getDSSP()) {
            ++i1;
            ImageIcon img = new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/product/" + q.getHinhAnh()));
            Image imgScaled = img.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(imgScaled));
            this.tb.addRow(new Object[]{Integer.toString(i1), q.getMaSP(), q.getMaLSP(), q.getTenSP(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia())), Quanlykhuyenmaibus.ToCurrent(Integer.toString(q.getSoLuong())), label, (q.getTrangThai() == 1) ? "Hiện" : "Ẩn"});
        }
        this.tb.setRowHeigth(60);
        this.tb.resizeColumnWidth();
        add(tb, BorderLayout.CENTER);
        validate();
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

    private void AddAllButtonEvent() {
        // Nút thêm SP
        btnThem.addActionListener((ActionEvent e) -> {
            AddProduct();
        });

        // Nút xóa SP
        btnXoa.addActionListener((ActionEvent e) -> {
            DeleteProduct();
        });

        // Nút sửa SP
        btnSua.addActionListener((ActionEvent e) -> {
            EditProduct();
        });

        // Nút Xuất Excel SP
        btnXuatExcel.addActionListener((ActionEvent e) -> {
            new XuatExcel().xuatFileExcelSanPham();
        });

        // Nút Nhập Excel SP
        btnNhapExcel.addActionListener((ActionEvent e) -> {
            new DocExcel().DocExcelSP();
            resetForm();
        });

        // Nút làm mới SP
        btnLamMoi.addActionListener((ActionEvent e) -> {
            resetForm();
        });
    }

    private void AddAllSearchEvent() {
        pnSearch.ComboBoxSearch.addActionListener((ActionEvent e) -> {
            pnSearch.EventSearchLSPforGiaoDien(this.tb);
        });
        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                pnSearch.EventSearchLSPforGiaoDien(tb);
            }
        });
        pnSoLuongSearch.btnTim.addActionListener((ActionEvent e) -> {
            pnDonGiaSearch.txtFrom.setText("");
            pnDonGiaSearch.txtFrom.setText("");
            pnSearch.txtSearch.setText("");
            pnSoLuongSearch.SearchSoLuongSanPham();
        });
        pnDonGiaSearch.btnTim.addActionListener((ActionEvent e) -> {
            pnSoLuongSearch.txtFrom.setText("");
            pnSoLuongSearch.txtTo.setText("");
            pnSearch.txtSearch.setText("");
            pnDonGiaSearch.SearchDonGiaSanPham();
        });
    }

    private void AddProduct() {
        QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
        String MaSP = Quanlysanphambus.MaSPnotHave();
        new FormThemSuaSanPham(MaSP).setVisible(true);
    }

    private void EditProduct() {
        if (this.tb.getSelectedRow() >= 0) {
            String MaSP = this.tb.LayMaSPTaiTable(i).toString();
            SanPham q = qlspBUS.getSanPham(MaSP);
            new FormThemSuaSanPham(q, Integer.parseInt(this.tb.getValueSTT(i).toString())).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(new JRootPane(), "Bạn chưa chọn sản phẩm cần sữa");
        }
    }

    private void DeleteProduct() {
        int i1 = this.tb.getSelectedRow();
        if (i1 >= 0) {
            int k = JOptionPane.showConfirmDialog(new JRootPane(), "Bạn có chắc xóa sản phẩm này không?");
            if (k != JOptionPane.YES_OPTION) {
                return;
            }
            String MaSP = this.tb.LayMaSPTaiTable(i1).toString();
            if (qlspBUS.deleteSanPham(MaSP)) {
                this.tb.removeRow(MaSP);
                JOptionPane.showMessageDialog(new JRootPane(), "Xóa sản phẩm thành công");
            } else {
                JOptionPane.showMessageDialog(new JRootPane(), "Xóa sản phẩm thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(new JRootPane(), "Bạn chưa chọn sản phẩm cần xóa");
        }
    }

    private void resetForm() {
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
        remove(this.tb);
        this.tb = new Table();
        this.tb.setHeaders(qlspBUS.getHeaders);
        this.tb.jTable1.getColumn("Hình ảnh").setCellRenderer(new myTableCellRenderer());
        this.tb.jTable1.getColumn("Hình ảnh").setMaxWidth(120);
        this.tb.jTable1.getColumn("Hình ảnh").setMinWidth(95);
        int i1 = 0;
        for (SanPham q : qlspBUS.getDSSP()) {
            ++i1;
            ImageIcon img = new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/product/" + q.getHinhAnh()));
            Image imgScaled = img.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(imgScaled));
            tb.addRow(new Object[]{Integer.toString(i1), q.getMaSP(), q.getMaLSP(), q.getTenSP(), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia())), Quanlykhuyenmaibus.ToCurrent(Integer.toString(q.getSoLuong())), label, (q.getTrangThai() == 1) ? "Hiện" : "Ẩn"});
        }
        this.tb.setRowHeigth(60);
        this.tb.resizeColumnWidth();
        add(this.tb, BorderLayout.CENTER);
        validate();
    }

}
