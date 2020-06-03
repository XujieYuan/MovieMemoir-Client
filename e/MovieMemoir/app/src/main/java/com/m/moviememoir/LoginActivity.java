package com.m.moviememoir;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.m.moviememoir.Bean.Credential;
import com.m.moviememoir.Utils.ArrayJson;
import com.m.moviememoir.Utils.HttpClient;
import com.m.moviememoir.Utils.SPUtils;
import com.m.moviememoir.Utils.T;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.Request;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (SPUtils.get(this, "IsLogin", "0") != null) {
            String islogin = (String) SPUtils.get(this, "IsLogin", "0");
            if (islogin.equals("1")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                    HttpClient.getInstance().asyncGet(Constant.findByUsernameCredential + username.getText().toString().trim(), new HttpClient.HttpCallBack() {
                        @Override
                        public void onError(Request request, IOException e) {
                            T.showShort("Network error");
                        }

                        @Override
                        public void onSuccess(Request request, String result, Headers headers) {
                            List<Credential> credentialList = ArrayJson.jsonToArrayList(result, Credential.class);
                            if (credentialList.size() == 0) {
                                T.showShort("Username error");
                            } else {
                                if (credentialList.get(0).getPassword().equals(password.getText().toString().trim())) {
                                    SPUtils.put(LoginActivity.this, "IsLogin", "1");
                                    SPUtils.put(LoginActivity.this, "username", username.getText().toString().trim());
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    T.showShort("Password error");
                                }
                            }
                        }
                    });
                } else {
                    T.showShort("Username or Password error");
                }
            }
        });
    }
}
