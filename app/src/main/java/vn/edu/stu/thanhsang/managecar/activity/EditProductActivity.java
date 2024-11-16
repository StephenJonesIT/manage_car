package vn.edu.stu.thanhsang.managecar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import vn.edu.stu.thanhsang.managecar.R;
import vn.edu.stu.thanhsang.managecar.databinding.ActivityEditProductBinding;
import vn.edu.stu.thanhsang.managecar.model.Product;

public class EditProductActivity extends AppCompatActivity {

    ActivityEditProductBinding binding;
    Product product = null;
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
    }

    private void addViews() {
        binding.toolbarEditProduct.setTitle("Product Detail");
        setSupportActionBar(binding.toolbarEditProduct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addEvents() {
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        binding.nsvEditProduct.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
//                (nestedScrollView, scrollX, scrollY, scrollXOld, oldScrollY) -> {
//                if (scrollY > oldScrollY) {
//                    binding.btnSave.setVisibility(View.GONE); // button biến mất khi cuộn xuống
//                } else if (scrollY < oldScrollY) {
//                    binding.btnSave.setVisibility(View.VISIBLE); // button hiển thị lại khi cuộn lên
//                }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}