    
package DAO;

import DTO.Quyen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class QuanlyquyenDAO {
    private ConnectDB qlquyenDAO;
        private QuanlychitietquyenDAO chitietquyenDAO;


    public QuanlyquyenDAO() {
        qlquyenDAO = new ConnectDB();
                chitietquyenDAO = new QuanlychitietquyenDAO(); // Khởi tạo đối tượng QuanlychitietquyenDAO

        System.out.println("Database Quyền");
    }
//    
//    public ArrayList<Quyen> SelectQuyen(){
////        qlquyenDAO = new ConnectDB();
//        qlquyenDAO.OpenDB();
//            String sql = "SELECT * FROM phanquyen";
//        System.out.println("---SQL: " + sql);
//        ResultSet res = qlquyenDAO.SelectSQL(sql);
//        ArrayList<Quyen> a = new ArrayList<>();
//        if(res != null){
//            try {
//                while(res.next()){
//                    String MaQuyen = res.getString("MaQuyen");
//                    String TenQuyen = res.getString("TenQuyen");
//                    String ChiTietQuyen = res.getString("ChiTietQuyen");
//                    
//                    a.add(new Quyen(MaQuyen, TenQuyen, ChiTietQuyen));
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(QuanlyquyenDAO.class.getName()).log(Level.SEVERE, null, ex);
//                JOptionPane.showMessageDialog(null, "--ERROR! Lỗi đọc dữ liệu từ bảng phanquyen");
//            } finally{
//                qlquyenDAO.CloseDB();
//            }
//        }
//        return a;
//    }
    
    // --------------------------------------------------------------vip
    public ArrayList<Quyen> SelectMaChucNang() {
    qlquyenDAO.OpenDB();
    String sql = "SELECT pq.MaQuyen, pq.TenQuyen, GROUP_CONCAT(cq.MaChucNang) AS MaChucNang " +
                 "FROM phanquyen pq " +
                 "LEFT JOIN chitietquyen cq ON pq.MaQuyen = cq.MaQuyen " +
                 "GROUP BY pq.MaQuyen";
    
    System.out.println("---SQL: " + sql);
    ResultSet res = qlquyenDAO.SelectSQL(sql);
    ArrayList<Quyen> b = new ArrayList<>();
    
    if (res != null) {
        try {
            while (res.next()) {
                String MaQuyen = res.getString("MaQuyen");
                String TenQuyen = res.getString("TenQuyen");
                String ChiTietQuyen = res.getString("MaChucNang");
                b.add(new Quyen(MaQuyen, TenQuyen, ChiTietQuyen));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuanlyquyenDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "--ERROR! Lỗi đọc dữ liệu từ bảng phanquyen");
        } finally {
            qlquyenDAO.CloseDB();
        }
    }
    return b;
}
    
    
//     public ArrayList<Quyen> getAllQuyenWithChiTiet() {
//        ArrayList<Quyen> listQuyen = new ArrayList<>();
//        // Lấy danh sách quyền từ bảng phanquyen
//        qlquyenDAO.OpenDB();
//        String sql = "SELECT * FROM phanquyen";
//        System.out.println("---SQL: " + sql);
//        ResultSet res = qlquyenDAO.SelectSQL(sql);
//        if (res != null) {
//            try {
//                while (res.next()) {
//                    String MaQuyen = res.getString("MaQuyen");
//                    String TenQuyen = res.getString("TenQuyen");
//                    // Lấy danh sách mã chức năng tương ứng với mã quyền từ QuanlychitietquyenDAO
//                    ArrayList<String> ChiTietQuyen = chitietquyenDAO.getMaChucNangByMaQuyen(MaQuyen);
//                    listQuyen.add(new Quyen(MaQuyen, TenQuyen, ChiTietQuyen));
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(QuanlyquyenDAO.class.getName()).log(Level.SEVERE, null, ex);
//                JOptionPane.showMessageDialog(null, "--ERROR! Lỗi đọc dữ liệu từ bảng phanquyen");
//            } finally {
//                qlquyenDAO.CloseDB();
//            }
//        }
//        return listQuyen;
//    }
//--------------------------------------------------------------
    public boolean addQuyen(Quyen q){
        String sql = "INSERT INTO phanquyen VALUES('" + q.getMaQuyen() + "','" + q.getTenQuyen() + "','" + q.getChiTietQuyen() + "');";
        System.out.println("---SQL: " + sql);
        qlquyenDAO.OpenDB();
        boolean t = qlquyenDAO.UpdateSQL(sql);
        qlquyenDAO.CloseDB();
        return t;
    }
    
    public boolean deleteQuyen(String MaQuyen){
        String sql = "DELETE FROM phanquyen WHERE MaQuyen = '" + MaQuyen + "'";
        System.out.println("---SQL: " + sql);
        qlquyenDAO.OpenDB();
        boolean t = qlquyenDAO.UpdateSQL(sql);
        qlquyenDAO.CloseDB();
        return t;
    }
    
    public boolean repairQuyen(String MaQuyen, String TenQuyen, String ChiTietQuyen){
        String sql = "UPDATE phanquyen SET TenQuyen = '" + TenQuyen + "', ChiTietQuyen = '" + ChiTietQuyen + "' WHERE MaQuyen = '" + MaQuyen + "'";
        System.out.println("---SQL: " + sql);
        qlquyenDAO.OpenDB();
        boolean t = qlquyenDAO.UpdateSQL(sql);
        qlquyenDAO.CloseDB();
        return t;
    }
    
    public void close(){
        qlquyenDAO.CloseDB();
    }
    
    //-----------------------------------------------vip----------------------------------------------------
    public ArrayList<String> getMaChucNangByMaQuyen(String maQuyen) {
    ArrayList<String> maChucNangList = new ArrayList<>();
    qlquyenDAO.OpenDB();
    String sql = "SELECT MaChucNang FROM chitietquyen WHERE MaQuyen = '" + maQuyen + "'";
    ResultSet rs = qlquyenDAO.SelectSQL(sql);
    try {
        while (rs.next()) {
            String maChucNang = rs.getString("MaChucNang");
            maChucNangList.add(maChucNang);
        }
    } catch (SQLException ex) {
        Logger.getLogger(QuanlyquyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error! Unable to fetch data from chitietquyen table");
    } finally {
        qlquyenDAO.CloseDB();
    }
    return maChucNangList;
}
}
