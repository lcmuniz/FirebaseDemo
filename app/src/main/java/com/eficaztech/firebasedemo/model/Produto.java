package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Produto implements Serializable {
    private String codigo;
    private String nome;
    private Double preco;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @NonNull
    @Override
    public String toString() {
        //return "Produto =  { codigo = \"" + codigo + "\", nome = \"" + nome + "\", preco = "+ preco + " }";
        return nome;
    }

}
