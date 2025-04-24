package GUI.giaodienadmin;

public class ChucNang {
    private String id;
    private String tenChucNang;
    private String iconHinh;
    public ChucNang(String id, String tenChucNang, String iconHinh) {
        this.id = id;
        this.tenChucNang = tenChucNang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }
    public String getIconHinh() {
        return iconHinh;
    }
    public void setIconHinh(String iconHinh) {
        this.iconHinh = iconHinh;
    }
}