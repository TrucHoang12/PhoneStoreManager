/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.ChucNang;

import BUS.QuanlyNCCBUS;
import BUS.QuanlychitietquyenBUS;
import DTO.ChiTietQuyen;
import DTO.NCC;
import EXEL.DocExcel;
import EXEL.XuatExcel;
import GUI.Form.FormThemSuaNCC;
import static GUI.GiaoDien.GiaoDienGUI.panelMain;
import GUI.GiaoDien.LoginGUI_V1;
import GUI.GiaoDien.Table;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author MSI
 */
public class NhaCungCapGUI extends JPanel {
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXem;

    private JButton btnXuatExcel;
    private JButton btnNhapExcel;
    private JButton btnLamMoi;
    public static Table tb;

    public static int tb_y = 0;
    public static int tb_x = 0;
    public static int tb_w = 0;
    public static int tb_h = 0;

    private JTextField txtSearch;
    private JComboBox<String> comboBoxSearch;
    private JButton btnSearch;
    private QuanlyNCCBUS qlnccbus;
    private JPanel searchPanel;
    private int i = 1;

    public NhaCungCapGUI (int width, int height) {
        qlnccbus = new QuanlyNCCBUS();
        
        int x = 0, y = 0, w = 150, h = 50;

        x = (panelMain.getSize().width - w * 6) / 2;

        setLayout(new BorderLayout());
        setBackground(Color.white);
        setSize(width, height);

        themChucNangButton();
        initSearch();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXem);
        buttonPanel.add(btnXuatExcel);
        buttonPanel.add(btnNhapExcel);
        buttonPanel.add(btnLamMoi);

        applyButtonHoverEffect(btnThem);
        applyButtonHoverEffect(btnXoa);
        applyButtonHoverEffect(btnSua);
        applyButtonHoverEffect(btnXem);

        applyButtonHoverEffect(btnXuatExcel);
        applyButtonHoverEffect(btnNhapExcel);
        applyButtonHoverEffect(btnLamMoi);

        add(buttonPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(new JScrollPane(tb), BorderLayout.SOUTH);
    }
    
    private void themChucNangButton() {
        
        int x = 0, y = 0, w = 150, h = 50;

        x = (panelMain.getSize().width - w * 6) / 2;

        qlnccbus = new QuanlyNCCBUS();
        
        btnThem = createButton("Thêm", "/com/PhoneStoreManager/image/signs-1.png");
        btnXoa = createButton("Xóa", "/com/PhoneStoreManager/image/button-delete.png");
        btnSua = createButton("Sửa", "/com/PhoneStoreManager/image/edit-tools.png");
        btnXem = createButton("Xem", "/com/PhoneStoreManager/image/eye.png");

        btnXuatExcel = createButton("Xuất Excel", "/com/PhoneStoreManager/image/excel.png");
        btnNhapExcel = createButton("Nhập Excel", "/com/PhoneStoreManager/image/excel.png");
        btnLamMoi = createButton("Làm mới", "/com/PhoneStoreManager/image/internet.png");

        tb = new Table();
        tb.setRowHeigth(35);
        
        // Thêm table nhà cung cấp
        tb_x = 0;
        tb_y = 100 + btnThem.getSize().height;
        tb_w = panelMain.getSize().width;
        tb_h = panelMain.getSize().height - tb_y - 1;
        tb.setBound(tb_x, tb_y, tb_w, tb_h);
        tb.setHeaders(qlnccbus.getHeaders);

        int i = 0;
        
        for(NCC ncc : qlnccbus.getDSNCC()) {
            ++i;
            tb.addRow(new String[]{Integer.toString(i), ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSDT(), ncc.getFax()});
        }
        tb.resizeColumnWidth();
        
        btnThem.addActionListener(e -> themNCC());
        btnXoa.addActionListener(e -> xoaNCC());
        btnSua.addActionListener(e -> suaNCC());
        btnXem.addActionListener(e -> suaNCC());

        btnXuatExcel.addActionListener(e -> xuatExcelNCC());
        btnNhapExcel.addActionListener(e -> nhapExcelNCC());
        btnLamMoi.addActionListener(e -> lamMoi());
        
           //------------------Ẩn nút hỗ trợ phân quyền 
        btnThem.setVisible(false);
    btnXoa.setVisible(false);
    btnSua.setVisible(false);
    btnXem.setVisible(false);
    
    applyButtonHoverEffect(btnThem);
    applyButtonHoverEffect(btnXoa);
    applyButtonHoverEffect(btnSua);
    applyButtonHoverEffect(btnXem);
    applyButtonHoverEffect(btnXuatExcel);
    applyButtonHoverEffect(btnNhapExcel);
    applyButtonHoverEffect(btnLamMoi);
    
        loadAndDisplayPermissions();
    }
    
