package com.example.tiketkereta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PesanActivity extends AppCompatActivity {

    private Toolbar tbBack;
    private TextInputEditText inputNama,date, inputTelepon;
    private int hitung1, hitung2, jmlAnak, jmlDewasa, telepon;
    private TextView tvJmlAnak, tvJmlDewasa;
    private ImageView imgAdd1, imgMinus1, imgAdd2, imgMinus2;
    private SimpleDateFormat dateFormater;
    private MaterialButton btnPesan;
    private AppCompatSpinner spBerangkat, spTujuan,spKelas;
    DatePickerDialog datePickerDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        datePickerDialog =  new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                date.setText(dateFormater.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);


        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        tbBack = findViewById(R.id.tbBack);
        inputNama = findViewById(R.id.inputNama);
        spBerangkat = findViewById(R.id.spBerangkat);
        spTujuan = findViewById(R.id.spTujuan);
        tvJmlAnak = findViewById(R.id.tvJmlAnak);
        tvJmlDewasa = findViewById(R.id.tvJmlDewasa);
        imgAdd1 = findViewById(R.id.imageAdd1);
        imgMinus1 = findViewById(R.id.imageMinus1);
        imgAdd2 = findViewById(R.id.imageAdd2);
        imgMinus2 = findViewById(R.id.imageMinus2);
        spKelas = findViewById(R.id.spKelas);
        date = findViewById(R.id.inputTanggal);
        dateFormater = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        inputTelepon = findViewById(R.id.inputTelepon);
        btnPesan = findViewById(R.id.btnPesan);

        progressDialog = new ProgressDialog(PesanActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        tbBack.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        });

        hitung1 = 0;
        hitung2 = 0;

        tvJmlAnak.setText(String.valueOf(hitung1));
        tvJmlDewasa.setText(String.valueOf(hitung2));

        imgAdd1.setOnClickListener( v  -> {
            hitung1 += 1;
            tvJmlAnak.setText(String.valueOf(hitung1));
        });

        imgMinus1.setOnClickListener( v -> {
            if ( hitung1 > 0) {
                hitung1 -= 1;
                tvJmlAnak.setText(String.valueOf(hitung1));
            }else {
                tvJmlAnak.setText(String.valueOf(hitung1));
            }
        });

        imgAdd2.setOnClickListener( v  -> {
            hitung2 += 1;
            tvJmlDewasa.setText(String.valueOf(hitung2));
        });

        imgMinus2.setOnClickListener( v -> {
            if ( hitung2 > 0) {
                hitung2 -= 1;
                tvJmlDewasa.setText(String.valueOf(hitung2));
            }else {
                tvJmlDewasa.setText(String.valueOf(hitung2));
            }
        });

        btnPesan.setOnClickListener(v ->{
            if(inputNama.getText().length() > 0 && tvJmlAnak.getText() != "0" && tvJmlDewasa.getText() != "0" && date.getText().length() > 0 && inputTelepon.getText().toString().length() > 0 ){
                if (spBerangkat.getSelectedItem().toString() != spTujuan.getSelectedItem().toString()){
                    telepon = Integer.parseInt(inputTelepon.getText().toString());
                    jmlAnak = Integer.parseInt(tvJmlAnak.getText().toString());
                    jmlDewasa = Integer.parseInt(tvJmlDewasa.getText().toString());
                    saveData(inputNama.getText().toString(),spBerangkat.getSelectedItem().toString(),spTujuan.getSelectedItem().toString(), jmlAnak, jmlDewasa, spKelas.getSelectedItem().toString(), date.getText().toString(),telepon);
                }else{
                    Toast.makeText(getApplicationContext(), "Keberangkatan dan tujuan tidak boleh sama",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data",Toast.LENGTH_SHORT).show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    private void saveData(String nama, String berangkat, String tujuan, int anak, int dewasa, String kelas, String tanggal, int telepon){
        Map<String, Object> pemesanan = new HashMap<>();
        pemesanan.put("nama",nama);
        pemesanan.put("keberangkatan",berangkat);
        pemesanan.put("tujuan",tujuan);
        pemesanan.put("penumpang anak",anak);
        pemesanan.put("penumpang dewasa", dewasa);
        pemesanan.put("kelas",kelas);
        pemesanan.put("tanggal",tanggal);
        pemesanan.put("nomor telepon",telepon);

        progressDialog.show();
        db.collection("tiket")
                .add(pemesanan)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "berhasil", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

    }
}
