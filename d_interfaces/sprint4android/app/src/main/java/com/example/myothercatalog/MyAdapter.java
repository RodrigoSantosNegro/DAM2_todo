package com.example.myothercatalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.ViewHolder {
    private final TextView textView;
    private final ImageView imageView;

    public MyAdapter(@NonNull View itemView) {
        super(itemView);
    }

    // ViewHolder que representa cada elemento de la lista
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView itemDescriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemDescriptionTextView = itemView.findViewById(R.id.itemDescriptionTextView);
        }
    }

    // Método que infla el diseño de cada elemento de la lista y crea su ViewHolder
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflar la vista del elemento de la lista
        View itemView = inflater.inflate(R.layout.activity_item, parent, false);

        // Crear y devolver el ViewHolder
        return new ViewHolder(itemView);
    }

    // Método que asocia los datos de cada elemento a su vista
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtener los datos del elemento en la posición actual
        Item currentItem = itemList.get(position);

        // Asociar los datos a las vistas del ViewHolder
        holder.itemNameTextView.setText(currentItem.getItemName());
        holder.itemDescriptionTextView.setText(currentItem.getItemDescription());
    }

    // Método que devuelve el número total de elementos en la lista
    public int getItemCount() {
        return itemList.size();
    }
}