    private void initSearch() {
        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        txtSearch = new JTextField(20);
        comboBoxSearch = new JComboBox<>(qlnccbus.getComboboxSearch);
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventSearchNCCforGiaoDien();
            }
        });

        Font font = new Font("Tahoma", Font.BOLD, 14);
        txtSearch.setFont(font);
        comboBoxSearch.setFont(font);
        btnSearch.setFont(font);
        txtSearch.setPreferredSize(new Dimension(200, 30));
        comboBoxSearch.setPreferredSize(new Dimension(150, 30));

        
        // Thiết lập kích thước và icon cho nút tìm kiếm
        btnSearch.setPreferredSize(new Dimension(120, 40));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(new JLabel("Loại:"));
        searchPanel.add(comboBoxSearch);
        searchPanel.add(btnSearch);

    }

    public void EventSearchNCCforGiaoDien() {
        String s = txtSearch.getText().trim();
        String type = comboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<NCC> list = qlnccbus.SearchNCC(s, type);
        
        tb.clear();

        if (list != null && !list.isEmpty()) {
            for (NCC ncc : list) {
                tb.addRow(new String[]{Integer.toString(i), ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSDT(), ncc.getFax()});
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
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

    private void themNCC() {
        qlnccbus = new QuanlyNCCBUS();
        String MaNCC = qlnccbus.MaNCCnotHave();
        new FormThemSuaNCC(MaNCC).setVisible(true);
    }

    private void xoaNCC() {
        qlnccbus = new QuanlyNCCBUS();
        int i = tb.getSelectedRow();
        if (i >= 0) {
            int k = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa nhà cung cấp này không?");
            if (k != JOptionPane.YES_OPTION) {
                return;
            }
            String maNCC = tb.LayMaNCCTaiTable(k).toString();
            if (qlnccbus.deleteNCC(maNCC)) {
                tb.removeRow(maNCC);
                JOptionPane.showMessageDialog(null, "Xóa nhà cung cấp thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Xóa nhà cung cấp thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhà cung cấp cần xóa");
        }
    }

    private void suaNCC() {
        qlnccbus = new QuanlyNCCBUS();
        int i = tb.getSelectedRow();
        if (i >= 0) {
            String MaNCC = tb.LayMaNCCTaiTable(i).toString();
            NCC ncc = qlnccbus.getNCC(MaNCC);
            new FormThemSuaNCC(ncc, Integer.parseInt(tb.getValueSTT(i).toString())).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhà cung cấp cần sữa");
        }
    }

    private void xuatExcelNCC() {
        new XuatExcel().xuatFileExcelNCC();
    }

    private void nhapExcelNCC() {
        new DocExcel().DocExcelNCC();
        btnLamMoi.doClick();
    }

    private void lamMoi() {
        // Xóa toàn bộ dữ liệu trong Table
        tb.clear();

        // Thêm lại dữ liệu mới vào Table
        qlnccbus = new QuanlyNCCBUS();
        int i = 0;
        for (NCC ncc : qlnccbus.getDSNCC()) {
            ++i;
            tb.addRow(new String[]{Integer.toString(i), ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSDT(), ncc.getFax()});
        }
        // Cập nhật lại giao diện Table
        tb.resizeColumnWidth();
    }
    
     private void loadAndDisplayPermissions() {
               String maQuyen = LoginGUI_V1.currentChiTietQuyen.getMaQuyen();

    QuanlychitietquyenBUS quanlychitietquyenBUS = new QuanlychitietquyenBUS();
    ArrayList<ChiTietQuyen> actions = quanlychitietquyenBUS.getChiTietQuyenByMaQuyen(maQuyen);

            System.out.println("meo ac  " + actions);

   for (ChiTietQuyen action : actions) {
            if (action != null&& action.getMaChucNang().equals("NhaCungCap")) {
                if (action.isThem()) {
                    btnThem.setVisible(true);
                }
                if (action.isXoa()) {
                    btnXoa.setVisible(true);
                }
                if (action.isSua()) {
                    btnSua.setVisible(true);
                }
                if (action.isXem()) {
                    btnXem.setVisible(true);
                }
            }
}

   }

}
