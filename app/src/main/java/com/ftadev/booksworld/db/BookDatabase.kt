package com.ftadev.booksworld.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ftadev.booksworld.model.BookModel

@Database(entities = [BookModel::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {

        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase? {
            if (INSTANCE == null) {
                synchronized(BookDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                BookDatabase::class.java, "book_db"
                            )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}