package com.example.feeplusfix;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.feeplusfix.adapter.PostRecyclerViewAdapter;
import com.example.feeplusfix.model.AddPosting;
import com.example.feeplusfix.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class FragmentShop extends Fragment {

    private PostRecyclerViewAdapter rvAdapter;
    private RecyclerView rvPost;

    private FloatingActionButton fabButtonAdd;

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser = fAuth.getCurrentUser();
    String userId = fUser.getUid();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = mDatabase.getReference().child("posts");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        rvPost = view.findViewById(R.id.rv_post);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        rvPost.setLayoutManager(layoutManager);

        rvPost.setFocusable(true);
        rvPost.setClickable(true);
        rvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fabButtonAdd = view.findViewById(R.id.btn_posting);

        fabButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddPosting.class);
                startActivity(intent);
            }
        });

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Post> data = new ArrayList<>();
                for(DataSnapshot childPost : snapshot.getChildren()){
                    for (DataSnapshot child : childPost.getChildren()){
                        Post tampilbarang = child.getValue(Post.class);
                        tampilbarang.setUserId(childPost.getKey());
                        data.add(tampilbarang);
                    }
                }

                rvAdapter = new PostRecyclerViewAdapter(data, getActivity());
                rvPost.setAdapter(rvAdapter);
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}