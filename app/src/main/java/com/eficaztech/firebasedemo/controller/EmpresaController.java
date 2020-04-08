package com.eficaztech.firebasedemo.controller;

import androidx.annotation.NonNull;

import com.eficaztech.firebasedemo.model.Empresa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EmpresaController {

    // referencia ao firebase
    private DatabaseReference db;

    public EmpresaController() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    // cadastrar empresa. usa o cnpj como chave
    public void cadastrar(Empresa empresa) {
        db.child("empresas").child(empresa.getCnpj()).setValue(empresa);
    }

    // procurar empresa pelo cnpj
    public void findByCnpj(String cnpj, IEmpresaListener listener) {
        db.child("empresas")
                .child(cnpj)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listener.onSuccess(dataSnapshot.getValue(Empresa.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // obter todas as empresas
    public void findAll(IEmpresaListener listener) {
        db.child("empresas")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Empresa> empresas = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                            empresas.add(dataSnapshot1.getValue(Empresa.class));
                        });
                        listener.onSuccess(empresas);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // rocurar empresas pelo nome. basta passar o inicio do nome
    public void findAllByNome(String nome, IEmpresaListener listener) {
        db.child("empresas")
                .orderByChild("nome")
                .startAt("!")
                .endAt(nome+"\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Empresa> empresas = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                            empresas.add(dataSnapshot1.getValue(Empresa.class));
                        });
                        listener.onSuccess(empresas);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void excluir(Empresa empresa) {
        db.child("empresas").child(empresa.getCnpj()).removeValue();
    }
}