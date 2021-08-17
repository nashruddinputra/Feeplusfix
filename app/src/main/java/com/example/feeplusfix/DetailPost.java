package com.example.feeplusfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.feeplusfix.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailPost extends AppCompatActivity {

    TextView tvNamaBarang, tvDeskripsiBarang, tvHargaBarang;
    android.widget.Button btnNoWaPenjual;
    Post post;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase mDatabase;

    String userId;
    String noWa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        post = (Post) getIntent().getExtras().getSerializable("post");

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        userId = fUser.getUid();

        mDatabase = FirebaseDatabase.getInstance();

        tvNamaBarang = findViewById(R.id.tv_nama_barang);
        tvDeskripsiBarang = findViewById(R.id.tv_deskripsi_barang);
        tvHargaBarang = findViewById(R.id.tv_harga_barang);
        btnNoWaPenjual = findViewById(R.id.btn_hub_wa_penjual);

        tvNamaBarang.setText(post.getNamaBarang());
        tvDeskripsiBarang.setText(post.getDeskripsiBarang());
        tvHargaBarang.setText(post.getHargaBarang());

        btnNoWaPenjual.setVisibility(View.GONE);

        mDatabase.getReference().child("users").child(post.getUserId()).child("no_whatsapp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noWa = (String) snapshot.getValue();
                if (noWa != null) {
                    btnNoWaPenjual.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnNoWaPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone="+noWa;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}