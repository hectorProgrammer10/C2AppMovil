package com.example.c1moviles.drogstore.core.data.local.appDatabase

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var appDataBase: AppDatabase? = null

    fun getAppDataBase(ctx: Context): AppDatabase{
        if (appDataBase == null){
            appDataBase = Room.databaseBuilder(
                ctx.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).fallbackToDestructiveMigration()
                .build()
        }
        return appDataBase!!
    }
    fun destroyDatabase(){
        appDataBase=null
    }
}