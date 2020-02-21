package com;
import java.util.Scanner;
import java.util.ArrayList;

interface menuMakanan {
    final public ArrayList<String> paket_makanan = new ArrayList<String>();
    final public ArrayList<Integer> harga_makanan = new ArrayList<Integer>();
    public ArrayList<Integer> stock_makanan = new ArrayList<Integer>();

    public void addMenuMakanan();
}

class TambahMenuMakanan implements menuMakanan {
    public void addMenuMakanan() {
        paket_makanan.add("Ayam + Nasi + Es Teh Manis\t\t");
        harga_makanan.add(15000);
        stock_makanan.add(5);

        paket_makanan.add("Ayam + Es Teh Manis\t\t\t");
        harga_makanan.add(10000);
        stock_makanan.add(5);

        paket_makanan.add("Bakso + Es Teh Manis\t\t");
        harga_makanan.add(15000);
        stock_makanan.add(5);

        paket_makanan.add("Mie Ayam + Es Teh Manis\t\t");
        harga_makanan.add(12000);
        stock_makanan.add(5);

        paket_makanan.add("Mie Ayam + Bakso + Es Teh Manis\t");
        harga_makanan.add(15000);
        stock_makanan.add(5);
    }
}

abstract class Init {
    public Scanner input = new Scanner(System.in);
    TambahMenuMakanan menuMakanan = new TambahMenuMakanan();
    protected String nama_kantin = "Ama";
    protected ArrayList<String> nama_pembeli = new ArrayList<String>();
    protected ArrayList<String> nim_pembeli = new ArrayList<String>();
    protected ArrayList<String> status_pembeli = new ArrayList<String>();
    protected int[][] pesanan_pembeli = new int[5][5];
    protected int[][] jumlah_makanan = new int[5][5];
    protected int jumlah_user = 0;
    int total_harga = 0;
    int user;

    public abstract void menu();
    
    public void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
   
    public int pilih(int var, int maks) {
        try {
            var = input.nextInt();
            if(var > maks) {
                System.out.println("Maaf pilihan yang anda masukan tidak tersedia");
                return 0;
            } else {
                return var;
            }
        } catch(Exception e) {
            System.out.println(e);
            System.out.println("Maaf pilihan yang anda masukan salah");
            return 0;
        }
    }

    public void tampilUser(int index) {
        System.out.println("Nama : " + nama_pembeli.get(index));
        System.out.println("NIM  : " + nim_pembeli.get(index));
    }
}

class Utama extends Init {
    int pilih;
    char isContinue;

    public void menu() {
        do {
            cls();
            System.out.println("Selamat datang di kantin " + nama_kantin);
            System.out.print("\n");
            System.out.println("=========================");
            System.out.println("\t  MENU");
            System.out.println("=========================");
            System.out.println("[1] Pesan Makanan+Minuman");
            System.out.println("[2] Bayar");
            System.out.println("[3] Keluar");
            System.out.println("=========================");
            System.out.print("Pilih [1..3] : ");
            pilih = pilih(pilih, 3);
        } while(pilih == 0);
    }
}

class Pesan extends Init {
    String nama, nim;
    char isContinue;
    int pilih, jumlah_pesanan;

    public void inputData() {
        do {
            cls();
            System.out.println("Masukan data anda terlebih dahulu.");
            System.out.print("Nama : ");
            nama = input.next();
            nama_pembeli.add(nama);
            System.out.print("NIM  : ");
            nim = input.next();
            nim_pembeli.add(nim);
            System.out.print("Anda sudah yakin [Y/N] ? ");
            isContinue = input.next().charAt(0);
            if(isContinue == 'y' || isContinue == 'Y') {
                jumlah_user++;
                break;
            }
            else if(!((isContinue == 'n' || isContinue == 'N') || (isContinue == 'y' || isContinue == 'Y'))) {
                System.out.println("Pilihan yang anda masukan salah");
            }
        } while((isContinue == 'n' || isContinue == 'N') || !(isContinue == 'y' || isContinue == 'Y'));
    }

    public void menu() {
        menuMakanan.addMenuMakanan();
        cls();
        tampilUser(jumlah_user-1);
        System.out.println("\n======================================================");
        System.out.println("\t\tPaket Makanan");
        System.out.println("======================================================");
        System.out.println("Makanan\t\t\t\t\t|Harga\t|Stock");
        System.out.println("----------------------------------------+-------+-----");
        for(int i = 0; i < 5; i++) {
            int nomor = i+1;
            System.out.println("[" + nomor + "] " + menuMakanan.paket_makanan.get(i) + "|" + menuMakanan.harga_makanan.get(i) 
            + "\t|  " + menuMakanan.stock_makanan.get(i));
        }
        System.out.println("[6] Kembali\t\t\t\t|\t|");
        System.out.println("======================================================");
        System.out.print("Pilih : ");
        pilih = pilih(pilih, 6);
    }

