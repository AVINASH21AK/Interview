package com.interview.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.system.Os.close
import android.os.Build.ID
import android.database.Cursor
import android.content.ContentValues


class DatabaseHelper : SQLiteOpenHelper() {

    var TAG = "DatabaseHelper"
    private val DATABASE_VERSION = 1
    var context: Context? = null
    var db: SQLiteDatabase? = null

    val TABLE_TECHNOLOGY = "tbl_technology"


    /*
    * Technology Parameter
    * */
    object modelTechnologyTbl {
        val ID = "departmentid"
        val IMAGE = "image"
        val DESC = "description"
    }



    override fun onCreate(p0: SQLiteDatabase?) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TECHNOLOGY + "(" +
                modelTechnologyTbl.ID + " TEXT," +
                modelTechnologyTbl.IMAGE + " TEXT," +
                modelTechnologyTbl.DESC + " TEXT" +
                ");");
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS '" + TABLE_TECHNOLOGY + "'");

        App.showLog(TAG, "---===---DATABASE TABLE UPDATED, ONCREATED CALLED.---===---");

        onCreate(db);
    }




    /*
    * Sub-Department Technology
    * */
    fun dbInsertTechnology(model: TechnologyModel) {
        try {
            val temp = dbCheckTechnology(model)
            if (temp != null && temp.length > 0) {
            } else {
                //App.showLog(TAG, "dbInsertTechnology");

                val db = this.writableDatabase
                val values = ContentValues()

                values.put(modelTechnologyTbl.ID, model.departmentid)
                values.put(modelTechnologyTbl.IMAGE, model.image)
                values.put(modelTechnologyTbl.DESC, model.desc)

                db.insert(TABLE_TECHNOLOGY, null, values)
                db.close()
            }
        } catch (e: Exception) {
        }

    }

    fun dbCheckTechnology(model: TechnologyModel): String {
        var getEntry = ""
        try {
            //App.showLog(TAG, "dbCheckTechnology");

            val db = this.readableDatabase
            var cursor: Cursor? = null
            val selectQuery = "SELECT  * FROM " + TABLE_TECHNOLOGY + " where " + modelTechnologyTbl.ID + " = '" + model.departmentid + "'"
            cursor = db.rawQuery(selectQuery, null) //THIS WILL GIVE NO. OF ROW HAVING VALUES

            if (cursor != null) {
                if (cursor.count > 0) {
                    getEntry = "AlreadyExist" //bcz it return no of row greater than 0 having deprt and mainid same
                    dbUpdateTechnology(model)
                }
            }
            cursor!!.close()
            db.close()
        } catch (e: Exception) {
        }

        return getEntry
    }

    private fun dbUpdateTechnology(model: TechnologyModel) {
        try {
            //App.showLog(TAG, "dbUpdateTechnology");

            val db = this.writableDatabase
            val values = ContentValues()

            values.put(modelTechnologyTbl.ID, model.departmentid)
            values.put(modelTechnologyTbl.IMAGE, model.image)
            values.put(modelTechnologyTbl.DESC, model.desc)

            db.update(TABLE_TECHNOLOGY, values, modelTechnologyTbl.ID + "='" + model.departmentid + "'", null)
            db.close()
        } catch (e: Exception) {
        }

    }

    fun getTechnologyModel(strDepartmentID: String): TechnologyModel {
        val data = TechnologyModel()
        try {
            val db = this.readableDatabase
            val selectQuery = "SELECT  * FROM " + TABLE_TECHNOLOGY + " where " + modelTechnologyTbl.ID + " = '" + strDepartmentID + "'"

            var cursor: Cursor? = null

            cursor = db.rawQuery(selectQuery, null)
            if (cursor != null) {
                cursor.moveToFirst()
                if (cursor.moveToFirst()) {
                    do {

                        val technologyID = cursor.getString(cursor
                            .getColumnIndex(modelTechnologyTbl.ID))
                        val technologyIMAGE = cursor.getString(cursor
                            .getColumnIndex(modelTechnologyTbl.IMAGE))
                        val technologyDESC = cursor.getString(cursor
                            .getColumnIndex(modelTechnologyTbl.DESC))


                        try {

                            data.departmentid = technologyID
                            data.image = technologyIMAGE
                            data.desc = technologyDESC

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    } while (cursor.moveToNext())
                }
                cursor.close()
            }
            db.close() // Closing database connection
        } catch (e: Exception) {

        }

        return data
    }


}