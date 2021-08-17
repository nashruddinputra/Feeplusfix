package com.example.feeplusfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase mDatabase;
    DatabaseReference dataRef;

    Fragment fProfile = new FragmentProfile();
    Fragment fShop = new FragmentShop();
    Fragment fAbout = new FragmentAbout();

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

        setFragment(fShop);
        setFragment(fProfile);
        setFragment(fAbout);

        dataRef = mDatabase.getReference().child("posts");

        bottomNavigationView = findViewById(R.id.btm_nav_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_menu_profile:
                        setFragment(fProfile);
                        break;

                    case R.id.item_menu_shop:
                        setFragment(fShop);
                        break;

                    case R.id.item_menu_about:
                        setFragment(fAbout);
                        break;
                }

                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.item_menu_shop);

    }

    protected void setFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void openDetail(Post post) {
        Intent intent = new Intent(getApplicationContext(), DetailPost.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post",(Serializable)post);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}