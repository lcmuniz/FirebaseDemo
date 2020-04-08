package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Funcionario implements Serializable {

    private String cpf;
    private String nome;
    private String email;
    private Empresa empresa;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @NonNull
    @Override
    public String toString() {
        //return "Funcionario = { cpf = \"" + cpf + "\", nome = \"" + nome + "\", email = \"" + email + "\", empresa = \"" + empresa + "\" }";
        return nome;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Funcionario)) return false;
        Funcionario o = (Funcionario) obj;
        if (o.getCpf().equals(this.getCpf())) return true;
        return false;
    }

}
