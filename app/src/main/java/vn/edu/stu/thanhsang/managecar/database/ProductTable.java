package vn.edu.stu.thanhsang.managecar.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class ProductTable {
    public static final String PRODUCT_TABLE = "PRODUCT";
    private static final String PRODUCT_ID = "ID";
    private static final String PRODUCT_NAME = "NAME";
    private static final String PRODUCT_YEAR = "YEAR";
    private static final String PRODUCT_PRICE = "PRICE";
    private static final String PRODUCT_IMAGE = "IMAGE";
    private static final String PRODUCT_BRANCH = "BRANCH";


    public static void createTable(SQLiteDatabase db){
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

    public static void insertProduct(
            SQLiteDatabase db,
            String id,
            String name,
            String year,
            String price,
            byte[] image,
            String branch
    ){
        String sqlInsertProduct = "INSERT INTO " + PRODUCT_TABLE + " VALUES (?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sqlInsertProduct);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,name);
        statement.bindString(3,year);
        statement.bindString(4,price);
        statement.bindBlob(5,image);
        statement.bindString(6,branch);
        statement.executeInsert();
    }

    public static void deleteProduct(SQLiteDatabase db, String id){
        db.execSQL("DELETE FROM "+PRODUCT_TABLE+" WHERE "+PRODUCT_ID+"=?",new String[]{id});
    }

    public static void updateProduct(
            SQLiteDatabase db,
            String id,
            String name,
            String year,
            String price,
            byte[] image,
            String branch
    ){
        String sqlUpdateProduct = "UPDATE "+ PRODUCT_TABLE+" SET "
                + PRODUCT_NAME+"=?, "
                + PRODUCT_YEAR+"=?, "
                + PRODUCT_PRICE+"=?, "
                + PRODUCT_IMAGE+"=?, "
                + PRODUCT_BRANCH+"=? WHERE "
                + PRODUCT_ID+"=?";

        SQLiteStatement statement = db.compileStatement(sqlUpdateProduct);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,year);
        statement.bindString(3,price);
        statement.bindBlob(4,image);
        statement.bindString(5,branch);
        statement.bindString(6,id);
        statement.execute();
    }

    public static Cursor getAllProduct(SQLiteDatabase db){
        return db.rawQuery("SELECT * FROM "+PRODUCT_TABLE ,null);
    }
}
