package com.eficaztech.firebasedemo.controller;

import com.eficaztech.firebasedemo.model.Empresa;

import java.util.List;

public interface IEmpresaListener {
    default void onSuccess(Empresa empresa) {};
    default void onSuccess(List<Empresa> empresas) {};
}
