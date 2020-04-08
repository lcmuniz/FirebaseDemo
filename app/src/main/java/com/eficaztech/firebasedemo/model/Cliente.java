package com.eficaztech.firebasedemo.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Cliente)) return false;
        Cliente o = (Cliente) obj;
        if (o.getCpf().equals(this.getCpf())) return true;
        return false;
    }

}
