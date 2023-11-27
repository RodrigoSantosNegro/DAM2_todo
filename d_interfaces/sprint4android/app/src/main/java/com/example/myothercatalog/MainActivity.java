package com.example.myothercatalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Item> itemList;
    private Activity activity;

    // Constructor que recibe la lista de datos
    public MainActivity(List<Item> itemList, Activity activity) {
        this.itemList = itemList;
        this.activity = activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        // Llamada a la función para obtener y mostrar los datos JSON
        petition();
    }

    private void petition() {
        String url = "https://raw.githubusercontent.com/RodrigoSantosNegro/DWES/main/resources/catalog.json";

        // Creamos una nueva solicitud JSON usando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    // Respuesta JSON
                    parseJson(response);
                },
                error -> {
                    // Respuesta de error de la solicitud
                });

        // Agregamos la solicitud a la cola de Volley
        Volley.newRequestQueue(this).add(request);
    }

    private void parseJson(JSONObject response) {
    }
}