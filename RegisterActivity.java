package com.sherlyane.tugas;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText Nim, Nama, Password;
    Button BtnRegister;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        Nim = (EditText)findViewById(R.id.nim);
        Nama = (EditText)findViewById(R.id.nama);
        Password = (EditText)findViewById(R.id.pass);
        BtnRegister = (Button)findViewById(R.id.buttonregis);

        TextView toLogin = (TextView)findViewById(R.id.login);

        toLogin.setText(fromHtml("Sudah Punya Akun? " + "</font><font color='#3b5998'>Login Disini</font>"));

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = Nim.getText().toString().trim();
                String nama = Nama.getText().toString().trim();
                String pass = Password.getText().toString().trim();

                ContentValues values = new ContentValues();



                if (pass.equals("") || nama.equals("") || nim.equals(" ")){
                    Toast.makeText(RegisterActivity.this, "Tidak Boleh Ada yang Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    values.put(DBHelper.row_nim, nim);
                    values.put(DBHelper.row_nama, nama);
                    values.put(DBHelper.row_pass, pass);
                    dbHelper.insertData(values);

                    Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                    finish();
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
