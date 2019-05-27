package com.capstone.blockchainand;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.capstone.blockchainand.AppHelper.NetworkHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegistEmail;
    private EditText etRegistPwd;
    private EditText etRegistRePwd;
    private Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        getSupportActionBar().hide();
        etRegistEmail = findViewById(R.id.etRegistEmail);
        etRegistPwd = findViewById(R.id.etRegistPwd);
        etRegistRePwd = findViewById(R.id.etRegistRePwd);
        btnRegist = findViewById(R.id.btnRegist);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =etRegistEmail.getText().toString().trim();
                String pwd = etRegistPwd.getText().toString().trim();
                String rePwd = etRegistRePwd.getText().toString().trim();
                if(checkAvailability(email, pwd, rePwd)) {
                    RegistFirebaseAuth(email, pwd);
                }
            }
        });
    }

    private boolean checkAvailability(String email, String pwd, String rePwd) {
        if (email ==null || pwd == null || rePwd == null || email.length()==0 || pwd.length() == 0 || rePwd.length() ==0) {
            Toast.makeText(this, "빈 칸이 존재합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(!pwd.equals(rePwd)) {
            Toast.makeText(this, "비밀번호와 비밀번호 재 확인이 동일하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void RegistFirebaseAuth(String email, String pwd) {
        NetworkHelper.mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
