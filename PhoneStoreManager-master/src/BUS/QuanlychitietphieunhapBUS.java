
package BUS;

import DAO.QuanlychitietphieunhapDAO;
import DTO.ChiTietPhieuNhap;
import DAO.QuanlyphieunhapDAO;
import java.util.ArrayList;

public class QuanlychitietphieunhapBUS {
    public  String[] getComboboxSearch = {"Tất cả", "Mã sản phẩm", "Số lượng", "Đơn giá"};
    public  String[] getHeaders = {"STT", "Mã sản phẩm", "Số lượng", "Đơn giá"};
    private  ArrayList<ChiTietPhieuNhap> a;
    private QuanlychitietphieunhapDAO qlctpnDAO = new QuanlychitietphieunhapDAO();

    public QuanlychitietphieunhapBUS() {
        a = qlctpnDAO.SelectChiTietPN();
    }
    public void readDB() {
        a = qlctpnDAO.SelectChiTietPN();
    }
    public  ArrayList<ChiTietPhieuNhap> getChiTietPhieuNhap(String MaPN){
        ArrayList<ChiTietPhieuNhap> list = new ArrayList<>();
        for(ChiTietPhieuNhap q: a){
            if(q.getMaPN().equals(MaPN)){
                list.add(q);
            }
        }
        if(list.size() <= 0) return null;
        return list;
    }
    
    public ChiTietPhieuNhap getChiTiet(String mapn, String masp) {
        for (ChiTietPhieuNhap ct : a) {
            if (ct.getMaSP().equals(masp) && ct.getMaPN().equals(mapn)) {
                return ct;
            }
        }
        return null;
    }

    public  ArrayList<ChiTietPhieuNhap> getDSCTPN() {
        return a;
    }
    
    public ArrayList<ChiTietPhieuNhap> search(String type, String value) {

        ArrayList<ChiTietPhieuNhap> result = new ArrayList<>();
        a.forEach((ctpn) -> {
            if (type.equals("Tất cả")) {
                if (ctpn.getMaPN().toLowerCase().contains(value.toLowerCase())
                        || ctpn.getMaSP().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(ctpn.getDonGia()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(ctpn.getSoLuong()).toLowerCase().contains(value.toLowerCase())) {
                    result.add(ctpn);
                }
            } else {
                switch (type) {
                    case "Mã phiếu nhập":
                        if (ctpn.getMaPN().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ctpn);
                        }
                        break;
                    case "Mã sản phẩm":
                        if (ctpn.getMaSP().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ctpn);
                        }
                        break;
                    case "Đơn giá":
                        if (String.valueOf(ctpn.getDonGia()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(ctpn);
                        }
                        break;
                    case "Số lượng":
                        if (String.valueOf(ctpn.getSoLuong()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(ctpn);
                        }
                        break;
                }
            }

        });

        return result;
    }
    
    public boolean repaireChiTietPhieuNhap(String MaPN, String MaSP, int SoLuong, long DonGia){
        if(qlctpnDAO.repairChiTietPhieuNhap(MaPN, MaSP, SoLuong,  DonGia)){
            for(ChiTietPhieuNhap q: a){
                if(q.getMaPN().equals(MaPN) && q.getMaSP().equals(MaSP)){
                    q.setMaSP(MaSP);
                    q.setSoLuong(SoLuong);
                    q.setDonGia(DonGia);
                    return UpdateTongTien(MaPN);
                }
            }
        }
        return false;
    }
    
    public boolean UpdateTongTien(String MaPN){
        long tong = 0;
        for(ChiTietPhieuNhap ctpn : a){
            if(ctpn.getMaPN().equals(MaPN)){
                tong += (long)(ctpn.getSoLuong() * ctpn.getDonGia());
            }
        }
        QuanlyphieunhapDAO qlpnDAO = new QuanlyphieunhapDAO();
        return qlpnDAO.UpdateTongTien(MaPN, tong);
    }
    
    public boolean deleteCTPN(String MaPN, String MaSP){
        if(qlctpnDAO.deleteChiTietPhieuNhap(MaPN, MaSP)){
            int i = 0;
            for(ChiTietPhieuNhap q: a){
                if(q.getMaPN().equals(MaPN) && q.getMaSP().equals(MaSP)){
                    a.remove(i);
                    return true;
                }
                ++i;
            }
        }
        return false;
    }
    
    public boolean addChiTietPhieuNhap(ChiTietPhieuNhap q){
        if(qlctpnDAO.addChiTietPhieuNhap(q)){
            a.add(q);
            return true;
        }
        return false;
    }
}
