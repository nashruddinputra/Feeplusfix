package com.example.feeplusfix;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentProfile extends Fragment {

    EditText noWhatsApp;
    TextView tvNoWa;


    android.widget.Button btnKirimWa, btnSignOut;

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser = fAuth.getCurrentUser();
    String userId = fUser.getUid();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = mDatabase.getReference().child("users").child(userId);
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        noWhatsApp = view.findViewById(R.id.et_nomer_wa);
        btnKirimWa = view.findViewById(R.id.btn_kirim_no_wa);
        tvNoWa = view.findViewById(R.id.tv_no_wa_user);
        btnSignOut = view.findViewById(R.id.btn_sign_out);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("673425761247-lblf5iu1j124tnacu83o8l978ji506is.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity().getApplicationContext(), gso);

        btnKirimWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kirimWa = noWhatsApp.getText().toString().trim();

                boolean isvalid = true;

                if (TextUtils.isEmpty(kirimWa)){
                    noWhatsApp.setError("Masukan nomor WhatsApp!");
                    isvalid = false;
                }
                if (!isvalid) {
                    Toast.makeText(getActivity().getApplicationContext(),"Tambah/Ubah Nomor WhatsApp Gagal!",Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference databaseReference = mDatabase.getReference().child("users").child(userId).child("no_whatsapp");
                databaseReference.setValue(noWhatsApp.getText().toString());
                Toast.makeText(getActivity().getApplicationContext(),"Tambah/Ubah Nomor WhatsApp Berhasil",Toast.LENGTH_SHORT).show();

                noWhatsApp.setText("");
            }
        });

        DatabaseReference databaseReference = mDatabase.getReference().child("users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("no_whatsapp").getValue() != null) {
                    String nowa = snapshot.child("no_whatsapp").getValue().toString();
                    tvNoWa.setText(nowa);
                } else {
                    tvNoWa.setText("No Wa belum Di isi");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        return view;
    }

    private void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        fAuth.signOut();
                        Toast.makeText(getActivity().getApplicationContext(),"SignOut Successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(), FirstPageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
    }
}