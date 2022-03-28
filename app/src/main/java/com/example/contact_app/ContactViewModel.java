package com.example.contact_app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactAsync contactAsync;
    private LiveData<List<Contact>> contacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactAsync = new ContactAsync(application);

        contacts = contactAsync.getContacts();
    }

    public void insert(Contact contact){
        contactAsync.insert(contact);
    }
    public void update(Contact contact){
        contactAsync.update(contact);
    }
    public void delete(Contact contact){
        contactAsync.delete(contact);
    }

    public LiveData<List<Contact>> getContacts(){
        return contacts;
    }
}
