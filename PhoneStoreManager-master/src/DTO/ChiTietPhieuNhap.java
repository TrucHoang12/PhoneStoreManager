
package DTO;

public class ChiTietPhieuNhap {
    private String MaPN;
    private String MaSP;
    private int SoLuong;
    private long DonGia;

    public ChiTietPhieuNhap(String MaPN, String MaSP, int SoLuong, long DonGia) {
        this.MaPN = MaPN;
        this.MaSP = MaSP;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
    }
    
    public ChiTietPhieuNhap() {
    }

    public String getMaPN() {
        return MaPN;
    }

    public String getMaSP() {
        return MaSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public long getDonGia() {
        return DonGia;
    }

    public void setMaPN(String MaPN) {
        this.MaPN = MaPN;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public void setDonGia(long DonGia) {
        this.DonGia = DonGia;
    }
    
    
}
