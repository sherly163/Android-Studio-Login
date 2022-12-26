package com.sherlyane.tugas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText Nim, Password;
    Button BtnLogin;
    DBHelper dbHelper;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Nim = (EditText) findViewById(R.id.nim);
        Password = (EditText) findViewById(R.id.pass);
        BtnLogin = (Button) findViewById(R.id.buttonlogin);

        dbHelper = new DBHelper(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);


        TextView toRegister = (TextView) findViewById(R.id.register);

        toRegister.setText(fromHtml("Belum Punya Akun? " + "</font><font color='#3b5998'>Register Disini</font>"));
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NIM = Nim.getText().toString().trim();
                String PASS = Password.getText().toString().trim();


                Boolean res = dbHelper.checkUser(NIM, PASS);
                if (res == true) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Nim", Nim.getText().toString());
                    editor.putString("Pass", Password.getText().toString());
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
