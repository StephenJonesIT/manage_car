package vn.edu.stu.thanhsang.managecar.activity;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.edu.stu.thanhsang.managecar.R;
import vn.edu.stu.thanhsang.managecar.database.BranchTable;
import vn.edu.stu.thanhsang.managecar.databinding.ActivityEditProductBinding;
import vn.edu.stu.thanhsang.managecar.model.Product;
import vn.edu.stu.thanhsang.managecar.utils.ImageHelper;

public class EditProductActivity extends AppCompatActivity {
    private static final int CODE_REQUEST_PERMISSION = 300;
    public static final int CODE_ADD = 100;
    public static final int CODE_EDIT = 200;
    ActivityEditProductBinding binding;
    Product product = null;
    List<String> listNameBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
        getDataFromIntent();
    }


    private void addViews() {
        binding.toolbarEditProduct.setTitle("Product Detail");
        setSupportActionBar(binding.toolbarEditProduct);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        listNameBranch = new ArrayList<>();
        getListNameFromDatabase();

        setSpinner(listNameBranch);
    }

    private void getListNameFromDatabase() {
        SQLiteDatabase db = MainActivity.manageCarDB.getReadableDatabase();
        Cursor cursorNameBranch = BranchTable.getNamesBranch(db);
        while (cursorNameBranch.moveToNext()){
            listNameBranch.add(cursorNameBranch.getString(0));
        }
    }

    private void setSpinner(List<String> listNameBranch) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listNameBranch
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        binding.spnBranch.setAdapter(adapter);
    }

    private void addEvents() {

        binding.tvAddPhoto.setOnClickListener(v -> ImageHelper.checkPermissionAndOpenSelector(this,launcher));

        binding.imgProduct.setOnClickListener(v -> ImageHelper.checkPermissionAndOpenSelector(this,launcher));

        binding.btnSave.setOnClickListener(v -> processSaveProduct());
    }

    private void getDataFromIntent() {
        Intent intentProduct = getIntent();
        if (intentProduct.hasExtra("PRODUCT")){
            product = intentProduct.getParcelableExtra("PRODUCT");
            Log.d("DATA FROM PRODUCT_ACTIVITY", Objects.requireNonNull(product).toString());
            if (product!=null){
                binding.tieId.setText(product.getIdProduct());
                binding.tieName.setText(product.getNameProduct());
                binding.tieManufacture.setText(product.getYearProduct());
                binding.tiePrice.setText(product.getPriceProduct());
                Bitmap image = convertImageByteArray(product.getImageProduct());
                binding.imgProduct.setImageBitmap(image);
                setPositionSpinner(product.getBranchProduct());
                setEnableView();
            }
        }
    }

    private Bitmap convertImageByteArray(byte[] image) {
        return BitmapFactory.decodeByteArray(
                image,
                0,
                image.length
        );
    }

    private void setPositionSpinner(String branchProduct) {
        int position = listNameBranch.indexOf(branchProduct);
        binding.spnBranch.setSelection(position);
    }

    private void processSaveProduct() {
        if (product!=null){
            processEditProduct();
        }else {
            processAddProduct();
        }
    }

    private void processAddProduct() {
        String id = String.valueOf(binding.tieId.getText());
        String name = String.valueOf(binding.tieName.getText());
        String price = String.valueOf(binding.tiePrice.getText());
        String year = String.valueOf(binding.tieManufacture.getText());
        String branch = String.valueOf(binding.spnBranch.getSelectedItem());
        byte[] image = getImageFromView();

        if (id.isEmpty() || name.isEmpty() || year.isEmpty()){
            return;
        }

        Intent intent = new Intent();
        product = new Product(id,name,year,price,image,branch);
        intent.putExtra("PRODUCT",product);
        setResult(CODE_ADD,intent);
        finish();
    }

    private void processEditProduct() {
        String id = Objects.requireNonNull(binding.tieId.getText()).toString();
        String name = Objects.requireNonNull(binding.tieName.getText()).toString();
        String price = Objects.requireNonNull(binding.tiePrice.getText()).toString();
        String year = Objects.requireNonNull(binding.tieManufacture.getText()).toString();
        String branch = binding.spnBranch.getSelectedItem().toString();
        byte[] image = getImageFromView();

        if (id.isEmpty() || name.isEmpty() || year.isEmpty()){
            return;
        }

        Intent intent = new Intent();
        product.setIdProduct(id);
        product.setNameProduct(name);
        product.setYearProduct(year);
        product.setPriceProduct(price);
        product.setBranchProduct(branch);
        product.setImageProduct(image);
        intent.putExtra("PRODUCT",product);
        setResult(CODE_EDIT,intent);
        finish();
    }

    private byte[] getImageFromView() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.imgProduct.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream image = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,image);
        return image.toByteArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ImageHelper.onRequestPermissionsResult(
                EditProductActivity.this,
                requestCode,
                permissions,
                grantResults,
                launcher
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int mnu_edit = R.id.mnu_edit;
        int mnu_about = R.id.mnu_about;
        int mnu_exit_app = R.id.mnu_exit_app;

        if (id == mnu_edit){
            onclickShowEnableView();
        }

        if (id == mnu_about){
            startActivity(new Intent(
                    EditProductActivity.this,
                    InfoActivity.class
            ));
        }

        if (id == mnu_exit_app){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK){
                        Intent data = result.getData();
                        if (data!=null){
                            Uri uri = data.getData();
                            if(uri!=null)
                                binding.imgProduct.setImageURI(uri);
                            else
                                binding.imgProduct.setImageBitmap((Bitmap) data.getExtras().get("data"));
                        }
                    }
                }
            }
    );
    private void showToasts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void onclickShowEnableView(){
        binding.btnSave.setVisibility(View.VISIBLE);
        binding.tvAddPhoto.setEnabled(true);
        binding.imgProduct.setEnabled(true);
        binding.tieName.setEnabled(true);
        binding.tieManufacture.setEnabled(true);
        binding.tiePrice.setEnabled(true);
    }

    private void setEnableView(){
        binding.btnSave.setVisibility(View.GONE);
        binding.tvAddPhoto.setEnabled(false);
        binding.imgProduct.setEnabled(false);
        binding.tieId.setEnabled(false);
        binding.tieName.setEnabled(false);
        binding.tiePrice.setEnabled(false);
        binding.tieManufacture.setEnabled(false);
    }
}