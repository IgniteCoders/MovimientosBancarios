package com.example.movimientosbancarios.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.movimientosbancarios.utils.DatabaseManager

class MovementDAO (val context: Context) {

    private var databaseManager: DatabaseManager = DatabaseManager(context)

    fun insert(movement: Movement): Movement {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Movement.COLUMN_NAME_QUANTITY, movement.quantity)
        values.put(Movement.COLUMN_NAME_DATE, movement.date)

        val newRowId = db.insert(Movement.TABLE_NAME, null, values)
        Log.i("DATABASE", "New record id: $newRowId")

        db.close()


        movement.id = newRowId.toInt()
        return movement
    }

    fun update(movement: Movement) {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Movement.COLUMN_NAME_QUANTITY, movement.quantity)
        values.put(Movement.COLUMN_NAME_DATE, movement.date)

        val updatedRows = db.update(Movement.TABLE_NAME, values, "${DatabaseManager.COLUMN_NAME_ID} = ${movement.id}", null)
        Log.i("DATABASE", "Updated records: $updatedRows")

        db.close()
    }

    fun delete(movement: Movement) {
        val db = databaseManager.writableDatabase

        val deletedRows = db.delete(Movement.TABLE_NAME, "${DatabaseManager.COLUMN_NAME_ID} = ${movement.id}", null)
        Log.i("DATABASE", "Deleted rows: $deletedRows")

        db.close()
    }

    fun find(id: Int): Movement? {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Movement.TABLE_NAME,                         // The table to query
            Movement.COLUMN_NAMES,       // The array of columns to return (pass null to get all)
            "${DatabaseManager.COLUMN_NAME_ID} = $id",                        // The columns for the WHERE clause
            null,                    // The values for the WHERE clause
            null,                        // don't group the rows
            null,                         // don't filter by row groups
            null                         // The sort order
        )

        var movement: Movement? = null

        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_NAME_ID))
            val quantity = cursor.getFloat(cursor.getColumnIndexOrThrow(Movement.COLUMN_NAME_QUANTITY))
            val date = cursor.getLong(cursor.getColumnIndexOrThrow(Movement.COLUMN_NAME_DATE))

            movement = Movement(id, quantity, date)
        }

        cursor.close()
        db.close()

        return movement
    }

    fun findAll(): List<Movement> {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Movement.TABLE_NAME,                 // The table to query
            Movement.COLUMN_NAMES,     // The array of columns to return (pass null to get all)
            null,                // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            "${Movement.COLUMN_NAME_DATE} DESC"               // The sort order
        )

        val list: MutableList<Movement> = mutableListOf()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_NAME_ID))
            val quantity = cursor.getFloat(cursor.getColumnIndexOrThrow(Movement.COLUMN_NAME_QUANTITY))
            val date = cursor.getLong(cursor.getColumnIndexOrThrow(Movement.COLUMN_NAME_DATE))

            val movement = Movement(id, quantity, date)
            list.add(movement)
        }

        cursor.close()
        db.close()

        return list
    }
}