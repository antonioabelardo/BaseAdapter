package com.senaigo.baseadapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.senaigo.baseadapter.R;
import com.senaigo.baseadapter.model.Users;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {

    Context context;
    List<Users> colecao;
    LayoutInflater inflter;

    public UsuarioAdapter(final Context applicationContext,
                        final List<Users> colecao) {
        this.context = applicationContext;
        this.colecao = colecao;

    }

    @Override
    public int getCount() {
        return this.colecao != null ? this.colecao.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return this.colecao.get(i);
    }

    private Users parsetItem(int i){
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // inflate the layout for each list row
        //'Infla' o layout(pega a referencia) para ser trabalhada
        //no método
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.activity_itens,viewGroup, false);
        }

        Users u = parsetItem(i);

        TextView nome, userName;


        nome = view.findViewById(R.id.txtName);
        userName = view.findViewById(R.id.txtUserName);

        nome.setText(u.getName());
        userName.setText(u.getUsername());

        return view;
    }
}
