package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;


public class Empresa implements Serializable {

    private String cnpj;
    private String endereco;
    private String nome;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NonNull
    @Override
    public String toString() {
        //return "Empresa = {" + " cnpj = \"" + cnpj + "\", endereco = \"" + endereco +  "\", nome = \"" + nome + "\" }";
        return nome;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Empresa)) return false;
        Empresa o = (Empresa) obj;
        if (o.getCnpj().equals(this.getCnpj())) return true;
        return false;
    }

}
