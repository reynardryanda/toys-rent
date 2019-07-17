import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    ChoiceBox<String> kategoriList = new ChoiceBox<>(); //untuk scene membuat barang untuk admin
    ArrayList<ListView<String>> listViewList = new ArrayList<>(); //untuk scene menyewa barang untuk anggota

    @Override
     public void start(Stage primaryStage) {
        // TODO Buat sesuai permintaan soal
        primaryStage.setResizable(false);
        Scene scene = homePage(primaryStage); //Cara kerjanya: setiap scene terdapat di setiap method
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene homePage(Stage primaryStage){
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 600);
        gridPane.setAlignment(Pos.CENTER);
        Text homeText = new Text("Selamat datang di Toys Rent");
        Text homeText2 = new Text("Ingin login sebagai siapa?");
        homeText.setFont(Font.font("Muli",26));
        homeText2.setFont(Font.font("Muli",26));
        Button btAnggota = new Button("Anggota");
        Button btAdmin = new Button("Admin");
        btAnggota.setOnAction((event) -> {
            primaryStage.setScene(anggotaPanel(primaryStage)); //Pindah ke scene anggota
        });
        btAdmin.setOnAction((event) -> {
            primaryStage.setScene(adminPanel(primaryStage)); //Pindah ke scene admin
        });
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        gridPane.add(homeText, 0, 0, 5, 1);
        gridPane.add(homeText2, 0, 1, 5, 1);
        gridPane.add(btAnggota, 0, 2, 5, 1);
        gridPane.add(btAdmin, 5, 2, 5, 1);
        primaryStage.setTitle("Home Page");
        return new Scene(gridPane);
    }

    public Scene anggotaPanel(Stage primaryStage){
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 600);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Text textAnggota = new Text("Halo anggota, apa yang ingin anda lakukan?");
        textAnggota.setFont(Font.font("Muli",20));
        Button btSewaBarang = new Button("Sewa Barang");
        Button btListPenyewaan = new Button("List Penyewaan");
        Button btLogout = new Button("Logout");

        gridPane.setPadding(new Insets(30, 0, 0, 0));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        gridPane.add(textAnggota, 0, 0, 4, 1);
        gridPane.add(btSewaBarang, 0, 1, 1, 1);
        gridPane.add(btListPenyewaan, 1, 1, 1, 1);
        gridPane.add(btLogout, 2, 1, 1, 1);
        
        btSewaBarang.setOnAction((event) -> {
            primaryStage.setScene(sewaBarang(primaryStage)); //Pindah ke scene menyewa barang untuk anggota
        });
        btListPenyewaan.setOnAction((event) -> {
            primaryStage.setScene(listPenyewaan(primaryStage)); //Pindah ke scene list penyewaan (seperti history) untuk anggota
        });
        btLogout.setOnAction((event) -> {
            primaryStage.setScene(homePage(primaryStage)); //Balik ke scene Home Page
        });

        primaryStage.setTitle("Anggota Panel");
        return new Scene(gridPane);
    }
    
    public Scene sewaBarang(Stage primaryStage){
        GridPane gridPane = new GridPane();
        GridPane gridPaneRoot = new GridPane();
        gridPaneRoot.setMinSize(600, 600);
        gridPaneRoot.setAlignment(Pos.TOP_CENTER);
        
        gridPaneRoot.setPadding(new Insets(30, 30, 30, 30));
        gridPaneRoot.setHgap(20);
        gridPaneRoot.setVgap(20);
        
        Text textAnggota = new Text("Pilih barang yang ingin anda sewa");
        textAnggota.setFont(Font.font("Muli",20));
        gridPaneRoot.add(textAnggota,0,0,1,1);
        
        for (int i = 0; i < Admin.getListKategori().size(); i++) { //Membuat listView berdasarkan banyak kategori dan memasukkan barang dengan status "Disewakan" berdasarkan kategori masing-masing
            listViewList.add(new ListView<String>());
            Text namaKategori = new Text(Admin.getNamaKategori().get(i));
            listViewList.get(i).getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            listViewList.get(i).setItems(FXCollections.observableArrayList(Admin.getListKategori().get(i).getNamaBarangDisewakan()));
            gridPane.add(namaKategori,i,1,1,1);
            gridPane.add(listViewList.get(i),i,2,1,1);
        }
        
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefSize(500, 450);
        gridPaneRoot.add(scrollPane, 0, 1);
        Button btSewaBarang = new Button("Sewa Barang");
        gridPaneRoot.add(btSewaBarang,0,2,1,1);
        Button btBack = new Button("       Back       ");
        GridPane.setHalignment(btBack, HPos.RIGHT);
        gridPaneRoot.add(btBack,0,2,1,1);

        
        btSewaBarang.setOnAction((event) -> { //Barang disewa (tahan shift atau ctrl untuk menyewa banyak sekaligus) dengan cara mengubah status menjadi "Menunggu Konfirmasi Admin" agar keluar dari listView anggota dan masuk ke list pemesanan di admin
            for (int i = 0; i < listViewList.size(); i++) {
                ObservableList<String> listBarang = listViewList.get(i).getSelectionModel().getSelectedItems();
                for (int j = 0; j < listBarang.size(); j++) {
                    Barang barang = Admin.getListKategori().get(i).findBarang(listBarang.get(j));
                    barang.changeStatusMenunggu();
                }
                listViewList.get(i).getItems().removeAll(listBarang);
            }
        });
        btBack.setOnAction((event) -> {
            primaryStage.setScene(anggotaPanel(primaryStage)); //Balik ke scene anggota
        });

        primaryStage.setTitle("Sewa Barang Panel");
        return new Scene(gridPaneRoot);
    }

    public Scene listPenyewaan(Stage primaryStage){ //ini cuman ngasih seperti history apakah pemesanan disetujui atau ditolak oleh admin
        GridPane gridPaneRoot = new GridPane();
        gridPaneRoot.setMinSize(600, 600);
        gridPaneRoot.setAlignment(Pos.TOP_CENTER);

        gridPaneRoot.setPadding(new Insets(20, 20, 20, 20));
        gridPaneRoot.setHgap(20);
        gridPaneRoot.setVgap(20);

        Text textAwal = new Text("List Penyewaan");
        textAwal.setFont(Font.font("Muli",20));

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(450, 400);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Text textKategori = new Text("Kategori");
        Text textBarang = new Text("Barang");
        Text textStatus = new Text("status");
        textKategori.setFont(Font.font("Muli",20));
        textBarang.setFont(Font.font("Muli",20));
        textStatus.setFont(Font.font("Muli",20));

        gridPane.add(textKategori, 0, 0, 1, 2);
        gridPane.add(textBarang, 1, 0, 1, 2);
        gridPane.add(textStatus, 2, 0, 1, 2);

        GridPane.setHalignment(textKategori, HPos.CENTER);
        GridPane.setHalignment(textBarang, HPos.CENTER);
        GridPane.setHalignment(textStatus, HPos.CENTER);

        int k = 1;
        for (int i = 0; i < Anggota.getListPemesanan().size(); i++) { //Ini berdasarkan disetujui atau ditolak dari list pemesanan di admin
            int l = 0;

            Text kategori = new Text(Anggota.getListPemesanan().get(i).getBarang().getNamaKategori());
            Text barang = new Text(Anggota.getListPemesanan().get(i).getBarang().getNama());
            kategori.setFont(Font.font("Muli",20));
            barang.setFont(Font.font("Muli",20));
            gridPane.add(kategori,l++,k,1,2);
            gridPane.add(barang,l++,k,1,2);
            Text status = new Text(Anggota.getListPemesanan().get(i).getStatus());
            gridPane.add(status,l++,k,1,2);

            k+=2;

            GridPane.setHalignment(kategori, HPos.CENTER);
            GridPane.setHalignment(barang, HPos.CENTER);
            GridPane.setHalignment(status, HPos.CENTER);

            l = 0;
        }

        gridPane.setHgap(30);
        gridPane.setVgap(30);
        
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefSize(500, 420);
        gridPaneRoot.add(scrollPane, 0, 1, 1, 1);
        Button btBack = new Button("Back");
        gridPaneRoot.add(btBack, 0, 2, 1, 1);

        btBack.setOnAction((event) -> {
            primaryStage.setScene(anggotaPanel(primaryStage));
        });

        primaryStage.setTitle("List Penyewaan Panel");
        return new Scene(gridPaneRoot);
    }

    public Scene adminPanel(Stage primaryStage){
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 600);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Text adminText = new Text("Halo admin, apa yang ingin anda lakukan?");
        adminText.setFont(Font.font("Muli",20));
        Button btBuatKategori = new Button("Buat Kategori");
        Button btBuatBarang = new Button("Buat Barang");
        Button btListPemesanan = new Button("List Pemesanan");
        Button btLogout = new Button("Logout");

        gridPane.setPadding(new Insets(30, 0, 0, 0));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        gridPane.add(adminText, 0, 0, 4, 1);
        gridPane.add(btBuatKategori, 0, 1, 1, 1);
        gridPane.add(btBuatBarang, 1, 1, 1, 1);
        gridPane.add(btListPemesanan, 2, 1, 1, 1);
        gridPane.add(btLogout, 3, 1, 1, 1);
        
        btBuatKategori.setOnAction((event) -> {
            primaryStage.setScene(buatKategori(primaryStage)); //Pindah ke scene buat kategori punya admin
        });
        btBuatBarang.setOnAction((event) -> {
            primaryStage.setScene(buatBarang(primaryStage)); //Pindah ke scene buat barang punya admin
        });
        btListPemesanan.setOnAction((event) -> {
            primaryStage.setScene(listPemesanan(primaryStage)); //Pindah ke scene list pemesanan punya admin
        });
        btLogout.setOnAction((event) -> {
            primaryStage.setScene(homePage(primaryStage)); //Balik ke Home Page
        });

        primaryStage.setTitle("Admin Panel");
        return new Scene(gridPane);
    }

    public Scene buatKategori(Stage primaryStage){
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 600);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Text textBuatKategori = new Text("Isi kategori yang ingin anda buat");
        textBuatKategori.setFont(Font.font("Muli",22));
        Text textAdaKategori = new Text("Kategori berhasil ditambahkan");
        textAdaKategori.setFont(Font.font("Muli",15));
        Text textTidakAdaKategori = new Text("Sudah ada kategori dengan nama yang sama");
        textTidakAdaKategori.setFont(Font.font("Muli",15));
        textTidakAdaKategori.setStroke(Color.RED);
        Label labelNamaKategori = new Label("Nama: ");
        labelNamaKategori.setFont(Font.font("Muli",20));
        TextField namaKategori = new TextField();
        Button btBuatKategori = new Button("Buat Kategori");
        Button btBack = new Button("        Back        ");

        gridPane.setPadding(new Insets(30, 0, 0, 0));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(textBuatKategori, 0, 0, 2, 1);
        gridPane.add(labelNamaKategori, 0, 1, 1, 1);
        gridPane.add(namaKategori, 1, 1, 1, 1);
        gridPane.add(btBuatKategori, 0, 2, 1, 1);
        GridPane.setHalignment(btBack, HPos.RIGHT);
        gridPane.add(btBack ,1, 2, 1, 1);

        btBuatKategori.setOnAction((event) -> { //Membuat kategori. Jika nama yang diinput sudah pernah diinput, maka nama tersebut akan ditolak
            if(Admin.createKategori(namaKategori.getText())){
                gridPane.getChildren().remove(textTidakAdaKategori);
                gridPane.getChildren().remove(textAdaKategori);
                gridPane.add(textAdaKategori, 0, 3, 2, 1);
            } else {
                gridPane.getChildren().remove(textTidakAdaKategori);
                gridPane.getChildren().remove(textAdaKategori);
                gridPane.add(textTidakAdaKategori, 0, 3, 2, 1);
            }
        });
        btBack.setOnAction((event) -> {
            primaryStage.setScene(adminPanel(primaryStage)); //balik ke scene admin
        });

        primaryStage.setTitle("Create Kategori Panel"); 
        return new Scene(gridPane);
    }

    public Scene buatBarang(Stage primaryStage){
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 600);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Text textBuatBarang = new Text("Isi barang yang ingin dibuat");
        textBuatBarang.setFont(Font.font("Muli",22));
        Label labelKategori = new Label("Kategori: ");
        labelKategori.setFont(Font.font("Muli",20));
        Label labelNama = new Label("Nama: ");
        TextField namaBarang = new TextField();
        Button btBuatBarang = new Button("Buat Barang");
        Button btBack = new Button("       Back       ");
        kategoriList.setItems(FXCollections.observableArrayList(Admin.getNamaKategori()));
        Text textAdaBarang = new Text("Barang berhasil ditambahkan");
        textAdaBarang.setFont(Font.font("Muli",15));
        Text textTidakAdaBarang = new Text("Sudah ada barang dengan nama yang sama");
        textTidakAdaBarang.setFont(Font.font("Muli",13));
        textTidakAdaBarang.setStroke(Color.RED);

        gridPane.setPadding(new Insets(30, 0, 0, 0));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(textBuatBarang, 0, 0, 2, 1);
        gridPane.add(labelKategori, 0, 1, 1, 1);
        gridPane.add(kategoriList, 1, 1, 1, 1);
        gridPane.add(labelNama, 0, 2, 1, 1);
        gridPane.add(namaBarang, 1, 2, 1, 1);
        gridPane.add(btBuatBarang, 0, 3, 1, 1);
        GridPane.setHalignment(btBack, HPos.RIGHT);
        gridPane.add(btBack, 1, 3, 1, 1);

        btBuatBarang.setOnAction((event) -> { //Tombol untuk membuat barang. Jika kategori belum ada atau menginput nama barang yang sudah pernah diinput, maka nama tersebut akan ditolak.
            if(Admin.createBarang(kategoriList.getSelectionModel().getSelectedItem(), namaBarang.getText())){
                gridPane.getChildren().remove(textTidakAdaBarang);
                gridPane.getChildren().remove(textAdaBarang);
                gridPane.add(textAdaBarang, 0, 4, 2, 1);
            } else {
                gridPane.getChildren().remove(textTidakAdaBarang);
                gridPane.getChildren().remove(textAdaBarang);
                gridPane.add(textTidakAdaBarang, 0, 4, 2, 1);
            }
        });
        btBack.setOnAction((event) -> {
            primaryStage.setScene(adminPanel(primaryStage)); //Balik ke page admin
        });

        primaryStage.setTitle("Create Barang Panel"); 
        return new Scene(gridPane);
    }

    public Scene listPemesanan(Stage primaryStage){
        GridPane gridPaneRoot = new GridPane();
        gridPaneRoot.setMinSize(600, 600);
        gridPaneRoot.setAlignment(Pos.TOP_CENTER);

        Text textListPemesanan = new Text("Konfirmasi barang-barang yang akan disewakan");
        textListPemesanan.setFont(Font.font("Muli",20));
        Text textKategori = new Text("Kategori");
        Text textBarang = new Text("Barang");
        Text textSetuju = new Text("Setuju");
        Text textTolak = new Text("Tolak");
        textKategori.setFont(Font.font("Muli",20));
        textBarang.setFont(Font.font("Muli",20));
        textSetuju.setFont(Font.font("Muli",20));
        textTolak.setFont(Font.font("Muli",20));

        gridPaneRoot.add(textListPemesanan, 0, 0, 1, 1);
        
        GridPane.setHalignment(textListPemesanan, HPos.CENTER);
        
        gridPaneRoot.setPadding(new Insets(20, 20, 20, 20));
        gridPaneRoot.setHgap(20);
        gridPaneRoot.setVgap(20);

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(450, 400);
        gridPane.setAlignment(Pos.TOP_CENTER);
        
        gridPane.add(textKategori, 0, 0, 1, 1);
        gridPane.add(textBarang, 1, 0, 1, 1);
        gridPane.add(textSetuju, 2, 0, 1, 1);
        gridPane.add(textTolak, 3, 0, 1, 1);

        GridPane.setHalignment(textKategori, HPos.CENTER);
        GridPane.setHalignment(textBarang, HPos.CENTER);
        GridPane.setHalignment(textSetuju, HPos.CENTER);
        GridPane.setHalignment(textTolak, HPos.CENTER);
        
        int k = 1;
        for (int i = 0; i < Admin.getListKategori().size(); i++) { //Menerima barang dengan status "Menunggu Konfirmasi Admin" dan diberi tombol setuju dan ditolak agar admin bisa menyetujui atau menolak
            ArrayList<Barang> barangPending = Admin.getListKategori().get(i).getPendingBarang();
            int l = 0;
            for (int j = 0; j < barangPending.size(); j++) { //Membuat baris dengan isi (berurutan) nama kategori, nama barang, tombol setuju, dan tombol tolak

                int m = j;
                Text kategori = new Text(Admin.getListKategori().get(i).getNama());
                Text barang = new Text(barangPending.get(j).getNama());
                kategori.setFont(Font.font("Muli",20));
                barang.setFont(Font.font("Muli",20));
                gridPane.add(kategori,l++,k,1,2);
                gridPane.add(barang,l++,k,1,2);
                Button btSetuju = new Button("Setuju");
                Button btTolak = new Button("Tolak");
                gridPane.add(btSetuju,l++,k,1,2);
                gridPane.add(btTolak,l++,k,1,2);

                k+=2;

                GridPane.setHalignment(kategori, HPos.CENTER);
                GridPane.setHalignment(barang, HPos.CENTER);
                GridPane.setHalignment(btSetuju, HPos.CENTER);
                GridPane.setHalignment(btTolak, HPos.CENTER);

                btSetuju.setOnAction((event) -> { //Jika setuju, maka barang berubah statusnya menjadi "Sedang Dipinjam", menghilang dari list pemesanan, dan masuk ke halaman list penyewaan di anggota dengan tulisan "disetujui"
                    barangPending.get(m).changeStatusDipinjam();
                    Anggota.addPemesananBerhasil(barangPending.get(m));
                    gridPane.getChildren().removeAll(kategori,barang,btSetuju,btTolak);
                });

                btTolak.setOnAction((event) -> { //Jika ditolak, maka barang berubah statusnya menjadi "Disewakan" kembali, menghilang dari list pemesanan, masuk ke halaman list penyewaan di anggota dengan tulisan "ditolak" dan kembali ke sewa barang di anggota
                    Anggota.addPemesananDitolak(barangPending.get(m));
                    barangPending.get(m).changeStatusDisewakan();
                    gridPane.getChildren().removeAll(kategori,barang,btSetuju,btTolak);
                });

                l = 0;
            }
        }

        gridPane.setHgap(30);
        
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefSize(500, 420);
        gridPaneRoot.add(scrollPane, 0, 1, 1, 1);
        Button btBack = new Button("Back");
        gridPaneRoot.add(btBack, 0, 2, 1, 1);

        btBack.setOnAction((event) -> {
            primaryStage.setScene(adminPanel(primaryStage)); //Balik ke page admin
        });

        primaryStage.setTitle("List Pemesanan Panel"); 
        return new Scene(gridPaneRoot);
    }



    public static void main(String[] args) {
         Application.launch(args);

    }
 }