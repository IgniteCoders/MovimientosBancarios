package com.example.movimientosbancarios.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.movimientosbancarios.data.Movement

class DatabaseManager(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "bank_movements.db"
        const val DATABASE_VERSION = 1
        const val COLUMN_NAME_ID = "id"

        private const val SQL_CREATE_TABLE_MOVEMENTS =
            "CREATE TABLE ${Movement.TABLE_NAME} (" +
                    "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Movement.COLUMN_NAME_QUANTITY} REAL," +
                    "${Movement.COLUMN_NAME_DATE} INTEGER)"

        private const val SQL_DELETE_TABLE_MOVEMENTS = "DROP TABLE IF EXISTS ${Movement.TABLE_NAME}"
    }

    /*override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys = ON;");
    }*/

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MOVEMENTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        destroyDatabase(db)
        onCreate(db)
    }

    private fun destroyDatabase (db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TABLE_MOVEMENTS)
    }
}