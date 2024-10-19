package com.example.reminderpill.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createMedicineListTableQuery = """
            CREATE TABLE $MEDICINE_LIST_TABLE_NAME (
                $COLUMN_MEDICINE_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $COLUMN_USER_ID INTEGER, 
                $COLUMN_MEDICINE_NAME TEXT, 
                $COLUMN_MEDICINE_TYPE_TITLE TEXT, 
                $COLUMN_MEDICINE_TYPE_IMAGE TEXT, 
                $COLUMN_MEDICINE_TIME TEXT, 
                $COLUMN_MEDICINE_REMINDERTIME TEXT, 
                $COLUMN_MEDICINE_REMINDERDATE TEXT, 
                $COLUMN_MEDICINE_SCHEDULE TEXT, 
                $COLUMN_MEDICINE_DURATION INTEGER, 
                $COLUMN_MEDICINE_FREQUENCY INTEGER, 
                $COLUMN_MEDICINE_REFILL_TIME TEXT, 
                $COLUMN_MEDICINE_INSTRUCTIONS TEXT, 
                $COLUMN_MEDICINE_STATUS INTEGER, 
                $COLUMN_MEDICINE_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()

        val createUsersTableQuery = """
            CREATE TABLE $USERS_TABLE_NAME (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $COLUMN_USER_NAME TEXT, 
                $COLUMN_EMAIL TEXT, 
                $COLUMN_PASSWORD TEXT, 
                $COLUMN_PHONE TEXT, 
                $COLUMN_IMAGE_URL TEXT, 
                $COLUMN_IS_LOGGED_IN INTEGER DEFAULT 0
            )
        """.trimIndent()

        val createScheduleTableQuery = """
            CREATE TABLE $TABLE_SCHEDULE (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_DESCRIPTION TEXT,
                $KEY_DURATION TEXT,
                $KEY_FREQUENCY TEXT,
                $KEY_INTAKE TEXT,
                $KEY_REFILL TEXT
            )
        """.trimIndent()

        db.execSQL(createMedicineListTableQuery)
        db.execSQL(createUsersTableQuery)
        db.execSQL(createScheduleTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $MEDICINE_LIST_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $USERS_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SCHEDULE")
        onCreate(db)
    }

    fun addSchedule(schedule: ScheduleModal) {
        val values = ContentValues().apply {
            put(KEY_DESCRIPTION, schedule.description)
            put(KEY_DURATION, schedule.duration)
            put(KEY_FREQUENCY, schedule.frequency)
            put(KEY_INTAKE, schedule.intake)
            put(KEY_REFILL, schedule.refill)
        }

        val db = this.writableDatabase
        db.insert(TABLE_SCHEDULE, null, values)
        db.close()
    }

    fun updateSchedule(schedule: ScheduleModal) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_ID, schedule.medicineId)
            put(KEY_DESCRIPTION, schedule.description)
            put(KEY_DURATION, schedule.duration)
            put(KEY_FREQUENCY, schedule.frequency)
            put(KEY_INTAKE, schedule.intake)
            put(KEY_REFILL, schedule.refill)
        }
        db.update(TABLE_SCHEDULE, values, "$KEY_ID = ?", arrayOf(schedule.medicineId.toString()))
        db.close()
    }

    fun getAllSchedules(): List<ScheduleModal> {
        val scheduleList = mutableListOf<ScheduleModal>()
        val selectQuery = "SELECT * FROM $TABLE_SCHEDULE"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val schedule = ScheduleModal(
                        medicineId = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                        description = it.getString(it.getColumnIndexOrThrow(KEY_DESCRIPTION)),
                        duration = it.getString(it.getColumnIndexOrThrow(KEY_DURATION)),
                        frequency = it.getString(it.getColumnIndexOrThrow(KEY_FREQUENCY)),
                        intake = it.getString(it.getColumnIndexOrThrow(KEY_INTAKE)),
                        refill = it.getString(it.getColumnIndexOrThrow(KEY_REFILL))
                    )
                    scheduleList.add(schedule)
                } while (it.moveToNext())
            }
        }
        db.close()
        return scheduleList
    }

    fun verifyUserCredentialsByEmail(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $USERS_TABLE_NAME WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password)
        )
        val isValidUser = cursor.count > 0
        cursor.close()
        return isValidUser
    }

    fun doesUserExistByEmail(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $USERS_TABLE_NAME WHERE $COLUMN_EMAIL = ?", arrayOf(email))
        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }

    fun logInUserByEmail(email: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_LOGGED_IN, 1)
        }
        val result = db.update(USERS_TABLE_NAME, values, "$COLUMN_EMAIL = ?", arrayOf(email)) > 0
        db.close()
        return result
    }

    fun addMedicine(medicine: Medicine) {
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, medicine.userId)
            put(COLUMN_MEDICINE_NAME, medicine.medicineName)
            put(COLUMN_MEDICINE_TYPE_TITLE, medicine.medicineTypeTitle)
            put(COLUMN_MEDICINE_TYPE_IMAGE, medicine.medicineTypeImage)
            put(COLUMN_MEDICINE_TIME, medicine.medicineTime)
            put(COLUMN_MEDICINE_REMINDERTIME, medicine.medicineReminderTime)
            put(COLUMN_MEDICINE_REMINDERDATE, medicine.medicineReminderDate)
            put(COLUMN_MEDICINE_SCHEDULE, medicine.medicineSchedule)
            put(COLUMN_MEDICINE_DURATION, medicine.medicineDuration)
            put(COLUMN_MEDICINE_FREQUENCY, medicine.medicineFrequency)
            put(COLUMN_MEDICINE_REFILL_TIME, medicine.medicineRefillTime)
            put(COLUMN_MEDICINE_INSTRUCTIONS, medicine.medicineInstructions)
            put(COLUMN_MEDICINE_STATUS, medicine.medicineStatus)
        }
        val db = this.writableDatabase
        db.insert(MEDICINE_LIST_TABLE_NAME, null, values)
        db.close()
    }

    fun addUser(user: UserModal): Boolean {
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, user.userName)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password)
            put(COLUMN_PHONE, user.phone)
            put(COLUMN_IMAGE_URL, user.imageUrl)
        }
        val db = this.writableDatabase
        val success = db.insert(USERS_TABLE_NAME, null, values) != -1L
        db.close()
        return success
    }

    fun getMedicineList(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $MEDICINE_LIST_TABLE_NAME", null)
    }

    fun getUsers(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $USERS_TABLE_NAME", null)
    }

    fun doesUserExist(userName: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $USERS_TABLE_NAME WHERE $COLUMN_USER_NAME = ?", arrayOf(userName))
        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }

    fun isUserLoggedIn(userEmail: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $USERS_TABLE_NAME WHERE $COLUMN_EMAIL = ? AND $COLUMN_IS_LOGGED_IN = 1",
            arrayOf(userEmail)
        )
        val isLoggedIn = cursor.count > 0
        cursor.close()
        return isLoggedIn
    }

    fun logOutUser(email: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_LOGGED_IN, 0)
        }
        val result = db.update(USERS_TABLE_NAME, values, "$COLUMN_EMAIL = ?", arrayOf(email)) > 0
        db.close()
        return result
    }

    fun getUserByEmailFromLoggedInUser(): String? {
        val db = this.readableDatabase
        var email: String? = null
        val cursor = db.rawQuery("SELECT $COLUMN_EMAIL FROM $USERS_TABLE_NAME WHERE $COLUMN_IS_LOGGED_IN = 1", null)
        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
        }
        cursor.close()
        db.close()
        return email
    }

    fun getUserByEmail(email: String): UserModal? {
        val db = this.readableDatabase
        val cursor = db.query(
            USERS_TABLE_NAME,
            null,
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null,
            null,
            null
        )
        var user: UserModal? = null
        cursor.use {
            if (it.moveToFirst()) {
                user = UserModal(
                    it.getString(it.getColumnIndexOrThrow(COLUMN_USER_NAME)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_PHONE)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_IMAGE_URL))
                )
            }
        }
        return user
    }

    fun updateUserDetails(email: String, newName: String, newPhone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, newName)
            put(COLUMN_PHONE, newPhone)
        }
        val result = db.update(USERS_TABLE_NAME, values, "$COLUMN_EMAIL = ?", arrayOf(email))
        db.close()
        return result != -1
    }

    fun getUserById(userId: Int): UserModal? {
        val db = this.readableDatabase
        var user: UserModal? = null
        val cursor = db.query(
            USERS_TABLE_NAME,
            arrayOf(
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD,
                COLUMN_PHONE,
                COLUMN_IMAGE_URL
            ),
            "$COLUMN_USER_ID = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null,
            null
        )
        cursor.use {
            if (it.moveToFirst()) {
                user = UserModal(
                    it.getString(it.getColumnIndexOrThrow(COLUMN_USER_NAME)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_PHONE)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_IMAGE_URL))
                )
            }
        }
        return user
    }

    fun updateUser(
        userId: Int,
        userName: String,
        email: String,
        password: String,
        phone: String,
        imageUrl: String
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, userName)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_PHONE, phone)
            put(COLUMN_IMAGE_URL, imageUrl)
        }
        val result = db.update(USERS_TABLE_NAME, values, "$COLUMN_USER_ID = ?", arrayOf(userId.toString()))
        db.close()
        return result > 0
    }

    companion object {
        private const val DATABASE_NAME = "ReminderPillDB"
        private const val DATABASE_VERSION = 3

        // Medicine List Table
        private const val MEDICINE_LIST_TABLE_NAME = "MedicineListTable"
        const val COLUMN_MEDICINE_ID = "id"
        const val COLUMN_MEDICINE_NAME = "medicine_name"
        const val COLUMN_MEDICINE_TYPE_TITLE = "medicine_type_title"
        const val COLUMN_MEDICINE_TYPE_IMAGE = "medicine_type_image"
        const val COLUMN_MEDICINE_TIME = "medicine_time"
        const val COLUMN_MEDICINE_INTAKE = "medicine_intake"
        const val COLUMN_MEDICINE_REMINDERTIME = "medicine_remindertime"
        const val COLUMN_MEDICINE_REMINDERDATE = "medicine_reminderdate"
        const val COLUMN_MEDICINE_SCHEDULE = "medicine_schedule"
        const val COLUMN_MEDICINE_DURATION = "medicine_duration"
        const val COLUMN_MEDICINE_FREQUENCY = "medicine_frequency"
        const val COLUMN_MEDICINE_REFILL_TIME = "medicine_refill_time"
        const val COLUMN_MEDICINE_INSTRUCTIONS = "medicine_instructions"
        const val COLUMN_MEDICINE_STATUS = "medicine_status"
        const val COLUMN_MEDICINE_TIMESTAMP = "medicine_timestamp"
        const val COLUMN_MEDICINE_DESCRIPTION = "medicine_description"

        // Users Table
        private const val USERS_TABLE_NAME = "UsersTable"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_IMAGE_URL = "url"
        const val COLUMN_IS_LOGGED_IN = "is_logged_in"

        // Schedule Table
        private const val TABLE_SCHEDULE = "ScheduleTable"
        private const val KEY_ID = "id"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DURATION = "duration"
        private const val KEY_FREQUENCY = "frequency"
        private const val KEY_INTAKE = "intake"
        private const val KEY_REFILL = "refill"
    }
}
