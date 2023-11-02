package com.afundacion.fp.library;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MonstersActivity extends AppCompatActivity {

    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monsters);

        BottomNavigationView bar = findViewById(R.id.bottomNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Fragment1()).commit();
        bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item1) {
                    Toast.makeText(context, "Primer Digimon", Toast.LENGTH_LONG).show();
                    Fragment myFragment = new Fragment1();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, myFragment).commit();
                }
                if (item.getItemId() == R.id.item2) {
                    Toast.makeText(context, "Este es el segundo", Toast.LENGTH_LONG).show();
                    Fragment myFragment = new Fragment2();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, myFragment).commit();
                }
                if (item.getItemId() == R.id.item3) {
                    Toast.makeText(context, "Has clicado en Digimon 3", Toast.LENGTH_LONG).show();
                    Fragment myFragment = new Fragment3();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, myFragment).commit();
                }
                return true;
            }
        });
    }
}
