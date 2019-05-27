package com.capstone.blockchainand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.blockchainand.AppHelper.NetworkHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.capstone.blockchainand.AppHelper.NetworkHelper.mAuth;
import static com.capstone.blockchainand.Keys.RequestServerUrl.*;
import static com.capstone.blockchainand.Keys.DataKey.*;

public class ServerSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser currentUser;

    private LinearLayout logInLayout;
    private EditText etLogInId;
    private EditText etLogInPwd;
    private TextView btnLogIn;
    private TextView btnRegist;
    private ImageView btnLogOut;

    private LinearLayout serverSelectLayout;

    private Button btnLocalServer;
    private Button btnTokyoServer;
    private Button btnOsakaServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_select);
        initView();
        importFireBase();
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            viewLogInLayout();
        } else {
            viewServerSelectLayout();
        }
    }

    private void initView() {
        getSupportActionBar().hide();

        logInLayout = findViewById(R.id.logInLayout);
        etLogInId = findViewById(R.id.etLogInId);
        etLogInPwd = findViewById(R.id.etLogInPwd);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnRegist = findViewById(R.id.btnRegist);
        btnLogOut = findViewById(R.id.btnLogOut);

        serverSelectLayout = findViewById(R.id.serverSelectLayout);

        btnLocalServer = findViewById(R.id.btnLocalServer);
        btnTokyoServer = findViewById(R.id.btnTokyoServer);
        btnOsakaServer = findViewById(R.id.btnOsakaServer);

        btnLogIn.setOnClickListener(this);
        btnRegist.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);

        btnLocalServer.setOnClickListener(this);
        btnTokyoServer.setOnClickListener(this);
        btnOsakaServer.setOnClickListener(this);
    }

    private void importFireBase() {
        if(mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLocalServer:
                handOverServerUrl(LOCAL_SERVER);
                break;
            case R.id.btnTokyoServer:
                handOverServerUrl(TOKYO_SERVER);
                break;
            case R.id.btnOsakaServer:
                handOverServerUrl(OSAKA_SERVER);
                break;
            case R.id.btnLogOut:
                tryLogOut();
                break;
            case R.id.btnLogIn:
                String id = etLogInId.getText().toString().trim();
                String pwd = etLogInPwd.getText().toString().trim();
                tryLogIn(id, pwd);
                break;
            case R.id.btnRegist:
                registNewId();
                break;

        }
    }

    private void handOverServerUrl(String ServerUrl) {
        if(ServerUrl != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(SERVER_URL,ServerUrl);
            startActivity(intent);
        }
    }

    private void tryLogIn(String email, String pwd) {
        if(checkAvailability(email, pwd)) {
            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        viewServerSelectLayout();
                    } else {
                        Toast.makeText(ServerSelectActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void tryLogOut() {
        mAuth.signOut();
        viewLogInLayout();
    }

    private void registNewId() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean checkAvailability(String email, String pwd) {
        if (email ==null || pwd == null || email.length()==0 || pwd.length() == 0 ) {
            Toast.makeText(this, "빈 칸이 존재합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void viewServerSelectLayout() {
        logInLayout.setVisibility(View.GONE);
        serverSelectLayout.setVisibility(View.VISIBLE);
    }

    private void viewLogInLayout() {
        serverSelectLayout.setVisibility(View.GONE);
        logInLayout.setVisibility(View.VISIBLE);
    }


}
