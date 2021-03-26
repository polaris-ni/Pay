package com.polaris.pay;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * @author Protein
 */
public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.fragment);
    }

    /**
     * 提供NavController便于跳转
     * @return
     */
    public NavController getNavController() {
        if (navController == null) {
            navController = Navigation.findNavController(this, R.id.fragment);
        }
        return navController;
    }
}