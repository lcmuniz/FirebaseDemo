package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class Pedido {

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

    @NonNull
    @Override
    public String toString() {
        return "Pedido = { codigo = \"" + codigo + "\", cliente = \"" + cliente + "\", empresa = \"" + empresa + "\" }";
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
}