    public void pesanMakanan() {
        int sisa_stock;
        jumlah_pesanan = 0;

        do {
            do {
                menu();
                cls();
                tampilUser(jumlah_user-1);
                System.out.println("\nAnda memilih paket " + menuMakanan.paket_makanan.get(pilih - 1));
                System.out.print("Jumlah yang ada beli : ");
                jumlah_makanan[jumlah_user-1][jumlah_pesanan] = input.nextInt();
                if(jumlah_makanan[jumlah_user-1][jumlah_pesanan] > menuMakanan.stock_makanan.get(pilih - 1)) {
                    System.out.println("Maaf jumlah yang ada masukan tidak tersedia");
                    input.next();
                } else {
                    System.out.print("Anda sudah yakin [Y/N] ? ");
                    isContinue = input.next().charAt(0);
                    if(isContinue == 'y' || isContinue == 'Y') {
                        pesanan_pembeli[jumlah_user-1][jumlah_pesanan] = pilih-1;
                        sisa_stock = menuMakanan.stock_makanan.get(pilih - 1);
                        sisa_stock -= jumlah_makanan[jumlah_user-1][jumlah_pesanan];
                        menuMakanan.stock_makanan.set(pilih - 1, sisa_stock);
                        jumlah_pesanan++;
                        System.out.print("Tambah pesanan [Y/N] ? ");
                        isContinue = input.next().charAt(0);
                        break;
                    }
                }
            } while(isContinue == 'n' || isContinue == 'N');
        } while(isContinue == 'y' || isContinue == 'Y');
        status_pembeli.add("Belum");
    }

    public void pesananUser(String nim) {
        int harga;
        total_harga = 0;
        for(user = 0; user<jumlah_user; user++) {
            if(nim.equalsIgnoreCase(nim_pembeli.get(user))) break;
        }
        if(nim_pembeli.isEmpty()) {
            System.out.println("Data tidak ditemukan");
        } else if(!(nim.equalsIgnoreCase(nim_pembeli.get(user)))) {
            System.out.println("Data tidak ditemukan");
        } else {
            cls();
            tampilUser(user);
            System.out.println("\nPesanan anda:");
            System.out.println("----------------------------------------+-------+--------");
            System.out.println("Makanan\t\t\t\t\t|Jumlah\t|Harga");
            System.out.println("----------------------------------------+-------+--------");
            for(int i = 0; i<jumlah_pesanan; i++) {
                int nomor = i+1;
                int index = pesanan_pembeli[user][i];
                harga = jumlah_makanan[user][i]* menuMakanan.harga_makanan.get(index);
                total_harga += harga;
                System.out.println("[" + nomor + "] " + menuMakanan.paket_makanan.get(index) + "|" +
                    jumlah_makanan[user][i] + "\t|" + harga);
            }
            System.out.println("----------------------------------------+-------+--------");
            System.out.println("Total\t\t\t\t\t\t|" + total_harga);
            System.out.println("----------------------------------------+-------+--------");
            System.out.println("Status pembayaran : " + status_pembeli.get(user));
            input.next();
        }
    }
}

class Bayar extends Pesan {
    char isContinue;
    int pilih;

    public void tunai() {
        int uang_masuk;
        int total_uang = 0;
        int kembalian = 0;

        do {
            cls();
            tampilUser(user);
            System.out.println("\n================================");
            System.out.println("\t     Bayar");
            System.out.println("================================");
            System.out.println("Jumlah yang harus dibayar = Rp." + total_harga);
            System.out.println("Uang yang masuk = Rp." + total_uang);

            if(total_uang >= total_harga) {
                System.out.println("Kembalian = Rp." + kembalian);
                System.out.println("Pembayaran sukses.");
                input.next();
                break;
            } else {
                System.out.print("\nMasukan nominal uang : ");
                uang_masuk = input.nextInt();
                total_uang += uang_masuk;
                if(total_uang < total_harga) {
                    System.out.println("Uang yang anda masukan kurang");
                    System.out.print("Masukan lagi [Y/N] ? ");
                    isContinue = input.next().charAt(0);
                } else {
                    kembalian = total_uang - total_harga;
                    status_pembeli.set(user, "Sudah");
                    isContinue = 'y';
                }
            }
        } while(isContinue == 'y' || isContinue == 'Y');
    }

