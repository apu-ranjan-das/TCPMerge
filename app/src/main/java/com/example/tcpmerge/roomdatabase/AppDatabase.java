package com.example.tcpmerge.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract ProductDao ProductDao();
}
