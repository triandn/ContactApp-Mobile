package com.example.contact_app;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    private static AppDatabase instance;
    public static synchronized AppDatabase getInstance(Context context){
        if( instance == null){
          instance = Room.databaseBuilder(context,
                  AppDatabase.class, "contactDb")
                  .fallbackToDestructiveMigration()
                  .addCallback(roomCallBack)
                  .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new DBAsyncTask(instance).execute();
        }
    };
    private static class DBAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao contactDao;

        private DBAsyncTask(AppDatabase db){
            this.contactDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
