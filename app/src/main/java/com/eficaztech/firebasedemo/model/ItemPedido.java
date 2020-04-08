package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class ItemPedido implements Serializable {
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemPedido = { codigo = \"" + codigo + "\", produto = \"" + produto + "\", quantidade = " + quantidade + " }";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ItemPedido)) return false;
        ItemPedido o = (ItemPedido) obj;
        if (o.getCodigo().equals(this.getCodigo())) return true;
        return false;
    }

}