    public void debit() {
        String no_rek;
        int uang;

        cls();
        tampilUser(user);
        System.out.println("\n=============================");
        System.out.println("\t    Bayar");
        System.out.println("=============================");
        System.out.print("Masukan nomor rekening : ");
        no_rek = input.next();
        System.out.println("Jumlah yang harus dibayar = Rp." + total_harga);
        System.out.print("Masukan nominal uang : ");
        uang = input.nextInt();
        if(uang < total_harga) {
            System.out.println("Pembayaran gagal");
        } else {
            status_pembeli.set(user, "Sudah");
            System.out.println("Pembayaran sukses");
            input.next();
        }
    }

    public void menuBayar() {
        String nim, kode, fkode, lkode;

        cls();
        do {
            System.out.println("Masukan NIM anda untuk mencari pesanan");
            System.out.print("NIM : ");
            nim = input.next();
            pesananUser(nim);
            if(nim_pembeli.isEmpty()) {
                System.out.print("Masukan ulang [Y/N] ?");
                isContinue = input.next().charAt(0);
            } else if(!(nim.equalsIgnoreCase(nim_pembeli.get(user)))) {
                System.out.print("Masukan ulang [Y/N] ?");
                isContinue = input.next().charAt(0);
            } else {
                isContinue = 'y';
                break;
            }
        } while(isContinue == 'y' || isContinue == 'Y');
        if(isContinue == 'n' || isContinue == 'N') {}
        else {
            if(status_pembeli.get(user).equals("Belum")) {
                cls();
                tampilUser(user);
                System.out.println("\n===========");
                System.out.println("   Kasir");
                System.out.println("===========");
                System.out.println("[1] Bayar");
                System.out.println("[2] Kembali");
                System.out.println("===========");
                System.out.print("pilih : ");
                pilih = pilih(pilih,2);
                
                if(pilih == 1) {
                    cls();
                    tampilUser(user);
                    System.out.println("\n===========");
                    System.out.println("   Bayar");
                    System.out.println("===========");
                    System.out.println("[1] Tunai");
                    System.out.println("[2] Debit");
                    System.out.println("[3] Kembali");
                    System.out.println("===========");
                    System.out.print("pilih : ");
                    pilih = pilih(pilih, 3);
                    System.out.print("\nPakai diskon [Y/N] ? ");
                    isContinue = input.next().charAt(0);
                    if(isContinue == 'y' || isContinue == 'Y') {
                        do {
                            cls();
                            tampilUser(user);
                            System.out.println("\n===================");
                            System.out.println("       Bayar");
                            System.out.println("===================");
                            System.out.println("\nMasukan kode diskon.");
                            System.out.print("Kode : ");
                            fkode = input.next();
                            lkode = input.next();
                            kode = fkode + " " + lkode;
                            if(kode.equals("LULUS UJIAN")) {
                                System.out.println("Selemat anda mendapatkan diskon 10%");
                                input.next();
                                double diskon = total_harga * 0.1;
                                total_harga -= diskon;
                                break;
                            } else {
                                System.out.println("Kode yang anda masukan salah");
                                System.out.print("Masukan ulang [Y/N] ? ");
                                isContinue = input.next().charAt(0);
                            }
                        } while(isContinue == 'y' || isContinue == 'Y');
                    }
                    switch (pilih) {
                        case 1:
                            tunai();
                            break;
                        case 2:
                            debit();
                            break;
                    }
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        boolean isContinue = true;
        Utama utama = new Utama();
        Bayar bayar = new Bayar();
        do {
            utama.menu();
            switch (utama.pilih) {
                case 1:
                    if(bayar.jumlah_user >= 5) {
                        System.out.println("Mohon antri terlebih dahulu");
                        System.out.println("Pelayan sedang menyiapkan makanan");
                        bayar.input.next();
                    } else {
                        bayar.inputData();
                        bayar.pesanMakanan();
                        bayar.pesananUser(bayar.nim);
                    }
                    break;
                case 2:
                    bayar.menuBayar();
                    break;
                case 3:
                    utama.cls();
                    System.out.println("Terima kasih telah menggunakan program ini");
                    isContinue = false;
                    break;
            }
        } while(isContinue);
    }
}