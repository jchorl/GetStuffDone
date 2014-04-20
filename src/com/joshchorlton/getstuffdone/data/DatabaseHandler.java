package com.joshchorlton.getstuffdone.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static DatabaseHandler singleton;

    public static DatabaseHandler getInstance(final Context context) {
        if (singleton == null) {
            singleton = new DatabaseHandler(context);
        }
        return singleton;
    }

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "Todosdb";

    private final Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Good idea to use process context here
        this.context = context.getApplicationContext();
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Todo.CREATE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public synchronized Todo getTodo(final long id) {
		final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Todo.TABLE_NAME,
                Todo.FIELDS, Todo.COL_ID + " IS ?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Todo item = null;
        if (cursor.moveToFirst()) {
            item = new Todo(cursor);
        }
        cursor.close();

        return item;
    }

    public synchronized boolean putTodo(final Todo todo) {
    	boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        if (todo.id > -1) {
            result += db.update(Todo.TABLE_NAME, todo.getContent(),
                    Todo.COL_ID + " IS ?",
                    new String[] { String.valueOf(todo.id) });
        }

        if (result > 0) {
            success = true;
        } else {
            // Update failed or wasn't possible, insert instead
            final long id = db.insert(Todo.TABLE_NAME, null,
                    todo.getContent());

            if (id > -1) {
                todo.id = id;
                success = true;
            }
        }
        
        if (success) {
            notifyProviderOnTodoChange();
        }

        return success;
    }

    private void notifyProviderOnTodoChange() {
        context.getContentResolver().notifyChange(
                TodoProvider.URI_TODOS, null, false);
    }
    
    public synchronized int removeTodo(final Todo todo) {
    	final SQLiteDatabase db = this.getWritableDatabase();
        final int result = db.delete(Todo.TABLE_NAME,
                Todo.COL_ID + " IS ?",
                new String[] { Long.toString(todo.id) });

        if (result > 0) {
            notifyProviderOnTodoChange();
        }
        
        return result;
    }

}
