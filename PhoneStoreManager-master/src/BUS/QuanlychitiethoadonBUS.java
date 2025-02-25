
package BUS;

import DTO.ChiTietHoaDon;
import DAO.QuanlychitiethoadonDAO;
import DAO.QuanlyhoadonDAO;
import java.util.ArrayList;

public class QuanlychitiethoadonBUS {
    public  String[] getComboboxSearch = {"Tất cả", "Mã sản phẩm", "Số lượng", "Đơn giá"};
    public  String[] getHeaders = {"STT", "Mã sản phẩm", "Số lượng", "Đơn giá"};
    private  ArrayList<ChiTietHoaDon> a;
    private QuanlychitiethoadonDAO qlcthdDAO = new QuanlychitiethoadonDAO();
    
    public void readDB() {
        a = qlcthdDAO.SelectChiTietHoaDon();
    }
    
    public QuanlychitiethoadonBUS() {
        a = qlcthdDAO.SelectChiTietHoaDon();
    }
    
    public ChiTietHoaDon getChiTiet(String mahd, String masp) {
        for (ChiTietHoaDon q : a) {
            if (q.getMaSP().equals(masp) && q.getMaHD().equals(mahd)) {
                return q;
            }
        }
        return null;
    }
    
    public  ArrayList<ChiTietHoaDon> getChiTietHoaDon(String MaHD){
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        for(ChiTietHoaDon q: a){
            if(q.getMaHD().equals(MaHD)){
                list.add(q);
            }
        }
        if(list.size() <= 0) return null;
        return list;
    }

    public  ArrayList<ChiTietHoaDon> getDSCTHD() {
        return a;
    }
    
    public boolean repaireChiTietHoaDon(String MaHD, String MaSP, int SoLuong, long DonGia){
        if(qlcthdDAO.repairChiTietHoaDon(MaHD, MaSP, SoLuong,  DonGia)){
            for(ChiTietHoaDon q: a){
                if(q.getMaHD().equals(MaHD) && q.getMaSP().equals(MaSP)){
                    q.setMaSP(MaSP);
                    q.setSoLuong(SoLuong);
                    q.setDonGia(DonGia);
                    return UpdateTongTien(MaHD);
                }
            }
        }
        return false;
    }
    
    public boolean UpdateTongTien(String MaHD){
        long tong = 0;
        for(ChiTietHoaDon cthd : a){
            if(cthd.getMaHD().equals(MaHD)){
                tong += (long)(cthd.getSoLuong() * cthd.getDonGia());
            }
        }
        QuanlyhoadonDAO qlhdDAO = new QuanlyhoadonDAO();
        return qlhdDAO.UpdateTongTien(MaHD, tong);
    }
    
    public boolean deleteCTHD(String MaHD, String MaSP){
        if(qlcthdDAO.deleteChiTietHoaDon(MaHD, MaSP)){
            int i = 0;
            for(ChiTietHoaDon q: a){
                if(q.getMaHD().equals(MaHD) && q.getMaSP().equals(MaSP)){
                    a.remove(i);
                    return true;
                }
                ++i;
            }
        }
        return false;
    }
    
    public boolean addChiTietHoaDon(ChiTietHoaDon q){
        if(qlcthdDAO.addChiTietHoaDon(q)){
            a.add(q);
            return true;
        }
        return false;
    }
    
    public ArrayList<ChiTietHoaDon> search(String type, String keyword, int soLuong1, int soLuong2, float thanhTien1, float thanhTien2) {
        ArrayList<ChiTietHoaDon> result = new ArrayList<>();

        a.forEach((hd) -> {
            switch (type) {
                case "Tất cả":
                    if (hd.getMaHD().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getMaSP().toLowerCase().contains(keyword.toLowerCase())
                            || String.valueOf(hd.getSoLuong()).toLowerCase().contains(keyword.toLowerCase())
                            || String.valueOf(hd.getDonGia()).toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }

                    break;

                case "Mã hóa đơn":
                    if (hd.getMaHD().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Mã sản phẩm":
                    if (hd.getMaSP().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Số lượng":
                    if (String.valueOf(hd.getSoLuong()).toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Đơn giá":
                    if (String.valueOf(hd.getDonGia()).toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;
            }
        });

        for (int i = result.size() - 1; i >= 0; i--) {
            ChiTietHoaDon ct = result.get(i);
            int sl = ct.getSoLuong();
            float tt = ct.getDonGia() * sl;

            Boolean soLuongKhongThoa = (soLuong1 != -1 && sl < soLuong1) || (soLuong2 != -1 && sl > soLuong2);
            Boolean donGiaKhongThoa = (thanhTien1 != -1 && tt < thanhTien1) || (thanhTien2 != -1 && tt > thanhTien2);

            if (soLuongKhongThoa || donGiaKhongThoa) {
                result.remove(ct);
            }
        }
        return result;
    }
}
