package GUI.Form;

import BUS.QuanlykhuyenmaiBUS;
import BUS.QuanlysanphamBUS;
import DTO.SanPham;
import GUI.GiaoDien.GiaoDienGUI;
import static GUI.GiaoDien.GiaoDienGUI.tb;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FormThemSuaSanPham extends javax.swing.JFrame {

    //---tạo các thuộc tính cần thiết cho frame
    private javax.swing.JButton btnThemSua;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkboxAn;
    private javax.swing.JCheckBox checkboxHien;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelChonMaLSP;
    private javax.swing.JLabel labelDonGia;
    private javax.swing.JLabel labelMaLSP;
    private javax.swing.JLabel labelMaSP;
    private javax.swing.JLabel labelSoLuong;
    private javax.swing.JLabel labelTenFileAnh;
    private javax.swing.JLabel labelTenSP;
    private javax.swing.JLabel labelTrangThai;
    private javax.swing.JLabel labelforder;
    private javax.swing.JTextField txtDonGia;
    public static javax.swing.JTextField txtMaLSP;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenFileAnh;
    private javax.swing.JTextField txtTenSP;
    private int row = 0;
    private String url = "";
    private String img = "";

    public FormThemSuaSanPham(String MaSP) {
        initComponents();
        //--Thêm hàm tạo giao diện
        InitComponentsForm();
        //------
        txtMaSP.setText(MaSP);
        checkboxHien.setSelected(true);
        checkboxAn.setSelected(false);
    }

    public FormThemSuaSanPham(SanPham sp, int k) {
        initComponents();
        //--Thêm hàm tạo giao diện
        InitComponentsForm();
        //------

        txtMaSP.setText(sp.getMaSP());
        txtMaLSP.setText(sp.getMaLSP());
        txtTenSP.setText(sp.getTenSP());
        txtDonGia.setText(Long.toString(sp.getDonGia()));
        txtSoLuong.setText(Integer.toString(sp.getSoLuong()));
        txtTenFileAnh.setText(sp.getHinhAnh());
        img = sp.getHinhAnh();
        if (sp.getTrangThai() == 1) {
            checkboxHien.setSelected(true);
        } else {
            checkboxAn.setSelected(true);
        }

        txtMaSP.setEditable(false);
        btnThemSua.setText("Sửa");
        btnThemSua.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/edit-tools.png")));
        row = k;
    }

    private void InitComponentsForm() {
        //--Thiết lập các thuộc tính cho frame
        getContentPane().setPreferredSize(new Dimension(600, 600));
        setPreferredSize(new Dimension(600, 600));
        setBounds(0, 0, 500, 600);
        setTitle("Sửa sản phẩm");
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/edit-tools.png")).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        //---Cấp phát các thuộc tính của frame
        buttonGroup1 = new javax.swing.ButtonGroup();
        labelMaSP = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        labelMaLSP = new javax.swing.JLabel();
        txtMaLSP = new javax.swing.JTextField();
        labelChonMaLSP = new javax.swing.JLabel();
        labelTenSP = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        labelDonGia = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        labelSoLuong = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        labelTenFileAnh = new javax.swing.JLabel();
        txtTenFileAnh = new javax.swing.JTextField();
        labelforder = new javax.swing.JLabel();
        labelTrangThai = new javax.swing.JLabel();
        checkboxHien = new javax.swing.JCheckBox();
        checkboxAn = new javax.swing.JCheckBox();
        btnThemSua = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        //--Tạo panel chính chứa toàn bộ components của form
        JPanel contentPN = new JPanel();
        //--Thiết lập BorderLayout cho panel chính
        contentPN.setLayout(new BorderLayout(10, 10));

        //--Tạo panel top chứa toàn bộ components nhập thông tin sản phẩm của form
        JPanel contentTopPN = new JPanel();
        //--Thiết lập BorderLayout cho panel trên
        contentTopPN.setLayout(new BorderLayout(10, 10));
        
        //--Tạo panel bottom chứa button xử lý của form
        JPanel contentBottomPN = new JPanel();
        //--Thiết lập BorderLayout cho panel trên
        contentBottomPN.setLayout(new BorderLayout());

        //--Tạo panel top left toàn bộ components label của form
        JPanel contentTopLeftPN = new JPanel();
        //--Thiết lập GridLayout với 7 dòng cột khoảng cách giữa các phần tử là 10,10 cho panel trên
        contentTopLeftPN.setLayout(new GridLayout(7, 1, 10, 10));
        
        //--Tạo panel top right toàn bộ components nhập liệu của form
        JPanel contentTopRightPN = new JPanel();
        //--Thiết lập BorderLayout cho panel trên
        contentTopRightPN.setLayout(new BorderLayout());

        //--Tạo panel chính giữa của panel top right chứa toàn bộ components textfield và checkbox của form
        JPanel contentTopRightCenterPN = new JPanel();
        //--Thiết lập GridLayout với 7 dòng cột khoảng cách giữa các phần tử là 10,10 cho panel trên
        contentTopRightCenterPN.setLayout(new GridLayout(7, 1, 10, 10));

         //--Tạo panel bên trái của panel top right chứa toàn bộ components xử lý chọn thông tin của form
        JPanel contentTopRightEastPN = new JPanel();
        //--Thiết lập GridLayout với 7 dòng cột khoảng cách giữa các phần tử là 10,10 cho panel trên
        contentTopRightEastPN.setLayout(new GridLayout(7, 1, 10, 10));

        //--Thiết lập Font chữ cho các label và textfield
        labelMaSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelMaSP.setText("Mã sản phẩm");
        
        labelMaLSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelMaLSP.setText("Mã loại sản phẩm");
        
        labelTenSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelTenSP.setText("Tên sản phẩm");

        labelDonGia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelDonGia.setText("Đơn giá");
        
        labelSoLuong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelSoLuong.setText("Số lượng");
        
        labelTenFileAnh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelTenFileAnh.setText("Tên file ảnh");

        labelforder.setBorder(new EmptyBorder(0, 10, 0, 10));
        labelforder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        labelTrangThai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelTrangThai.setText("Trạng thái");

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaLSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDonGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenFileAnh.setEditable(false);
        txtTenFileAnh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        labelChonMaLSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        //--Canh giữa text
        labelChonMaLSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelChonMaLSP.setText("...");
        
        //--Tạo viền cho label chọn loại sản phẩm
        labelChonMaLSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        //--Sử lý sự kiện khi chuyển chuột và nhấn chuột vào label chọn loại sản phẩm
        labelChonMaLSP.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelChonMaLSPMouseClicked();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelChonMaLSPMouseEntered();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelChonMaLSPMouseExited();
            }
        });

        txtDonGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDonGiaKeyTyped(evt);
            }
        });


        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyTyped(evt);
            }
        });

        labelforder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/folder.png"))); // NOI18N
        labelforder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        //--Sử lý sự kiện khi chuyển chuột và nhấn chuột vào label chọn folder
        labelforder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelforderMouseClicked();
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelforderMouseEntered();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelforderMouseExited();
            }
        });

        //--Thiết lập button và checkbox
        buttonGroup1.add(checkboxHien);
        checkboxHien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        checkboxHien.setText("Hiện");

        buttonGroup1.add(checkboxAn);
        checkboxAn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        checkboxAn.setText("Ẩn");

        btnThemSua.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/signs-1.png"))); // NOI18N
        btnThemSua.setText("Thêm");
        //--Batứ sự kiện nhấn vào button
        btnThemSua.addActionListener((java.awt.event.ActionEvent evt) -> {
            btnThemSuaActionPerformed();
        });

        jLabel2.setText("VND");
        
        //--Add các components vào các phần tương ứng
        contentPN.add(contentTopPN, BorderLayout.CENTER);
        contentPN.add(contentBottomPN, BorderLayout.SOUTH);
        contentTopPN.add(contentTopLeftPN, BorderLayout.WEST);
        contentTopPN.add(contentTopRightPN, BorderLayout.CENTER);
        
        contentTopRightPN.add(contentTopRightCenterPN, BorderLayout.CENTER);
        contentTopRightPN.add(contentTopRightEastPN, BorderLayout.EAST);
        
        contentTopLeftPN.add(labelMaSP);
        contentTopLeftPN.add(labelMaLSP);
        contentTopLeftPN.add(labelTenSP);
        contentTopLeftPN.add(labelDonGia);
        contentTopLeftPN.add(labelSoLuong);
        contentTopLeftPN.add(labelTenFileAnh);
        contentTopLeftPN.add(labelTrangThai);

        contentTopRightCenterPN.add(txtMaSP);
        contentTopRightCenterPN.add(txtMaLSP);
        contentTopRightCenterPN.add(txtTenSP);
        contentTopRightCenterPN.add(txtDonGia);
        contentTopRightCenterPN.add(txtSoLuong);
        contentTopRightCenterPN.add(txtTenFileAnh);

        JPanel contentRDB_PN = new JPanel();
        contentRDB_PN.setLayout(new GridLayout(1, 2, 4, 4));
        contentRDB_PN.add(checkboxHien);
        contentRDB_PN.add(checkboxAn);
        contentTopRightCenterPN.add(contentRDB_PN);

        contentTopRightEastPN.add(new JPanel());
        contentTopRightEastPN.add(labelChonMaLSP);
        contentTopRightEastPN.add(new JPanel());
        contentTopRightEastPN.add(jLabel2);
        contentTopRightEastPN.add(labelforder);
        contentTopRightEastPN.add(new JPanel());
        
        contentBottomPN.setLayout(new GridLayout(1, 3));
        contentBottomPN.add(new JPanel());
        contentBottomPN.add(btnThemSua);
        contentBottomPN.add(new JPanel());
        contentPN.setBorder(new EmptyBorder(20, 20, 20, 20));
        

        add(contentPN, BorderLayout.CENTER);
    }

    private void labelforderMouseEntered() {
        labelforder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 255)));
    }

    private void labelforderMouseExited() {
        labelforder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    }

    private void labelforderMouseClicked() {
        FileDialog fd = new FileDialog(this);
        fd.setTitle("Chọn ảnh");
        fd.setVisible(true);
        String filename = fd.getFile();
        if (filename != null) {
            if (filename.contains(".png") || filename.contains(".jpg")) {
                txtTenFileAnh.setText(filename);
                url = fd.getDirectory() + fd.getFile();
                System.out.println(fd.getDirectory() + fd.getFile());
            }
        }
    }

    private void labelChonMaLSPMouseEntered() {
        labelChonMaLSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 255)));
    }

    private void labelChonMaLSPMouseExited() {
        labelChonMaLSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    }

    private void txtDonGiaKeyTyped(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9'))
                && (caracter != '\b')) {
            evt.consume();
        }
    }

    private void txtSoLuongKeyTyped(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9'))
                && (caracter != '\b')) {
            evt.consume();
        }
    }

    private void btnThemSuaActionPerformed() {
        String MaSP = txtMaSP.getText().trim();
        String MaLSP = txtMaLSP.getText().trim();
        String TenSP = txtTenSP.getText().trim();
        String DonGia = txtDonGia.getText().trim();
        String SoLuong = txtSoLuong.getText().trim();
        String TenAnh = txtTenFileAnh.getText().trim();
        String TrangThai = "";
        if (checkboxHien.isSelected()) {
            TrangThai = "Hiện";
        } else if (checkboxAn.isSelected()) {
            TrangThai = "Ẩn";
        }
        String type = btnThemSua.getText();
        if (type.equals("Thêm")) {
            boolean t = true;
            if (MaSP.equals("")) {
                t = false;
            }
            if (MaLSP.equals("")) {
                t = false;
            }
            if (TenSP.equals("")) {
                t = false;
            }
            if (DonGia.equals("")) {
                t = false;
            }
            if (SoLuong.equals("")) {
                t = false;
            }
            if (TenAnh.equals("")) {
                t = false;
            }
            if (TrangThai.equals("")) {
                t = false;
            }

            if (!t) {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại. Hãy kiểm tra lại các trường dữ liệu..!");
                return;
            }

            if (!Pattern.matches("SP[\\d]{1,}", MaSP)) {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại. Mã sản phẩm sai quy tắc..!");
                return;
            }

            if (!Pattern.matches("LSP[\\d]{1,}", MaLSP)) {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại. Mã loại sản phẩm sai quy tắc..!");
                return;
            }

            if (!Pattern.matches("[\\d]{1,}", DonGia)) {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại. Đơn giá phải là số..!");
                return;
            }

            if (!Pattern.matches("[\\d]{1,}", SoLuong)) {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại. Đơn giá phải là số..!");
                return;
            }

            if (Long.parseLong(DonGia) == 0) {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại. Đơn giá phải lớn hơn không..!");
                return;
            }

            if (Long.parseLong(SoLuong) == 0) {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại. Số lượng phải lớn hơn không..!");
                return;
            }
            //Sau khi non- Static

            QuanlysanphamBUS quanlysanphambus = new QuanlysanphamBUS();
            SanPham sp = quanlysanphambus.getSanPham(MaSP);

            //-------------------------------------------------------------------------
            if (sp != null) {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại. Mã sản phẩm đã tồn tại..!");
                return;
            }
            sp = new SanPham(MaSP, MaLSP, TenSP, Long.parseLong(DonGia), Integer.parseInt(SoLuong), TenAnh, (TrangThai.equals("Hiện")) ? 1 : 0);
            QuanlysanphamBUS qlsp = new QuanlysanphamBUS();
            QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
            if (qlsp.addSanPham(sp)) {
                if (SaveImg(TenAnh)) {
                    JOptionPane.showMessageDialog(rootPane, "Thêm thành công");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
                    return;
                }
                ImageIcon img1 = new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/product/" + TenAnh));
                Image imgScaled = img1.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
                JLabel label = new JLabel();
                label.setIcon(new ImageIcon(imgScaled));
                tb.addRow(new Object[]{Integer.toString(GiaoDienGUI.tb.getRowCount() + 1), MaSP, MaLSP, TenSP, Quanlykhuyenmaibus.ToCurrent(DonGia),
                    Quanlykhuyenmaibus.ToCurrent(SoLuong), label, TrangThai
                });

            } else {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
            }
            this.dispose();
        } else {
            boolean t = true;
            if (MaLSP.equals("")) {
                t = false;
            }
            if (TenSP.equals("")) {
                t = false;
            }
            if (DonGia.equals("")) {
                t = false;
            }
            if (SoLuong.equals("")) {
                t = false;
            }
            if (TenAnh.equals("")) {
                t = false;
            }
            if (TrangThai.equals("")) {
                t = false;
            }

            if (!t) {
                JOptionPane.showMessageDialog(rootPane, "Sửa thất bại. Hãy kiểm tra lại các trường dữ liệu..!");
                return;
            }

            if (!Pattern.matches("LSP[\\d]{1,}", MaLSP)) {
                JOptionPane.showMessageDialog(rootPane, "Sửa thất bại. Mã loại sản phẩm sai quy tắc..!");
                return;
            }

            if (!Pattern.matches("[\\d]{1,}", DonGia)) {
                JOptionPane.showMessageDialog(rootPane, "Sửa thất bại. Đơn giá phải là số..!");
                return;
            }

            if (!Pattern.matches("[\\d]{1,}", SoLuong)) {
                JOptionPane.showMessageDialog(rootPane, "Sửa thất bại. Đơn giá phải là số..!");
                return;
            }

            if (Long.parseLong(DonGia) == 0) {
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại. Đơn giá phải lớn hơn không..!");
                return;
            }

            if (Long.parseLong(SoLuong) == 0) {
                JOptionPane.showMessageDialog(rootPane, "Sửa thất bại. Số lượng phải lớn hơn không..!");
                return;
            }
            QuanlysanphamBUS qlsp = new QuanlysanphamBUS();
            QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();
            if (qlsp.repairSanPham(MaSP, MaLSP, TenSP, Long.parseLong(DonGia), Integer.parseInt(SoLuong), TenAnh, (TrangThai.equals("Hiện")) ? 1 : 0)) {
                if (DeleteAnh(img) && SaveImg(TenAnh)) {
                    JOptionPane.showMessageDialog(rootPane, "Sửa thất bại");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Sửa thành công");
                    return;
                }
                ImageIcon img1 = new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/product/" + TenAnh));
                Image imgScaled = img1.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
                JLabel label = new JLabel();
                label.setIcon(new ImageIcon(imgScaled));
                GiaoDienGUI.tb.setValueRow(MaSP, new Object[]{
                    Integer.toString(row), MaSP, MaLSP, TenSP, Quanlykhuyenmaibus.ToCurrent(DonGia),
                    Quanlykhuyenmaibus.ToCurrent(SoLuong), label, TrangThai
                });

            } else {
                JOptionPane.showMessageDialog(rootPane, "Sửa thất bại");
            }
            this.dispose();
        }
    }

    private boolean SaveImg(String url2) {
        if (url.equals("")) {
            return false;
        }
        File source = new File(url);
        File dest = new File("src/com/PhoneStoreManager/image/product/" + url2);
        InputStream is = null;
        OutputStream os = null;
        boolean t = true;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = is.read(buffer)) > 0) {

                os.write(buffer, 0, length);
            }
            t = true;
        } catch (IOException e) {
            t = false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }

                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                t = false;
            }
        }
        return t;
    }

    private boolean DeleteAnh(String anh) {
        boolean t = true;
        try {
            File file = new File("src/com/PhoneStoreManager/image/product/" + anh);
            t = file.delete();
        } catch (Exception e) {
            t = false;
        }
        return t;
    }

    private void labelChonMaLSPMouseClicked() {
        new FormChon("ChonMaLoaiSanPham").setVisible(true);
    }
    // </editor-fold>
    // Variables declaration - do not modify
    // End of variables declaration

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
