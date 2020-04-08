package com.eficaztech.firebasedemo.controller;

import androidx.annotation.NonNull;

import com.eficaztech.firebasedemo.model.Empresa;
import com.eficaztech.firebasedemo.model.Funcionario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FuncionarioController {

    // referencia ao firebase
    private DatabaseReference db;

    public FuncionarioController() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    // cadastrar funcionario. usa o cpf como chave
    public void cadastrar(Funcionario funcionario) {
        db.child("funcionarios").child(funcionario.getCpf()).setValue(funcionario);
    }

    // procura funcionario pelo cpf
    public void findByCpf(String cpf, IFuncionarioListener listener) {
        db.child("funcionarios")
                .child(cpf)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listener.onSuccess(dataSnapshot.getValue(Funcionario.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // obtem todos os funcionarios
    public void findAll(IFuncionarioListener listener) {
        db.child("funcionarios")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Funcionario> funcionarios = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                            funcionarios.add(dataSnapshot1.getValue(Funcionario.class));
                        });
                        listener.onSuccess(funcionarios);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // procura funcionarios  pela empresa
    public void findAllByEmpresa(Empresa empresa, IFuncionarioListener listener) {
        db.child("funcionarios")
                .orderByChild("empresa/cnpj")
                .equalTo(empresa.getCnpj())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Funcionario> funcionarios = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                            funcionarios.add(dataSnapshot1.getValue(Funcionario.class));
                        });
                        listener.onSuccess(funcionarios);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void excluir(Funcionario funcionario) {
        db.child("funcionarios").child(funcionario.getCpf()).removeValue();
    }

}