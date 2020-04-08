package com.eficaztech.firebasedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickClientesButton(View view) {
        startActivity(new Intent(this, ClientesActivity.class));
    }

    public void onClickEmpresasButton(View view) {
        startActivity(new Intent(this, EmpresasActivity.class));
    }

    public void onClickProdutosButton(View view) {
        startActivity(new Intent(this, ProdutosActivity.class));
    }
}
