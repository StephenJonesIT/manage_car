package vn.edu.stu.thanhsang.managecar.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import java.util.Objects;

import vn.edu.stu.thanhsang.managecar.R;
import vn.edu.stu.thanhsang.managecar.databinding.ActivityEditBranchBinding;
import vn.edu.stu.thanhsang.managecar.model.Branch;
import vn.edu.stu.thanhsang.managecar.utils.ImageHelper;

public class EditBranchActivity extends AppCompatActivity {

    public static final int CODE_ADD = 100;
    public static final int CODE_EDIT = 200;
    public static final int CODE_REQUEST_PERMISSION = 300;
    
    ActivityEditBranchBinding binding;
    Branch branch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditBranchBinding.inflate(getLayoutInflater());
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
        binding.toolbarEditBranch.setTitle("Branch Detail");
        setSupportActionBar(binding.toolbarEditBranch);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void addEvents() {
        binding.imgBranch.setOnClickListener(v -> ImageHelper.checkPermissionAndOpenSelector(this,launcher));
        binding.tvAddPhoto.setOnClickListener(v -> ImageHelper.checkPermissionAndOpenSelector(this,launcher));

        binding.btnSave.setOnClickListener(v -> {
            if (branch!=null){
                processEditBranch();
            }else {
                processAddBranch();
            }
        });
    }

    private void getDataFromIntent() {
        Intent dataIntentBranch = getIntent();
        if (dataIntentBranch.hasExtra("BRANCH")) {

            branch = (Branch) dataIntentBranch.getSerializableExtra("BRANCH");
            Log.d("DATA FROM MAIN_ACTIVITY", Objects.requireNonNull(branch).toString());
            Bitmap image = convertImageByteArray(branch.getImage());
            binding.tieId.setText(branch.getId());
            binding.tieName.setText(branch.getName());
            binding.tieBase.setText(branch.getBase());
            binding.imgBranch.setImageBitmap(image);

            setEnableView();
        }

    }

    private Bitmap convertImageByteArray(byte[] image) {
        return BitmapFactory.decodeByteArray(
                image,
                0,
                image.length
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ImageHelper.onRequestPermissionsResult(
                this,
                requestCode,
                permissions,
                grantResults,
                launcher
        );
    }

    private void processAddBranch() {
        String id = String.valueOf(binding.tieId.getText());
        String name = String.valueOf(binding.tieName.getText());
        String base = String.valueOf(binding.tieBase.getText());

        if (id.isEmpty() || name.isEmpty() || base.isEmpty()){
            return;
        }

        byte[] image = getImageFromView();
        branch = new Branch(id,name,base,image);

        Intent intent = new Intent();
        intent.putExtra("BRANCH",branch);
        setResult(CODE_ADD,intent);
        finish();
    }

    private void processEditBranch() {
        if (branch != null){
            String id = Objects.requireNonNull(binding.tieId.getText()).toString();
            String name = Objects.requireNonNull(binding.tieName.getText()).toString();
            String base = Objects.requireNonNull(binding.tieBase.getText()).toString();
            byte[] image = getImageFromView();

            if (id.isEmpty() || name.isEmpty() || base.isEmpty()){
                return;
            }
            branch.setId(id);
            branch.setName(name);
            branch.setBase(base);
            branch.setImage(image);

            Intent intent = new Intent();
            intent.putExtra("BRANCH",branch);
            setResult(CODE_EDIT,intent);
            finish();
        }
    }

    private byte[] getImageFromView() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.imgBranch.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream image = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,image);
        return image.toByteArray();
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
                    EditBranchActivity.this,
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
                                binding.imgBranch.setImageURI(uri);
                            else
                                binding.imgBranch.setImageBitmap((Bitmap) data.getExtras().get("data"));
                        }
                    }
                }
            }
    );
    private void onclickShowEnableView(){
        binding.btnSave.setVisibility(View.VISIBLE);
        binding.tvAddPhoto.setEnabled(true);
        binding.imgBranch.setEnabled(true);
        binding.tieName.setEnabled(true);
        binding.tieBase.setEnabled(true);
    }

    private void setEnableView(){
        binding.btnSave.setVisibility(View.GONE);
        binding.tvAddPhoto.setEnabled(false);
        binding.imgBranch.setEnabled(false);
        binding.tieId.setEnabled(false);
        binding.tieName.setEnabled(false);
        binding.tieBase.setEnabled(false);
    }
}