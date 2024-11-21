package vn.edu.stu.thanhsang.managecar.business.branch;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.thanhsang.managecar.activity.MainActivity;
import vn.edu.stu.thanhsang.managecar.database.BranchTable;
import vn.edu.stu.thanhsang.managecar.model.Branch;

public class ListBranchBiz {

    public static Cursor getDataFromDatabase() {
        SQLiteDatabase db = MainActivity.manageCarDB.getReadableDatabase();
        return BranchTable.getAllBranch(db);
    }

    public static List<Branch> convertCursorToList(Cursor cursor) {
        List<Branch> branches = new ArrayList<>();
        while (cursor.moveToNext()) {
            branches.add(new Branch(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3) )
            );
        }
        return branches;
    }

}
