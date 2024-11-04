package vn.edu.stu.thanhsang.managecar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.stu.thanhsang.managecar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
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
    }

    @SuppressLint("ResourceAsColor")
    private void addViews() {
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
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
}