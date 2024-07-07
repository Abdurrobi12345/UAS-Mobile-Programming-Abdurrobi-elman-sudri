package com.abdurrobi.loginactivity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "images.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "photos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_IMAGE BLOB)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addPhoto(image: ByteArray) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_IMAGE, image)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllPhotos(): MutableList<ByteArray> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_IMAGE), null, null, null, null, null)
        val images = mutableListOf<ByteArray>()
        if (cursor.moveToFirst()) {
            do {
                val image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                images.add(image)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return images
    }

    fun deletePhoto(image: ByteArray) {
        val db = this.writableDatabase
        val whereClause = "$COLUMN_IMAGE = ?"
        val whereArgs = arrayOf(image.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}
