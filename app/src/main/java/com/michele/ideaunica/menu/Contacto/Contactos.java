package com.michele.ideaunica.menu.Contacto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Patterns;
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
import com.google.zxing.common.StringUtils;
import com.michele.ideaunica.R;
import com.michele.ideaunica.menu.Entrevista.AdaptadorEntrevista;
import com.michele.ideaunica.menu.Entrevista.EntrevistaClass;
import com.michele.ideaunica.menu.Entrevista.Entrevistas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Contactos extends AppCompatActivity {

    //Componentes
    public TextView celular;
    public TextView correo;
    public TextView asunto;
    public TextView mensaje;
    public Button enviar;

    //Complemetos
    boolean bcorreo;
    boolean bcelular;

    public Pattern patron_numero = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Contáctanos");

        Inicializar();

        //Funcionalidad
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!celular.getText().toString().isEmpty())
                {
                    if(patron_numero.matcher(celular.getText().toString()).matches()==true){

                        if(!correo.getText().toString().isEmpty())
                        {
                            if((Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString()).matches()==true))
                            {
                                if(asunto.getText().toString().trim().isEmpty() || mensaje.getText().toString().trim().isEmpty())
                                {
                                    Toast.makeText(getApplicationContext(),"Por favor complete los campos de asunto y mensaje. Gracias",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    sendMail();
                                }

                            }else{
                                correo.setError("Correo invalido");
                            }

                        }else {

                            if(asunto.getText().toString().trim().isEmpty() || mensaje.getText().toString().trim().isEmpty())
                            {
                                Toast.makeText(getApplicationContext(),"Por favor complete los campos de asunto y mensaje. Gracias",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                sendMail();
                            }
                        }
                    }
                    else {
                        celular.setError("Numero de celular invalido");
                    }
                }
                else
                {
                    if(!correo.getText().toString().isEmpty())
                    {
                        if((Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString()).matches()==true))
                        {
                            if(asunto.getText().toString().trim().isEmpty() || mensaje.getText().toString().trim().isEmpty())
                            {
                                Toast.makeText(getApplicationContext(),"Por favor complete los campos de asunto y mensaje. Gracias",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                sendMail();
                            }

                        }else{
                            correo.setError("Correo invalido");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Por favor porpocione una forma de contacto.",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void Inicializar() {
        celular = findViewById(R.id.celular_contacto);
        correo = findViewById(R.id.email_contacto);
        asunto = findViewById(R.id.asunto_contacto);
        mensaje = findViewById(R.id.mesaje_contacto);
        enviar = findViewById(R.id.enviar_contacto);
    }

    public String getValueOrDefault(String value, String defaultValue){
        return value.isEmpty() ? defaultValue : value;
    }

    private void sendMail(){

        String message = Html.toHtml((Spanned) mensaje.getText());
        String subject = asunto.getText().toString().trim();
        String phone = getValueOrDefault(celular.getText().toString().trim(),"No porporcionado");
        String email = getValueOrDefault(correo.getText().toString().trim(),"No porporcionado");

        String MSN = "<p>Buenas, mensaje recibido desde la app Ide Unica.</p> <p>Celular: " + phone + "</p><p>Correo: " + email + "</p><p>Mensaje:</p><p>" + message + "</p>";

        //Enviar Email
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,Utils.TO,subject,MSN);

        javaMailAPI.execute();
        Vaciar();
    }

    public void Vaciar(){
        celular.setText(null);
        correo.setText(null);
        asunto.setText(null);
        mensaje.setText(null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
