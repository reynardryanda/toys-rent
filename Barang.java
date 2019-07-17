public class Barang {
	private Kategori kategori;
	private String nama;
	private String status;

	/**
	 *
	 * @param kategori
	 * @param nama
	 */
	public Barang(Kategori kategori, String nama) {
		this.kategori = kategori;
		this.nama = nama;
		this.status = "Disewakan";
	}

	/**
	 *
	 * @return nama
	 */
	public String getNama() {
		return nama;
	}

	public String getNamaKategori() { return kategori.getNama(); }

	/**
	 *
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	public void changeStatusDisewakan() {
		 status = "Disewakan";
	}

	public void changeStatusMenunggu() {
		status = "Menunggu Konfirmasi Admin";
	}

	public void changeStatusDipinjam() {
		status = "Sedang Dipinjam";
	}

	/**
	 *
	 * @return true if status disewakan and false otherwise
	 */
	public boolean isDisewakan() {
		return status.equals("Disewakan");
	}

	public boolean isMenunggu() {
		return status.equals("Menunggu Konfirmasi Admin");
	}

	public boolean isDipinjam() {
		return status.equals("Sedang Dipinjam");
	}
}
