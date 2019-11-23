package com.example.ejericioagendapropuesto;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {
    TextView txtNombre, txtApellidos, txtCorreo, txtTelefono;

    public Holder(@NonNull View itemView) {
        super(itemView);
        txtNombre = (TextView)itemView.findViewById(R.id.nombreTextView);
        txtApellidos = (TextView)itemView.findViewById(R.id.apellidosTextView);
        txtCorreo = (TextView)itemView.findViewById(R.id.correoTextView);
        txtTelefono = (TextView)itemView.findViewById(R.id.telefonoTextView);
    }

    public void bind(Contacto contacto){
        txtNombre.setText(contacto.getNombre());
        txtApellidos.setText(contacto.getApellidos());
        txtCorreo.setText(contacto.getCorreo());
        txtTelefono.setText(String.valueOf(contacto.getNumTelefono()));
    }
}
