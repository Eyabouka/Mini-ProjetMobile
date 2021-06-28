package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;


    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;

    private Button appCompatButtonLogin;

    private TextView textViewLinkRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initListeners();
    }

    private void initViews() {

        nestedScrollView = findViewById(R.id.nestedScrollView);


        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);

    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appCompatButtonLogin:
            verif();
            break;
        }
    }

    private void verif() {
        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
        if (textInputEditTextEmail.getText().toString().equals("admin")&&textInputEditTextPassword.getText().toString().equals("admin")){
            intent.putExtra("admin",1);
            startActivity(intent);
        }else{
            intent.putExtra("admin",0);
            startActivity(intent);
        }
    }


}
