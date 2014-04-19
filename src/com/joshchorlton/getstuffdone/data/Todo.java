package com.joshchorlton.getstuffdone.data;

import android.content.ContentValues;
import android.database.Cursor;

public class Todo {
    public static final String TABLE_NAME = "Todo";
    // Naming the id column with an underscore is good to be consistent
    // with other Android things. This is ALWAYS needed
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";

    // For database projection so order is consistent
    public static final String[] FIELDS = { COL_ID, COL_CONTENT};

    /*
     * The SQL code that creates a Table for storing Todos in.
     * Note that the last row does NOT end in a comma like the others.
     * This is a common source of error.
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY,"
            + COL_CONTENT + " TEXT NOT NULL DEFAULT ''"
            + ")";

    // Fields corresponding to database columns
    public long id = -1;
    public String content = "";

    public Todo() {
    }

    /**
     * Convert information from the database into a Person object.
     */
    public Todo(final Cursor cursor) {
        // Indices expected to match order in FIELDS!
        this.id = cursor.getLong(0);
        this.content = cursor.getString(1);
    }

    /**
     * Return the fields in a ContentValues object, suitable for insertion
     * into the database.
     */
    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that ID is NOT included here
        values.put(COL_CONTENT, content);
        return values;
    }
}
