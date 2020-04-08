package com.eficaztech.firebasedemo.controller;

import androidx.annotation.NonNull;

import com.eficaztech.firebasedemo.model.Cliente;
import com.eficaztech.firebasedemo.model.Pedido;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PedidoController {

    // referencia ao firebase
    private DatabaseReference db;

    public PedidoController() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    // cadastrar pedido. usa o codigo do pedido como chave
    public void cadastrar(Pedido pedido) {
        db.child("pedidos").child(pedido.getCodigo()).setValue(pedido);
    }

    // procura pedido pelo codigo
    public void findByCodigo(String codigo, IPedidoListener listener) {
        db.child("pedidos")
                .child(codigo)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listener.onSuccess(dataSnapshot.getValue(Pedido.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // obtem todos os pedidos
    public void findAll(IPedidoListener listener) {
        db.child("pedidos")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Pedido> pedidos = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                            pedidos.add(dataSnapshot1.getValue(Pedido.class));
                        });
                        listener.onSuccess(pedidos);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // procura pedidos pelo cliente
    public void findAllByCliente(Cliente cliente, IPedidoListener listener) {
        db.child("pedidos")
                .orderByChild("cliente/cpf")
                .equalTo(cliente.getCpf())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Pedido> pedidos = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                            pedidos.add(dataSnapshot1.getValue(Pedido.class));
                        });
                        listener.onSuccess(pedidos);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void excluir(Pedido pedido) {
        if (pedido == null) return;
        if (pedido.getCodigo() == null || pedido.getCodigo().equals("")) return;
        db.child("pedidos").child(pedido.getCodigo()).removeValue();
    }
}