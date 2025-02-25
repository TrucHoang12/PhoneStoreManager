package DAO;

import DTO.ChiTietQuyen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class QuanlychitietquyenDAO {
    private ConnectDB chitietquyenDAO;

    public QuanlychitietquyenDAO() {
        chitietquyenDAO = new ConnectDB();
        System.out.println("Database Chi Tiết Quyền");
    }

    public boolean addChiTietQuyen(ChiTietQuyen chiTietQuyen) {
        String sql = "INSERT INTO chitietquyen (MaQuyen, MaChucNang, Them, Xoa, Sua, Xem) VALUES ('"
                     + chiTietQuyen.getMaQuyen() + "', '"
                     + chiTietQuyen.getMaChucNang() + "', "
                     + (chiTietQuyen.isThem() ? 1 : 0) + ", "
                     + (chiTietQuyen.isXoa() ? 1 : 0) + ", "
                     + (chiTietQuyen.isSua() ? 1 : 0) + ", "
                     + (chiTietQuyen.isXem() ? 1 : 0) + ")";
        System.out.println("---SQL: " + sql);
        chitietquyenDAO.OpenDB();
        boolean success = chitietquyenDAO.UpdateSQL(sql);
        chitietquyenDAO.CloseDB();
        return success;
    }

   public boolean deleteChiTietQuyen(String maQuyen) {
        String sql = "DELETE FROM chitietquyen WHERE MaQuyen = '" + maQuyen + "'";
        System.out.println("---SQL: " + sql);
        chitietquyenDAO.OpenDB();
        boolean success = chitietquyenDAO.UpdateSQL(sql);
        chitietquyenDAO.CloseDB();
        return success;
    }

   public boolean updateChiTietQuyen(ChiTietQuyen chiTietQuyen) {
    String sql = "UPDATE chitietquyen SET "
               + "Them = " + (chiTietQuyen.isThem() ? 1 : 0) + ", "
               + "Xoa = " + (chiTietQuyen.isXoa() ? 1 : 0) + ", "
               + "Sua = " + (chiTietQuyen.isSua() ? 1 : 0) + ", "
               + "Xem = " + (chiTietQuyen.isXem() ? 1 : 0) + " "
               + "WHERE MaQuyen = '" + chiTietQuyen.getMaQuyen() + "' AND MaChucNang = '" + chiTietQuyen.getMaChucNang() + "'";
    System.out.println("---SQL: " + sql);
    chitietquyenDAO.OpenDB();
    boolean success = chitietquyenDAO.UpdateSQL(sql);
    chitietquyenDAO.CloseDB();
    return success;
}



    public ArrayList<ChiTietQuyen> getAllChiTietQuyen() {
        ArrayList<ChiTietQuyen> chiTietQuyenList = new ArrayList<>();
        chitietquyenDAO.OpenDB();
        String sql = "SELECT * FROM chitietquyen";
        System.out.println("---SQL: " + sql);
        ResultSet rs = chitietquyenDAO.SelectSQL(sql);
        try {
            while (rs.next()) {
                String maQuyen = rs.getString("MaQuyen");
                String maChucNang = rs.getString("MaChucNang");
                boolean them = rs.getBoolean("Them");
                boolean xoa = rs.getBoolean("Xoa");
                boolean sua = rs.getBoolean("Sua");
                boolean xem = rs.getBoolean("Xem");
                ChiTietQuyen chiTietQuyen = new ChiTietQuyen(maQuyen, maChucNang, them, xoa, sua, xem);
                chiTietQuyenList.add(chiTietQuyen);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuanlychitietquyenDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "--ERROR! Lỗi đọc dữ liệu từ bảng chitietquyen");
        } finally {
            chitietquyenDAO.CloseDB();
        }
        return chiTietQuyenList;
    }
    
    public ArrayList<ChiTietQuyen> getChiTietQuyenByMaQuyen(String maQuyen) {
    ArrayList<ChiTietQuyen> chiTietQuyenList = new ArrayList<>();
    chitietquyenDAO.OpenDB();
    String sql = "SELECT * FROM chitietquyen WHERE MaQuyen = '" + maQuyen + "'";
    System.out.println("---SQL: " + sql);
    ResultSet rs = chitietquyenDAO.SelectSQL(sql);
    try {
        while (rs.next()) {
            String maChucNang = rs.getString("MaChucNang");
            boolean them = rs.getBoolean("Them");
            boolean xoa = rs.getBoolean("Xoa");
            boolean sua = rs.getBoolean("Sua");
            boolean xem = rs.getBoolean("Xem");
            ChiTietQuyen chiTietQuyen = new ChiTietQuyen(maQuyen, maChucNang, them, xoa, sua, xem);
            chiTietQuyenList.add(chiTietQuyen);
        }
    } catch (SQLException ex) {
        Logger.getLogger(QuanlychitietquyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "--ERROR! Lỗi đọc dữ liệu từ bảng chitietquyen");
    } finally {
        chitietquyenDAO.CloseDB();
    }
    return chiTietQuyenList;
}
}
