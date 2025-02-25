
package EXEL;

import DTO.NCC;
import BUS.QuanlyNCCBUS;
import DTO.KhachHang;
import BUS.QuanlykhachhangBUS;
import DTO.KhuyenMai;
import BUS.QuanlykhuyenmaiBUS;
import DTO.NhanVien;
import BUS.QuanlynhanvienBUS;
import BUS.QuanlyquyenBUS;
import DTO.Quyen;
import BUS.QuanlysanphamBUS;
import DTO.SanPham;
import DTO.TaiKhoan;
import BUS.XulyTaiKhoanBUS;
import GUI.GiaoDien.Table;
import DTO.LoaiSanPham;
import BUS.QuanlyloaisanphamBUS;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DocExcel {
    private FileDialog fd;
    
    public DocExcel(){
        JFrame jf = new JFrame();
        jf.setLocationRelativeTo(null);
        jf.setIconImage(new ImageIcon(getClass().getResource("/com/PhoneStoreManager/image/excel.png")).getImage());
        fd = new FileDialog(jf, "Đọc Excel", FileDialog.LOAD);
    }
    
    private String getFile(){
        fd.setFile("*.xls");
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if(url.equals("") || url.equals("null") || url.equals("nullnull")){
            return null;
        }
        return url;
    }
    
    // Doc File Excel Nhà Cung Cấp
    public void DocExcelNCC(){
        fd.setTitle("Nhập dữ liệu nhà cung cấp từ Excel");
        String url = getFile();
        if(url == null){
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thất bại!");
            return;
        }
        
        FileInputStream inpFile = null;
        try{
            inpFile = new FileInputStream(new File(url));
            
            HSSFWorkbook workbook = new HSSFWorkbook(inpFile);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            String hanhdongkhitrung = "";
            
            int countThem = 0, countGhide = 0, countBoqua = 0;
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while(cellIterator.hasNext()){
                    
                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String MaNCC = cellIterator.next().getStringCellValue();
                    String TenNCC = cellIterator.next().getStringCellValue();
                    String DiaChi = cellIterator.next().getStringCellValue();
                    String SDT = cellIterator.next().getStringCellValue();
                    String Fax = cellIterator.next().getStringCellValue();
                    
                    QuanlyNCCBUS qlncc = new QuanlyNCCBUS();
                    NCC oldNCC = qlncc.getNCC(MaNCC);
                    
                    if(oldNCC != null){
                        if(!hanhdongkhitrung.contains("tất cả")){
                            Table mtb = new Table();
                            mtb.setHeaders(new String[]{"", "Mã NCC", "Tên NCC", "Địa chỉ", "SDT", "Fax"});
                            mtb.addRow(new String[]{"Cũ", oldNCC.getMaNCC(), oldNCC.getTenNCC(), oldNCC.getDiaChi(), oldNCC.getSDT(), oldNCC.getFax()});
                            mtb.addRow(new String[]{"Mới", MaNCC, TenNCC, DiaChi, SDT, Fax});
                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhdongkhitrung);
                            hanhdongkhitrung = mop.getAnswer();
                        }
                        if(hanhdongkhitrung.contains("Ghi đè")){
                            qlncc.repaireNCC(MaNCC, TenNCC, DiaChi, SDT, Fax);
                            ++countGhide;
                        }
                        else {
                            ++countBoqua;
                        }
                    } else {
                        NCC q = new NCC(MaNCC, TenNCC, DiaChi, SDT, Fax);
                        qlncc.addNCC(q);
                        ++countThem;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem
                    + "; Ghi đè " + countGhide
                    + "; Bỏ qua " + countBoqua
            );
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu" + e.getMessage());
        } finally {
            try{
                if (inpFile != null) {
                    inpFile.close();
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng file " + ex);
            }
        }
    }
    
    // Doc File Excel Sãn Phẩm
    public void DocExcelSP(){
        fd.setTitle("Nhập dữ liệu sản phẩm từ Excel");
        String url = getFile();
        if(url == null){
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thất bại!");
            return;
        }
        
        FileInputStream inpFile = null;
        try{
            inpFile = new FileInputStream(new File(url));
            
            HSSFWorkbook workbook = new HSSFWorkbook(inpFile);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            String hanhdongkhitrung = "";
            
            int countThem = 0, countGhide = 0, countBoqua = 0;
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while(cellIterator.hasNext()){
                    
                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String MaSP = cellIterator.next().getStringCellValue();
                    String MaLSP = cellIterator.next().getStringCellValue();
                    String TenSP = cellIterator.next().getStringCellValue();
                    long DonGia = (long)cellIterator.next().getNumericCellValue();
                    int SoLuong = (int) cellIterator.next().getNumericCellValue();
                    String HinhAnh = cellIterator.next().getStringCellValue();
                    String tt = cellIterator.next().getStringCellValue();
                    int TrangThai = 1;
                    if(!tt.equals("Hiện")){
                        TrangThai = 0;
                    }
                    
                    QuanlysanphamBUS qlsp = new QuanlysanphamBUS();
                    SanPham oldsp = qlsp.getSanPham(MaSP);
                    
                    if(oldsp != null){
                        if(!hanhdongkhitrung.contains("tất cả")){
                            Table mtb = new Table();
                            mtb.setHeaders(new String[]{"", "Mã sản phẩm", "Mã loại sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng", "Hình ảnh","Trạng thái"});
                            mtb.addRow(new String[]{"Cũ", oldsp.getMaSP(), oldsp.getMaLSP(), oldsp.getTenSP(), Long.toString(oldsp.getDonGia()), Integer.toString(oldsp.getSoLuong()),
                                oldsp.getHinhAnh(), (oldsp.getTrangThai() == 1) ? "Hiện" : "Ẩn"});
                            mtb.addRow(new String[]{"Mới", MaSP, MaLSP, TenSP, Long.toString(DonGia), Integer.toString(SoLuong),
                                HinhAnh, tt});
                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhdongkhitrung);
                            hanhdongkhitrung = mop.getAnswer();
                        }
                        if(hanhdongkhitrung.contains("Ghi đè")){
                            qlsp.repairSanPham(MaSP, MaLSP, TenSP, DonGia, SoLuong, HinhAnh, TrangThai);
                            ++countGhide;
                        }
                        else {
                            ++countBoqua;
                        }
                    } else {
                        SanPham q = new SanPham(MaSP, MaLSP, TenSP, DonGia, SoLuong, HinhAnh, TrangThai);
                        qlsp.addSanPham(q);
                        ++countThem;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem
                    + "; Ghi đè " + countGhide
                    + "; Bỏ qua " + countBoqua
            );
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu" + e.getMessage());
        } finally {
            try{
                if (inpFile != null) {
                    inpFile.close();
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng file " + ex);
            }
        }
    }
    
    // Doc File Excel Loại Sãn Phẩm
    public void DocExcelLSP(){
        fd.setTitle("Nhập dữ liệu loại sản phẩm từ Excel");
        String url = getFile();
        if(url == null){
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thất bại!");
            return;
        }
        
        FileInputStream inpFile = null;
        try{
            inpFile = new FileInputStream(new File(url));
            
            HSSFWorkbook workbook = new HSSFWorkbook(inpFile);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            String hanhdongkhitrung = "";
            
            int countThem = 0, countGhide = 0, countBoqua = 0;
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while(cellIterator.hasNext()){
                    
                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String MaLSP = cellIterator.next().getStringCellValue();
                    String TenLSP = cellIterator.next().getStringCellValue();
                    String Mota = cellIterator.next().getStringCellValue();
                    
                    QuanlyloaisanphamBUS qllsp = new QuanlyloaisanphamBUS();
                    LoaiSanPham oldlsp = qllsp.getLSP(MaLSP);
                    
                    if(oldlsp != null){
                        if(!hanhdongkhitrung.contains("tất cả")){
                            Table mtb = new Table();
                            mtb.setHeaders(new String[]{"", "Mã loại", "Tên loại", "Mô tả"});
                            mtb.addRow(new String[]{"Cũ", oldlsp.getMaLSP(), oldlsp.getTenLSP(), oldlsp.getMota()});
                            mtb.addRow(new String[]{"Mới", MaLSP, MaLSP, Mota});
                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhdongkhitrung);
                            hanhdongkhitrung = mop.getAnswer();
                        }
                        if(hanhdongkhitrung.contains("Ghi đè")){
                            qllsp.repaireLSP(MaLSP, TenLSP, Mota);
                            ++countGhide;
                        }
                        else {
                            ++countBoqua;
                        }
                    } else {
                        LoaiSanPham q = new LoaiSanPham(MaLSP, TenLSP, Mota);
                        qllsp.addLSP(q);
                        ++countThem;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem
                    + "; Ghi đè " + countGhide
                    + "; Bỏ qua " + countBoqua
            );
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu" + e.getMessage());
        } finally {
            try{
                if (inpFile != null) {
                    inpFile.close();
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng file " + ex);
            }
        }
    }
    
    // Doc File Excel Khuyến Mãi
    public void DocExcelKM(){
        fd.setTitle("Nhập dữ liệu khuyến mãi từ Excel");
        String url = getFile();
        if(url == null){
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thất bại!");
            return;
        }
        
        FileInputStream inpFile = null;
        try{
            inpFile = new FileInputStream(new File(url));
            
            HSSFWorkbook workbook = new HSSFWorkbook(inpFile);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            String hanhdongkhitrung = "";
            
            int countThem = 0, countGhide = 0, countBoqua = 0;
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while(cellIterator.hasNext()){
                    
                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String MaKM = cellIterator.next().getStringCellValue();
                    String TenKM = cellIterator.next().getStringCellValue();
                    long DieuKienKM = (long)cellIterator.next().getNumericCellValue();
                    int PhanTramKM = (int) cellIterator.next().getNumericCellValue();
                    LocalDate NgayBD = LocalDate.parse(cellIterator.next().getStringCellValue());
                    LocalDate NgayKT = LocalDate.parse(cellIterator.next().getStringCellValue());
                    
                    QuanlykhuyenmaiBUS qlkm = new QuanlykhuyenmaiBUS();
                    KhuyenMai oldkm = qlkm.getKhuyenMai(MaKM);
                    
                    if(oldkm != null){
                        if(!hanhdongkhitrung.contains("tất cả")){
                            Table mtb = new Table();
                            mtb.setHeaders(new String[]{"", "Mã KM", "Tên KM", "Điều kiện KM", "Phần trăm KM", "Ngày bắt đầu", "Ngày kết thúc"});
                            mtb.addRow(new String[]{"Cũ", oldkm.getMaKM(), oldkm.getTenKM(), Long.toString(oldkm.getDieuKienKM()), Integer.toString(oldkm.getPhanTramKM()), 
                                oldkm.getNgayBD().toString(), oldkm.getNgayKT().toString()
                            });
                            mtb.addRow(new String[]{"Mới", MaKM, TenKM, Long.toString(DieuKienKM), Integer.toString(PhanTramKM), 
                                NgayBD.toString(), NgayKT.toString()
                            });
                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhdongkhitrung);
                            hanhdongkhitrung = mop.getAnswer();
                        }
                        if(hanhdongkhitrung.contains("Ghi đè")){
                            qlkm.repaireKhuyenMai(MaKM, TenKM, DieuKienKM, PhanTramKM, NgayBD, NgayKT);
                            ++countGhide;
                        }
                        else {
                            ++countBoqua;
                        }
                    } else {
                        KhuyenMai q = new KhuyenMai(MaKM, TenKM, DieuKienKM, PhanTramKM, NgayBD, NgayKT);
                        qlkm.addKhuyenMai(q);
                        ++countThem;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem
                    + "; Ghi đè " + countGhide
                    + "; Bỏ qua " + countBoqua
            );
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu" + e.getMessage());
        } finally {
            try{
                if (inpFile != null) {
                    inpFile.close();
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng file " + ex);
            }
        }
    }
    
    // Doc File Excel Nhân Viên
    public void DocExcelNhanVien(){
        fd.setTitle("Nhập dữ liệu nhân viên từ Excel");
        String url = getFile();
        if(url == null){
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thất bại!");
            return;
        }
        
        FileInputStream inpFile = null;
        try{
            inpFile = new FileInputStream(new File(url));
            
            HSSFWorkbook workbook = new HSSFWorkbook(inpFile);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            String hanhdongkhitrung = "";
            
            int countThem = 0, countGhide = 0, countBoqua = 0;
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while(cellIterator.hasNext()){
                    
                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String MaNV = cellIterator.next().getStringCellValue();
                    String TenNV = cellIterator.next().getStringCellValue();
                    String NgaySinh = cellIterator.next().getStringCellValue();
                    String DiaChi = cellIterator.next().getStringCellValue();
                    String SDT = cellIterator.next().getStringCellValue();
                    String tt = cellIterator.next().getStringCellValue();
                    int TrangThai = 1;
                    if(!tt.equals("Còn làm")){
                        TrangThai = 0;
                    }
                    
                    QuanlynhanvienBUS qlnv = new QuanlynhanvienBUS();
                    NhanVien oldnv = qlnv.getNhanVien(MaNV);
                    
                    if(oldnv != null){
                        if(!hanhdongkhitrung.contains("tất cả")){
                            Table mtb = new Table();
                            mtb.setHeaders(new String[]{"", "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Địa chỉ", "Số điện thoại", "Trạng thái"});
                            mtb.addRow(new String[]{"Cũ", oldnv.getMaNV(), oldnv.getTenNV(), oldnv.getNgaySinh(), oldnv.getDiaChi(), oldnv.getSDT()
                            , (oldnv.getTrangThai() == 1) ? "Còn làm" : "Hết làm"});
                            mtb.addRow(new String[]{"Mới", MaNV, TenNV, NgaySinh, DiaChi, SDT, tt});
                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhdongkhitrung);
                            hanhdongkhitrung = mop.getAnswer();
                        }
                        if(hanhdongkhitrung.contains("Ghi đè")){
                            qlnv.repairNhanVien(MaNV, TenNV, NgaySinh, DiaChi, SDT, TrangThai);
                            ++countGhide;
                        }
                        else {
                            ++countBoqua;
                        }
                    } else {
                        NhanVien q = new NhanVien(MaNV, TenNV, NgaySinh, DiaChi, SDT, TrangThai);
                        qlnv.addNhanVien(q);
                        ++countThem;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem
                    + "; Ghi đè " + countGhide
                    + "; Bỏ qua " + countBoqua
            );
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu" + e.getMessage());
        } finally {
            try{
                if (inpFile != null) {
                    inpFile.close();
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng file " + ex);
            }
        }
    }
    
    // Doc File Excel Nhân Viên
    public void DocExcelKhachHang(){
        fd.setTitle("Nhập dữ liệu nhân viên từ Excel");
        String url = getFile();
        if(url == null){
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thất bại!");
            return;
        }
        
        FileInputStream inpFile = null;
        try{
            inpFile = new FileInputStream(new File(url));
            
            HSSFWorkbook workbook = new HSSFWorkbook(inpFile);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            String hanhdongkhitrung = "";
            
            int countThem = 0, countGhide = 0, countBoqua = 0;
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while(cellIterator.hasNext()){
                    
                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String MaKH = cellIterator.next().getStringCellValue();
                    String TenKH = cellIterator.next().getStringCellValue();
                    String DiaChi = cellIterator.next().getStringCellValue();
                    String SDT = cellIterator.next().getStringCellValue();
                    String tt = cellIterator.next().getStringCellValue();
                    int TrangThai = 1;
                    if(!tt.equals("Hiện")){
                        TrangThai = 0;
                    }
                    
                    QuanlykhachhangBUS qlkh = new QuanlykhachhangBUS();
                    KhachHang oldkh = qlkh.getKhachHang(MaKH);
                    
                    if(oldkh != null){
                        if(!hanhdongkhitrung.contains("tất cả")){
                            Table mtb = new Table();
                            mtb.setHeaders(new String[]{"", "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại", "Trạng thái"});
                            mtb.addRow(new String[]{"Cũ", oldkh.getMaKH(), oldkh.getTenKH(), oldkh.getDiaChi(), oldkh.getSDT()
                            , (oldkh.getTrangThai() == 1) ? "Hiện" : "Ẩn"});
                            mtb.addRow(new String[]{"Mới", MaKH, TenKH, DiaChi, SDT, tt});
                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhdongkhitrung);
                            hanhdongkhitrung = mop.getAnswer();
                        }
                        if(hanhdongkhitrung.contains("Ghi đè")){
                            qlkh.repairKhachHang(MaKH, TenKH, DiaChi, SDT, TrangThai);
                            ++countGhide;
                        }
                        else {
                            ++countBoqua;
                        }
                    } else {
                        KhachHang q = new KhachHang(MaKH, TenKH, DiaChi, SDT, TrangThai);
                        qlkh.addKhachHang(q);
                        ++countThem;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem
                    + "; Ghi đè " + countGhide
                    + "; Bỏ qua " + countBoqua
            );
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu" + e.getMessage());
        } finally {
            try{
                if (inpFile != null) {
                    inpFile.close();
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng file " + ex);
            }
        }
    }
    
    // Doc File Excel Quyền
    public void DocExcelQuyen(){
        fd.setTitle("Nhập dữ liệu quyền từ Excel");
        String url = getFile();
        if(url == null){
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thất bại!");
            return;
        }
        
        FileInputStream inpFile = null;
        try{
            inpFile = new FileInputStream(new File(url));
            
            HSSFWorkbook workbook = new HSSFWorkbook(inpFile);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            String hanhdongkhitrung = "";
            
            int countThem = 0, countGhide = 0, countBoqua = 0;
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while(cellIterator.hasNext()){
                    
                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String MaQuyen = cellIterator.next().getStringCellValue();
                    String TenQuyen = cellIterator.next().getStringCellValue();
                    String ChiTietQuyen = cellIterator.next().getStringCellValue();
                    
                    QuanlyquyenBUS qlq = new QuanlyquyenBUS();
                    Quyen oldq = qlq.getQuyen(MaQuyen);
                    
                    if(oldq != null){
                        if(!hanhdongkhitrung.contains("tất cả")){
                            Table mtb = new Table();
                            mtb.setHeaders(new String[]{"", "Mã quyền", "Tên quyền", "Chi tiết quyền"});
                            mtb.addRow(new String[]{"Cũ", oldq.getMaQuyen(), oldq.getTenQuyen(), oldq.getChiTietQuyen()});
                            mtb.addRow(new String[]{"Mới", MaQuyen, TenQuyen, ChiTietQuyen});
                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhdongkhitrung);
                            hanhdongkhitrung = mop.getAnswer();
                        }
                        if(hanhdongkhitrung.contains("Ghi đè")){
                            qlq.repaireQuyen(MaQuyen, TenQuyen, ChiTietQuyen);
                            ++countGhide;
                        }
                        else {
                            ++countBoqua;
                        }
                    } else {
                        Quyen q = new Quyen(MaQuyen, TenQuyen, ChiTietQuyen);
                        qlq.addQuyen(q);
                        ++countThem;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem
                    + "; Ghi đè " + countGhide
                    + "; Bỏ qua " + countBoqua
            );
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu" + e.getMessage());
        } finally {
            try{
                if (inpFile != null) {
                    inpFile.close();
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng file " + ex);
            }
        }
    }
    
    // Doc File Excel Tài khoản
    public void DocExcelTaiKhoan(){
        fd.setTitle("Nhập dữ liệu tài khoản từ Excel");
        String url = getFile();
        if(url == null){
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thất bại!");
            return;
        }
        
        FileInputStream inpFile = null;
        try{
            inpFile = new FileInputStream(new File(url));
            
            HSSFWorkbook workbook = new HSSFWorkbook(inpFile);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            String hanhdongkhitrung = "";
            
            int countThem = 0, countGhide = 0, countBoqua = 0;
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while(cellIterator.hasNext()){
                    
                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String TenTaiKhoan = cellIterator.next().getStringCellValue();
                    String MatKhau = cellIterator.next().getStringCellValue();
                    String MaNV = cellIterator.next().getStringCellValue().split(" - ")[0];
                    String MaQuyen = cellIterator.next().getStringCellValue().split(" - ")[0];
                    
                    XulyTaiKhoanBUS xltk = new XulyTaiKhoanBUS();
                    TaiKhoan oldtk = xltk.getTaiKhoan(TenTaiKhoan);
                    
                    if(oldtk != null){
                        if(!hanhdongkhitrung.contains("tất cả")){
                            Table mtb = new Table();
                            mtb.setHeaders(new String[]{"", "Tên tài khoản", "Mật khẩu", "Mã nhân viên", "Mã quyền"});
                            mtb.addRow(new String[]{"Cũ", oldtk.getTenTaiKhoan(), oldtk.getMatKhau(), oldtk.getMaNV(), oldtk.getMaQuyen()});
                            mtb.addRow(new String[]{"Mới", TenTaiKhoan, MatKhau, MaNV, MaQuyen});
                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhdongkhitrung);
                            hanhdongkhitrung = mop.getAnswer();
                        }
                        if(hanhdongkhitrung.contains("Ghi đè")){
                            xltk.repairTaiKhoan(TenTaiKhoan, MatKhau, MaNV, MaQuyen);
                            ++countGhide;
                        }
                        else {
                            ++countBoqua;
                        }
                    } else {
                        TaiKhoan q = new TaiKhoan(TenTaiKhoan, MatKhau, MaNV, MaQuyen);
                        xltk.addTaiKhoan(q);
                        ++countThem;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem
                    + "; Ghi đè " + countGhide
                    + "; Bỏ qua " + countBoqua
            );
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu" + e.getMessage());
        } finally {
            try{
                if (inpFile != null) {
                    inpFile.close();
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng file " + ex);
            }
        }
    }
}
