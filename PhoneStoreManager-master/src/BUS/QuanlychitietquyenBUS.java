package BUS;

import DAO.QuanlychitietquyenDAO;
import DTO.ChiTietQuyen;
import java.util.ArrayList;

public class QuanlychitietquyenBUS {
    private ArrayList<ChiTietQuyen> listChitietQuyen;
    private QuanlychitietquyenDAO chitietquyenDAO;

    public QuanlychitietquyenBUS() {
        chitietquyenDAO = new QuanlychitietquyenDAO();
        listChitietQuyen = chitietquyenDAO.getAllChiTietQuyen();
    }

    public ArrayList<ChiTietQuyen> getAllChiTietQuyen() {
        return listChitietQuyen;
    }
     public ArrayList<ChiTietQuyen> getChiTietQuyenByMaQuyen(String maQuyen) {
        return chitietquyenDAO.getChiTietQuyenByMaQuyen(maQuyen);
    }
    // Trả về mã quyền 
    public ChiTietQuyen getQuyen(String MaQuyen) {
        for (ChiTietQuyen q : listChitietQuyen) {
            if (q.getMaQuyen().equals(MaQuyen)) {
                return q;
            }
        }
        return null;
    }

    public ChiTietQuyen getChiTietQuyen(String maQuyen, String maChucNang) {
        for (ChiTietQuyen chitiet : listChitietQuyen) {
            if (chitiet.getMaQuyen().equals(maQuyen) && chitiet.getMaChucNang().equals(maChucNang)) {
                return chitiet;
            }
        }
        return null;
    }

    public boolean updateChiTietQuyen(ChiTietQuyen chitiet) {
        if (chitietquyenDAO.updateChiTietQuyen(chitiet)) {
            // Cập nhật lại danh sách chi tiết quyền nếu cập nhật thành công
            for (int i = 0; i < listChitietQuyen.size(); i++) {
                ChiTietQuyen oldChitiet = listChitietQuyen.get(i);
                if (oldChitiet.getMaQuyen().equals(chitiet.getMaQuyen()) && oldChitiet.getMaChucNang().equals(chitiet.getMaChucNang())) {
                    listChitietQuyen.set(i, chitiet);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean addChiTietQuyen(ChiTietQuyen chitiet) {
        if (chitietquyenDAO.addChiTietQuyen(chitiet)) {
            listChitietQuyen.add(chitiet);
            return true;
        }
        return false;
    }

    public boolean deleteChiTietQuyen(String maQuyen) {
        if (chitietquyenDAO.deleteChiTietQuyen(maQuyen)) {
            listChitietQuyen.removeIf(chitiet -> chitiet.getMaQuyen().equals(maQuyen) );
            return true;
        }
        return false;
    }
}
