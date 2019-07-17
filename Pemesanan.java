public class Pemesanan {
    private Barang barang;
    private String status;

    public Pemesanan(Barang barang, String status) {
        this.barang = barang;
        this.status = status;
    }

    public Barang getBarang() {
        return barang;
    }

    public String getStatus() {
        return status;
    }
 }