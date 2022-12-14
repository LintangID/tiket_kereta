
package com.example.tiketkereta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private TextView tvNama, tvTiket, tvPesan;
    private ImageView imgLogout;
    private FirebaseUser firebaseUser;
    private CardView cvPesan, cvTiket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        tvNama = findViewById(R.id.tvNama);
        tvPesan = findViewById(R.id.tvPesan);
        tvTiket = findViewById(R.id.tvTiket);
        cvPesan = findViewById(R.id.cvPesan);
        cvTiket = findViewById(R.id.cvTiket);
        imgLogout = findViewById(R.id.imageLogout);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        if (firebaseUser!=null){
            tvNama.setText(firebaseUser.getDisplayName());
        }else{
            tvNama.setText("Login Gagal");
        }

        imgLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        });

        cvPesan.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PesanActivity.class));
        });

        tvPesan.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PesanActivity.class));
        });

        cvTiket.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ListActivity.class));
        });

        tvTiket.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ListActivity.class));
        });

    }
}