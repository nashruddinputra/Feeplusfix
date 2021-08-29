package com.example.feeplusfix.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.feeplusfix.MainActivity;
import com.example.feeplusfix.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class AddPosting extends AppCompatActivity implements View.OnClickListener {

    private EditText etNamaBarang, etDeskripsiBarang, etHargaBarang;
    private android.widget.Button btnKirimBarang, btnOpenStorage;
    private ImageView imgViewPhoto;
    private ProgressBar pbUploadImage;

    private static final int CAMERA_PERMISSION_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int GALLERY_REQUEST_CODE = 105;

    FirebaseStorage fStorage;
    StorageReference fStorageRef;
    FirebaseDatabase mDatabase;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    String userId;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_posting);

        mDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        assert fUser != null;
        userId = fUser.getUid();

        fStorage = FirebaseStorage.getInstance();
        fStorageRef = fStorage.getReference();

        etNamaBarang = findViewById(R.id.et_nama_barang);
        etDeskripsiBarang = findViewById(R.id.et_deskripsi_barang);
        etHargaBarang = findViewById(R.id.et_harga_barang);
        btnKirimBarang = findViewById(R.id.btn_kirim_barang);
        btnOpenStorage = findViewById(R.id.btn_open_storage);
        imgViewPhoto = findViewById(R.id.im_add_image);
        pbUploadImage = findViewById(R.id.pb_upload_image);

        btnOpenStorage.setOnClickListener(this);

        btnKirimBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String namabarang = etNamaBarang.getText().toString().trim();
                String deskripsibarang = etDeskripsiBarang.getText().toString().trim();
                String hargabarang = etHargaBarang.getText().toString().trim();
                String gambarbarang = imageUrl;

                boolean isvalid = true;
                if (TextUtils.isEmpty(namabarang)) {
                    etNamaBarang.setError("Masukan Nama Barang!");
                    isvalid = false;
                }
                if (TextUtils.isEmpty(deskripsibarang)) {
                    etDeskripsiBarang.setError("Masukan Deskripsi Barang!");
                    isvalid = false;
                }
                if (TextUtils.isEmpty(hargabarang)) {
                    etHargaBarang.setError("Masukan Harga Barang!");
                    isvalid = false;
                }
                if (!isvalid) {
                    Toast.makeText(AddPosting.this, "Posting Barang Gagal!", Toast.LENGTH_SHORT).show();
                    return;
                }

                writeNewPost(namabarang, deskripsibarang, hargabarang, gambarbarang);

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

    public void writeNewPost(String namaBarang, String deskripsiBarang, String hargaBarang, String gambarbarang) {
        Post tambahbarang = new Post(namaBarang, deskripsiBarang, hargaBarang, gambarbarang);

        DatabaseReference dataref = mDatabase.getReference().child("posts").child(userId).child(this.getSaltString());

        dataref.setValue(tambahbarang)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddPosting.this, "Posting Barang Berhasil", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddPosting.this, MainActivity.class);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_open_storage:
                AskPermissionsCamera();
                AskPermissionsGalery();
                getImage();
                break;
        }

    }

    private void AskPermissionsCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

        }
    }

    private void AskPermissionsGalery(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                OpenCamera();
            } else {
                Toast.makeText(AddPosting.this, "Permission is Required!", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                OpenGalery();
            } else {
                Toast.makeText(AddPosting.this, "Permission is Required!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void OpenCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void OpenGalery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
    }

    private void getImage(){
        CharSequence[] menu = {"Kamera", "Galeri"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Upload Image").setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        Intent Camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(Camera,CAMERA_REQUEST_CODE);
                        break;

                    case 1:
                        Intent Galery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(Galery,GALLERY_REQUEST_CODE);
                }
            }
        });

        dialog.create();
        dialog.show();
    }

    private void uploadImage(){
        imgViewPhoto.setDrawingCacheEnabled(true);
        imgViewPhoto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgViewPhoto.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        String namaFile = UUID.randomUUID() + ".jpg";
        String pathImage = "gambar/" + namaFile;
        StorageReference photoRef = fStorageRef.child("images").child(userId).child(pathImage);
        UploadTask uploadTask = photoRef.putBytes(bytes);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        imageUrl = url;
                    }
                });
                Toast.makeText(AddPosting.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                pbUploadImage.setVisibility(View.GONE);
                imgViewPhoto.setVisibility(View.VISIBLE);
            }
        });

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddPosting.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        });

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                Toast.makeText(AddPosting.this, "On Progress", Toast.LENGTH_SHORT).show();
                pbUploadImage.setVisibility(View.VISIBLE);
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                pbUploadImage.setProgress((int) progress);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgViewPhoto.setImageBitmap(bitmap);
                    uploadImage();
                }
                break;

            case GALLERY_REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    imgViewPhoto.setImageURI(uri);
                    uploadImage();
                }
                break;
        }
    }


}