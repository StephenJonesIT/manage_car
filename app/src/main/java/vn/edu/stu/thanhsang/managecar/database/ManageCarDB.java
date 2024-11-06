package vn.edu.stu.thanhsang.managecar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class ManageCarDB extends SQLiteOpenHelper {

    private static final String BRANCH_TABLE = "BRANCH";
    private static final String BRANCH_ID = "ID";
    private static final String BRANCH_NAME = "NAME";
    private static final String BRANCH_BASE = "BASE";
    private static final String BRANCH_LOGO = "LOGO";

    private static final String CAR_TABLE = "CAR";


    public ManageCarDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateBranch = "CREATE TABLE IF NOT EXISTS " + BRANCH_TABLE + " ("
                + BRANCH_ID + " VARCHAR(30) PRIMARY KEY, "
                + BRANCH_NAME + " NVARCHAR(255), "
                + BRANCH_BASE + " NVARCHAR(255), "
                + BRANCH_LOGO + " BLOB"
                + ")";
        db.execSQL(sqlCreateBranch);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+BRANCH_TABLE);
        onCreate(db);
    }

    public void InsertBranch(String id, String name, String base, byte[] logo){
        SQLiteDatabase db = getWritableDatabase();
        String sqlInsertBranch = "INSERT INTO " + BRANCH_TABLE + " VALUES (?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sqlInsertBranch);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,name);
        statement.bindString(3,base);
        statement.bindBlob(4,logo);
        statement.executeInsert();
    }

    public Cursor getAllBranch(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+BRANCH_TABLE,null);
    }
}
