package com.example.contact_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    public LiveData<List<Contact>> getAllContact();
    @Insert
    public void insertAlL(Contact... contact);
    @Insert
    void insert(Contact contact);
    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact")
    void deleteAll();
}
