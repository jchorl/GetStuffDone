package com.joshchorlton.getstuffdone.data;

import android.content.ContentValues;
import android.database.Cursor;

public class Todo {
    public static final String TABLE_NAME = "Todo";
    // Naming the id column with an underscore is good to be consistent
    // with other Android things. This is ALWAYS needed
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_DATE = "date";
    public static final String COL_PRIORITY = "priority";
    public static final String COL_COMPLETE = "complete";
    

    // For database projection so order is consistent
    public static final String[] FIELDS = { COL_ID, COL_CONTENT, COL_DATE, COL_PRIORITY, COL_COMPLETE };

    /*
     * The SQL code that creates a Table for storing Todos in.
     * Note that the last row does NOT end in a comma like the others.
     * This is a common source of error.
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY,"
            + COL_CONTENT + " TEXT NOT NULL DEFAULT '',"
            + COL_DATE + " TEXT NOT NULL DEFAULT '',"
            + COL_PRIORITY + " TEXT NOT NULL DEFAULT '',"
            + COL_COMPLETE + " TEXT NOT NULL DEFAULT ''"
            + ")";

    // Fields corresponding to database columns
    public long id = -1;
    public String content = "";
    public String date = "";
    public String priority = "";
    public String complete = "";

    public Todo() {
    }

    /**
     * Convert information from the database into a Todo object.
     */
    public Todo(final Cursor cursor) {
        // Indices expected to match order in FIELDS!
        this.id = cursor.getLong(0);
        this.content = cursor.getString(1);
        this.date = cursor.getString(2);
        this.priority = cursor.getString(3);
        this.complete = cursor.getString(4);
    }

    /**
     * Return the fields in a ContentValues object, suitable for insertion
     * into the database.
     */
    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that ID is NOT included here
        values.put(COL_CONTENT, content);
        values.put(COL_DATE, date);
        values.put(COL_PRIORITY, priority);
        values.put(COL_COMPLETE, complete);
        return values;
    }
}
