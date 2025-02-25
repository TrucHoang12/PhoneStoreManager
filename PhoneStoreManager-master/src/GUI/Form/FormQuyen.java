package GUI.Form;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import GUI.Form.FormChon;
import DTO.NhanVien;
import BUS.QuanlynhanvienBUS;
import BUS.QuanlyphieunhapBUS;
import BUS.QuanlyquyenBUS;
import DTO.Quyen;
import DTO.TaiKhoan;
import BUS.XulyTaiKhoanBUS;
import GUI.GiaoDien.GiaoDienGUI;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class FormQuyen extends JFrame {

    private int row = 0;
    private JButton btnExit;

    public FormQuyen() {
        initComponents();
        setTitle("Thêm Quyền");
        setSize(500, 400);
        setBackground(Color.WHITE);
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/signs-1.png")).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public FormQuyen(TaiKhoan tk, int k) {
        initComponents();
        setTitle("Xem Quyền");
        setSize(500, 400);
        setBackground(Color.WHITE);
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/eye.png")).getImage());
        btnExit.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/exitt.png")));
        txtTenTaiKhoan.setText(tk.getTenTaiKhoan());
        txtMaNV.setText(tk.getMaNV());
        txtMaQuyen.setText(tk.getMaQuyen());
        txtMatKhau.setText(tk.getMatKhau());
        btnExit.setText("Thoát");
        txtTenTaiKhoan.setEditable(false);
        row = k;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {

        setLayout(null); // Sử dụng layout kiểu null để có thể sử dụng setBounds()
                setBackground(Color.WHITE);


        labelTenTaiKhoan = new JLabel("Tên tài khoản");
        labelTenTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTenTaiKhoan = new JTextField();
        txtTenTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 16));

        labelMatKhau = new JLabel("Mật khẩu");
        labelMatKhau.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMatKhau = new JPasswordField();
        txtMatKhau.setFont(new Font("Arial", Font.PLAIN, 16));

        labelMaNV = new JLabel("Mã nhân viên");
        labelMaNV.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMaNV = new JTextField();
        txtMaNV.setFont(new Font("Arial", Font.PLAIN, 16));

        labelMaQuyen = new JLabel("Mã quyền");
        labelMaQuyen.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMaQuyen = new JTextField();
        txtMaQuyen.setFont(new Font("Arial", Font.PLAIN, 16));

//
//        labelMoreMaQuyen = new JLabel("...");
//        labelMoreMaQuyen.setFont(new Font("Arial", Font.PLAIN, 16));

        btnExit = new JButton("Thêm");
        btnExit.setFont(new Font("Arial", Font.BOLD, 16));

        // Đặt vị trí và kích thước của các thành phần
        labelTenTaiKhoan.setBounds(20, 20, 120, 30);
        txtTenTaiKhoan.setBounds(160, 20, 300, 30);

        labelMatKhau.setBounds(20, 70, 120, 30);
        txtMatKhau.setBounds(160, 70, 300, 30);

        labelMaNV.setBounds(20, 120, 120, 30);
        txtMaNV.setBounds(160, 120, 300, 30);
//        labelMoreNV.setBounds(470, 120, 30, 30);

        labelMaQuyen.setBounds(20, 170, 120, 30);
        txtMaQuyen.setBounds(160, 170, 300, 30);
//        labelMoreMaQuyen.setBounds(470, 170, 30, 30);

        btnExit.setBounds(180, 240, 120, 40);
        
        
                btnExit.addActionListener(e -> Exitt());


        // Thêm các thành phần vào frame
        add(labelTenTaiKhoan);
        add(txtTenTaiKhoan);
        add(labelMatKhau);
        add(txtMatKhau);
        add(labelMaNV);
        add(txtMaNV);
//        add(labelMoreNV);
        add(labelMaQuyen);
        add(txtMaQuyen);
//        add(labelMoreMaQuyen);
        add(btnExit);

        setVisible(true);
    }

    private void labelMoreNVMouseExited(java.awt.event.MouseEvent evt) {
        labelMoreNV.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void labelMoreNVMouseEntered(java.awt.event.MouseEvent evt) {
        labelMoreNV.setBorder(BorderFactory.createLineBorder(new Color(26, 115, 232)));
    }

    private void labelMoreMaQuyenMouseEntered(java.awt.event.MouseEvent evt) {
        labelMoreMaQuyen.setBorder(BorderFactory.createLineBorder(new Color(26, 115, 232)));
    }

    private void labelMoreMaQuyenMouseExited(java.awt.event.MouseEvent evt) {
        labelMoreMaQuyen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void labelMoreNVMousePressed(java.awt.event.MouseEvent evt) {
        new FormChon("ChonNhanVien").setVisible(true);
    }

    private void labelMoreMaQuyenMousePressed(java.awt.event.MouseEvent evt) {
        new FormChon("ChonMaQuyen").setVisible(true);
    }

    private void btnThemSuaActionPerformed(java.awt.event.ActionEvent evt) {
    this.dispose(); // Đóng form
    }

    private JLabel labelTenTaiKhoan;
    private JTextField txtTenTaiKhoan;
    private JLabel labelMatKhau;
    private JLabel labelMaNV;
    private JTextField txtMaNV;
    private JLabel labelMaQuyen;
    private JLabel labelMoreNV;
    private JTextField txtMaQuyen;
    private JLabel labelMoreMaQuyen;
    private JPasswordField txtMatKhau;

    private void Exitt() {
    this.dispose(); // Đóng form
    }
}


