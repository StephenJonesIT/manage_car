package vn.edu.stu.thanhsang.managecar.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.thanhsang.managecar.R;
import vn.edu.stu.thanhsang.managecar.adapter.BranchAdapter;
import vn.edu.stu.thanhsang.managecar.database.BranchTable;
import vn.edu.stu.thanhsang.managecar.database.ManageCarDB;
import vn.edu.stu.thanhsang.managecar.databinding.ActivityMainBinding;
import vn.edu.stu.thanhsang.managecar.model.Branch;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static ManageCarDB manageCarDB;
    List<Branch> branchList;
    BranchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
    }

    @SuppressLint("ResourceAsColor")
    private void addViews() {
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);

        manageCarDB = new ManageCarDB(this, "ManageCar", null, 2);
        branchList = new ArrayList<>();

        adapter = new BranchAdapter(
                branchList,

                position -> {
                    Intent intentEdit = new Intent(
                            MainActivity.this,
                            EditBranchActivity.class
                    );
                    intentEdit.putExtra("BRANCH",branchList.get(position));
                    launcher.launch(intentEdit);
                },
                new BranchAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(int position) {
                        processDeleteBranch(position);
                        return false;
                    }
                });

            binding.rcvBranch.setLayoutManager(new LinearLayoutManager(this));
            binding.rcvBranch.setAdapter(adapter);
            getDataFromDatabase();
    }



    private void addEvents() {
        binding.btnAddBranch.setOnClickListener(v -> {
            clickAddBranch();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataFromDatabase() {
        SQLiteDatabase db = manageCarDB.getReadableDatabase();
        Cursor cursorBranch = BranchTable.getAllBranch(db);
        branchList.clear();

        while (cursorBranch.moveToNext()){
            branchList.add(new Branch(
                    cursorBranch.getString(0),
                    cursorBranch.getString(1),
                    cursorBranch.getString(2),
                    cursorBranch.getBlob(3)
            ));
        }
        adapter.notifyDataSetChanged();
    }
    private void processDeleteBranch(int position) {
    }

    private void clickAddBranch() {
      launcher.launch(new Intent(
              MainActivity.this,
              EditBranchActivity.class));
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()==EditBranchActivity.CODE_ADD){
                    Branch branch = (Branch) result.getData().getSerializableExtra("BRANCH");
                    processAddBranch(branch);
                }
                else if (result.getResultCode()==EditBranchActivity.CODE_EDIT) {
                    Branch branch = (Branch) result.getData().getSerializableExtra("BRANCH");
                    processEditBranch(branch);
                }
                getDataFromDatabase();
            }
    );

    private void processAddBranch(Branch branch) {
        String id = branch.getId();
        for (Branch b: branchList) {
            if (b.getId().equals(id)){
                showToasts("Id already exists");
                return;
            }
        }

        String name = branch.getName();
        String base = branch.getBase();
        byte[] image = branch.getImage();
        SQLiteDatabase db = manageCarDB.getWritableDatabase();
        BranchTable.InsertBranch(db,id,name,base,image);
        showToasts("Add branch success");
    }

    private void processEditBranch(Branch branch) {
        String id = branch.getId();
        for (Branch item: branchList) {
            if(item.getId().equals(id)){
                SQLiteDatabase db = manageCarDB.getWritableDatabase();
                BranchTable.UpdateBranch(
                        db,
                        id,
                        branch.getName(),
                        branch.getBase(),
                        branch.getImage()
                );
                showToasts("Edit branch success");
                return;
            }
        }
        showToasts("Edit branch failed");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int mnu_switch = R.id.mnu_switch;
        int mnu_exit = R.id.mnu_exit_app;
        int mnu_info = R.id.mnu_about;

        if (id == mnu_switch) {
            startActivity(new Intent(
                    MainActivity.this,
                    ProductActivity.class));
            return true;
        }

        if (id == mnu_exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            System.exit(0);
            return true;
        }

        if (id == mnu_info) {
            startActivity(new Intent(
                    MainActivity.this,
                    InfoActivity.class
            ));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToasts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}