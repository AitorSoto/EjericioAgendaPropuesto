package com.example.ejericioagendapropuesto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adaptador extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener{
    Context context;
    Holder holder;
    View.OnClickListener listener;
    View.OnLongClickListener longClickListener;
    View.OnTouchListener listenerTouch;

    public Adaptador(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacto, parent, false);
        holder = new Holder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        view.setOnTouchListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hd, int position) {
        ((Holder)hd).bind(((MainActivity)context).contactos.get(position));
    }

    @Override
    public int getItemCount() {
        return ((MainActivity)context).contactos.size();
    }

    public void setClickListener(View.OnClickListener listener){
        if(listener!=null)
            this.listener = listener;
    }
    @Override
    public void onClick(View view){
        if(listener!=null)
            listener.onClick(view);
    }

    public void setLongClickListener(View.OnLongClickListener listener){
        if(listener!=null)
            longClickListener = listener;
    }
    @Override
    public boolean onLongClick(View view){
        if(longClickListener!=null){
            longClickListener.onLongClick(view);
        }
        return true;
    }

    public void setOnTouch(View.OnTouchListener listener){
        this.listenerTouch = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(listenerTouch!=null)
            listenerTouch.onTouch(v, event);

        return false;
    }
}
