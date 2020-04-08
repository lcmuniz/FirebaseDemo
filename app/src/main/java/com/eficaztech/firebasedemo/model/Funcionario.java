package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;

public class Funcionario   {

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

    @NonNull
    @Override
    public String toString() {
        //return "Funcionario = { cpf = \"" + cpf + "\", nome = \"" + nome + "\", email = \"" + email + "\", empresa = \"" + empresa + "\" }";
        return nome;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
