package com.example.contact_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.app.SearchManager;
import android.widget.SearchView.OnQueryTextListener;
import com.example.contact_app.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;


    private ActivityMainBinding binding;
    private ContactViewModel viewModel;
    private ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);


        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getContacts().observe(this, list->{
            contactAdapter = new ContactAdapter(list);
            binding.rvContact.setAdapter(contactAdapter);
        });

        binding.btnUp.setOnClickListener(e -> {
            Intent intent = new Intent(MainActivity.this, ContactNewActivity.class);
            startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search contact");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            String name = data.getStringExtra(ContactNewActivity.EXTRA_NAME);
            String phone = data.getStringExtra(ContactNewActivity.EXTRA_PHONE);
            String email = data.getStringExtra(ContactNewActivity.EXTRA_EMAIL);

            Contact contact = new Contact(name, phone, email);
            viewModel.insert(contact);
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not saved", Toast.LENGTH_SHORT).show();
        }
    }
}