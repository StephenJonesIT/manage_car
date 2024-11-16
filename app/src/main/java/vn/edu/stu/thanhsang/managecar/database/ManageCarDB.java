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

    private static final String PRODUCT_TABLE = "PRODUCT";
    private static final String PRODUCT_ID = "ID";
    private static final String PRODUCT_NAME = "NAME";
    private static final String PRODUCT_YEAR = "YEAR";
    private static final String PRODUCT_PRICE = "PRICE";
    private static final String PRODUCT_IMAGE = "IMAGE";
    private static final String PRODUCT_BRANCH = "BRANCH";

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

        String sqlCreateProduct = "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE + " ("
                + PRODUCT_ID + " VARCHAR(30) PRIMARY KEY, "
                + PRODUCT_NAME + " NVARCHAR(255), "
                + PRODUCT_YEAR + " VARCHAR(30), "
                + PRODUCT_PRICE + " DOUBLE, "
                + PRODUCT_IMAGE + " BLOB, "
                + PRODUCT_BRANCH + " NVARCHAR(255)"
                + ")";
        db.execSQL(sqlCreateProduct);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+BRANCH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_TABLE);
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

    public void UpdateBranch(String id, String name, String base, byte[] logo){
        SQLiteDatabase db = getWritableDatabase();
        String sqlUpdateBranch = "UPDATE "+BRANCH_TABLE+" SET "+BRANCH_NAME+"=?, "+BRANCH_BASE+"=?, "+BRANCH_LOGO+"=? WHERE "+BRANCH_ID+"=?";
        SQLiteStatement statement = db.compileStatement(sqlUpdateBranch);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,base);
        statement.bindBlob(3,logo);
        statement.bindString(4,id);
        statement.execute();
    }
    public Cursor getAllBranch(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+BRANCH_TABLE,null);
    }


}
