package br.com.philippesis.ksqlitedatabase.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.philippesis.ksqlitedatabase.data.models.User

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($ID Integer PRIMARY KEY, $FIRST_NAME TEXT, $LAST_NAME TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addUser(user: User): Boolean {
        // Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FIRST_NAME, user.firstName)
        values.put(LAST_NAME, user.lastName)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun getAllUsers(): String {
        var allUsers = ""
        val db = readableDatabase
        val selectAllQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectAllQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex(ID))
                    val firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                    val lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))
                    allUsers = "$allUsers\n$id $firstName $lastName"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUsers
    }

    companion object {
        private const val DB_NAME = "UsersDB"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val ID = "id"
        private const val FIRST_NAME = "FirstName"
        private const val LAST_NAME = "LastName"
    }

}