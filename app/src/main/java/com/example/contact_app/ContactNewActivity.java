package com.example.contact_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.contact_app.databinding.ActivityContactNewBinding;

import java.util.ArrayList;

public class ContactNewActivity extends AppCompatActivity {

    public ActivityContactNewBinding binding;

    private ArrayList<Contact> contactList;
    private ContactAdapter contactAdapter;
    private AppDatabase appDatabase;
    private ContactDao contactDao;
    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String EXTRA_PHONE = "EXTRA_PHONE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactNewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Contact");
        actionBar.setDisplayHomeAsUpEnabled(true);

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
                String name = binding.editName.getText().toString().trim();
                String phoneNumber = binding.editPhone.getText().toString().trim();
                String email = binding.editEmail.getText().toString().trim();

                replyIntent.putExtra(EXTRA_NAME, name);
                replyIntent.putExtra(EXTRA_PHONE, phoneNumber);
                replyIntent.putExtra(EXTRA_EMAIL, email);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }


}

