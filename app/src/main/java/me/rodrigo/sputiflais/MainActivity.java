package me.rodrigo.sputiflais;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.ParseFacebookUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import me.rodrigo.sputiflais.domain.User;
import me.rodrigo.sputiflais.fragment.ArtistFragment;
import me.rodrigo.sputiflais.fragment.HomeFragment;
import me.rodrigo.sputiflais.R;
import me.rodrigo.sputiflais.fragment.AlbumFragment;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private String drawerTitle;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(); // Setear Toolbar como action bar

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = getResources().getString(R.string.home_item);
        if (savedInstanceState == null) {
            selectItem(drawerTitle);
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();
                        selectItem(title);
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyboard();
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        InputMethodManager imm=
                (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void selectItem(String title) {
        // Enviar título como arguemento del fragmento
        Bundle args = new Bundle();
        Fragment fragment;
        if(title.equals("Albums"))
            fragment = AlbumFragment.newInstance(title);
        else if(title.equals("Artists"))
            fragment = ArtistFragment.newInstance(title);
        else
            fragment = HomeFragment.newInstance(title);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
        drawerLayout.closeDrawers(); // Cerrar drawer
        setTitle(title); // Setear título actual
    }


    public void setUser(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.username);
        name.setText(user.getName());
        TextView email = (TextView) header.findViewById(R.id.email_user);
        email.setText(user.getEmail());
        CircleImageView image_circle = (CircleImageView) header.findViewById(R.id.circle_image_user);
        Picasso.with(this)
                .load(user.getPhoto())
                .into(image_circle);


        TextView username_pp = (TextView)findViewById(R.id.user_name);
        TextView email_pp = (TextView)findViewById(R.id.user_email);
        ImageView image = (ImageView) findViewById(R.id.user_img);
        username_pp.setText(user.getName());
        email_pp.setText(user.getEmail());
        Picasso.with(this)
                .load(user.getPhoto())
                .into(image);
    }

    public void getUserFacebook(){
        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        String user_session = myPrefs.getString("username", null);
        String email_session = myPrefs.getString("email", null);
        String pp_session = myPrefs.getString("picture", null);
        if(user_session!=null) {
            user = new User();
            user.setName(user_session);
            user.setEmail(email_session);
            user.setPhoto(pp_session);
            Log.i("Sputiflais", user.getPhoto());
            setUser();
        }
        else{
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            String username;
                            try{
                                user = new User();
                                user.setName(object.getString("name"));
                                user.setEmail(object.getString("email"));
                                JSONObject picture = object.getJSONObject("picture");
                                JSONObject image_data = picture.getJSONObject("data");
                                user.setPhoto(image_data.getString("url").replaceAll("\\/", "/"));
                                username= object.getString("name");
                                Toast.makeText(MainActivity.this, "Hello, "+username, Toast.LENGTH_LONG).show();
                                setUserSession();
                                setUser();
                            }
                            catch (Exception e){
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email, link, picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    public void setUserSession(){
        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString("username", user.getName());
        prefsEditor.putString("email", user.getEmail());
        prefsEditor.putString("picture", user.getPhoto());
        prefsEditor.commit();
    }

}
