
package BUS;

import DAO.QuanlyquyenDAO;
import DTO.Quyen;
import java.util.ArrayList;


public class QuanlyquyenBUS {
    public  String[] getComboboxSearch = {"Tất cả", "Mã quyền", "Tên quyền", "Chi tiết quyền"};
    public  String[] getHeaders = {"STT", "Mã quyền", "Tên quyền", "Chi tiết quyền"};
    private  ArrayList<Quyen> a;
        private  ArrayList<Quyen> b;

        private  ArrayList<Quyen> DSMaChucNang;

    private QuanlyquyenDAO qlquyenDAO = new QuanlyquyenDAO();

    public QuanlyquyenBUS() {
//        a = qlquyenDAO.SelectQuyen();
                b = qlquyenDAO.SelectMaChucNang();

        
        
    }
    
    // Trả về mã quyền 
    public  Quyen getQuyen(String MaQuyen){
        for(Quyen q: b){
            if(q.getMaQuyen().equals(MaQuyen)){
                return q;
            }
        }
        return null;
    }

//    public  ArrayList<Quyen> getDSQuyen() {
//        return a;
//    }
    
        public  ArrayList<Quyen> getDSChucNang() {
        return b;
    }
    
    
    
    public  String MaQuyennotHave(){
        String s = "";
        int max = 0;
        int temp = 0;
        for(Quyen q: b){
            s = q.getMaQuyen();
            s = s.trim();
            s = s.replace("Q", "");
            temp = Integer.parseInt(s);
            if(max < temp){
                max = temp;
            }
        }
        ++max;
        return "Q" + max;
    }
    
    public boolean repaireQuyen(String MaQuyen, String TenQuyen, String ChiTietQuyen){
        if(qlquyenDAO.repairQuyen(MaQuyen, TenQuyen, ChiTietQuyen)){
            for(Quyen q: b){
                if(q.getMaQuyen().equals(MaQuyen)){
                    q.setTenQuyen(TenQuyen);
                    q.setChiTietQuyen(ChiTietQuyen);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean deleteQuyen(String MaQuyen){
        if(qlquyenDAO.deleteQuyen(MaQuyen)){
            int i = 0;
            for(Quyen q: b){
                if(q.getMaQuyen().equals(MaQuyen)){
                    b.remove(i);
                    return true;
                }
                ++i;
            }
        }
        return false;
    }
    
    public boolean addQuyen(Quyen q){
        if(qlquyenDAO.addQuyen(q)){
            b.add(q);
            return true;
        }
        return false;
    }
    
    public  ArrayList<Quyen> SearchQuyen(String s, String type){
        ArrayList<Quyen> list = new ArrayList<>();
        s = s.toLowerCase();
        switch(type){
            case "Tất cả":{
                for(Quyen q: b){
                    if(q.getMaQuyen().toLowerCase().contains(s) || q.getTenQuyen().toLowerCase().contains(s) || q.getChiTietQuyen().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Mã quyền":{
                for(Quyen q: b){
                    if(q.getMaQuyen().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Tên quyền":{
                for(Quyen q: b){
                    if(q.getTenQuyen().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Chi tiết quyền":{
                for(Quyen q: b){
                    if(q.getChiTietQuyen().toLowerCase().contains(s)){
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
    
    
    //-------------------------------------VIP  
    
     public ArrayList<String> getDanhSachChucNangTheoQuyen(String maQuyen) {
        ArrayList<String> DSMaChucNang = new ArrayList<>();

        // Sử dụng phương thức từ QuanlyquyenDAO để lấy danh sách mã chức năng theo mã quyền
        ArrayList<String> maChucNangList = qlquyenDAO.getMaChucNangByMaQuyen(maQuyen);

        // Thêm mã chức năng vào danh sách chức năng
        DSMaChucNang.addAll(maChucNangList);

        return DSMaChucNang;
    }
   
}
