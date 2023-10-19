package com.example.simplecontactapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        const val DATABASE_NAME = "contactDB"
        const val TABLE_NAME = "contacts"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_NUMBER = "number"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT, $COL_NUMBER TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertContact(name: String, number: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_NUMBER, number)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    fun updateContact(id: Int, name: String, number: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_NUMBER, number)
        val result = db.update(TABLE_NAME, contentValues, "$COL_ID=?", arrayOf(id.toString()))
        return result > 0
    }

    fun deleteContact(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun getAllContacts(): MutableList<Contact> {
        val contacts = mutableListOf<Contact>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                val number = cursor.getString(cursor.getColumnIndex(COL_NUMBER))
                contacts.add(Contact(id, name, number))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return contacts
    }
}
