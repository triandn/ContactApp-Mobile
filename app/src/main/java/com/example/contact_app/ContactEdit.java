package com.example.contact_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contact_app.databinding.ActivityContactEdit2Binding;
import com.example.contact_app.databinding.ContactDetailBinding;

import java.io.ByteArrayOutputStream;

public class ContactEdit extends AppCompatActivity {
    private ActivityContactEdit2Binding binding;
    private ContactViewModel viewModel;
    private ContactAdapter contactAdapter;
    public static final String EXTRA_ID_EDIT = "EXTRA_ID_EDIT";
    public static final String EXTRA_NAME_EDIT = "EXTRA_NAME_EDIT";
    public static final String EXTRA_EMAIL_EDIT = "EXTRA_EMAIL_EDIT";
    public static final String EXTRA_PHONE_EDIT = "EXTRA_PHONE_EDIT";
    public static final String EXTRA_NAME_ORIGINAL = "EXTRA_NAME_ORIGINAL";

    public static String nameContact;
    public static String phoneContact;
    public static String emailContact;
    public static int idContact;
    public static  byte[] imageContact;

    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactEdit2Binding.inflate(getLayoutInflater());

        View viewRoot = binding.getRoot();

        setContentView(viewRoot);

        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getContacts().observe(this, list->{
            contactAdapter = new ContactAdapter(list);
        });

        Intent intent = this.getIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Contact");
        actionBar.setDisplayHomeAsUpEnabled(true);
        dbHandler = new DBHandler(ContactEdit.this);
        nameContact = intent.getStringExtra(ContactDetail.EXTRA_NAME_EDIT);
        phoneContact = intent.getStringExtra(ContactDetail.EXTRA_PHONE_EDIT);
        emailContact = intent.getStringExtra(ContactDetail.EXTRA_EMAIL_EDIT);
        idContact = intent.getIntExtra(ContactDetail.EXTRA_ID_EDIT , -1);
        imageContact = intent.getByteArrayExtra(ContactDetail.EXTRA_IMAGE_EDIT);
        binding.editName.setText(nameContact);
        binding.editPhone.setText(phoneContact);
        binding.editEmail.setText(emailContact);
        binding.idIVContact.setImageBitmap(BitmapFactory.decodeByteArray(imageContact, 0, imageContact.length));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.save_edit);

        menuItem.setOnMenuItemClickListener(e ->{

            if (TextUtils.isEmpty(binding.editName.getText()) || TextUtils.isEmpty(binding.editEmail.getText()) || TextUtils.isEmpty(binding.editPhone.getText())) {
                Toast.makeText(this, "Please insert information", Toast.LENGTH_SHORT).show();
            } else {
                String name = binding.editName.getText().toString().trim();
                String phoneNumber = binding.editPhone.getText().toString().trim();
                String email = binding.editEmail.getText().toString().trim();
                binding.idIVContact.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) binding.idIVContact.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] byteImage = baos.toByteArray();

                if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                    Toast.makeText(ContactEdit.this, "Please enter the valid contact details.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Contact contact = new Contact();
                contact.setId(idContact);
                contact.setName(name);
                contact.setPhone(phoneNumber);
                contact.setEmail(email);
                contact.setImage(byteImage);
                viewModel.update(contact);
                finish();
            }
            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }
}