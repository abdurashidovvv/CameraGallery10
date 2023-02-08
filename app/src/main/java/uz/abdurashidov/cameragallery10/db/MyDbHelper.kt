package uz.abdurashidov.cameragallery10.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.abdurashidov.cameragallery10.models.User

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    MyDbInterface {

    companion object {
        val DB_NAME = "users_db"
        val DB_VERSION = 1

        val TABLE_USERS = "users_table"
        val USER_ID = "id"
        val USER_NAME = "name"
        val USER_SURNAME = "surname"
        val USER_IMAGE = "image"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val queryUser =
            "create table $TABLE_USERS($USER_ID integer not null primary key autoincrement, $USER_NAME text not null, $USER_SURNAME text not null, $USER_IMAGE text not null)"
        p0?.execSQL(queryUser)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun getAllUsers(): ArrayList<User> {
        val list = ArrayList<User>()
        val query = "select * from $TABLE_USERS"
        val database = readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    User(
                        id = cursor.getInt(0),
                        name = cursor.getString(1),
                        surname = cursor.getString(2),
                        image = cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }

        return list
    }

    override fun addUser(user: User) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, user.name)
        contentValues.put(USER_SURNAME, user.surname)
        contentValues.put(USER_IMAGE, user.image)
        database.insert(TABLE_USERS, null, contentValues)
        database.close()
    }

    override fun editUser(user: User) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_ID, user.id)
        contentValues.put(USER_NAME, user.name)
        contentValues.put(USER_SURNAME, user.surname)
        contentValues.put(USER_IMAGE, user.image)
        database.update(TABLE_USERS, contentValues, "$USER_ID=?", arrayOf(user.id.toString()))
    }

    override fun deleteUser(user: User) {
        val database = writableDatabase
        database.delete(TABLE_USERS, "$USER_ID=?", arrayOf(user.id.toString()))
        database.close()
    }


}