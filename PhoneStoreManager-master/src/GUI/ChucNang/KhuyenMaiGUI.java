package GUI.ChucNang;

import BUS.QuanlychitietquyenBUS;
import DTO.KhuyenMai;
import BUS.QuanlykhuyenmaiBUS;
import DTO.ChiTietQuyen;
import EXEL.DocExcel;
import EXEL.XuatExcel;
import GUI.Form.FormThemSuaKhuyenMai;
import GUI.GiaoDien.GiaoDienGUI;
import static GUI.GiaoDien.GiaoDienGUI.panelMain;
import static GUI.GiaoDien.GiaoDienGUI.tb;
import GUI.GiaoDien.LoginGUI_V1;
import GUI.GiaoDien.Table;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
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

public class KhuyenMaiGUI extends JPanel {

    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXem;
    private JButton btnKetthuc;

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
    private QuanlykhuyenmaiBUS qlkmbus;
    private JPanel searchPanel;
    private int i = 1;

    public KhuyenMaiGUI (int width, int height) {
        qlkmbus = new QuanlykhuyenmaiBUS();
        
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
        buttonPanel.add(btnKetthuc);
        buttonPanel.add(btnXem);
        buttonPanel.add(btnXuatExcel);
        buttonPanel.add(btnNhapExcel);
        buttonPanel.add(btnLamMoi);

        applyButtonHoverEffect(btnThem);
        applyButtonHoverEffect(btnXoa);
        applyButtonHoverEffect(btnSua);
        applyButtonHoverEffect(btnKetthuc);
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

        qlkmbus = new QuanlykhuyenmaiBUS();
        
        btnThem = createButton("Thêm", "/com/PhoneStoreManager/image/signs-1.png");
        btnXoa = createButton("Xóa", "/com/PhoneStoreManager/image/button-delete.png");
        btnSua = createButton("Sửa", "/com/PhoneStoreManager/image/edit-tools.png");
        btnKetthuc = createButton("Kết thúc", "/com/PhoneStoreManager/image/ketthuc.png");
        btnXem = createButton("Xem", "/com/PhoneStoreManager/image/eye.png");

        btnXuatExcel = createButton("Xuất Excel", "/com/PhoneStoreManager/image/excel.png");
        btnNhapExcel = createButton("Nhập Excel", "/com/PhoneStoreManager/image/excel.png");
        btnLamMoi = createButton("Làm mới", "/com/PhoneStoreManager/image/internet.png");

        tb = new Table();
        tb.setRowHeigth(35);
        
        // Thêm table khuyến mãi
        tb_x = 0;
        tb_y = 100 + btnThem.getSize().height;
        tb_w = panelMain.getSize().width;
        tb_h = panelMain.getSize().height - tb_y - 1;
        tb.setBound(tb_x, tb_y, tb_w, tb_h);
        tb.setHeaders(qlkmbus.getHeaders);
        int i = 0;
        LocalDate today = LocalDate.now();
        String[] day = today.toString().split("-");
        String[] kt;
        String[] bd;
        for (KhuyenMai km : qlkmbus.getDSKM()) {
            ++i;
            kt = km.getNgayKT().toString().split("-");
            bd = km.getNgayBD().toString().split("-");
            String trangthai = "Đang diễn ra";
            if (today.toString().equals(km.getNgayKT().toString())) {
                trangthai = "Đã kết thúc";
            } else {
                if (!(qlkmbus.CompareDate(day[0], day[1], day[2], kt[0], kt[1], kt[2])
                        && qlkmbus.CompareDate(bd[0], bd[1], bd[2], day[0], day[1], day[2]))) {
                    if (qlkmbus.CompareDate(day[0], day[1], day[2], bd[0], bd[1], bd[2])) {
                        trangthai = "Chưa diễn ra";
                    } else {
                        trangthai = "Đã kết thúc";
                    }
                }
            }
            tb.addRow(new String[]{Integer.toString(i), km.getMaKM(), km.getTenKM(), qlkmbus.ToCurrent(Long.toString(km.getDieuKienKM())), Integer.toString(km.getPhanTramKM()),
                km.getNgayBD().toString(), km.getNgayKT().toString(), trangthai});
        }
        tb.resizeColumnWidth();
        
        btnThem.addActionListener(e -> themKM());
        btnXoa.addActionListener(e -> xoaKM());
        btnSua.addActionListener(e -> suaKM());
        btnKetthuc.addActionListener(e -> ketthucKM());
        btnXem.addActionListener(e -> suaKM());

        btnXuatExcel.addActionListener(e -> xuatExcelKM());
        btnNhapExcel.addActionListener(e -> nhapExcelKM());
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
        comboBoxSearch = new JComboBox<>(qlkmbus.getComboboxSearch);
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventSearchQuyenforGiaoDien();
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

    public void EventSearchQuyenforGiaoDien() {
        String s = txtSearch.getText().trim();
        String type = comboBoxSearch.getSelectedItem().toString().trim();
        ArrayList<KhuyenMai> list = qlkmbus.SearchKhuyenMai(s, type);

        tb.clear();

        if (list != null && !list.isEmpty()) {
            LocalDate today = LocalDate.now();
            String[] day = today.toString().split("-");
            String[] kt;
            String[] bd;
            for (KhuyenMai km : list) {
                kt = km.getNgayKT().toString().split("-");
                bd = km.getNgayBD().toString().split("-");
                String trangthai = "Đang diễn ra";
            if (today.toString().equals(km.getNgayKT().toString())) {
                trangthai = "Đã kết thúc";
            } else {
                if (!(qlkmbus.CompareDate(day[0], day[1], day[2], kt[0], kt[1], kt[2])
                        && qlkmbus.CompareDate(bd[0], bd[1], bd[2], day[0], day[1], day[2]))) {
                    if (qlkmbus.CompareDate(day[0], day[1], day[2], bd[0], bd[1], bd[2])) {
                        trangthai = "Chưa diễn ra";
                    } else {
                        trangthai = "Đã kết thúc";
                    }
                }
            }
            tb.addRow(new String[]{Integer.toString(i), km.getMaKM(), km.getTenKM(), qlkmbus.ToCurrent(Long.toString(km.getDieuKienKM())), Integer.toString(km.getPhanTramKM()),
                km.getNgayBD().toString(), km.getNgayKT().toString(), trangthai});
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

    private void themKM() {
        qlkmbus = new QuanlykhuyenmaiBUS();
        String MaKM = qlkmbus.MaKMnotHave();
        new FormThemSuaKhuyenMai(MaKM).setVisible(true);
    }

    private void xoaKM() {
        qlkmbus = new QuanlykhuyenmaiBUS();
        int i = tb.getSelectedRow();
        if (i >= 0) {
            int k = JOptionPane.showConfirmDialog(null, "Bạn có chắc xóa khuyến mãi này không?");
            if (k != JOptionPane.YES_OPTION) {
                return;
            }
            String MaKM = tb.LayMaKhuyenMaiTaiTable(k).toString();
            if (qlkmbus.deleteKM(MaKM)) {
                tb.removeRow(MaKM);
                JOptionPane.showMessageDialog(null, "Xóa khuyến mãi thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Xóa khuyến mãi thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn khuyến mãi cần xóa");
        }
    }

    private void suaKM() {
        qlkmbus = new QuanlykhuyenmaiBUS();
        int i = tb.getSelectedRow();
        if (i >= 0) {
            String MaKM = tb.LayMaKhuyenMaiTaiTable(i).toString();
            KhuyenMai km = qlkmbus.getKhuyenMai(MaKM);
            new FormThemSuaKhuyenMai(km, Integer.parseInt(tb.getValueSTT(i).toString())).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn khuyến mãi cần sữa");
        }
    }
    
    private void ketthucKM() {
        qlkmbus = new QuanlykhuyenmaiBUS();
        int i = tb.getSelectedRow();
        if (i >= 0) {
            String tt = tb.LayTrangThaiKhuyenMaiTaiTable(i).toString();
                    if (tt.equals("Chưa diễn ra")) {
                        JOptionPane.showMessageDialog(null, "Không thể kết thúc khuyến mãi này. Khuyến mãi này chưa diễn ra.");
                        return;
                    }
                    if (tt.equals("Đã kết thúc")) {
                        JOptionPane.showMessageDialog(null, "Không thể kết thúc khuyến mãi này. Khuyến mãi này đã kết thúc.");
                        return;
                    }
                    int k = JOptionPane.showConfirmDialog(null, "Bạn có chắc kết thúc khuyến mãi này không? Dời ngày kết thúc về hôm nay?");
                    if (k != JOptionPane.YES_OPTION) {
                        return;
                    }
                    String MaKM = tb.LayMaKhuyenMaiTaiTable(i).toString();
                    if (qlkmbus.KetThucKM(MaKM)) {
                        LocalDate today = LocalDate.now();
                        tb.setValueTrangThai(MaKM, "Đã kết thúc");
                        JOptionPane.showMessageDialog(null, "Kết thúc khuyến mãi thành công");
                    } else {
                        JOptionPane.showMessageDialog(null, "Kết thúc khuyến mãi thất bại");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn khuyến mãi cần kết thúc");  
                }
    }

    private void xuatExcelKM() {
        new XuatExcel().xuatFileExcelKhuyenMai();
    }

    private void nhapExcelKM() {
        new DocExcel().DocExcelKM();
        btnLamMoi.doClick();
    }

    private void lamMoi() {
        // Xóa toàn bộ dữ liệu trong Table
        tb.clear();

        // Thêm lại dữ liệu mới vào Table
        qlkmbus = new QuanlykhuyenmaiBUS();
        int i = 0;
        LocalDate today = LocalDate.now();
        String[] day = today.toString().split("-");
        String[] kt;
        String[] bd;
        for (KhuyenMai km : qlkmbus.getDSKM()) {
            ++i;
            kt = km.getNgayKT().toString().split("-");
            bd = km.getNgayBD().toString().split("-");
            String trangthai = "Đang diễn ra";
            if (today.toString().equals(km.getNgayKT().toString())) {
                trangthai = "Đã kết thúc";
            } else {
                if (!(qlkmbus.CompareDate(day[0], day[1], day[2], kt[0], kt[1], kt[2])
                        && qlkmbus.CompareDate(bd[0], bd[1], bd[2], day[0], day[1], day[2]))) {
                    if (qlkmbus.CompareDate(day[0], day[1], day[2], bd[0], bd[1], bd[2])) {
                        trangthai = "Chưa diễn ra";
                    } else {
                        trangthai = "Đã kết thúc";
                    }
                }
            }
            tb.addRow(new String[]{Integer.toString(i), km.getMaKM(), km.getTenKM(), qlkmbus.ToCurrent(Long.toString(km.getDieuKienKM())), Integer.toString(km.getPhanTramKM()),
                km.getNgayBD().toString(), km.getNgayKT().toString(), trangthai});
        }
        tb.setRowHeigth(35);
        tb.resizeColumnWidth();
    }

      private void loadAndDisplayPermissions() {
               String maQuyen = LoginGUI_V1.currentChiTietQuyen.getMaQuyen();

    QuanlychitietquyenBUS quanlychitietquyenBUS = new QuanlychitietquyenBUS();
    ArrayList<ChiTietQuyen> actions = quanlychitietquyenBUS.getChiTietQuyenByMaQuyen(maQuyen);

            System.out.println("meo ac  " + actions);

   for (ChiTietQuyen action : actions) {
            if (action != null&& action.getMaChucNang().equals("KhuyenMai")) {
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
