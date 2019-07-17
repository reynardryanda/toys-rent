import java.util.ArrayList;

public class Admin {

	private static ArrayList<Kategori> listKategori = new ArrayList<>();

	/**
	 * @param nama
	 * @return true if kategori added and false if there is kategori with same name
	 */
	public static boolean createKategori(String nama) {
		for (Kategori kategori: listKategori) {
			if (kategori.getNama().equals(nama)) {
				return false;
			}
		}

		listKategori.add(new Kategori(nama));
		return true;
	}

	/**
	 *
	 * @return list of kategori
	 */
	public static ArrayList<Kategori> getListKategori() {
		return listKategori;
	}

	/**
	 *
	 * @return ArrayList of nama kategori
	 */
	public static ArrayList<String> getNamaKategori() {
		ArrayList<String> namaKategori = new ArrayList<>();
		for (Kategori kategori : listKategori) {
			namaKategori.add(kategori.getNama());
		}

		return namaKategori;
	}

	/**
	 *
	 * @param namaKategori
	 * @param namaBarang
	 * @return
	 */
	public static boolean createBarang(String namaKategori, String namaBarang) {
		Kategori kategoriTemp = null;

		for (Kategori kategori : listKategori) {
			if (kategori.getNama().equals(namaKategori)) {
				kategoriTemp = kategori;
			}
		}

		if (kategoriTemp == null) return false;

		if (kategoriTemp.checkBarang(namaBarang)) {
			Barang barang = new Barang(kategoriTemp, namaBarang);
			kategoriTemp.addBarang(barang);

			return true;
		}
		else {
			return false;
		}
	}

	public static ArrayList<Barang> getPendingBarang() {
		ArrayList<Barang> listBarang = new ArrayList<Barang>();

		for (Kategori kategori : listKategori) {
			ArrayList<Barang> tmp = kategori.getPendingBarang();
			if(tmp != null) {
				listBarang.addAll(tmp);
			}
		}

		return listBarang;
	}

}
