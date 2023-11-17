package com.afundacion.fp.sessions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUser;
    private EditText editTextPassword;
    private Button registerButton;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Los inicializamos a partir de su contraparte en el XML
        editTextUser = findViewById(R.id.aliceDoe);
        editTextPassword = findViewById(R.id.password);
        registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editTextUser.getText().toString();
                if (!username.isEmpty()) {
                    String toastMessage = "Usuario " + username;
                    Toast.makeText(RegisterActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Por favor, introduce un nombre de usuario", Toast.LENGTH_SHORT).show();
                }


                sendRegisterRequest();

            }
        });

        queue = Volley.newRequestQueue(this);

    }

    private void sendRegisterRequest() {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", editTextUser.getText().toString());
            requestBody.put("password", editTextPassword.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Server.name + "/users",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegisterActivity.this, "Usuario creado con éxito", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(RegisterActivity.this, "Sin conexión", Toast.LENGTH_LONG).show();
                        } else {
                            // El servidor ha dado una respuesta de error

                            // La siguiente variable contendrá el código HTTP,
                            // por ejemplo 404, 500,...
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(RegisterActivity.this, "Código de respuesta " + serverCode, Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );

        queue.add(request);

    }


}