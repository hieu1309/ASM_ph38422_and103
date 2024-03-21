package com.example.asm_ph38422.choice_login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_ph38422.MainActivity;
import com.example.asm_ph38422.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private EditText edtEmail, edtPass, edtRePass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

         edtEmail = findViewById(R.id.edtEmail_signup);
         edtPass = findViewById(R.id.edtPass_signup);
         edtRePass = findViewById(R.id.edtRePass);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnGoBack = findViewById(R.id.btnGoBack);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigup();
            }
//                String email = edtEmail.getText().toString();
//                String password = edtPass.getText().toString();
//                String rePass = edtRePass.getText().toString();
//
//                if(email.equals("")||password.equals("")|| rePass.isEmpty()){
//                    Toast.makeText(SignUp.this, "vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!password.equals(rePass)){
//                    Toast.makeText(SignUp.this,    "mật khẩu không khớp nhau!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!isValidEmail(email)) {
//                    Toast.makeText(SignUp.this, "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (password.length() < 6 || !Character.isUpperCase(password.charAt(0))) {
//                    Toast.makeText(SignUp.this, "Mật khẩu phải có ít nhất 6 kí tự và viết hoa chữ cái đầu tiên!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "createUserWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    Intent in = new Intent(SignUp.this, MainActivity.class);
//                                    in.putExtra("email",email);
//                                    in.putExtra("password",password);
//                                    startActivity(in);
//                                    Toast.makeText(SignUp.this, "Đăng Kí Thành Công!",
//                                            Toast.LENGTH_SHORT).show();
//
//                                } else {
//                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                    Toast.makeText(SignUp.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });


        });
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SignUp.this, EmailLogin.class);
                startActivity(in);
            }
        });
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void sigup() {
        String email, pass;
        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                }
//                if (!task.isSuccessful()) {
//                    Log.e("Login", "Đăng nhập thất bại", task.getException());
//                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
//                }
                else {
                    Log.e("Login", "Đăng ký thất bại", task.getException());
                    Toast.makeText(SignUp.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}