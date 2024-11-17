package vn.edu.stu.thanhsang.managecar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ManageCarDB extends SQLiteOpenHelper {

    public ManageCarDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BranchTable.createTable(db);
        ProductTable.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+BranchTable.BRANCH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ProductTable.PRODUCT_TABLE);
        onCreate(db);
    }

}
