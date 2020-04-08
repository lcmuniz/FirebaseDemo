package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;

public class ItemPedido  {
    private String codigo;
    private Produto produto;
    private Integer quantidade;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemPedido = { codigo = \"" + codigo + "\", produto = \"" + produto + "\", quantidade = " + quantidade + " }";
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
