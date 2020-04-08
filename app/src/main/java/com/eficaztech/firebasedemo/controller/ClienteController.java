package com.eficaztech.firebasedemo.controller;

import androidx.annotation.NonNull;

import com.eficaztech.firebasedemo.model.Cliente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ClienteController {

    // referencia ao Firebase
    private DatabaseReference db;

    public ClienteController() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    // cadastrar cliente (usa o cpf como chave)
    public void cadastrar(Cliente cliente) {
        db.child("clientes").child(cliente.getCpf()).setValue(cliente);
    }

    // encontrar cliente pelo cpf
    public void findByCpf(String cpf, IClienteListener listener) {
        db.child("clientes")
                .child(cpf)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listener.onSuccess(dataSnapshot.getValue(Cliente.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // obter todos os clientes
    public void findAll(IClienteListener listener) {
        db.child("clientes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Cliente> clientes = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                            clientes.add(dataSnapshot1.getValue(Cliente.class));
                        });
                        listener.onSuccess(clientes);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // procurar os clientes pelo nome. basta passar o inicio do nome
    public void findAllByNome(String nome, IClienteListener listener) {
        db.child("clientes")
                .orderByChild("nome")
                .startAt("!")
                .endAt(nome+"\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Cliente> clientes = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                            clientes.add(dataSnapshot1.getValue(Cliente.class));
                        });
                        listener.onSuccess(clientes);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void excluir(Cliente cliente) {
        db.child("clientes").child(cliente.getCpf()).removeValue();
    }
}