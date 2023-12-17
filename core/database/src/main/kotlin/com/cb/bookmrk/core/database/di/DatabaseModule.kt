package com.cb.bookmrk.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cb.bookmrk.core.database.BookmrkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesBookmrkDatabase(
        @ApplicationContext context: Context
    ): BookmrkDatabase = Room.databaseBuilder(
        context,
        BookmrkDatabase::class.java,
        "bookmrk-database"
    ).addCallback(
        object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL(
                    """
                        INSERT INTO groups (title) VALUES ('Collections')
                    """.trimIndent()
                )
            }
        }
    ).build()
}
