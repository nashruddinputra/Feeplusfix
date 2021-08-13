package com.example.feeplusfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.feeplusfix.adapter.PostRecyclerViewAdapter;
import com.example.feeplusfix.adapter.PostViewHolder;
import com.example.feeplusfix.model.AddPosting;
import com.example.feeplusfix.model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PostRecyclerViewAdapter rvAdapter;
    RecyclerView rvPost;

    private FloatingActionButton fabButtonAdd;
//    BottomNavigationView bottomNavigationView;

    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase mDatabase;
    DatabaseReference dataRef;

    String userId;
    private BottomNavigationView.OnNavigationItemReselectedListener navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();

        assert fUser != null;
        userId = fUser.getUid();

        dataRef = mDatabase.getReference().child("posts").child(userId);

//        bottomNavigationView = findViewById(R.id.btm_nav_menu);
//        bottomNavigationView.setOnNavigationItemReselectedListener(navigation);

        rvPost = findViewById(R.id.rv_post);
        fabButtonAdd = findViewById(R.id.btn_posting);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvPost.setLayoutManager(layoutManager);

//        navigation = new BottomNavigationView.OnNavigationItemReselectedListener() {
//            @Override
//            public boolean onNavigationItemReselected(MenuItem item) {
//                Fragment f = null;
//                switch (item.getItemId()){
//                    case R.id.item_menu_profile:
//                        f = new FragmentProfile();
//                        break;
//
//                    case R.id.item_menu_shop:
//                        f = new FragmentShop();
//                        break;
//
//                    case R.id.item_menu_search:
//                        f = new FragmentSearch();
//                        break;
//
//                    case R.id.item_menu_about:
//                        f = new FragmentAbout();
//                        break;
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,f).commit();
//                return true;
//            }
//        };

        fabButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPosting.class);
                startActivity(intent);
            }
        });

        dataRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Post> data = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()){
                    Post tampilbarang = child.getValue(Post.class);
                    data.add(tampilbarang);
                }
                rvAdapter = new PostRecyclerViewAdapter(data);
                rvPost.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}