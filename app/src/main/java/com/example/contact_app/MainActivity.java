package com.example.contact_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.app.SearchManager;
import android.widget.SearchView.OnQueryTextListener;
import com.example.contact_app.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    public static final int DETAIL_CONTACT_ACTIVITY_REQUEST_CODE = 2;
    public static final int EDIT_CONTACT_ACTIVITY_REQUEST_CODE = 3;
    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_PHONE = "EXTRA_PHONE";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";

    public ActivityMainBinding binding;
    public ContactViewModel viewModel;
    public ContactAdapter contactAdapter;
    public List<Contact> contactList;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getContacts().observe(this, list->{
            contactList = list;
            contactAdapter = new ContactAdapter(list);
            binding.rvContact.setAdapter(contactAdapter);
            contactAdapter.setClickListener(this);
        });

        dbHandler = new DBHandler(MainActivity.this);

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
//            byte[] image = data.getByteArrayExtra(ContactNewActivity.EXTRA_IMAGE);
//            Log.i("image" , image.toString());
//            Contact contact = new Contact(name, phone, email ,image);
//            viewModel.insert(contact);
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not saved", Toast.LENGTH_SHORT).show();
        }
//        if(requestCode == EDIT_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
//            int id = data.getIntExtra(ContactEdit.EXTRA_ID_EDIT, -1);
//            String name = data.getStringExtra(ContactEdit.EXTRA_NAME_EDIT);
//            String phone = data.getStringExtra(ContactEdit.EXTRA_PHONE_EDIT);
//            String email = data.getStringExtra(ContactEdit.EXTRA_EMAIL_EDIT);
//            Log.i("edit name" , name);
//
//            Contact contact = new Contact(name, phone, email);
//            contact.setId(id);
//            viewModel.update(contact);
//            contactAdapter.notifyDataSetChanged();
//            Toast.makeText(this, "Update succes", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this, "Update Error", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onClick(View view, int position) {
        final Contact contact = contactList.get(position);
        Intent intent = new Intent(MainActivity.this , ContactDetail.class);
        intent.putExtra(EXTRA_ID , contact.getId());
        intent.putExtra(EXTRA_NAME , contact.getName());
        intent.putExtra(EXTRA_PHONE , contact.getPhone());
        intent.putExtra(EXTRA_EMAIL , contact.getEmail());
        intent.putExtra(EXTRA_IMAGE , contact.getImage());
//        intent.putExtra(EXTRA_ID , contact.getId());
        Log.i("hello" , contact.getName() + contact.getPhone());
        startActivity(intent);
    }
}