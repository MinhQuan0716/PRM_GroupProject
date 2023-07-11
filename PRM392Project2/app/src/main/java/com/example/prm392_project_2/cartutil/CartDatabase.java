package com.example.prm392_project_2.cartutil;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {CartItem.class},version = 2)
public abstract class CartDatabase extends RoomDatabase {
    public abstract CartDAO cartDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS cartitem");
            database.execSQL("CREATE TABLE cartitem (cartId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, product TEXT, quantity INTEGER NOT NULL)");
        }
    };

    // Override the "create" method to use the fallback migration
    public static CartDatabase create(Context context) {
        return Room.databaseBuilder(context, CartDatabase.class, "cartdb")
                .addMigrations(MIGRATION_1_2)
                .build();
    }
}

