package com.example.tiketkereta.model;

import com.google.errorprone.annotations.Var;

public class Pemesanan {
    private String id, nama, keberangkatan, tujuan, kelas, tanggal, hargaTiket;

    public Pemesanan(String nama, String keberangkatan, String tujuan, String tanggal, String kelas, String hargaTiket){
        this.nama = nama;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
        this.tanggal = tanggal;
        this.kelas = kelas;
        this.hargaTiket = hargaTiket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(String hargaTiket) {
        this.hargaTiket = hargaTiket;
    }
}
