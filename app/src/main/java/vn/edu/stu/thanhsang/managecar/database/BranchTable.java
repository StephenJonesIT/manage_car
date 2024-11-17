package vn.edu.stu.thanhsang.managecar.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class BranchTable {
    public static final String BRANCH_TABLE = "BRANCH";
    private static final String BRANCH_ID = "ID";
    private static final String BRANCH_NAME = "NAME";
    private static final String BRANCH_BASE = "BASE";
    private static final String BRANCH_LOGO = "LOGO";

    public static void createTable(SQLiteDatabase db){
       String sqlCreateBranch = "CREATE TABLE IF NOT EXISTS " + BRANCH_TABLE + " ("
               + BRANCH_ID + " VARCHAR(30) PRIMARY KEY, "
               + BRANCH_NAME + " NVARCHAR(255), "
               + BRANCH_BASE + " NVARCHAR(255), "
               + BRANCH_LOGO + " BLOB"
               + ")";
       db.execSQL(sqlCreateBranch);
   }

    public static void InsertBranch(
            SQLiteDatabase db,
            String id,
            String name,
            String base,
            byte[] logo
    ){
        String sqlInsertBranch = "INSERT INTO " + BRANCH_TABLE + " VALUES (?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sqlInsertBranch);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,name);
        statement.bindString(3,base);
        statement.bindBlob(4,logo);
        statement.executeInsert();
    }

    public static void deleteBranch(SQLiteDatabase db, String id){
        db.execSQL("DELETE FROM "+BRANCH_TABLE+" WHERE "+BRANCH_ID+"=?",new String[]{id});
    }

    public static void UpdateBranch(
            SQLiteDatabase db,
            String id,
            String name,
            String base,
            byte[] logo
    ){
        String sqlUpdateBranch = "UPDATE "+BRANCH_TABLE+" SET "+BRANCH_NAME+"=?, "+BRANCH_BASE+"=?, "+BRANCH_LOGO+"=? WHERE "+BRANCH_ID+"=?";
        SQLiteStatement statement = db.compileStatement(sqlUpdateBranch);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,base);
        statement.bindBlob(3,logo);
        statement.bindString(4,id);
        statement.execute();
    }

    public static Cursor getAllBranch(SQLiteDatabase db){
        return db.rawQuery("SELECT * FROM "+BRANCH_TABLE,null);
    }

    public static Cursor getNamesBranch(SQLiteDatabase db){
        return db.rawQuery("SELECT "+BRANCH_NAME+" FROM "+BRANCH_TABLE,null);
    }
}
