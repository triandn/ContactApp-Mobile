package com.example.contact_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    public LiveData<List<Contact>> getAllContact();
    @Insert
    public void insertAlL(Contact... contact);
    @Insert
    void insert(Contact contact);
}
