package com.example.tiketkereta.model;

public class Pemesanan {
    private String nama, keberangkatan, tujuan, kelas, tanggal;
    private int anak, dewasa, telepon;

    public Pemesanan(String nama, String keberangkatan, String tujuan, String tanggal, String kelas){
        this.nama = nama;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
        this.tanggal = tanggal;
        this.kelas = kelas;
    }

//    public Pemesanan(String nama, String berangkat, String tujuan, int anak, int dewasa, String kelas, String tanggal, int telepon){
//        this.nama = nama;
//        this.berangkat = berangkat;
//        this.tujuan = tujuan;
//        this.anak = anak;
//        this.dewasa = dewasa;
//        this.kelas = kelas;
//        this.tanggal = tanggal;
//        this.telepon = telepon;
//    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeberangkatan() {
        return keberangkatan;
    }

    public void setKeberangkatan(String keberangkatan) {
        this.keberangkatan = keberangkatan;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getAnak() {
        return anak;
    }

    public void setAnak(int anak) {
        this.anak = anak;
    }

    public int getDewasa() {
        return dewasa;
    }

    public void setDewasa(int dewasa) {
        this.dewasa = dewasa;
    }

    public int getTelepon() {
        return telepon;
    }

    public void setTelepon(int telepon) {
        this.telepon = telepon;
    }
}
