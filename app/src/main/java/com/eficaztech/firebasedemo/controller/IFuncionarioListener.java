package com.eficaztech.firebasedemo.controller;

import com.eficaztech.firebasedemo.model.Funcionario;

import java.util.List;

public interface IFuncionarioListener {
    default void onSuccess(Funcionario funcionario) {};
    default void onSuccess(List<Funcionario> funcionarios) {};
}
