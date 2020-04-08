package com.eficaztech.firebasedemo.controller;

import com.eficaztech.firebasedemo.model.Cliente;

import java.util.List;

public interface IClienteListener {
    default void onSuccess(Cliente cliente) {};
    default void onSuccess(List<Cliente> clientes) {};
}
