package com.icegotcha.searchlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by icegotcha on 29/8/2560.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + BookTable.TABLE_NAME + "(" +
                BookTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BookTable.BOOK_NAME + " TEXT NOT NULL," +
                BookTable.AUTHOR_NAME + " TEXT NOT NULL," +
                BookTable.TRANSLATOR_NAME + " TEXT," +
                BookTable.PUBLISHER_NAME + " TEXT," +
                BookTable.BOOK_REL_DATE + " DEFAULT CURRENT_DATE," +
                BookTable.BOOK_PRICE + " FLOAT DEFAULT 0" +
                ");");

        insertContents(db);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(BookTable.TABLE_NAME, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + BookTable.TABLE_NAME);
        // Recreates the database with a new version
        onCreate(db);
    }

    public void insertContents(SQLiteDatabase db) {
        String sql = "INSERT INTO `SaleBook` (_id,book_name,book_author_name,book_translator_name,book_publisher_name,book_release_date,book_price) VALUES     " +
                " (1,'A','author1 ','translator1','publisher1','2016-03-30',336.0)," +
                " (2,'B','author2 ','translator2','publisher2','2014-11-14',238.0)," +
                " (3,'C','author3 ','translator3','publisher3','2016-02-09',89.0)," +
                " (4,'D','author4 ','translator4','publisher4','2011-12-13',335.75)," +
                " (5,'E','author5 ','translator5','publisher5','2013-04-01',420.75);";
        db.execSQL(sql);
    }

    // เพิ่มเรคอร์ด
    public long addReccord(String name, String author, String translator,
                           String publisher, float price, String date) {
        ContentValues values = new ContentValues();
        values.put(BookTable.BOOK_NAME, name);
        values.put(BookTable.AUTHOR_NAME, author);
        values.put(BookTable.TRANSLATOR_NAME, translator);
        values.put(BookTable.PUBLISHER_NAME, publisher);
        values.put(BookTable.BOOK_PRICE, price);
        values.put(BookTable.BOOK_REL_DATE, date);


        SQLiteDatabase db = getWritableDatabase();
        long row = db.insert(BookTable.TABLE_NAME, null, values);
        //debug
        Log.d(BookTable.TABLE_NAME, "inserted at row " + row);

        db.close();
        return row;
    }

    // เลือกเรคอร์ด
    public String[] getRecord(String id) {
        String[] data = null;

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(BookTable.TABLE_NAME, BookTable.COLUMNS, "_id=?", new String[]{id},
                null, null, null, null);
        //debug
        Log.d(BookTable.TABLE_NAME, "get id:" + id + " count:" + c.getCount());

        if (c != null) {
            if (c.moveToFirst()) {
                // เลขที่คอลัมน์
                int idCol = c.getColumnIndex(BookTable._ID);
                int nameCol = c.getColumnIndex(BookTable.BOOK_NAME);
                int authorCol = c.getColumnIndex(BookTable.AUTHOR_NAME);
                int translatorCol = c.getColumnIndex(BookTable.TRANSLATOR_NAME);
                int publisherCol = c.getColumnIndex(BookTable.PUBLISHER_NAME);
                int priceCol = c.getColumnIndex(BookTable.BOOK_PRICE);
                int dateCol = c.getColumnIndex(BookTable.BOOK_REL_DATE);

                // เอาข้อมูล
                String book_id = String.valueOf(c.getInt(idCol));
                String name = c.getString(nameCol);
                String author = c.getString(authorCol);
                String translator = c.getString(translatorCol);
                String publisher = c.getString(publisherCol);
                String price = String.valueOf(c.getFloat(priceCol));
                String date = c.getString(dateCol);

                data = new String[]{book_id, name, author, translator, publisher, price, date};
            }
            c.close();
        }
        return data;
    }

    // นับจำนวนเรคอร์ด
    public int getRecordCount() {
        String countQuery = "SELECT _id FROM " + BookTable.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(countQuery, null);
        return c.getCount();
    }


    // ดึงข้อมูลทั้งหมด
    public Cursor getAllRecord() {
        return getReadableDatabase().query(BookTable.TABLE_NAME, BookTable.COLUMNS,
                null, null, null, null, null);
    }

    // ดึงเฉพาะรายการที่มีคำค้นอยู่
    public Cursor getSearchedResult(String search) {
        return getReadableDatabase().query(BookTable.TABLE_NAME, BookTable.COLUMNS,
                BookTable.BOOK_NAME + " LIKE ?", new String[]{"%" + search + "%"}, null, null, null);
    }


}