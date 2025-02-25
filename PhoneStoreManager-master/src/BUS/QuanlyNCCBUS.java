
package BUS;

import DTO.NCC;
import DAO.QuanlyNCCDAO;
import java.util.ArrayList;

public class QuanlyNCCBUS {
    public  String[] getHeaders = {"STT", "Mã NCC", "Tên NCC", "Địa chỉ", "SDT", "Fax"};
    public  String[] getComboboxSearch = {"Tất cả", "Mã NCC", "Tên NCC", "Địa chỉ", "SDT", "Fax"};
    private  ArrayList<NCC> a;
    private QuanlyNCCDAO qlNCCDAO = new QuanlyNCCDAO();

    public QuanlyNCCBUS() {
        a = qlNCCDAO.SelectNCC();
    }
    
    public void readDB() {
        a = qlNCCDAO.SelectNCC();
    }
    
    public  NCC getNCC(String MaNCC){
        for(NCC ncc: a){
            if(ncc.getMaNCC().equals(MaNCC)){
                return ncc;
            }
        }
        return null;
    }
    

    public  ArrayList<NCC> getDSNCC() {
        return a;
    }
    
    public  String MaNCCnotHave(){
        String s = "";
        int max = 0;
        int temp = 0;
        for(NCC q: a){
            s = q.getMaNCC();
            s = s.trim();
            s = s.replace("NCC", "");
            temp = Integer.parseInt(s);
            if(max < temp){
                max = temp;
            }
        }
        ++max;
        return "NCC" + max;
    }
    
    public boolean repaireNCC(String MaNCC, String TenNCC, String DiaChi, String SDT, String Fax){
        if(qlNCCDAO.repairNCC(MaNCC, TenNCC, DiaChi, SDT, Fax)){
            for(NCC q: a){
                if(q.getMaNCC().equals(MaNCC)){
                    q.setTenNCC(TenNCC);
                    q.setDiaChi(DiaChi);
                    q.setSDT(SDT);
                    q.setFax(Fax);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean deleteNCC(String MaNCC){
        if(qlNCCDAO.deleteNCC(MaNCC)){
            int i = 0;
            for(NCC q: a){
                if(q.getMaNCC().equals(MaNCC)){
                    a.remove(i);
                    return true;
                }
                ++i;
            }
        }
        return false;
    }
    
    public boolean addNCC(NCC q){
        if(qlNCCDAO.addNCC(q)){
            a.add(q);
            return true;
        }
        return false;
    }
    
    public  ArrayList<NCC> SearchNCC(String s, String type){
        ArrayList<NCC> list = new ArrayList<>();
        s = s.toLowerCase();
        switch(type){
            case "Tất cả":{
                for(NCC q: a){
                    if(q.getMaNCC().toLowerCase().contains(s) || q.getTenNCC().toLowerCase().contains(s) || q.getDiaChi().toLowerCase().contains(s)
                            || q.getSDT().toLowerCase().contains(s) || q.getFax().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Mã NCC":{
                for(NCC q: a){
                    if(q.getMaNCC().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Tên NCC":{
                for(NCC q: a){
                    if(q.getTenNCC().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Địa chỉ":{
                for(NCC q: a){
                    if(q.getDiaChi().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "SDT":{
                for(NCC q: a){
                    if(q.getSDT().toLowerCase().contains(s)){
                        list.add(q);
                    }
                }
                break;
            }
            case "Fax":{
                for(NCC q: a){
                    if(q.getFax().toLowerCase().contains(s)){
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
    
}
