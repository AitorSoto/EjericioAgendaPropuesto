package com.example.ejericioagendapropuesto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddContact extends AppCompatActivity {

    EditText nombre, apellidos, email, telefono;
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        nombre = (EditText)findViewById(R.id.nombreEditText2);
        apellidos = (EditText)findViewById(R.id.apellidosEditText2);
        email = (EditText)findViewById(R.id.emailEditText2);
        telefono = (EditText)findViewById(R.id.telefonoEditText2);
        fab = (FloatingActionButton)findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacto c = new Contacto();
                c.setNombre(String.valueOf(nombre.getText()));
                c.setApellidos(String.valueOf(apellidos.getText()));
                c.setNumTelefono(Long.valueOf(String.valueOf(telefono.getText())));
                c.setCorreo(String.valueOf(email.getText()));
                Intent intent = new Intent();
                intent.putExtra("SALIDA", c);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
