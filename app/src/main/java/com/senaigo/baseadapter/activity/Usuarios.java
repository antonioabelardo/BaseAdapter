package com.senaigo.baseadapter.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.senaigo.baseadapter.R;
import com.senaigo.baseadapter.adapter.UsuarioAdapter;
import com.senaigo.baseadapter.conexao.APIClient;
import com.senaigo.baseadapter.interfaces.InterfaceUsuario;
import com.senaigo.baseadapter.model.Address;
import com.senaigo.baseadapter.model.Company;
import com.senaigo.baseadapter.model.Geo;
import com.senaigo.baseadapter.model.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Usuarios extends AppCompatActivity {
    public TextView nome;
    public TextView userName;
    public TextView street;
    public TextView suite;
    public Users usuario;

    public TextView txtEmail;
    public List<Users> lista = new ArrayList<>();
    public ListView minhaLista;
    public List<Users> listUsers;

    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        Retrofit retrofit = APIClient.getClient();
        InterfaceUsuario interfaceUsuario = retrofit.create(InterfaceUsuario.class);
        Call<List<Users>> get = interfaceUsuario.get();
        get.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                minhaLista = findViewById(R.id.llUsuario);
                listUsers = response.body();
                UsuarioAdapter usuarioAdapter = new UsuarioAdapter(getApplicationContext(), listUsers);
                minhaLista.setAdapter(usuarioAdapter);
            }
            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                minhaLista = findViewById(R.id.llUsuario);
            }
        });
    }



    protected  void onStart(){
        super.onStart();

        Retrofit retrofit = APIClient.getClient();
        InterfaceUsuario interfaceUsuario = retrofit.create(InterfaceUsuario.class);
        Call<List<Users>> get = interfaceUsuario.get();
        get.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                minhaLista = findViewById(R.id.llUsuario);
                listUsers = response.body();
                UsuarioAdapter usuarioAdapter = new UsuarioAdapter(getApplicationContext(), listUsers);
                minhaLista.setAdapter(usuarioAdapter);
                minhaLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        id = listUsers.get(arg2).getId();

                        Retrofit retrofit = APIClient.getClient();
                        InterfaceUsuario interfaceUsuario = retrofit.create(InterfaceUsuario.class);

                        Call<Users> get = interfaceUsuario.getPorId(id);
                        get.enqueue(new Callback<Users>() {
                            @Override
                            public void onResponse(Call<Users> call, Response<Users> response) {
                                usuario = response.body();
                                nome = findViewById(R.id.edtNome);
                                userName = findViewById(R.id.edtUserName);
                                street =  findViewById(R.id.edtStreet);
                                suite = findViewById(R.id.edtSuite);

                                nome.setText(usuario.getName());
                                userName.setText(usuario.getUsername());
                                street.setText(usuario.getAddress().getStreet());
                                suite.setText(usuario.getAddress().getSuite());
                            }

                            @Override
                            public void onFailure(Call<Users> call, Throwable t) {

                            }
                        });

                        return true;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {}
        });
    }

    public void deletar(View view){

        Retrofit retrofit = APIClient.getClient();
        InterfaceUsuario interfaceUsuario = retrofit.create(InterfaceUsuario.class);

        Call<Void> del = interfaceUsuario.delete(usuario.getId());
        del.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getBaseContext(), "Removido",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }

    public void alterar(View view){
        usuario.setName(nome.getText().toString());
        usuario.setUsername(userName.getText().toString());
        usuario.getAddress().setStreet(street.getText().toString());
        usuario.getAddress().setSuite(suite.getText().toString());

        Retrofit retrofit = APIClient.getClient();
        InterfaceUsuario interfaceUsuario = retrofit.create(InterfaceUsuario.class);
        Call<Users> alterar = interfaceUsuario.put(usuario.getId(), usuario);
        alterar.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users u = response.body();
                Toast.makeText(getBaseContext(), "Usuario " + u.getName() + " alterado!" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });




    }

    public void incluir(View view){
        Address a = new Address();
        Company c = new Company();
        Geo g = new Geo();


        a.setGeo(g);
        usuario.setAddress(a);
        usuario.setCompany(c);

        usuario.setName(nome.getText().toString());
        usuario.setName(nome.getText().toString());
        usuario.setUsername(userName.getText().toString());
        usuario.getAddress().setStreet(street.getText().toString());
        usuario.getAddress().setSuite(suite.getText().toString());


        Retrofit retrofit = APIClient.getClient();
        InterfaceUsuario interfaceUsuario = retrofit.create(InterfaceUsuario.class);
        Call<Users> post = interfaceUsuario.post(usuario);
        post.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users u = response.body();
                Toast.makeText(getBaseContext(), "Usuario " + u.getName() + " cadastrado!" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });





    }


}
