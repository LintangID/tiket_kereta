package com.example.tiketkereta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private EditText etNama, etEmail, etPassword, etPasswordConfirm;
    private Button btnRegister,btnPunya;
    private TextView tvLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        etNama = findViewById(R.id.nama);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etPasswordConfirm = findViewById(R.id.passwordConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("silahkan tunggu");
        progressDialog.setCancelable(false);

        tvLogin.setOnClickListener(v -> {
            finish();
        });

        btnRegister.setOnClickListener( v -> {
            if (etNama.getText().length() > 0 && etEmail.getText().length()>0 && etPassword.getText().length()>0 && etPasswordConfirm.getText().length() > 0){
                if (etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
                    register(etNama.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(),"Silahkan masukan password yang sama!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register (String nama, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null){
                    FirebaseUser firebaseuser = task.getResult().getUser();
                    if (firebaseuser!= null) {
                        progressDialog.show();
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(nama).build();
                        firebaseuser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "Register gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void reload(){
        Intent reload = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(reload);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}