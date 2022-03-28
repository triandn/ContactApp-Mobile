package com.example.contact_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.contact_app.databinding.ActivityContactNewBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactNewActivity extends AppCompatActivity {

    public ActivityContactNewBinding binding;

    private List<Contact> contactList;
    private ContactAdapter contactAdapter;
    private AppDatabase appDatabase;
    private ContactDao contactDao;
    public ContactViewModel viewModel;

    private static final int REQUEST_CODE_CAPTURE = 100;

    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String EXTRA_PHONE = "EXTRA_PHONE";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactNewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getContacts().observe(this, list->{
            contactList = list;
            contactAdapter = new ContactAdapter(list);

        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Contact");
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(ContextCompat.checkSelfPermission(ContactNewActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactNewActivity.this, new String[] {
                    Manifest.permission.CAMERA
            }, REQUEST_CODE_CAPTURE);
        }

        binding.ibCameraAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCapture, REQUEST_CODE_CAPTURE);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_contact, menu);

        MenuItem menuItem = menu.findItem(R.id.save_action);

        menuItem.setOnMenuItemClickListener(e ->{
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(binding.editName.getText()) || TextUtils.isEmpty(binding.editEmail.getText()) || TextUtils.isEmpty(binding.editPhone.getText())) {
                Toast.makeText(this, "Please insert information", Toast.LENGTH_SHORT).show();
            } else {
                binding.idIVContact.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) binding.idIVContact.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] byteImage = baos.toByteArray();

                String name = binding.editName.getText().toString().trim();
                String phoneNumber = binding.editPhone.getText().toString().trim();
                String email = binding.editEmail.getText().toString().trim();
                Contact contact = new Contact(name , phoneNumber , email , byteImage);
                viewModel.insert(contact);
                Log.i("image" , byteImage.toString());
                replyIntent.putExtra(EXTRA_NAME, name);
                replyIntent.putExtra(EXTRA_PHONE, phoneNumber);
                replyIntent.putExtra(EXTRA_EMAIL, email);
                replyIntent.putExtra(EXTRA_IMAGE , byteImage);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CAPTURE) {
            //GET CAPTURE Image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //SET CAPTURE Image
            binding.idIVContact.setImageBitmap(captureImage);
        }
    }


}

