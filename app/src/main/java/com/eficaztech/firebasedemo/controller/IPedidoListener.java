package com.eficaztech.firebasedemo.controller;

import com.eficaztech.firebasedemo.model.Pedido;

import java.util.List;

public interface IPedidoListener {
    default void onSuccess(Pedido pedido) {};
    default void onSuccess(List<Pedido> pedidos) {};
}
