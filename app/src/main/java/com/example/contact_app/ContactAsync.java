package com.example.contact_app;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactAsync {
    private ContactDao contactDao;
    private LiveData<List<Contact>> contacts;

    public ContactAsync(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        contactDao = appDatabase.contactDao();
        contacts = contactDao.getAllContact();
    }
    public void insert(Contact contact){
        new InsertContactAsyncTask(contactDao).execute(contact);
    }
    public LiveData<List<Contact>> getContacts(){
        return contacts;
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;
        private InsertContactAsyncTask(ContactDao contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }
    }
}
