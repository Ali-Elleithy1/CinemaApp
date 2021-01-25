package com.example.cinemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    ShowingFragment sFragment;
    MoviesFragment mFragment;
    AccountFragment aFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        sFragment = new ShowingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container,sFragment).commit();
        mFragment = new MoviesFragment();
        aFragment = new AccountFragment();

//        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
//        Sprite cubeGrid = new CubeGrid();
//        progressBar.setIndeterminateDrawable(cubeGrid);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.navigation_showing)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,sFragment).commit();
        }
        else if(item.getItemId()==R.id.navigation_movies)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,mFragment).commit();
        }
        else if(item.getItemId()==R.id.navigation_account)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,aFragment).commit();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void logOut(View view) {

        Backendless.UserService.logout(new AsyncCallback<Void>()
        {
            public void handleResponse( Void response )
            {
                Toast.makeText(MainActivity.this, "LOGGING OUT!", Toast.LENGTH_SHORT).show();
            }

            public void handleFault( BackendlessFault fault )
            {
                // something went wrong and logout failed, to get the error code call fault.getCode()
            }
        });
        Intent in = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(in);
    }

    public void pickTickets(View view)
    {
//        Intent in = new Intent(this,BookingActivity.class);
//
//        int position=0,size=0;
//        String s = view.getTag().toString();
//        String newS="";
//        size=Integer.parseInt(String.valueOf(s.charAt(0)));
//        for(int i=1; i<s.length(); i++)
//        {
//            newS+=s.charAt(i);
//        }
//
//        if(Max.size()==size)
//        {
//            in.putExtra("price",prices.get(0));
//            in.putExtra("time",newS);
//        }
//        else if(Gold.size()==size)
//        {
//            in.putExtra("price",prices.get(1));
//            in.putExtra("time",newS);
//        }
//        else
//        {
//            in.putExtra("price",prices.get(2));
//            in.putExtra("time",newS);
//        }
//        startActivity(in);
    }

    public void showTrailer(View view)
    {
        Toast.makeText(this, "PLAY TRAILER", Toast.LENGTH_SHORT).show();
    }

    public void showInfo(View view)
    {
//        ShowingFragment fragment = new ShowingFragment();
//        String name = fragment.getArguments().getString("movie");
//        Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
//        Intent in = new Intent(this,MovieInfoActivity.class);
//        startActivity(in);
    }
}