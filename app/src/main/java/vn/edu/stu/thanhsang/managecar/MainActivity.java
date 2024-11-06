package vn.edu.stu.thanhsang.managecar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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

import vn.edu.stu.thanhsang.managecar.adapter.BranchAdapter;
import vn.edu.stu.thanhsang.managecar.database.ManageCarDB;
import vn.edu.stu.thanhsang.managecar.databinding.ActivityMainBinding;
import vn.edu.stu.thanhsang.managecar.model.Branch;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ManageCarDB manageCarDB;
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

        manageCarDB = new ManageCarDB(this, "ManageCar", null, 1);
        branchList = new ArrayList<>();

        adapter = new BranchAdapter(
                branchList,
                new BranchAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                },
                new BranchAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(int position) {
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

    private void getDataFromDatabase() {
        Cursor cursorBranch = manageCarDB.getAllBranch();
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

    private void clickAddBranch() {
      launcher.launch(new Intent(
              MainActivity.this,
              EditBranchActivity.class));
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==EditBranchActivity.CODE_ADD){
                        Branch branch = (Branch) result.getData().getSerializableExtra("BRANCH");
                        processAddBranch(branch);
                    }
                    getDataFromDatabase();
                }
            }
    );

    private void processAddBranch(Branch branch) {
        String id = branch.getId();
        for (Branch b: branchList) {
            if (b.getId()==id){
                showToasts("Id already exists");
                return;
            }
        }

        String name = branch.getName();
        String base = branch.getBase();
        byte[] image = branch.getImage();
        manageCarDB.InsertBranch(id,name,base,image);
        showToasts("Add branch success");
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