package com.afundacion.fp.sessions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class StatusActivity extends AppCompatActivity {
    private Context context = this;
    private RequestQueue queue;
    private TextView textViewUserStatus;
    private FloatingActionButton buttonPutStatus;
    private EditText editTextUpdateStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        queue = Volley.newRequestQueue(this);
        textViewUserStatus = findViewById(R.id.loadingTextView);
        retrieveUserStatus();

        buttonPutStatus = findViewById(R.id.button_open_dialog);
        buttonPutStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(context);
                myBuilder.setView(inflateDialogBody());
                myBuilder.setPositiveButton("Nuevo estado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Actualizar a: " + editTextUpdateStatus.getText().toString(), Toast.LENGTH_LONG).show();
                        sendUpdateStatusRequest();
                    }
                });
                AlertDialog myDialog = myBuilder.create(); // Esta línea es como 'new AlertDialog'
                myDialog.show();

            }
        });


    }

    private void retrieveUserStatus() {
        // Recuperamos el nombre de usuario de las preferencias
        SharedPreferences preferences = getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        String username = preferences.getString("VALID_USERNAME", null); // null será el valor por defecto
        // Mandaremos la petición GET
        JsonObjectRequestWithCustomAuth request = new JsonObjectRequestWithCustomAuth(
                Request.Method.GET,
                Server.name + "/users/" + username + "/status",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Estado obtenido", Toast.LENGTH_LONG).show();
                        try {
                            textViewUserStatus.setText(response.getString("status"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Estado KO", Toast.LENGTH_LONG).show();
                    }
                },
                context
        );
        this.queue.add(request);

    }

    private View inflateDialogBody() {
        LayoutInflater inflater = getLayoutInflater();
        View inflatedView = inflater.inflate(R.layout.update_status_dialog, null);
        editTextUpdateStatus = inflatedView.findViewById(R.id.edit_text_change_status);
        return inflatedView;
    }

    private void sendUpdateStatusRequest() {
        // Recuperamos el nombre de usuario de las preferencias
        SharedPreferences preferences = getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        String username = preferences.getString("VALID_USERNAME", null);
        // Creamos el cuerpo de la petición
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("status", editTextUpdateStatus.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Aquí va la petición
        JsonObjectRequestWithCustomAuth request = new JsonObjectRequestWithCustomAuth(
                Request.Method.PUT,
                Server.name + "/users/" + username + "/status",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textViewUserStatus.setText("Cargando");
                        retrieveUserStatus();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(context, "Sin conexión", Toast.LENGTH_LONG).show();
                        } else {
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(context, "Código de respuesta " + serverCode, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                context
        );
        queue.add(request);

    }

}