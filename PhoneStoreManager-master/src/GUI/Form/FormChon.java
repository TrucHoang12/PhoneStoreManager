package GUI.Form;

import DTO.ChiTietHoaDon;
import BUS.QuanlychitiethoadonBUS;
import DTO.ChiTietPhieuNhap;
import BUS.QuanlychitietphieunhapBUS;
import DTO.HoaDon;
import BUS.QuanlykhuyenmaiBUS;
import DTO.NhanVien;
import BUS.QuanlynhanvienBUS;
import DTO.PhieuNhap;
import BUS.QuanlyquyenBUS;
import DTO.Quyen;
import BUS.QuanlysanphamBUS;
import GUI.Panel.SearchFromTo;
import GUI.Panel.DateSearch;
import GUI.Panel.SearchPanel;
import DTO.LoaiSanPham;
import BUS.QuanlyloaisanphamBUS;
import BUS.QuanlyphieunhapBUS;
import BUS.XulyTaiKhoanBUS;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class FormChon extends JFrame {

    private JButton btnLamMoi;
    private JButton btnChon;
    private JPanel pnmain;
    private JPanel pnSouth;
    private JPanel pnNorth;
    private JTable tb;
    private SearchPanel pnSearch;
    private DateSearch pnDateSearch;
    private SearchFromTo pnAgeSearch;
    private SearchFromTo pnSoLuongSearch;
    private SearchFromTo pnThanhTienSearch;
    private DefaultTableModel model;
    private JScrollPane JS;
    private Object[] s = null;

    public FormChon(String type) {
        setIconImage((new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/signs-1.png"))).getImage());
        Font font = new Font("Tahoma", 1, 12);
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/internet.png")));
        btnLamMoi.setFont(font);
        btnChon = new JButton("Chọn");
        btnChon.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/correct.png")));
        btnChon.setFont(font);
        pnSouth = new JPanel();
        pnSouth.add(btnChon, BorderLayout.CENTER);
        pnSouth.add(btnLamMoi, BorderLayout.CENTER);
        pnSouth.setBackground(Color.white);
        switch (type) {
            case "ChonNhanVien": {
                initChonNhanVien();
                break;
            }
            case "ChonMaQuyen": {
                initChonMaQuyen();
                break;
            }
            case "ChonMaLoaiSanPham": {
                initChonLoaiSanPham();
                break;
            }
            default:
                break;
        }

        sort();
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tb.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    int colum = tb.getSelectedColumn();
                    if (colum >= 0) {
                        if (s == null) {
                            s = new Object[]{tb.getValueAt(row, colum), Integer.toString(row), Integer.toString(colum)};
                        } else {
                            tb.setValueAt(s[0], Integer.parseInt(s[1].toString()), Integer.parseInt(s[2].toString()));
                            s = null;
                        }
                    }
                }
            }
        });
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public FormChon(HoaDon hd) {
        setIconImage((new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/view.png"))).getImage());
        setTitle("Chi tiết hóa đơn " + hd.getMaHD());
        setSize(1100, 500);
        Font font = new Font("Tahoma", 1, 12);
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/internet.png")));
        btnLamMoi.setFont(font);
        btnLamMoi.addActionListener(new ActionListener() {
            QuanlychitiethoadonBUS Quanlychitiethoadonbus = new QuanlychitiethoadonBUS();

            @Override
            public void actionPerformed(ActionEvent e) {
                QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
                QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
                QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();

                remove(JS);
                repaint();
                tb = new JTable();
                model = (DefaultTableModel) tb.getModel();
                tb.setRowHeight(35);
                model.setColumnIdentifiers(new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"});
                JS = new JScrollPane(tb);

                ArrayList<ChiTietHoaDon> list = Quanlychitiethoadonbus.getChiTietHoaDon(hd.getMaHD());
                int i = 0;
                for (ChiTietHoaDon q : list) {
                    ++i;
                    model.addRow(new Object[]{
                        Integer.toString(i), q.getMaSP(), Quanlysanphambus.getSanPham(q.getMaSP()).getTenSP(), Quanlykhuyenmaibus.ToCurrent(Integer.toString(q.getSoLuong())),
                        Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia())), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia() * q.getSoLuong()))
                    });
                }

                sort();
                tb.setFont(new Font("Segoe UI", 0, 16));
                tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
                tb.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        int row = tb.getSelectedRow();
                        if (row >= 0) {
                            int colum = tb.getSelectedColumn();
                            if (colum >= 0) {
                                if (s == null) {
                                    s = new Object[]{tb.getValueAt(row, colum), Integer.toString(row), Integer.toString(colum)};
                                } else {
                                    tb.setValueAt(s[0], Integer.parseInt(s[1].toString()), Integer.parseInt(s[2].toString()));
                                    s = null;
                                }
                            }
                        }
                    }
                });
                resizeColumnWidth();
                add(JS, BorderLayout.CENTER);
                validate();
            }
        });
        pnSouth = new JPanel();
        pnSouth.add(btnLamMoi, BorderLayout.CENTER);
        pnSouth.setBackground(Color.white);

        tb = new JTable();
        QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
        QuanlychitiethoadonBUS Quanlychitiethoadonbus = new QuanlychitiethoadonBUS();
        QuanlychitietphieunhapBUS Quanlychitietphieunhapbus = new QuanlychitietphieunhapBUS();
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();

        model = (DefaultTableModel) tb.getModel();
        String[] header = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        model.setColumnIdentifiers(header);
        tb.setRowHeight(35);
        JS = new JScrollPane(tb);

        ArrayList<ChiTietHoaDon> list = Quanlychitiethoadonbus.getChiTietHoaDon(hd.getMaHD());
        int i = 0;
        for (ChiTietHoaDon q : list) {
            ++i;
            model.addRow(new Object[]{
                Integer.toString(i), q.getMaSP(), Quanlysanphambus.getSanPham(q.getMaSP()).getTenSP(), Quanlykhuyenmaibus.ToCurrent(Integer.toString(q.getSoLuong())),
                Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia())), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia() * q.getSoLuong()))
            });
        }

        sort();
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tb.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    int colum = tb.getSelectedColumn();
                    if (colum >= 0) {
                        if (s == null) {
                            s = new Object[]{tb.getValueAt(row, colum), Integer.toString(row), Integer.toString(colum)};
                        } else {
                            tb.setValueAt(s[0], Integer.parseInt(s[1].toString()), Integer.parseInt(s[2].toString()));
                            s = null;
                        }
                    }
                }
            }
        });

        add(pnSouth, BorderLayout.SOUTH);
        add(JS, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public FormChon(PhieuNhap pn) {
        setIconImage((new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/view.png"))).getImage());
        setTitle("Chi tiết phiếu nhập " + pn.getMaPN());
        setSize(1100, 500);
        Font font = new Font("Tahoma", 1, 12);
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setIcon(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/internet.png")));
        btnLamMoi.setFont(font);
        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
                QuanlychitietphieunhapBUS Quanlychitietphieunhapbus = new QuanlychitietphieunhapBUS();
                QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
                QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();

                remove(JS);
                repaint();
                tb = new JTable();
                model = (DefaultTableModel) tb.getModel();
                tb.setRowHeight(35);
                model.setColumnIdentifiers(new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"});
                JS = new JScrollPane(tb);

                ArrayList<ChiTietPhieuNhap> list = Quanlychitietphieunhapbus.getChiTietPhieuNhap(pn.getMaPN());
                int i = 0;
                for (ChiTietPhieuNhap q : list) {
                    ++i;
                    model.addRow(new Object[]{
                        Integer.toString(i), q.getMaSP(), Quanlysanphambus.getSanPham(q.getMaSP()).getTenSP(), Quanlykhuyenmaibus.ToCurrent(Integer.toString(q.getSoLuong())),
                        Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia())), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia() * q.getSoLuong()))
                    });
                }

                sort();
                tb.setFont(new Font("Segoe UI", 0, 16));
                tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
                tb.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        int row = tb.getSelectedRow();
                        if (row >= 0) {
                            int colum = tb.getSelectedColumn();
                            if (colum >= 0) {
                                if (s == null) {
                                    s = new Object[]{tb.getValueAt(row, colum), Integer.toString(row), Integer.toString(colum)};
                                } else {
                                    tb.setValueAt(s[0], Integer.parseInt(s[1].toString()), Integer.parseInt(s[2].toString()));
                                    s = null;
                                }
                            }
                        }
                    }
                });
                resizeColumnWidth();
                add(JS, BorderLayout.CENTER);
                validate();
            }
        });
        pnSouth = new JPanel();
        pnSouth.add(btnLamMoi, BorderLayout.CENTER);
        pnSouth.setBackground(Color.white);

        tb = new JTable();
        model = (DefaultTableModel) tb.getModel();
        String[] header = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        model.setColumnIdentifiers(header);
        tb.setRowHeight(35);
        JS = new JScrollPane(tb);
        QuanlychitietphieunhapBUS Quanlychitietphieunhapbus = new QuanlychitietphieunhapBUS();
        QuanlysanphamBUS Quanlysanphambus = new QuanlysanphamBUS();
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        QuanlykhuyenmaiBUS Quanlykhuyenmaibus = new QuanlykhuyenmaiBUS();

        ArrayList<ChiTietPhieuNhap> list = Quanlychitietphieunhapbus.getChiTietPhieuNhap(pn.getMaPN());

        int i = 0;
        for (ChiTietPhieuNhap q : list) {
            ++i;
            model.addRow(new Object[]{
                Integer.toString(i), q.getMaSP(), Quanlysanphambus.getSanPham(q.getMaSP()).getTenSP(), Quanlykhuyenmaibus.ToCurrent(Integer.toString(q.getSoLuong())),
                Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia())), Quanlykhuyenmaibus.ToCurrent(Long.toString(q.getDonGia() * q.getSoLuong()))
            });
        }

        sort();
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tb.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    int colum = tb.getSelectedColumn();
                    if (colum >= 0) {
                        if (s == null) {
                            s = new Object[]{tb.getValueAt(row, colum), Integer.toString(row), Integer.toString(colum)};
                        } else {
                            tb.setValueAt(s[0], Integer.parseInt(s[1].toString()), Integer.parseInt(s[2].toString()));
                            s = null;
                        }
                    }
                }
            }
        });

        add(pnSouth, BorderLayout.SOUTH);
        add(JS, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initChonNhanVien() {
        setTitle("Chọn nhân viên");
        setSize(1200, 600);

        TaoLaiTableNhanVien();

        QuanlynhanvienBUS Quanlynhanvienbus = new QuanlynhanvienBUS();
        ArrayList<NhanVien> list = Quanlynhanvienbus.getDSNV();
        int i = 0;
        for (NhanVien nv : list) {
            ++i;
            model.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSDT(), (nv.getTrangThai() == 1) ? "Còn làm" : "Hết làm"});
        }
        resizeColumnWidth();

        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(JS);
                repaint();
                TaoLaiTableNhanVien();

                ArrayList<NhanVien> list = Quanlynhanvienbus.getDSNV();
                int i = 0;
                for (NhanVien nv : list) {
                    ++i;
                    model.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSDT(), (nv.getTrangThai() == 1) ? "Còn làm" : "Hết làm"});
                }
                tb.setFont(new Font("Segoe UI", 0, 16));
                tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
                resizeColumnWidth();
                sort();
                add(JS, BorderLayout.CENTER);
                validate();
            }
        });

        btnChon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    FormThemSuaTaiKhoan.txtMaNV.setText(LayMaNhanVienTrenTable(row));
                    dispose();
                }
            }
        });

        pnNorth = new JPanel();
        pnAgeSearch = new SearchFromTo("CHON", "Tuổi");
        pnAgeSearch.setPreferredSize(new Dimension(pnAgeSearch.getSize().width, pnAgeSearch.getSize().height));
        pnAgeSearch.btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnDateSearch.dateFrom.setDate(null);
                pnDateSearch.dateTo.setDate(null);
                pnSearch.txtSearch.setText("");
                String age1 = pnAgeSearch.txtFrom.getText().trim();
                String age2 = pnAgeSearch.txtTo.getText().trim();
                ArrayList<NhanVien> list;
                if (Pattern.matches("[\\d]{1,}", age1) && Pattern.matches("[\\d]{1,}", age2)) {
                    list = Quanlynhanvienbus.SearchNVAge(Integer.parseInt(age1), Integer.parseInt(age2));
                } else {
                    list = null;
                }
                remove(JS);
                repaint();
                TaoLaiTableNhanVien();
                if (list != null) {
                    int i = 0;
                    for (NhanVien nv : list) {
                        ++i;
                        model.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSDT(), (nv.getTrangThai() == 1) ? "Còn làm" : "Hết làm"});
                    }
                }
                tb.setFont(new Font("Segoe UI", 0, 16));
                tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
                resizeColumnWidth();
                sort();
                add(JS, BorderLayout.CENTER);
                validate();
            }
        });

        pnSearch = new SearchPanel("CHON");
        pnSearch.setPreferredSize(new Dimension(pnSearch.getSize().width, pnSearch.getSize().height));
        pnSearch.setComboBox(Quanlynhanvienbus.getComboboxSearch);
        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                SearchNhanVien();
                pnDateSearch.dateFrom.setDate(null);
                pnDateSearch.dateTo.setDate(null);
                pnAgeSearch.txtFrom.setText("");
                pnAgeSearch.txtTo.setText("");
            }
        });
        pnSearch.ComboBoxSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchNhanVien();
                pnDateSearch.dateFrom.setDate(null);
                pnDateSearch.dateTo.setDate(null);
                pnAgeSearch.txtFrom.setText("");
                pnAgeSearch.txtTo.setText("");
            }
        });

        pnDateSearch = new DateSearch("CHON");
        pnDateSearch.setPreferredSize(new Dimension(pnDateSearch.getSize().width, pnDateSearch.getSize().height));
        pnDateSearch.btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnAgeSearch.txtFrom.setText("");
                pnAgeSearch.txtTo.setText("");
                pnSearch.txtSearch.setText("");
                String DateFrom = "";
                String DateTo = "";

                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd", Locale.getDefault());
                if (pnDateSearch.dateFrom.getDate() != null) {
                    DateFrom = sdf.format(pnDateSearch.dateFrom.getDate());
                }
                if (pnDateSearch.dateTo.getDate() != null) {
                    DateTo = sdf.format(pnDateSearch.dateTo.getDate());
                }
                ArrayList<NhanVien> list;
                if (DateFrom.equals("") || DateTo.equals("")) {
                    list = Quanlynhanvienbus.SearchNVFromDate(-1, -1, -1, -1, -1, -1);
                } else {
                    String[] s1 = DateFrom.split("-");
                    String[] s2 = DateTo.split("-");
                    list = Quanlynhanvienbus.SearchNVFromDate(Integer.parseInt(s1[0]), Integer.parseInt(s1[1]), Integer.parseInt(s1[2]),
                            Integer.parseInt(s2[0]), Integer.parseInt(s2[1]), Integer.parseInt(s2[2]));
                }

                remove(JS);
                repaint();
                TaoLaiTableNhanVien();
                if (list != null) {
                    int i = 0;
                    for (NhanVien nv : list) {
                        ++i;
                        model.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSDT(), (nv.getTrangThai() == 1) ? "Còn làm" : "Hết làm"});
                    }
                }
                tb.setFont(new Font("Segoe UI", 0, 16));
                tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
                resizeColumnWidth();
                sort();
                add(JS, BorderLayout.CENTER);
                validate();
            }
        });
        resizeColumnWidth();
        pnNorth.add(pnSearch, BorderLayout.CENTER);
        pnNorth.add(pnDateSearch, BorderLayout.CENTER);
        pnNorth.add(pnAgeSearch, BorderLayout.CENTER);
        pnNorth.setBorder(BorderFactory.createLineBorder(Color.black));
        pnNorth.setBackground(Color.white);
        add(pnSouth, BorderLayout.SOUTH);
        add(JS, BorderLayout.CENTER);
        add(pnNorth, BorderLayout.NORTH);
    }

    private void initChonMaQuyen() {

        setTitle("Chọn mã quyền");
        setSize(1200, 600);
        
        // -------vip----------------------------------
        TaoLaiTableMaQuyen();
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
        ArrayList<Quyen> list = Quanlyquyenbus.getDSChucNang();
        
        
        int i = 0;
        for (Quyen q : list) {
            ++i;
            model.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
        }
         // ----------------------vip-------------------

        resizeColumnWidth();

        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(JS);
                repaint();
                TaoLaiTableMaQuyen();
// VIPPP
                ArrayList<Quyen> list = Quanlyquyenbus.getDSChucNang();
                int i = 0;
                for (Quyen q : list) {
                    ++i;
                    model.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
                }
                resizeColumnWidth();
                sort();
                tb.setFont(new Font("Segoe UI", 0, 16));
                tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
                add(JS, BorderLayout.CENTER);
                validate();
            }
        });

        btnChon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    FormThemSuaTaiKhoan.txtMaQuyen.setText(LayMaQuyenTrenTable(row));
                    dispose();
                }
            }
        });

        pnSearch = new SearchPanel("CHON");
        pnSearch.setPreferredSize(new Dimension(pnSearch.getSize().width, pnSearch.getSize().height));
        pnSearch.setComboBox(Quanlyquyenbus.getComboboxSearch);
        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                SearchQuyen();
            }
        });
        pnSearch.ComboBoxSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchQuyen();
            }
        });

        pnNorth = new JPanel();
        pnNorth.add(pnSearch, BorderLayout.CENTER);
        pnNorth.setBorder(BorderFactory.createLineBorder(Color.black));
        pnNorth.setBackground(Color.white);

        add(pnSouth, BorderLayout.SOUTH);
        add(JS, BorderLayout.CENTER);
        add(pnNorth, BorderLayout.NORTH);
    }

    private void initChonLoaiSanPham() {
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        setTitle("Chọn loại sản phẩm");
        setSize(700, 500);
        TaoLaiTableLoaiSanPham();

        ArrayList<LoaiSanPham> list = Quanlyloaisanphambus.getDSLSP();
        int i = 0;
        for (LoaiSanPham q : list) {
            ++i;
            model.addRow(new String[]{Integer.toString(i), q.getMaLSP(), q.getTenLSP(), q.getMota()});
        }
        resizeColumnWidth();

        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(JS);
                repaint();
                TaoLaiTableLoaiSanPham();

                ArrayList<LoaiSanPham> list = Quanlyloaisanphambus.getDSLSP();
                int i = 0;
                for (LoaiSanPham q : list) {
                    ++i;
                    model.addRow(new String[]{Integer.toString(i), q.getMaLSP(), q.getTenLSP(), q.getMota()});
                }
                resizeColumnWidth();
                sort();
                tb.setFont(new Font("Segoe UI", 0, 16));
                tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
                add(JS, BorderLayout.CENTER);
                validate();
            }
        });

        btnChon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    FormSanPham.txtMaLSP.setText(LayMaLSPTrenTable(row));
                    dispose();
                }
            }
        });

        pnSearch = new SearchPanel("CHON");
        pnSearch.setPreferredSize(new Dimension(pnSearch.getSize().width, pnSearch.getSize().height));
        pnSearch.setComboBox(Quanlyloaisanphambus.getComboboxSearch);
        pnSearch.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                SearchLSP();
            }
        });
        pnSearch.ComboBoxSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchLSP();
            }
        });

        pnNorth = new JPanel();
        pnNorth.add(pnSearch, BorderLayout.CENTER);
        pnNorth.setBorder(BorderFactory.createLineBorder(Color.black));
        pnNorth.setBackground(Color.white);

        add(pnSouth, BorderLayout.SOUTH);
        add(JS, BorderLayout.CENTER);
        add(pnNorth, BorderLayout.NORTH);
    }

    private void sort() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tb.setRowSorter(sorter);
    }

    private void SearchNhanVien() {
        QuanlynhanvienBUS Quanlynhanvienbus = new QuanlynhanvienBUS();
        String type = pnSearch.ComboBoxSearch.getSelectedItem().toString().trim();
        String s = pnSearch.txtSearch.getText().trim();
        ArrayList<NhanVien> list = Quanlynhanvienbus.SearchNhanVien(s, type);
        remove(JS);
        repaint();
        TaoLaiTableNhanVien();
        if (list != null) {
            int i = 0;
            for (NhanVien nv : list) {
                ++i;
                model.addRow(new String[]{Integer.toString(i), nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getDiaChi(),
                    nv.getSDT(), (nv.getTrangThai() == 1) ? "Còn làm" : "Hết làm"});
            }
        }
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        resizeColumnWidth();
        sort();
        add(JS, BorderLayout.CENTER);
        validate();
    }

    private void SearchQuyen() {

        String type = pnSearch.ComboBoxSearch.getSelectedItem().toString().trim();
        String s = pnSearch.txtSearch.getText().trim();
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
        ArrayList<Quyen> list = Quanlyquyenbus.SearchQuyen(s, type);
        remove(JS);
        repaint();
        TaoLaiTableMaQuyen();
        if (list != null) {
            int i = 0;
            for (Quyen q : list) {
                ++i;
                model.addRow(new String[]{Integer.toString(i), q.getMaQuyen(), q.getTenQuyen(), q.getChiTietQuyen()});
            }
        }
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        resizeColumnWidth();
        sort();
        add(JS, BorderLayout.CENTER);
        validate();
    }

    private void SearchLSP() {
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        String type = pnSearch.ComboBoxSearch.getSelectedItem().toString().trim();
        String s = pnSearch.txtSearch.getText().trim();
        ArrayList<LoaiSanPham> list = Quanlyloaisanphambus.SearchLSP(s, type);
        remove(JS);
        repaint();
        TaoLaiTableLoaiSanPham();
        if (list != null) {
            int i = 0;
            for (LoaiSanPham q : list) {
                ++i;
                model.addRow(new String[]{Integer.toString(i), q.getMaLSP(), q.getTenLSP(), q.getMota()});
            }
        }
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        resizeColumnWidth();
        sort();
        add(JS, BorderLayout.CENTER);
        validate();
    }

    private void TaoLaiTableNhanVien() {
        QuanlynhanvienBUS Quanlynhanvienbus = new QuanlynhanvienBUS();
        tb = new JTable();
        model = (DefaultTableModel) tb.getModel();
        tb.setRowHeight(35);
        model.setColumnIdentifiers(Quanlynhanvienbus.getHeaders);
        JS = new JScrollPane(tb);
    }

    private void TaoLaiTableMaQuyen() {
        tb = new JTable();
        model = (DefaultTableModel) tb.getModel();
        QuanlyquyenBUS Quanlyquyenbus = new QuanlyquyenBUS();
        model.setColumnIdentifiers(Quanlyquyenbus.getHeaders);
        tb.setRowHeight(35);
        JS = new JScrollPane(tb);
    }

    private void TaoLaiTableLoaiSanPham() {
        QuanlyloaisanphamBUS Quanlyloaisanphambus = new QuanlyloaisanphamBUS();
        tb = new JTable();
        model = (DefaultTableModel) tb.getModel();
        model.setColumnIdentifiers(Quanlyloaisanphambus.getHeaders);
        tb.setRowHeight(35);
        JS = new JScrollPane(tb);
    }

    private void TaoLaiTableChiTietHoaDon() {
        QuanlychitiethoadonBUS Quanlychitiethoadonbus = new QuanlychitiethoadonBUS();

        tb = new JTable();
        model = (DefaultTableModel) tb.getModel();
        model.setColumnIdentifiers(Quanlychitiethoadonbus.getHeaders);
        tb.setRowHeight(35);
        JS = new JScrollPane(tb);
    }

    private String LayMaNhanVienTrenTable(int row) {
        for (int i = 0; i < tb.getColumnCount(); ++i) {
            if (tb.getColumnName(i).equals("Mã nhân viên")) {
                return tb.getValueAt(row, i).toString();
            }
        }
        return null;
    }

    private String LayMaQuyenTrenTable(int row) {
        for (int i = 0; i < tb.getColumnCount(); ++i) {
            if (tb.getColumnName(i).equals("Mã quyền")) {
                return tb.getValueAt(row, i).toString();
            }
        }
        return null;
    }

    private String LayMaLSPTrenTable(int row) {
        for (int i = 0; i < tb.getColumnCount(); ++i) {
            if (tb.getColumnName(i).equals("Mã loại")) {
                return tb.getValueAt(row, i).toString();
            }
        }
        return null;
    }

//    https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
    public void resizeColumnWidth() {
        final TableColumnModel columnModel = tb.getColumnModel();
        for (int column = 0; column < tb.getColumnCount(); column++) {
            int width = 100; // Min width
            for (int row = 0; row < tb.getRowCount(); row++) {
                TableCellRenderer renderer = tb.getCellRenderer(row, column);
                Component comp = tb.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }

            width += 100;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
//        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
//    
//    public static void main(String[] args){
//        new FormChon("ChonMaQuyen").setVisible(true);
//    }
}
