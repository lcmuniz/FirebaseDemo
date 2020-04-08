package com.eficaztech.firebasedemo.controller;

import com.eficaztech.firebasedemo.model.Produto;

import java.util.List;

public interface IProdutoListener {
    default void onSuccess(Produto produto) {};
    default void onSuccess(List<Produto> produtos) {};
}
