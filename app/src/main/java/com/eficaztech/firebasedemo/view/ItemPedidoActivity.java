package com.eficaztech.firebasedemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.PedidoController;
import com.eficaztech.firebasedemo.controller.ProdutoController;
import com.eficaztech.firebasedemo.controller.IProdutoListener;
import com.eficaztech.firebasedemo.model.Pedido;
import com.eficaztech.firebasedemo.model.Produto;
import com.eficaztech.firebasedemo.model.ItemPedido;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemPedidoActivity extends AppCompatActivity {

    private EditText codigoEditText;
    private EditText quantidadeEditText;
    private Spinner produtosSpinner;

    private ProdutoController produtoController;
    private PedidoController pedidoController;

    private ArrayList<Produto> produtos;
    private ArrayAdapter<Produto> produtosListAdapter;

    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pedido);

        EventBus.getDefault().register(this);

        setComponents();

        setControllers();

        configSpinner();
        loadProdutos();

    }

    private void setComponents() {
        codigoEditText = findViewById(R.id.codigoEditText);
        quantidadeEditText = findViewById(R.id.quantidadeEditText);
        produtosSpinner = findViewById(R.id.produtosSpinner);
    }

    private void setControllers() {
        produtoController = new ProdutoController();
        pedidoController = new PedidoController();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loadProdutos() {
        produtoController.findAll(new IProdutoListener() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                EventBus.getDefault().post(new Success(produtos));
            }
        });
    }

    private void configSpinner() {
        produtos = new ArrayList<>();
        produtosListAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtos);
        produtosSpinner.setAdapter(produtosListAdapter);
    }

    private void loadItemPedido() {

        ItemPedido itemPedido = (ItemPedido) getIntent().getSerializableExtra("itemPedido");
        pedido = (Pedido) getIntent().getSerializableExtra("pedido");

        codigoEditText.setText(itemPedido.getCodigo());
        quantidadeEditText.setText(itemPedido.getQuantidade().toString());
        int position = produtos.indexOf(itemPedido.getProduto());
        produtosSpinner.setSelection(position);

    }

    public void onClickSalvarButton(View view) {

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setCodigo(codigoEditText.getText().toString());
        itemPedido.setQuantidade(Integer.valueOf(quantidadeEditText.getText().toString()));
        itemPedido.setProduto(produtos.get(produtosSpinner.getSelectedItemPosition()));

        pedido.getItensPedido().remove(itemPedido); // remover o anterior (se houver)
        pedido.getItensPedido().add(itemPedido); // salvar o novo
        pedidoController.cadastrar(pedido);

        EventBus.getDefault().post(new PedidoActivity.ItemPedidoChanged());
        finish();

    }

    public void onClickExcluirButton(View view) {

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setCodigo(codigoEditText.getText().toString());

        pedido.getItensPedido().remove(itemPedido);
        pedidoController.cadastrar(pedido);

        EventBus.getDefault().post(new PedidoActivity.ItemPedidoChanged());

        finish();

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
        loadItemPedido();
    }
}
