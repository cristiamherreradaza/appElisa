package net.goghu.elisa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    int REQUEST_CODE = 200;
    TextInputLayout txtEmail, txtPassword, txtNombre;
    String correo, nombre, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtEmail = findViewById(R.id.txtEmail);

        verificarPermisos();
    }

    private void verificarPermisos() {
        int permisoInternet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        if (permisoInternet == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso de internet concedido", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
        }

    }

    public void Envia(View view) {

        String url = "https://goghu.net/elisa/public/api/User/familiares";
        txtNombre = findViewById(R.id.txtNombre);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        nombre = txtNombre.getEditText().getText().toString().trim();
        correo = txtEmail.getEditText().getText().toString().trim();
        password = txtPassword.getEditText().getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(RegistroActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(RegistroActivity.this, "Registro Exitoso" + response, Toast.LENGTH_SHORT).show();
                Log.i("My success", "" + response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistroActivity.this, "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", nombre);
                map.put("email", correo);
                map.put("password", password);

                return map;
            }

        };
        queue.add(request);
    }

}