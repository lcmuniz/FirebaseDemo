package com.eficaztech.firebasedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.IProdutoListener;
import com.eficaztech.firebasedemo.controller.ProdutoController;
import com.eficaztech.firebasedemo.model.Produto;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProdutosActivity extends AppCompatActivity {

    private ListView produtosListView;

    private ArrayList<Produto> produtos;
    private ArrayAdapter<Produto> produtosListAdapter;

    private ProdutoController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        EventBus.getDefault().register(this);

        setComponents();
        setController();

        configListView();
        loadProdutos();

    }

    private void setComponents() {
        produtosListView = findViewById(R.id.produtosListView);
    }

    private void setController() {
        controller = new ProdutoController();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loadProdutos() {

        controller.findAll(new IProdutoListener() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                EventBus.getDefault().post(new Success(produtos));
            }
        });
    }

    private void configListView() {
        produtos = new ArrayList<>();
        produtosListAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtos);
        produtosListView.setAdapter(produtosListAdapter);
        produtosListView.setOnItemClickListener((adapterView, view, position, l) -> onClickProdutosListView(position));
    }

    private void onClickProdutosListView(int position) {
        Intent intent = new Intent(this, ProdutoActivity.class);
        intent.putExtra("produto", produtos.get(position));
        startActivity(intent);
    }

    public void onClickAdicionarButton(View view) {
        Produto produto = new Produto();
        produto.setCodigo(UUID.randomUUID().toString());
        produto.setPreco(new Double(0));

        Intent intent = new Intent(this, ProdutoActivity.class);
        intent.putExtra("produto", produto);
        startActivity(intent);
    }

    private class Success {
        private final List<Produto> produtos;

        public Success(List<Produto> produtos) {
            this.produtos = produtos;
        }
    }

    @Subscribe
    public void on(Success success) {
        produtos.clear();
        produtos.addAll(success.produtos);
        produtosListAdapter.notifyDataSetChanged();
    }
}
