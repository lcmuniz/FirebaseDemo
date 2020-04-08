package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String cpf;
    private String nome;
    private String email;

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
        //return "Cliente = { cpf = \"" + cpf + "\", nome = \"" + nome + "\", email = \"" + email + "\" }";
        return nome;
    }

}
