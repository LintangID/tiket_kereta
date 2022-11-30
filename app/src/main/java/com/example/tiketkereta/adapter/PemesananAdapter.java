package com.example.tiketkereta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiketkereta.R;
import com.example.tiketkereta.model.Pemesanan;

import java.util.List;

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.MyviewHolder>{
    private Context context;
    private List<Pemesanan> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public Dialog getDialog() {
        return dialog;
    }

    public PemesananAdapter(Context context, List<Pemesanan> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.kelas.setText(list.get(position).getKelas());
        holder.tanggal.setText(list.get(position).getTanggal());
        holder.nama.setText(list.get(position).getNama());
        holder.harga.setText(list.get(position).getNama());
        holder.kode1.setText(list.get(position).getKeberangkatan());
        holder.keberangkatan.setText(list.get(position).getKeberangkatan());
        holder.kode2.setText(list.get(position).getTujuan());
        holder.tujuan.setText(list.get(position).getTujuan());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder{
        TextView kelas, tanggal, nama, harga, kode1, keberangkatan, kode2, tujuan;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            kelas = itemView.findViewById(R.id.tvKelas);
            tanggal = itemView.findViewById(R.id.tvDate);
            nama = itemView.findViewById(R.id.tvNama);
            harga = itemView.findViewById(R.id.tvHargaTiket);
            kode1 = itemView.findViewById(R.id.tvKode1);
            keberangkatan = itemView.findViewById(R.id.tvKeberangkatan);
            kode2 = itemView.findViewById(R.id.tvKode2);
            tujuan = itemView.findViewById(R.id.tvTujuan);


        }
    }
}
