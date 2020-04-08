package com.eficaztech.firebasedemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.ClienteController;
import com.eficaztech.firebasedemo.controller.EmpresaController;
import com.eficaztech.firebasedemo.controller.FuncionarioController;
import com.eficaztech.firebasedemo.controller.PedidoController;
import com.eficaztech.firebasedemo.controller.ProdutoController;
import com.eficaztech.firebasedemo.model.Cliente;
import com.eficaztech.firebasedemo.model.Empresa;
import com.eficaztech.firebasedemo.model.Funcionario;
import com.eficaztech.firebasedemo.controller.IClienteListener;
import com.eficaztech.firebasedemo.controller.IEmpresaListener;
import com.eficaztech.firebasedemo.controller.IFuncionarioListener;
import com.eficaztech.firebasedemo.controller.IPedidoListener;
import com.eficaztech.firebasedemo.controller.IProdutoListener;
import com.eficaztech.firebasedemo.model.ItemPedido;
import com.eficaztech.firebasedemo.model.Pedido;
import com.eficaztech.firebasedemo.model.Produto;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //exemploDeCadastroDeClientes();

       // exemploDeConsultaDeClientes();

        //exemploDeCadastroDeEmpresas();

        //exemploDeConsultaDeEmpresas();

        //exemploDeCadastroDeFuncionarios();

        // exemploDeConsultaDeFuncionarios();

        // exemploDeCadastroDeProdutos();

        // exemploDeConsultaDeProdutos();

        //exemploDeCadastroDePedidos();

        // exemploDeConsultaDePedidos();

    }

    private void exemploDeCadastroDeClientes() {

        ClienteController controller = new ClienteController();

        Cliente joao = new Cliente();
        joao.setCpf("283123123213213");
        joao.setNome("João Silva");
        joao.setEmail("joao@gmail.com");
        controller.cadastrar(joao);

    }

    private void exemploDeConsultaDeClientes() {

        ClienteController controller = new ClienteController();

        // consulta pelo cpf
        controller.findByCpf("283123123213213", new IClienteListener() {
            @Override
            public void onSuccess(Cliente cliente) {
                System.out.println(cliente);
            }
        });

        // mostrar todas os clientes
        controller.findAll(new IClienteListener() {
            @Override
            public void onSuccess(List<Cliente> clientes) {
                System.out.println(clientes);
            }
        });

        // mostrar todas os clientes com nome = "João"
        controller.findAllByNome("João", new IClienteListener() {
            @Override
            public void onSuccess(List<Cliente> clientes) {
                System.out.println(clientes);
            }
        });

    }

    private void exemploDeCadastroDeEmpresas() {
        EmpresaController controller = new EmpresaController();

        Empresa cocaCola = new Empresa();
        cocaCola.setCnpj("1234");
        cocaCola.setNome("Coca Cola");
        cocaCola.setEndereco("Rua ABC");
        controller.cadastrar(cocaCola);

        Empresa jesus = new Empresa();
        jesus.setCnpj("5678");
        jesus.setNome("Jesus");
        jesus.setEndereco("Rua ABC");
        controller.cadastrar(jesus);
    }

    private void exemploDeConsultaDeEmpresas() {

        EmpresaController controller = new EmpresaController();

        // consulta pelo cnpj
        controller.findByCnpj("5678", new IEmpresaListener() {
            @Override
            public void onSuccess(Empresa empresa) {
                System.out.println(empresa);
            }
        });

        // mostrar todas as empresas
        controller.findAll(new IEmpresaListener() {
            @Override
            public void onSuccess(List<Empresa> empresas) {
                System.out.println(empresas);
            }
        });

        // mostrar todas as empresas com nome = "Coca Cola"
        controller.findAllByNome("Coca Cola", new IEmpresaListener() {
            @Override
            public void onSuccess(List<Empresa> empresas) {
                System.out.println(empresas);
            }
        });


    }


    private void exemploDeCadastroDeFuncionarios() {

        EmpresaController empresaController = new EmpresaController();
        empresaController.findAll(new IEmpresaListener() {
            @Override
            public void onSuccess(List<Empresa> empresas) {
                Empresa empresa = empresas.get(0);

                FuncionarioController funcionarioController = new FuncionarioController();

                Funcionario luiz = new Funcionario();
                luiz.setCpf("72822833322");
                luiz.setNome("Junior Carlos");
                luiz.setEmail("junior@gmail.com");
                luiz.setEmpresa(empresa);
                funcionarioController.cadastrar(luiz);


                Funcionario maria = new Funcionario();
                maria.setCpf("8888888888888");
                maria.setNome("Maria Joana");
                maria.setEmail("maria@gmail.com");
                maria.setEmpresa(empresa);
                funcionarioController.cadastrar(maria);

            }
        });

    }

    private void exemploDeConsultaDeFuncionarios() {

        FuncionarioController controller = new FuncionarioController();

        controller.findByCpf("72822833322", new IFuncionarioListener() {
            @Override
            public void onSuccess(Funcionario funcionario) {
                System.out.println(funcionario);
            }
        });

        controller.findAll(new IFuncionarioListener() {
            @Override
            public void onSuccess(List<Funcionario> funcionarios) {
                System.out.println(funcionarios);
            }
        });


        Empresa empresa = new Empresa();
        empresa.setCnpj("1234");
        controller.findAllByEmpresa(empresa, new IFuncionarioListener() {
            @Override
            public void onSuccess(List<Funcionario> funcionarios) {
                System.out.println(funcionarios);
            }
        });


    }

    private void exemploDeCadastroDeProdutos() {

        ProdutoController controller = new ProdutoController();

        Produto ventilador = new Produto();
        ventilador.setCodigo(UUID.randomUUID().toString());
        ventilador.setNome("Ventilador");
        ventilador.setPreco(200.00);
        controller.cadastrar(ventilador);

        Produto ps4 = new Produto();
        ps4.setCodigo(UUID.randomUUID().toString());
        ps4.setNome("PS4");
        ps4.setPreco(1243.50);
        controller.cadastrar(ps4);

    }


    private void exemploDeConsultaDeProdutos() {
        ProdutoController controller = new ProdutoController();

        controller.findByCodigo("1ef9432b-7f0b-4c0f-80d9-2394d91ca63a", new IProdutoListener() {
            @Override
            public void onSuccess(Produto produto) {
                System.out.println(produto);
            }
        });

        controller.findAll(new IProdutoListener() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                System.out.println(produtos);
            }
        });
    }


    private void exemploDeCadastroDePedidos() {

        EmpresaController empresaController = new EmpresaController();
        ClienteController clienteController = new ClienteController();
        ProdutoController produtoController = new ProdutoController();
        PedidoController pedidoController = new PedidoController();

        empresaController.findByCnpj("1234", new IEmpresaListener() {
            @Override
            public void onSuccess(Empresa empresa) {
                clienteController.findByCpf("74774747", new IClienteListener() {
                    @Override
                    public void onSuccess(Cliente cliente) {
                        produtoController.findByCodigo("1ef9432b-7f0b-4c0f-80d9-2394d91ca63a", new IProdutoListener() {
                            @Override
                            public void onSuccess(Produto produto) {
                                Pedido pedido = new Pedido();
                                pedido.setCodigo(UUID.randomUUID().toString());
                                pedido.setCliente(cliente);
                                pedido.setEmpresa(empresa);
                                pedidoController.cadastrar(pedido);

                                ItemPedido itemPedido1 = new ItemPedido();
                                itemPedido1.setCodigo(UUID.randomUUID().toString());
                                itemPedido1.setProduto(produto);
                                itemPedido1.setQuantidade(2);

                                pedido.getItensPedido().add(itemPedido1);

                                ItemPedido itemPedido2 = new ItemPedido();
                                itemPedido2.setCodigo(UUID.randomUUID().toString());
                                itemPedido2.setProduto(produto);
                                itemPedido2.setQuantidade(1);

                                pedido.getItensPedido().add(itemPedido2);

                                pedidoController.cadastrar(pedido);

                            }
                        });
                    }
                });
            }
        });


    }

    private void exemploDeConsultaDePedidos() {
        PedidoController controller = new PedidoController();

        controller.findByCodigo("f428d617-5ff2-4f00-a824-8bf9ec8115b2", new IPedidoListener() {
            @Override
            public void onSuccess(Pedido pedido) {
               System.out.println(pedido);
            }
        });

        controller.findAll(new IPedidoListener() {
            @Override
            public void onSuccess(List<Pedido> pedidos) {
               System.out.println(pedidos);
            }
        });

        Cliente cliente = new Cliente();
        cliente.setCpf("74774747");
        controller.findAllByCliente(cliente, new IPedidoListener() {
            @Override
            public void onSuccess(List<Pedido> pedidos) {
                System.out.println(pedidos);
            }
        });

    }

    public void onClickClientesButton(View view) {
        startActivity(new Intent(this, ClientesActivity.class));
    }

    public void onClickEmpresasButton(View view) {
        startActivity(new Intent(this, EmpresasActivity.class));
    }

    public void onClickProdutosButton(View view) {
        startActivity(new Intent(this, ProdutosActivity.class));
    }
}
