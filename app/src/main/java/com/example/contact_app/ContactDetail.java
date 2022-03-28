package com.example.contact_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contact_app.databinding.ActivityMainBinding;
import com.example.contact_app.databinding.ContactDetailBinding;

public class ContactDetail extends AppCompatActivity {
    private ContactDetailBinding binding;
    private ContactViewModel viewModel;
    private ContactAdapter contactAdapter;
    public static final int DETAIL_CONTACT_ACTIVITY_REQUEST_CODE = 2;
    public static final String EXTRA_NAME_EDIT = "EXTRA_NAME_EDIT";
    public static final String EXTRA_EMAIL_EDIT = "EXTRA_EMAIL_EDIT";
    public static final String EXTRA_PHONE_EDIT = "EXTRA_PHONE_EDIT";
    public static final String EXTRA_ID_EDIT = "EXTRA_ID_EDIT";
    public static final String EXTRA_IMAGE_EDIT = "EXTRA_IMAGE_EDIT";
    public static String nameContact;
    public static String phoneContact;
    public static String emailContact;
    public static int idContact;
    public static byte[] imageContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ContactDetailBinding.inflate(getLayoutInflater());

        View viewRoot = binding.getRoot();

        setContentView(viewRoot);

        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getContacts().observe(this, list->{
            contactAdapter = new ContactAdapter(list);
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Contact Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent = this.getIntent();
        nameContact = intent.getStringExtra(MainActivity.EXTRA_NAME);
        phoneContact = intent.getStringExtra(MainActivity.EXTRA_PHONE);
        emailContact = intent.getStringExtra(MainActivity.EXTRA_EMAIL);
        imageContact = intent.getByteArrayExtra(MainActivity.EXTRA_IMAGE);
        idContact = intent.getIntExtra(MainActivity.EXTRA_ID , -1);
        binding.tvNameDetail.setText(nameContact);
        binding.tvPhoneDetail.setText(phoneContact);
        binding.idIVContact.setImageBitmap(BitmapFactory.decodeByteArray(imageContact, 0, imageContact.length));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.edit_action);
        menuItem.setOnMenuItemClickListener(e ->{
            Intent replyIntent = new Intent(ContactDetail.this , ContactEdit.class);
            if (TextUtils.isEmpty(binding.tvNameDetail.getText()) || TextUtils.isEmpty(binding.tvPhoneDetail.getText())) {
                Toast.makeText(this, "Please choose persion", Toast.LENGTH_SHORT).show();
            } else {
                replyIntent.putExtra(EXTRA_NAME_EDIT, nameContact);
                replyIntent.putExtra(EXTRA_PHONE_EDIT, phoneContact);
                replyIntent.putExtra(EXTRA_EMAIL_EDIT, emailContact);
                replyIntent.putExtra(EXTRA_ID_EDIT , idContact);
                replyIntent.putExtra(EXTRA_IMAGE_EDIT , imageContact);
                setResult(RESULT_OK, replyIntent);
                startActivity(replyIntent);
                finish();
            }
            return true;
        });

        return super.onCreateOptionsMenu(menu);
    }
}