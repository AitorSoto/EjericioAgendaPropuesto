package com.example.ejericioagendapropuesto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Contacto> contactos;
    RecyclerView recyclerView;
    Adaptador adaptador;
    FloatingActionButton fab;
    SwipeDetector swipeDetector;
    TextView nombreContactoDialogo;
    int posicionContactoEnLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactos = new ArrayList<>();
        cargaDatos();
        nombreContactoDialogo = (TextView)findViewById(R.id.nombreContacto);
        swipeDetector = new SwipeDetector();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adaptador = new Adaptador(this);
        adaptador.setOnTouch(swipeDetector);
        adaptador.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = recyclerView.getChildAdapterPosition(v);
                if (swipeDetector.swipeDetected()){
                    if (swipeDetector.getAction() == SwipeDetector.Action.LR){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("¿Quieres llamar a "+contactos.get(pos).getNombre()+" "
                                +contactos.get(pos).getApellidos()+"?")
                                .setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intento = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ contactos.get(pos).getNumTelefono()));
                                        startActivity(intento);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
                if (swipeDetector.getAction() == SwipeDetector.Action.RL){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("¿Quieres enviar un mail a "+contactos.get(pos).getNombre()+" "
                            +contactos.get(pos).getApellidos()+"?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.setData(Uri.parse("mailto:"));
                                    sendIntent.putExtra(Intent.EXTRA_EMAIL, "aitor.soto@iesdoctorbalmis.com");
                                    sendIntent.putExtra(Intent.EXTRA_CC, new String[]{contactos.get(pos).getCorreo()});
                                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Pruebas de la aplicación");
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Esto es una prueba");
                                    sendIntent.setType("message/rfc822");
                                    Intent shareIntent = Intent.createChooser(sendIntent, "Email");
                                    startActivity(shareIntent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    Intent intent = new Intent(MainActivity.this, EditContact.class);
                    intent.putExtra("Contacto", contactos.get(pos));
                    posicionContactoEnLista = pos;
                    startActivityForResult(intent, 1);
                }

            }
        });
        adaptador.setLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int pos = recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Quieres eliminar a "+contactos.get(pos).getNombre()+" "
                        +contactos.get(pos).getApellidos()+"?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                contactos.remove(pos);
                                dialog.cancel();
                                recyclerView.setAdapter(adaptador);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
        adaptador.setClickImage(new onImageClick(){
            @Override
            public void onImageClickListener(Contacto contacto, View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialogo, null);

                String nombre = contacto.getNombre();
                builder.setView(view);

                builder.setCancelable(true);
                Toast.makeText(MainActivity.this, nombre, Toast.LENGTH_SHORT).show();
                nombreContactoDialogo = (TextView)view.findViewById(R.id.nombreContacto);
                nombreContactoDialogo.setText(nombre);
                builder.show();
            }
        });
        recyclerView.setAdapter(adaptador);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Añadir contacto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            Snackbar snackbar = Snackbar.make(findViewById(R.id.MainActivity), "Edicion del contacto cancelada", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }else{
            contactos.set(posicionContactoEnLista, (Contacto)data.getExtras().getParcelable("SALIDA"));
            recyclerView.setAdapter(adaptador);
        }
    }

    public void cargaDatos(){
        Contacto aitor = new Contacto("Aitor", "Soto Jiménez", 674263291, "aitor.soto@iesdoctorbalmis.com");
        Contacto carlos = new Contacto("Carlos", "Clement Bellido", 684227321, "carlos.clement@iescotorbalmis.com");
        Contacto marcos = new Contacto("Marcos", "Alvira Romero", 684449212, "marcos.alvira@iescotorbalmis.com");
        Contacto ismael = new Contacto("Ismael", "Collado Martinez", 682632128, "ismael.collado@iescotorbalmis.com");
        Contacto sergio = new Contacto("Sergio", "Ramos Santonja", 666291104, "sergio.ramos@iescotorbalmis.com");
        Contacto ivan = new Contacto("Ivan", "Gallego", 666912843, "ivan.gallego@iescotorbalmis.com");
        Contacto mahroz = new Contacto("Mahroz", "Jawad", 687912149, "mahroz.jawad@iesdoctorbalmis.com");
        contactos.add(aitor);
        contactos.add(carlos);
        contactos.add(marcos);
        contactos.add(ismael);
        contactos.add(sergio);
        contactos.add(ivan);
        contactos.add(mahroz);
    }
}
