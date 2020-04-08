package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Pedido implements Serializable {

    private String codigo;
    private Cliente cliente;
    private Empresa empresa;
    private List<ItemPedido> itensPedido = new ArrayList<>();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }


    @NonNull
    @Override
    public String toString() {
        return "Pedido = { codigo = \"" + codigo + "\", cliente = \"" + cliente + "\", empresa = \"" + empresa + "\" }";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Pedido)) return false;
        Pedido o = (Pedido) obj;
        if (o.getCodigo().equals(this.getCodigo())) return true;
        return false;
    }

}
