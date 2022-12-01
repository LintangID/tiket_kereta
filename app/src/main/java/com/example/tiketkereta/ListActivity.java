package com.example.tiketkereta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.tiketkereta.adapter.PemesananAdapter;
import com.example.tiketkereta.model.Pemesanan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pemesanan> list = new ArrayList<>();
    private PemesananAdapter pemesananAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.rvList);

        progressDialog = new ProgressDialog(ListActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");

        pemesananAdapter = new PemesananAdapter(getApplicationContext(), list);
        pemesananAdapter.setDialog(new PemesananAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), PesanActivity.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama", list.get(pos).getNama());
                                intent.putExtra("tanggal", list.get(pos).getTanggal());
                                startActivity(intent);
                                break;
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(pemesananAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData(){
        progressDialog.show();
        db.collection("tiket")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            list.clear();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Pemesanan pemesanan = new Pemesanan(
                                        document.getString("nama"),
                                        document.getString("keberangkatan"),
                                        document.getString("tujuan"),
                                        document.getString("tanggal"),
                                        document.getString("kelas"),
                                        document.getString("harga tiket"));

                                pemesanan.setId(document.getId());
                                list.add(pemesanan);
                            }
                            pemesananAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getApplicationContext(), "data gagal diambil", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void deleteData(String id){
        progressDialog.show();
        db.collection("tiket").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"data gagal dihapus",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        getData();
                    }
                });
    }
}