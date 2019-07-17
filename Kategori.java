import java.util.ArrayList;
import java.util.List;

public class Kategori {

	private String nama;
	private ArrayList<Barang> listBarang;

	/**
	 *
	 * @param nama
	 */
	public Kategori(String nama) {
		this.nama = nama;
		listBarang = new ArrayList<>();
	}

	/**
	 *
	 * @param barang
	 */
	public void addBarang(Barang barang) {
		listBarang.add(barang);
	}

	/**
	 *
	 * @return
	 */
	public List<Barang> getListBarang() {
		return listBarang;
	}

	/**
	 *
	 * @return
	 */
	public String getNama() {
		return nama;
	}

	/**
	 *
	 * @param namaBarang
	 * @return
	 */
	public boolean checkBarang(String namaBarang) {
		for(Barang barang: listBarang) {
			if(barang.getNama().equals(namaBarang)) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<String> getNamaBarang() {
		ArrayList<String> namaBarang = new ArrayList<>();

		for (Barang barang: listBarang) {
			namaBarang.add(barang.getNama());
		}

		return namaBarang;
	}

	/**
	 *
	 * @return list of string barang disewakan
	 */
	public ArrayList<String> getNamaBarangDisewakan() {
		ArrayList<String> namaBarang = new ArrayList<>();

		for (Barang barang: listBarang) {
			if (barang.isDisewakan()) namaBarang.add(barang.getNama());
		}

		return namaBarang;
	}

	/**
	 *
	 * @param namaBarang
	 * @return
	 */
	public Barang findBarang(String namaBarang) {
		for (Barang barang: listBarang) {
			if (barang.getNama().equals(namaBarang)) return barang;
		}
		return null;
	}

	/**
	 *
	 * @param index
	 * @return
	 */
	public Barang findBarang(Integer index) {
		return listBarang.get(index);
	}

	/**
	 *
	 * @param index
	 * @return
	 */
	public Barang findDisewakanBarang(int index) {
		System.out.println(listBarang.size());
		int count = -1;
		for (Barang barang: listBarang) {
			if (barang.isDisewakan()) count++;

			if(count == index) {
				return barang;
			}
		}

		return null;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<Barang> getPendingBarang() {
		ArrayList<Barang> listPendingBarang = new ArrayList<>();

		for (Barang barang: listBarang) {
			if(barang.isMenunggu()) {
				listPendingBarang.add(barang);
			}
		}

		return listPendingBarang;
	}
}
