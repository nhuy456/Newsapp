package com.example.mynews;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.mynews.databinding.ActivityLoginBinding;
public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    EditText loginEmail, loginPassword;
    CheckBox chkLuu;
    Button loginButton;
    DatabaseHelper databaseHelper;
    String preferName = "DangNhapData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        chkLuu = findViewById(R.id.checkboxLuu);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();
                if(email.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this, "Please, Enter full!!!", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);
                    if(checkCredentials == true){
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        SavePreferences();
    }
    // ham luu thong tin dang nhap

    private void SavePreferences(){
        SharedPreferences preferences = getSharedPreferences(preferName, MODE_PRIVATE);
        // tao doi tuong
        SharedPreferences.Editor editor = preferences.edit();
        if (chkLuu.isChecked()){
            editor.putString("putUser", loginEmail.getText().toString());
            editor.putString("putPass", loginPassword.getText().toString());
            editor.putBoolean("putSave", chkLuu.isChecked());

        } else
            editor.clear();
        // luu thong tin xuong file
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RestorPreferences();
    }

    private void RestorPreferences(){
        SharedPreferences preferences = getSharedPreferences(preferName, MODE_PRIVATE);
        // tao doi tuong
        boolean luuthongtin = preferences.getBoolean("putSave", false);
        if (luuthongtin){
            String email = preferences.getString("putUser", "");
            String password = preferences.getString("putPass","");
            loginEmail.setText(email);
            loginPassword.setText(password);

        }
        chkLuu.setChecked(luuthongtin);
    }
}