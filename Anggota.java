import java.util.ArrayList;

public class Anggota{

	private static ArrayList<Pemesanan> listPemesanan = new ArrayList<>();

	/**
	 * @param nama
	 * @return true if it Barang added and false if there is item with same name
	 */
	public static void addPemesananBerhasil(Barang barang) {
		listPemesanan.add(new Pemesanan(barang, "Disetujui"));
	}

	public static void addPemesananDitolak(Barang barang) {
		listPemesanan.add(new Pemesanan(barang, "Ditolak"));
	}

	public static ArrayList<Pemesanan> getListPemesanan() {
		return listPemesanan;
	}
}
