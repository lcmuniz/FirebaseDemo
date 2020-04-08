package com.eficaztech.firebasedemo.controller;

import androidx.annotation.NonNull;

import com.eficaztech.firebasedemo.model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProdutoController {

    // referencia ao firebase
    private DatabaseReference db;

    public ProdutoController() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    // cadastrar produto. usa o codigo do produto como chave
    public void cadastrar(Produto produto) {
        db.child("produtos").child(produto.getCodigo()).setValue(produto);
    }

    // procura produto pelo codigo
    public void findByCodigo(String codigo, IProdutoListener listener) {
        db.child("produtos");
        db.child(codigo);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot.getValue(Produto.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // obtem todos os produtos
    public void findAll(IProdutoListener listener) {
        db.child("produtos")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Produto> produtos = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                            produtos.add(dataSnapshot1.getValue(Produto.class));
                        });
                        listener.onSuccess(produtos);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void excluir(Produto produto) {
        db.child("produtos").child(produto.getCodigo()).removeValue();
    }
}