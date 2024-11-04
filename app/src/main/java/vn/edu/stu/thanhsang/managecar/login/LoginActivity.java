package vn.edu.stu.thanhsang.managecar.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.stu.thanhsang.managecar.MainActivity;
import vn.edu.stu.thanhsang.managecar.R;
import vn.edu.stu.thanhsang.managecar.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getDataFromLogin();
        addEvents();
    }

    private void getDataFromLogin() {
        sharedPref = getSharedPreferences("data_login",Context.MODE_PRIVATE);
        String username = sharedPref.getString("username","");
        String password = sharedPref.getString("password","");
        boolean isChecked = sharedPref.getBoolean("isChecked",false);
        if (isChecked){
            binding.tieUsername.setText(username);
            binding.tiePassword.setText(password);
            binding.cbRememberPassword.setChecked(isChecked);
        }
    }

    private void addEvents() {
        binding.btnSignIn.setOnClickListener(v -> {
           processLogin();
        });
    }

    private void processLogin() {
        String username = binding.tieUsername.getText().toString();
        String password = binding.tiePassword.getText().toString();

        if (password.isEmpty()|| username.isEmpty()) {
            return;
        }

        if (password.equals("admin") && username.equals("admin")) {
            if (binding.cbRememberPassword.isChecked()){
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username",username);
                editor.putString("password",password);
                editor.putBoolean("isChecked",true);
                editor.commit();
            }else{
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("username");
                editor.remove("password");
                editor.putBoolean("isChecked",false);
                editor.commit();
            }
            startActivity(new Intent(this, MainActivity.class));
            showToast(getString(R.string.login_success));
        }else{
            showToast(getString(R.string.login_failed));
        }

    }

    private void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}