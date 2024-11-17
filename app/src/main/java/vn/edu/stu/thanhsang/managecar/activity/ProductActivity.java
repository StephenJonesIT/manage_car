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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.thanhsang.managecar.R;
import vn.edu.stu.thanhsang.managecar.adapter.ProductAdapter;
import vn.edu.stu.thanhsang.managecar.database.ProductTable;
import vn.edu.stu.thanhsang.managecar.databinding.ActivityProductBinding;
import vn.edu.stu.thanhsang.managecar.model.Product;

public class ProductActivity extends AppCompatActivity {
    private ActivityProductBinding binding;
    public static List<Product> listProduct;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
    }

    private void addViews() {
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);

        listProduct = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(this,2);
        binding.rcvProduct.setLayoutManager(manager);
        adapter = new ProductAdapter(
                listProduct,
                position -> {
                    Intent intentEdit = new Intent(
                            ProductActivity.this,
                            EditProductActivity.class
                    );
                    Product data = listProduct.get(position);
                    intentEdit.putExtra("PRODUCT",data);
                    launcher.launch(intentEdit);
                },
                position -> {
                    processDeleteProduct(position);
                    return false;
                });

        binding.rcvProduct.setAdapter(adapter);
        getDataFromDatabase();
    }


    private void processDeleteProduct(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        builder.setTitle("Delete Product");
        builder.setMessage("Are you sure to delete this product?");
        builder.setPositiveButton("Yes", (dialog, which) ->{
            SQLiteDatabase db = MainActivity.manageCarDB.getWritableDatabase();
            ProductTable.deleteProduct(db,listProduct.get(position).getIdProduct());
            getDataFromDatabase();
            showToasts("Delete product success");
        });
        builder.setNegativeButton("No", (dialog, which) ->{

        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void addEvents() {
        binding.btnAddProduct.setOnClickListener(v -> {
            startActivity(new Intent(ProductActivity.this, EditProductActivity.class));
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataFromDatabase() {
        SQLiteDatabase db = MainActivity.manageCarDB.getReadableDatabase();
        Cursor cursorProduct = ProductTable.getAllProduct(db);
        listProduct.clear();

        while (cursorProduct.moveToNext()){
            listProduct.add(new Product(
                    cursorProduct.getString(0),
                    cursorProduct.getString(1),
                    cursorProduct.getString(2),
                    cursorProduct.getString(3),
                    cursorProduct.getBlob(4),
                    cursorProduct.getString(5)
            ));
        }
        cursorProduct.close();
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general,menu);
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
                    ProductActivity.this,
                    MainActivity.class
                   ));
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
                    ProductActivity.this,
                    InfoActivity.class
            ));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToasts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {

                }
            }
    );

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromDatabase();
    }
}