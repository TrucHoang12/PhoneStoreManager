
package BUS;

import DTO.PhieuNhap;
import DAO.QuanlyphieunhapDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class QuanlyphieunhapBUS {
    public  String[] getComboboxSearch = {"Tất cả", "Mã phiếu nhập", "Mã nhà cung cấp", "Mã nhân viên", "Ngày nhập", "Giờ nhập", "Tổng tiền"};
    public  String[] getHeaders = {"STT", "Mã phiếu nhập", "Mã nhà cung cấp", "Mã nhân viên", "Ngày nhập", "Giờ nhập", "Tổng tiền"};
    private  ArrayList<PhieuNhap> a;
    private QuanlyphieunhapDAO qlpnDAO = new QuanlyphieunhapDAO();
    
    public void readDB() {
        a = qlpnDAO.SelectPhieuNhap();
    }

    public QuanlyphieunhapBUS() {
        a = qlpnDAO.SelectPhieuNhap();
    }
    
    public  PhieuNhap getPhieuNhap(String Mapn){
        for(PhieuNhap q: a){
            if(q.getMaPN().equals(Mapn)){
                return q;
            }
        }
        return null;
    }

    public  ArrayList<PhieuNhap> getDSPN() {
        return a;
    }
    
    public  String MaPNnotHave(){
        String s = "";
        int max = 0;
        int temp = 0;
        for(PhieuNhap q: a){
            s = q.getMaPN();
            s = s.trim();
            s = s.replace("PN", "");
            temp = Integer.parseInt(s);
            if(max < temp){
                max = temp;
            }
        }
        ++max;
        return "PN" + max;
    }
    
    public boolean repairePhieuNhap(String MaPN, String MaNCC, String MaNV, LocalDate NgayNhap, LocalTime GioNhap, long TongTien){
        if(qlpnDAO.repairPhieuNhap(MaPN, MaNCC, MaNV, NgayNhap, GioNhap, TongTien)){
            for(PhieuNhap q: a){
                if(q.getMaPN().equals(MaPN)){
                    q.setMaNV(MaNV);
                    q.setMaNCC(MaNCC);
                    q.setMaNV(MaNV);
                    q.setNgayNhap(NgayNhap);
                    q.setGioNhap(GioNhap);
                    q.setTongTien(TongTien);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean deletePhieuNhap(String Mapn){
        if(qlpnDAO.deletePhieuNhap(Mapn)){
            int i = 0;
            for(PhieuNhap q: a){
                if(q.getMaPN().equals(Mapn)){
                    a.remove(i);
                    return true;
                }
                ++i;
            }
        }
        return false;
    }
    
    public boolean addPhieuNhap(PhieuNhap q){
        if(qlpnDAO.addPhieuNhap(q)){
            a.add(q);
            return true;
        }
        return false;
    }
    
    //search thống kê
    public ArrayList<PhieuNhap> search(String type, String value, LocalDate _ngay1, LocalDate _ngay2, int _tong1, int _tong2) {
        ArrayList<PhieuNhap> result = new ArrayList<>();

        a.forEach((pn) -> {
            if (type.equals("Tất cả")) {
                if (pn.getMaPN().toLowerCase().contains(value.toLowerCase())
                        || pn.getMaNCC().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getMaNV()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getNgayNhap()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getGioNhap()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getTongTien()).toLowerCase().contains(value.toLowerCase())) {
                    result.add(pn);
                }
            } else {
                switch (type) {
                    case "Mã phiếu nhập":
                        if (pn.getMaPN().toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Mã nhà cung cấp":
                        if (pn.getMaNCC().toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Mã nhân viên":
                        if (String.valueOf(pn.getMaNV()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Ngày nhập":
                        if (String.valueOf(pn.getNgayNhap()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Giờ nhập":
                        if (String.valueOf(pn.getGioNhap()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Tổng tiền":
                        if (String.valueOf(pn.getTongTien()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                }
            }

        });
        //Ngay lap, tong tien
        for (int i = result.size() - 1; i >= 0; i--) {
            PhieuNhap pn = result.get(i);
            LocalDate ngaynhap = pn.getNgayNhap();
            float tongtien = pn.getTongTien();

            Boolean ngayKhongThoa = (_ngay1 != null && ngaynhap.isBefore(_ngay1)) || (_ngay2 != null && ngaynhap.isAfter(_ngay2));
            Boolean tienKhongThoa = (_tong1 != -1 && tongtien < _tong1) || (_tong2 != -1 && tongtien > _tong2);

            if (ngayKhongThoa || tienKhongThoa) {
                result.remove(pn);
            }
        }

        return result;
    }
    
    public  ArrayList<PhieuNhap> SearchPhieuNhap(String s, String type){
        ArrayList<PhieuNhap> list = new ArrayList<>();
        s = s.toLowerCase();
        switch(type){
            case "Tất cả":{
                for(PhieuNhap q: a){
                    if(q.getMaPN().toLowerCase().contains(s) || q.getMaNCC().toLowerCase().contains(s) || q.getMaNV().toLowerCase().contains(s) || 
                            q.getNgayNhap().toString().contains(s) || q.getGioNhap().toString().contains(s) ||
                            Long.toString(q.getTongTien()).contains(s)) {
                        list.add(q);
                    }
                }
                break;
            }
            case "Mã phiếu nhập":{
                for(PhieuNhap q: a){
                    if(q.getMaPN().toLowerCase().contains(s) ){
                        list.add(q);
                    }
                }
                break;
            }
            case "Mã nhà cung cấp":{
                for(PhieuNhap q: a){
                    if(q.getMaNCC().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Mã nhân viên":{
                for(PhieuNhap q: a){
                    if(q.getMaNV().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Ngày nhập":{
                for(PhieuNhap q: a){
                    if(q.getNgayNhap().toString().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Giờ nhập":{
                for(PhieuNhap q: a){
                    if(q.getGioNhap().toString().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Tổng tiền":{
                for(PhieuNhap q: a){
                    if(Long.toString(q.getTongTien()).contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            
            default:break;
        }
        if(list.size() > 0) return list;
        return null;
    }
    
    public  ArrayList<PhieuNhap> SearchPNFromDate(int yearfrom, int monthfrom, int dayfrom, int yearto, int monthto, int dayto){
        if(yearfrom < 0 || monthfrom < 0 || dayfrom < 0 || yearto < 0 || monthto < 0 || dayto < 0){
            return null;
        }
        
        if(!CompareDate(yearfrom, monthfrom, dayfrom, yearto, monthto, dayto)){
            return null;
        }
        ArrayList<PhieuNhap> list = new ArrayList<>();
        String[] s;
        int y ,m , d;
        for(PhieuNhap q : a){
            s = q.getNgayNhap().toString().split("-");
            y = Integer.parseInt(s[0]);
            m = Integer.parseInt(s[1]);
            d = Integer.parseInt(s[2]);
            if(CompareDate(yearfrom, monthfrom, dayfrom, y, m, d) && CompareDate(y, m, d, yearto, monthto, dayto)){
                list.add(q);
            }
        }
        if(list.size() <= 0) return null;
        return list;
    }
    
    public  ArrayList<PhieuNhap> SearchPhieuNhapTongTienFromTo(long from, long to){
        if(from > to) return null;
        ArrayList<PhieuNhap> list = new ArrayList<>();
        for(PhieuNhap q: a){
            if(from <= q.getTongTien() && q.getTongTien() <= to){
                list.add(q);
            }
        }
        if(list.size() <= 0) return null;
        return list;
    }
    
    private  boolean CompareDate(int y1, int m1, int d1, int y2, int m2, int d2){
        if(y1 < y2) return true;
        if(y1 > y2) return false;
        if(m1 < m2) return true;
        if(m1 > m2) return false;
        if(d1 < d2) return true;
        if(d1 > d2) return false;
        return true;
    }
}
