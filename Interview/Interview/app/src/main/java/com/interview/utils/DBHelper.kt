package com.interview.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.interview.api.model.MedicineModel



class DBHelper(context: Context)  : SQLiteOpenHelper(context, App.DATABASE_FOLDER_FULLPATH, null, DATABASE_VERSION) {

    var strTAG : String = "DBHelper"




    companion object {


        private val DATABASE_VERSION = 1
        val DATABASE_NAME = "Interview.db"


        val TABLE_MEDICINE = "tbl_medicine"

        object modelTechnologyTbl {
            val ID = "id"
            val NAME = "name"
            val DESC = "description"
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MEDICINE + "(" +
                modelTechnologyTbl.ID + " TEXT," +
                modelTechnologyTbl.NAME + " TEXT," +
                modelTechnologyTbl.DESC + " TEXT" +
                ");");
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS '" + TABLE_MEDICINE + "'");

        App.showLog(strTAG, "---===---DATABASE TABLE UPDATED, ONCREATED CALLED.---===---");

        onCreate(db);
    }


    fun dbInsertTechnology(model: MedicineModel) {
        try {
            val db = this.writableDatabase
            val values = ContentValues()

            values.put(modelTechnologyTbl.ID, model.mid)
            values.put(modelTechnologyTbl.NAME, model.medicine_name)
            values.put(modelTechnologyTbl.DESC, model.shape)

            db.insert(TABLE_MEDICINE, null, values)
            db.close()
        } catch (e: Exception) {
        }

    }


    fun getAllMedicine(): ArrayList<MedicineModel> {
        val data : ArrayList<MedicineModel> = ArrayList()
        try {
            val db = this.readableDatabase
            val selectQuery = "SELECT  * FROM $TABLE_MEDICINE"

            var cursor: Cursor? = null

            cursor = db.rawQuery(selectQuery, null)
            if (cursor != null) {
                cursor.moveToFirst()
                if (cursor.moveToFirst()) {
                    do {

                        val strID = cursor.getString(
                            cursor
                                .getColumnIndex(modelTechnologyTbl.ID)
                        )
                        val strNAME = cursor.getString(
                            cursor
                                .getColumnIndex(modelTechnologyTbl.NAME)
                        )
                        val strDESC = cursor.getString(
                            cursor
                                .getColumnIndex(modelTechnologyTbl.DESC)
                        )




                        try {

                            val notificationModel = MedicineModel()
                            notificationModel.mid = strID
                            notificationModel.medicine_name = strNAME
                            notificationModel.shape = strDESC

                            data.add(notificationModel)


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





    fun deleteAllMedicine(){
        try{
            val db = this.writableDatabase
            db.delete(TABLE_MEDICINE, null, null)

        }catch (e : Exception){

        }
    }






    /* Insert - Check - Update - Get(Single & All) - Delete (Single & All)

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

                db.insert(TABLE_MEDICINE, null, values)
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
            val selectQuery = "SELECT  * FROM " + TABLE_MEDICINE + " where " + modelTechnologyTbl.ID + " = '" + model.departmentid + "'"
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

            db.update(TABLE_MEDICINE, values, modelTechnologyTbl.ID + "='" + model.departmentid + "'", null)
            db.close()
        } catch (e: Exception) {
        }

    }

    fun getTechnologyModel(strDepartmentID: String): TechnologyModel {
        val data = TechnologyModel()
        try {
            val db = this.readableDatabase
            val selectQuery = "SELECT  * FROM " + TABLE_MEDICINE + " where " + modelTechnologyTbl.ID + " = '" + strDepartmentID + "'"

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


    public void dbDeleteInsertNew_DocHOD(String docID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //----Delete full table--->  db.delete(TABLE_NAME_DEPARTMENT_DATA_SERVICES_DETAIL, null, null);
            db.execSQL("DELETE FROM " + TABLE_DOCTOR_HOD + " where " + modelDoctorHODTble.DOCID + " = '" + docID + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dbDeleteTBLNotification() {
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                //----Delete  full table--->
                db.delete(TABLE_NOTIFICATION, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    */


}