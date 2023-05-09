package com.example.preference_ptm7.DB

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        val DATABASE_NAME = "myaps.db"
        val DATABASE_VERSION = 1
        private val SQL_CREATE_USER = "CREATE TABLE " + DBinfo.UserTable.TABLE_NAME + "("+DBinfo.UserTable.COL_EMAIL+" VARCHAR(200) PRIMARY KEY, " + DBinfo.UserTable.COL_PASS + " TEXT, " + DBinfo.UserTable.COL_FULLNAME + " TEXT);"
        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBinfo.UserTable.TABLE_NAME
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    @Throws(SQLiteConstraintException::class)
    fun RegisterUser(emailin: String, passin: String, fullnamein: String) {
        val db = writableDatabase
        val namatable = DBinfo.UserTable.TABLE_NAME
        val emailt = DBinfo.UserTable.COL_EMAIL
        val passt = DBinfo.UserTable.COL_PASS
        val fullnamet = DBinfo.UserTable.COL_FULLNAME

        var sql = "INSERT INTO " + namatable + " (" + emailt + ", " + passt + ", " + fullnamet + ") VALUES('" + emailin + "', '" + passin + "', '" + fullnamein + "')"
        db.execSQL(sql)
    }
    @SuppressLint("Range")
    fun cekUser(emailin: String): String {
        val db = writableDatabase
        var cursor: Cursor? = null
        var jumlah = ""
        try {
            cursor = db.rawQuery("SELECT COUNT("+ DBinfo.UserTable.COL_EMAIL +") as jumlah FROM "+ DBinfo.UserTable.TABLE_NAME + " WHERE " + DBinfo.UserTable.COL_EMAIL + "=='" + emailin +"'" , null)
        } catch (e: android.database.SQLException) {
            db.execSQL(SQL_CREATE_USER)
            return "Tabel Dibuat"
        }
        if (cursor!!.moveToFirst()){
            jumlah = cursor.getString(cursor.getColumnIndex(DBinfo.UserTable.COL_JUMLAH))
        }
        return jumlah
    }
    @SuppressLint("Range")
    fun cekLogin(emailin: String, passin: String): String {
        val db = writableDatabase
        var cursor: Cursor? = null
        var jumlah = ""
        try {
            cursor = db.rawQuery("SELECT COUNT("+ DBinfo.UserTable.COL_EMAIL +") as jumlah FROM "+ DBinfo.UserTable.TABLE_NAME + " WHERE " + DBinfo.UserTable.COL_EMAIL + "=='" + emailin +"' AND " + DBinfo.UserTable.COL_PASS + "=='" + passin + "'" , null)
        } catch (e: android.database.SQLException) {
            db.execSQL(SQL_CREATE_USER)
            return "Tabel Dibuat"
        }
        if (cursor!!.moveToFirst()){
            jumlah = cursor.getString(cursor.getColumnIndex(DBinfo.UserTable.COL_JUMLAH))
        }
        return jumlah
    }
}