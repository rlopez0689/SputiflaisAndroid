package me.rodrigo.sputiflais;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.FacebookSdk;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.util.Arrays;

import me.rodrigo.sputiflais.R;

public class HomeActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.home);

        setToolbar(); // Setear Toolbar como action bar

        Parse.initialize(this, "hwupcc5i5KfGlIIRs3rsjzy8Xibx4oMuAusKXTAK", "6ng13c4hVSvkqqMvCe9o4zeOaqp7tkIqjz6aMN6j");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        FacebookSdk.sdkInitialize(getApplicationContext());

        ParseFacebookUtils.initialize(this);

        ImageButton iButton = (ImageButton) findViewById(R.id.imageButtonFacebook);
        iButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(HomeActivity.this, Arrays.asList("email", "public_profile"), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user != null)
                            logIn();
                    }
                });
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void logIn(){
        Intent startApp = new Intent(this, MainActivity.class);
        startActivity(startApp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

}
