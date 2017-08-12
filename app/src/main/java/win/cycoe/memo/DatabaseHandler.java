package win.cycoe.memo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by cycoe on 17-8-12.
 */

public class DatabaseHandler {

    private final String[] HEADERLIST = {"title", "content", "date"};

    public String[][] contentList;
    public String[] contentTemp;
    public int[] idList;
    private String tb;

    private SQLiteDatabase db;

    public DatabaseHandler(SQLiteDatabase database, String tbName) {
        db = database;
        tb = tbName;
    }

    public void addItemInDatabase(String[] content) {
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < HEADERLIST.length; i++)
            contentValues.put(HEADERLIST[i], content[i]);
        db.insert(tb, null, contentValues);
    }

    public void modifyItemInDatabase(int postion, String[] content) {
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < HEADERLIST.length; i++)
            contentValues.put(HEADERLIST[i], content[i]);
        String whereClause = "_id=?";
        String[] whereArgs = new String[] {String.valueOf(idList[postion])};
        db.update(tb, contentValues, whereClause, whereArgs);
    }

    public void deleteItemInDatabase(int position) {
        String whereClause = "_id=?";
        String[] whereArgs = new String[] {String.valueOf(idList[position])};
        db.delete(tb, whereClause, whereArgs);
        contentTemp = contentList[position];
    }

    public void createTable() {
        db.execSQL("create table if not exists " + tb + " (_id integer primary key autoincrement, title text not null, content text not null, date text not null)");
    }

    public void readDatabase() {
        Cursor cr = db.rawQuery("select * from " + tb + " ORDER BY date desc", null);
        idList = new int[cr.getCount()];
        contentList = new String[cr.getCount()][4];
        if(cr != null) {
            for(int i = 0; cr.moveToNext(); i++) {
                idList[i] = cr.getInt(cr.getColumnIndex("_id"));
                for(int j = 0; j < HEADERLIST.length; j++)
                    contentList[i][j] = cr.getString(cr.getColumnIndex(HEADERLIST[j]));
            }
        }

        cr.close();
    }

}