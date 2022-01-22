package com.example.qurantineexam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Register extends AppCompatActivity {

    FloatingActionButton floatbtn;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    TextView signin;
    EditText userField, mailField, passField;
    ImageButton uploadbtn;
    TextView uploadbtnText;
    DatabaseReference mDatabase;
    private Uri filePath;

    private String imgUrl;
    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        signin = findViewById(R.id.sign_in);
        floatbtn = findViewById(R.id.floatBtn);
        uploadbtn =findViewById(R.id.upload_btn);
        uploadbtnText = findViewById(R.id.upload_btn_text);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID uid;
                uid = UUID.randomUUID();
                uploadImage(uid);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.example.hackt2.Register.this, LogIn.class));
            }
        });

        uploadbtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(uploadbtn);
            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(uploadbtn);
            }
        });
    }

   private void chooseImage(ImageButton imgBtn){
       Intent intent = new Intent();
       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        ImageButton imgbtn = findViewById(R.id.upload_btn);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgbtn.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private void uploadImage(UUID uid){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+uid.toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(com.example.hackt2.Register.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                    new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String fileLink = task.getResult().toString();
                                            registerUser(fileLink);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(com.example.hackt2.Register.this, "Oops something went wrong! ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void registerUser(String img_Url){
        FirebaseApp.initializeApp(this);
      EditText  userField = findViewById(R.id.TF1);
       EditText mailField=  findViewById(R.id.TF2);
      EditText  passField = findViewById(R.id.TF3);
        mAuth = FirebaseAuth.getInstance();
        String username = userField.getText().toString();
        String mail = mailField.getText().toString();
        String password = passField.getText().toString();


        if(username.isEmpty()){
            userField.setError("Required field");
        }
        if(mail.isEmpty()){
            mailField.setError("Required field");
        }
        if(password.length()<8){
            passField.setError("Password too weak");
        }
        if (!mail.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mailField.setError("Invalid email");
        }

        mAuth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                User user = new User(username, mail,img_Url);
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser fUser = mAuth.getCurrentUser();
                String userId = fUser.getUid();
                mDatabase.child("users").child(fUser.getUid()).setValue(user);
               /* mDatabase.child("users").child("username").setValue(username);
                mDatabase.child("users").child("image link").setValue(img_Url);
                mDatabase.child("users").child("email").setValue(mail);*/

                Toast.makeText(com.example.hackt2.Register.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(com.example.hackt2.Register.this, LogIn.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(com.example.hackt2.Register.this, "Oops something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    }

