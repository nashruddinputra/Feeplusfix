package com.example.feeplusfix.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feeplusfix.MainActivity;
import com.example.feeplusfix.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class AddPosting extends AppCompatActivity {

    private EditText etNamaBarang, etDeskripsiBarang, etHargaBarang;
    private Button btnKirimBarang;

    FirebaseDatabase mDatabase;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_posting);

        mDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        assert fUser != null;
        userId = fUser.getUid();

        etNamaBarang = findViewById(R.id.et_nama_barang);
        etDeskripsiBarang = findViewById(R.id.et_deskripsi_barang);
        etHargaBarang = findViewById(R.id.et_harga_barang);
        btnKirimBarang = findViewById(R.id.btn_kirim_barang);

        btnKirimBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String namabarang = etNamaBarang.getText().toString().trim();
                String deskripsibarang = etDeskripsiBarang.getText().toString().trim();
                String hargabarang = etHargaBarang.getText().toString().trim();

                boolean isvalid = true;
                if(TextUtils.isEmpty(namabarang)){
                    etNamaBarang.setError("Masukan Nama Barang!");
                    isvalid = false;
                }
                if(TextUtils.isEmpty(deskripsibarang)){
                    etDeskripsiBarang.setError("Masukan Deskripsi Barang!");
                    isvalid = false;
                }
                if(TextUtils.isEmpty(hargabarang)){
                    etHargaBarang.setError("Masukan Harga Barang!");
                    isvalid = false;
                }
                if(!isvalid){
                    Toast.makeText(AddPosting.this,"Posting Barang Gagal!", Toast.LENGTH_SHORT).show();
                    return;
                }

                writeNewPost(namabarang, deskripsibarang, hargabarang);
            }
        });

    }

    String getSaltString() {

        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        String saltStr = salt.toString();
        return saltStr;
    }

    public void writeNewPost(String namaBarang, String deskripsiBarang, String hargaBarang) {
        Post tambahbarang = new Post(namaBarang,deskripsiBarang,hargaBarang);

        DatabaseReference dataref = mDatabase.getReference().child("posts").child(userId).child(this.getSaltString());

        dataref.setValue(tambahbarang)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddPosting.this,"Posting Barang Berhasil", Toast.LENGTH_SHORT).show();
                Intent i = new Intent (AddPosting.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPosting.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}