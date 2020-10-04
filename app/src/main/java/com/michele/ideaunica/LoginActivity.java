package com.michele.ideaunica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.michele.ideaunica.departamento.AdaptadorCategoria;
import com.michele.ideaunica.departamento.Categoria;
import com.michele.ideaunica.departamento.CategoriaClass;
import com.michele.ideaunica.empresa.Empresas;
import com.michele.ideaunica.sharedPreferences.SessionManager;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class LoginActivity extends AppCompatActivity {

    private Button btnfacebook;
    private Button btngoogle;

    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int GOOGLE_SIGN_IN = 444 ;

    SessionManager sessionManager;

    AlertDialog mDialog;

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/loginapp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        mDialog = new SpotsDialog.Builder().setContext(this).setMessage("Espera un momento por favor").build();

        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);

        InicializarComponente();

        CrearRequest();

        FacebookSdk.sdkInitialize(LoginActivity.this);

        mCallbackManager = CallbackManager.Factory.create();

        btnfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logOut();
                List<String> permissionNeeds= Arrays.asList("email","public_profile");

                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, permissionNeeds);
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if(loginResult!=null){
                            //setFacebookData(loginResult);
                        }else{
                            Toast.makeText(getApplicationContext(),"FUERA",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Cancelado",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGoogle();
            }
        });
    }

    private void initGoogle(){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            mDialog.show();
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if (acct != null) {

                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();

                Loguear("GO",personId,personName,personEmail);

            }else{
                Toast.makeText(this,"DENRO",Toast.LENGTH_LONG).show();
            }


        } catch (ApiException e) {

            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    public void InicializarComponente(){
        btnfacebook = findViewById(R.id.facebook_iniciar_btn);
        btngoogle = findViewById(R.id.google_iniciar_btn);
    }


    private void CrearRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        mGoogleSignInClient.signOut();
    }

    public void Loguear(final String provider, final String idprovider, final String name, final String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("usuario");

                            JSONObject object = jsonArray.getJSONObject(0);
                            sessionManager.createSession(
                                    object.getString("id"),
                                    object.getString("provider"),
                                    object.getString("idprovider"),
                                    object.getString("nombre"),
                                    object.getString("email"));

                            Intent intent = new Intent(getApplicationContext(), IdeaUnica.class);
                            startActivity(intent);
                            mDialog.dismiss();
                            killActivity();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,
                                    "Error. Por favor intentelo mas tarde, gracias.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,
                                "Error de conexión, por favor verifique el acceso a internet.", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("provider",provider);
                params.put("idprovider",idprovider);
                params.put("nombre",name);
                params.put("email",email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.i("Response",response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");


                            Log.i("Login" + "Email", email);
                            Log.i("Login"+ "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            Log.i("Login" + "Gender", gender);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void killActivity() {

        finish();

    }
}
