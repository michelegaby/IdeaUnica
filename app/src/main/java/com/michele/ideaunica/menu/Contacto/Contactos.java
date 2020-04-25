package com.michele.ideaunica.menu.Contacto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Contactos extends AppCompatActivity {

    //Componentes
    private TextView celular;
    private TextView correo;
    private TextView asunto;
    private TextView mensaje;
    private Button enviar;

    //complementos
    private String correoOrigen;
    private String contrasenyaOrigen;
    private Session session;
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

        contrasenyaOrigen = "siceboliviasrl@gmail.com";
        contrasenyaOrigen = "W@lterS1ceR";
        //Funcionalidad
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*
                try {
                    GMailSender sender = new GMailSender("siceboliviasrl@gmail.com", "W@lterS1ceR");
                    sender.sendMail("This is Subject",
                            "This is Body",
                            "siceboliviasrl@gmail.com",
                            "micheleserrano2016@gmail.com");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }*/
                
                Properties properties = new Properties();
                properties.put("mail.smtp.host","smtp.googlemail.com");
                properties.put("mail.smtp.socketFactory.port","465");
                properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.port","465");

                try {

                    session = Session.getDefaultInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(contrasenyaOrigen,contrasenyaOrigen);
                        }
                    });

                    if(session != null){
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(correoOrigen));
                        message.setSubject(asunto.getText().toString());
                        message.setRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse("micheleserrano2016@gmail.com"));
                        message.setContent(mensaje.getText().toString(),"text/html; charset=utf-8");
                        Transport.send(message);

                    }

                }catch (Exception e){
                    e.printStackTrace();
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
