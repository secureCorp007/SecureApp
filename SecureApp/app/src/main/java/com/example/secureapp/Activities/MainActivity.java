package com.example.secureapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.secureapp.Entidades.Contacto;
import com.example.secureapp.Entidades.Grupo;
import com.example.secureapp.Fragments.ContactoFragment;
import com.example.secureapp.Fragments.DetalleContactoFragment;
import com.example.secureapp.Fragments.NuevoContactoFragment;
import com.example.secureapp.Interfaces.IComunicaFragments;
import com.example.secureapp.R;
import com.google.android.material.navigation.NavigationView;

import com.example.secureapp.Fragments.AlertaFragment;
import com.example.secureapp.Fragments.DetalleGrupoFragment;
import com.example.secureapp.Fragments.GrupoFragment;
import com.example.secureapp.Fragments.MainFragment;
import com.example.secureapp.Fragments.NuevoGrupoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IComunicaFragments {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    //variables para cargar el fragment principal
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //variable del fragment detalle grupo
    DetalleGrupoFragment detalleGrupoFragment;

    //variable del fragment detalle contacto
    DetalleContactoFragment detalleContactoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);

        //establecer evento onClick al navigationView
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //cargar fragment principal
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new MainFragment());
        fragmentTransaction.commit();



    }

    //Se controla la pulsación del botón atrás.
    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Deseas salir de SecureApp?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.inicio){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new MainFragment());
            fragmentTransaction.commit();

        }

        if (item.getItemId() == R.id.contactos){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ContactoFragment());
            fragmentTransaction.commit();

        }

        if (item.getItemId() == R.id.nuevoContacto){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new NuevoContactoFragment());
            fragmentTransaction.commit();

        }

        if (item.getItemId() == R.id.grupo){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new GrupoFragment());
            fragmentTransaction.commit();

        }

        if (item.getItemId() == R.id.nuevoGrupo){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new NuevoGrupoFragment());
            fragmentTransaction.commit();

        }

        if (item.getItemId() == R.id.alerta){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new AlertaFragment());
            fragmentTransaction.commit();

        }

        if (item.getItemId() == R.id.cerrarSesion){

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
            irAinicioSesion();

        }

        return false;
    }

    private void irAinicioSesion() {

        Intent intent = new Intent(MainActivity.this, InicioSesionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    @Override
    public void enviarGrupo(Grupo grupo) {

        //Aquí se realiza la lógica necesaria para poder realizar el envio
        detalleGrupoFragment = new DetalleGrupoFragment();

        //Objeto bundle para transportar la información
        Bundle bundleEnvio = new Bundle();

        //Enviar el objeto que está llegando con Serializable
        bundleEnvio.putSerializable("objetoGrupo", grupo);

        detalleGrupoFragment.setArguments(bundleEnvio);

        //abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detalleGrupoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    @Override
    public void enviarContacto(Contacto contacto) {

        //Aquí se realiza la lógica necesaria para poder realizar el envio
        detalleContactoFragment = new DetalleContactoFragment();

        //Objeto bundle para transportar la información
        Bundle bundleEnvio = new Bundle();

        //Enviar el objeto que está llegando con Serializable
        bundleEnvio.putSerializable("objetoContacto", contacto);

        detalleContactoFragment.setArguments(bundleEnvio);

        //abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detalleContactoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}

