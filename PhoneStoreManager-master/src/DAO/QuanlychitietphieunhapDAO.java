
package DAO;

import DTO.ChiTietPhieuNhap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class QuanlychitietphieunhapDAO {
    private ConnectDB qlctpnDAO;

    public QuanlychitietphieunhapDAO() {
        qlctpnDAO = new ConnectDB();
        System.out.println("Database Chi Tiết Phiếu Nhập");
    }
    
    public ArrayList<ChiTietPhieuNhap> SelectChiTietPN(){
        qlctpnDAO.OpenDB();
        String sql = "SELECT * FROM chitietphieunhap";
        System.out.println("---SQL: " + sql);
        ResultSet res = qlctpnDAO.SelectSQL(sql);
        ArrayList<ChiTietPhieuNhap> a = new ArrayList<>();
        if(res != null){
            try {
                while(res.next()){
                    ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
                    ctpn.setMaPN(res.getString("MaPN"));
                    ctpn.setMaSP(res.getString("MaSP"));
                    ctpn.setSoLuong(res.getInt("SoLuong"));
                    ctpn.setDonGia(res.getLong("DonGia"));
                    a.add(ctpn);
                }
            } catch (SQLException ex) {
                Logger.getLogger(QuanlychitietphieunhapDAO.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "--ERROR! Lỗi đọc dữ liệu từ bảng chitietphieunhap");
            } finally{
                qlctpnDAO.CloseDB();
            }
        }
        return a;
    }
    
    public boolean addChiTietPhieuNhap(ChiTietPhieuNhap ctpn){
        qlctpnDAO.OpenDB();
        boolean t = qlctpnDAO.UpdateSQL("INSERT INTO chitietphieunhap(MaPN,MaSP,SoLuong,DonGia) VALUES ('" 
                + ctpn.getMaPN()+ "','" 
                + ctpn.getMaSP()+ "','" 
                + ctpn.getSoLuong()+ "','" 
                + ctpn.getDonGia()+ "');");
        qlctpnDAO.CloseDB();
        return t;
    }
    
    public boolean deleteChiTietPhieuNhap(String MaPN, String MaSP){
        qlctpnDAO.OpenDB();
        boolean t = qlctpnDAO.UpdateSQL("DELETE FROM chitietphieunhap WHERE "
                + "MaHD='" + MaPN
                + "' AND MaSP='" + MaSP + "';");
        qlctpnDAO.CloseDB();
        return t;
    }
    
    public Boolean deleteAll(String MaPN) {
        qlctpnDAO.OpenDB();
        boolean t = qlctpnDAO.UpdateSQL("DELETE FROM chitietphieunhap WHERE MaPN='" + MaPN + "';");
        qlctpnDAO.CloseDB();
        return t;
    }
    
    public boolean repairChiTietPhieuNhap(ChiTietPhieuNhap ct){
        qlctpnDAO.OpenDB();
        boolean t = qlctpnDAO.UpdateSQL("UPDATE chitietphieunhap set "
                + "SoLuong='" + ct.getSoLuong()
                + "', DonGia='" + ct.getDonGia()
                + "' WHERE MaPN='" + ct.getMaPN()+ "' AND MaSP='" + ct.getMaSP()+ "';");
        qlctpnDAO.CloseDB();
        return t;
    }
    
    public boolean repairChiTietPhieuNhap(String MaPN, String MaSP, int SoLuong, long DonGia){
        ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(MaPN, MaSP, SoLuong, DonGia);
        return repairChiTietPhieuNhap(ctpn);
    }
    
    public void close(){
        qlctpnDAO.CloseDB();
    }
}
