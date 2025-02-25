package GUI.GiaoDien;




import BUS.Duytridangnhap;
import BUS.QuanlychitiethoadonBUS;
import BUS.QuanlychitietquyenBUS;
import DTO.NhanVien;
import BUS.QuanlynhanvienBUS;
import BUS.QuanlyquyenBUS;
import DTO.Quyen;
import BUS.XulyTaiKhoanBUS;
import DTO.ChiTietQuyen;
import DTO.TaiKhoan;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jdk.internal.org.jline.terminal.TerminalBuilder;

public class LoginGUI_V1 extends javax.swing.JFrame {

    private boolean userfocus = false;
    private boolean passfocus = false;
    public String z ;

    public static TaiKhoan currentUser;
    public static NhanVien currentNhanVien;
    public static Quyen currentQuyen;
        public static ChiTietQuyen currentChiTietQuyen;

    
    public static  Duytridangnhap user_duytri;

    public LoginGUI_V1() {
        initComponents();
        txtusername.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtpass.setBackground(new java.awt.Color(0, 0, 0, 1));
        user_duytri = new Duytridangnhap();

        initduytridangnhap();

        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    private void initduytridangnhap() {

        if (!user_duytri.getUsername().equals("")) {
            XulyTaiKhoanBUS xltkBUS = new XulyTaiKhoanBUS();
            TaiKhoan tk = xltkBUS.getTaiKhoan(user_duytri.getUsername().trim());
            if (tk != null) {
                QuanlynhanvienBUS qlnvBUS = new QuanlynhanvienBUS();
                NhanVien nv = qlnvBUS.getNhanVien(tk.getMaNV());
                if (nv != null) {
                    currentUser = tk;
                    currentNhanVien = nv;
                    
                    QuanlyquyenBUS qlquyenBUS = new QuanlyquyenBUS();
                    // set Đối tượng maQuyen cho DTO.Quyen
                    currentQuyen = qlquyenBUS.getQuyen(tk.getMaQuyen());
                    z = tk.getMaQuyen();
                    CheckBoxDuyTriDangNhap.setSelected(true);
                    txtusername.setText(tk.getTenTaiKhoan());
                    txtpass.setText(tk.getMatKhau());
                    userfocus = true;
                    passfocus = true;
                    return;
                }
            }
        }
    }
    
     
    

    
    //----------------------------------------------------------------------------------------------
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        Panel_img = new javax.swing.JPanel();
        lbImg = new javax.swing.JLabel();
        Panel_Login = new javax.swing.JPanel();
        lbExit = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbPass = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        txtusername = new javax.swing.JTextField();
        txtpass = new javax.swing.JPasswordField();
        CheckBoxDuyTriDangNhap = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/meo.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panel_img.setBackground(new java.awt.Color(255, 255, 255));

        lbImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/meo3_v1.png"))); // NOI18N
        lbImg.setText("jLabel1");

        javax.swing.GroupLayout Panel_imgLayout = new javax.swing.GroupLayout(Panel_img);
        Panel_img.setLayout(Panel_imgLayout);
        Panel_imgLayout.setHorizontalGroup(
            Panel_imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_imgLayout.createSequentialGroup()
                .addComponent(lbImg, javax.swing.GroupLayout.PREFERRED_SIZE, 587, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_imgLayout.setVerticalGroup(
            Panel_imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(Panel_img, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 470));

        Panel_Login.setBackground(new java.awt.Color(255, 255, 255));

        lbExit.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        lbExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/off.png.png"))); // NOI18N
        lbExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbExitMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Login");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Hello ! Let's get started");

        lbPass.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lbPass.setForeground(new java.awt.Color(0, 0, 0));
        lbPass.setText("Password");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("................................................................................................................................");

        lbUser.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lbUser.setForeground(new java.awt.Color(0, 0, 0));
        lbUser.setText("Username");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("................................................................................................................................");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/human.png"))); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/eye.png"))); // NOI18N
        jLabel11.setText("jLabel10");

        btnLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        txtusername.setBackground(new java.awt.Color(255, 255, 255));
        txtusername.setForeground(new java.awt.Color(0, 0, 0));
        txtusername.setBorder(null);
        txtusername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtusernameFocusGained(evt);
            }
        });
        txtusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtusernameActionPerformed(evt);
            }
        });

        txtpass.setBackground(new java.awt.Color(255, 255, 255));
        txtpass.setForeground(new java.awt.Color(0, 0, 0));
        txtpass.setText("jPasswordField1");
        txtpass.setBorder(null);
        txtpass.setPreferredSize(new java.awt.Dimension(69, 28));
        txtpass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtpassFocusGained(evt);
            }
        });
        txtpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpassActionPerformed(evt);
            }
        });

        CheckBoxDuyTriDangNhap.setBackground(new java.awt.Color(255, 255, 255));
        CheckBoxDuyTriDangNhap.setForeground(new java.awt.Color(0, 0, 0));
        CheckBoxDuyTriDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxDuyTriDangNhapActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Keep login");

        javax.swing.GroupLayout Panel_LoginLayout = new javax.swing.GroupLayout(Panel_Login);
        Panel_Login.setLayout(Panel_LoginLayout);
        Panel_LoginLayout.setHorizontalGroup(
            Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_LoginLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbExit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(Panel_LoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_LoginLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_LoginLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_LoginLayout.createSequentialGroup()
                                .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtpass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Panel_LoginLayout.createSequentialGroup()
                                .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10))
                            .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbPass, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))
                    .addGroup(Panel_LoginLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 14, Short.MAX_VALUE))))
            .addGroup(Panel_LoginLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(CheckBoxDuyTriDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_LoginLayout.setVerticalGroup(
            Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_LoginLayout.createSequentialGroup()
                .addComponent(lbExit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_LoginLayout.createSequentialGroup()
                        .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_LoginLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbPass, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_LoginLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel7))
                            .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CheckBoxDuyTriDangNhap))
                        .addGap(61, 61, 61)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(Panel_LoginLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(Panel_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 440, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {                                     
        this.setState(JFrame.ICONIFIED);
    }  
        
  
 
    
    private void lbExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lbExitMouseClicked

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
XulyTaiKhoanBUS xltkBUS = new XulyTaiKhoanBUS();
        TaiKhoan tk = xltkBUS.getTaiKhoan(txtusername.getText().trim(), txtpass.getText().trim());
        if(tk != null){
            if(CheckBoxDuyTriDangNhap.isSelected()){
                user_duytri.write(tk.getTenTaiKhoan());
            }
            QuanlynhanvienBUS qlnvBUS = new QuanlynhanvienBUS();
            NhanVien nv = qlnvBUS.getNhanVien(tk.getMaNV());
            if(nv != null){
                currentUser = tk;
                currentNhanVien = nv;
                QuanlyquyenBUS qlquyenBUS = new QuanlyquyenBUS();
                
                QuanlychitietquyenBUS qlchitietquyenBUS = new QuanlychitietquyenBUS();
                currentQuyen = qlquyenBUS.getQuyen(tk.getMaQuyen());
                
                currentChiTietQuyen = qlchitietquyenBUS.getQuyen(tk.getMaQuyen());
                
                new GiaoDienGUI().setVisible(true);
                this.dispose();
            }
        }else {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu sai!");
        }    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusernameActionPerformed

    private void txtpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpassActionPerformed

    private void CheckBoxDuyTriDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxDuyTriDangNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxDuyTriDangNhapActionPerformed

    private void txtusernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtusernameFocusGained
        if (!userfocus) {
            txtusername.setText("");
        }
        userfocus = true;
    }//GEN-LAST:event_txtusernameFocusGained

    private void txtpassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtpassFocusGained
  if (!passfocus) {
            txtpass.setText("");
        }
        passfocus = true;    }//GEN-LAST:event_txtpassFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Lo and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginGUI_V1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginGUI_V1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginGUI_V1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginGUI_V1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginGUI_V1 login = new LoginGUI_V1();
                login.setLocationRelativeTo(null);
                login.setVisible(true);
                String a = "";
                
                String s = LoginGUI_V1.currentQuyen.getChiTietQuyen();
                s = s.replace(",", " ");
                System.out.println();

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckBoxDuyTriDangNhap;
    private javax.swing.JPanel Panel_Login;
    private javax.swing.JPanel Panel_img;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbExit;
    private javax.swing.JLabel lbImg;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbUser;
    private javax.swing.JPasswordField txtpass;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables

    }
