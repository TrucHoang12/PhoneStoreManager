package DTO;

public class ChiTietQuyen {
    private String maQuyen;
    private String maChucNang;
    private boolean them;
    private boolean xoa;
    private boolean sua;
    private boolean xem;

    public ChiTietQuyen(String maQuyen, String maChucNang, boolean them, boolean xoa, boolean sua, boolean xem) {
        this.maQuyen = maQuyen;
        this.maChucNang = maChucNang;
        this.them = them;
        this.xoa = xoa;
        this.sua = sua;
        this.xem = xem;
    }

    public ChiTietQuyen() {
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(String maChucNang) {
        this.maChucNang = maChucNang;
    }

    public boolean isThem() {
        return them;
    }

    public void setThem(boolean them) {
        this.them = them;
    }

    public boolean isXoa() {
        return xoa;
    }

    public void setXoa(boolean xoa) {
        this.xoa = xoa;
    }

    public boolean isSua() {
        return sua;
    }

    public void setSua(boolean sua) {
        this.sua = sua;
    }

    public boolean isXem() {
        return xem;
    }

    public void setXem(boolean xem) {
        this.xem = xem;
    }
}
