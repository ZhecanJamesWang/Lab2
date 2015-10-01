package com.mycompany.imageview;

/**
 * Created by root on 9/28/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "Image_Library";

    // Contacts table name
    private static final String TABLE_CONTACTS = "Images";
    // Image Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_keyword = "keyword";
    private static final String KEY_link = "link";

    private static final String[] COLUMNS = {KEY_ID,KEY_keyword,KEY_link};






    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_IMAGE_TABLE = "CREATE TABLE "+TABLE_CONTACTS+" ( " +
                KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +KEY_keyword+
                " TEXT, "+
                KEY_link+" TEXT )";

        // create books table
        db.execSQL(CREATE_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONTACTS);

        // create fresh books table
        this.onCreate(db);
    }




    public void add_image(Image_DB image){
        //for logging
        Log.d("add_image", image.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_keyword, image.getkeyword()); // get title
        values.put(KEY_link, image.getLink()); // get author

        // 3. insert
        db.insert(TABLE_CONTACTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }
    public Image_DB get_image(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_CONTACTS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Image_DB image = new Image_DB();
        image.set_id(Integer.parseInt(cursor.getString(0)));
        image.set_keyword(cursor.getString(1));
        image.set_link(cursor.getString(2));

        //log
        Log.d("getBook(" + id + ")", image.toString());

        // 5. return book
        return image;
    }
    public List<Image_DB> get_all_images() {
        List<Image_DB> images = new LinkedList<Image_DB>();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_CONTACTS, // a. table
                        COLUMNS, // b. column names
                        null, // c. selections
                        null, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();


        Image_DB image = null;
        if (cursor.moveToFirst()) {
            do {
                image = new Image_DB();
                String string= cursor.getString(0)+cursor.getString(1)+cursor.getString(2);
                Log.d("cursor.getstring",string);

                image.set_id(Integer.parseInt(cursor.getString(0)));
                image.set_keyword(cursor.getString(1));
                image.set_link(cursor.getString(2));

                // Add book to books
                images.add(image);
            } while (cursor.moveToNext());
        }

        Log.d("getAllImages()", images.toString());

        // return books
        return images;
    }
    // Deleting single book
    public void deleteImage(Image_DB image) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_CONTACTS,
                KEY_ID + " = ?",
                new String[]{String.valueOf(image.getid())});

        // 3. close
        db.close();

        Log.d("deleteBook", image.toString());

    }

}
