package com.michele.ideaunica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
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

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    private LoginButton loginButton;
    private Button btnfacebook;
    private Button btngoogle;

    private SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    private static final int GOOGLE_SIGN_IN = 444 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        InicializarComponente();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }

            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();



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
                            Toast.makeText(getApplicationContext(),"DENTRO",Toast.LENGTH_LONG).show();
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


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Toast.makeText(this,"DENRO= "+ personEmail,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"DENRO",Toast.LENGTH_LONG).show();
            }


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    public void InicializarComponente(){
        btnfacebook = findViewById(R.id.facebook_iniciar_btn);
        btngoogle = findViewById(R.id.google_iniciar_btn);

        signInButton = findViewById(R.id.sign_in_button);
    }
}