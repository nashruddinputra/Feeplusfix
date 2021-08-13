package com.example.feeplusfix;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.feeplusfix.adapter.PostRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class FragmentShop extends Fragment {

    private PostRecyclerViewAdapter rvAdapter;
    private RecyclerView rvPost;

    private FloatingActionButton fabButtonAdd;

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser = fAuth.getCurrentUser();
    String userId = fUser.getUid();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = mDatabase.getReference().child("posts").child(userId);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }
}