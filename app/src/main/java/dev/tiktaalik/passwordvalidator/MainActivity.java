package dev.tiktaalik.passwordvalidator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.tiktaalik.passwordvalidator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Crear variables globales
    private static final String TAG = "MainActivity";
    private static final boolean DEBUG = true;
    private static final String LOG_TAG = "MainActivity";
    private static final boolean LOG_DEBUG = true;
    private ActivityMainBinding mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Activar View Binding
        mainView = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainView.getRoot());

        // Activar Action Bar and get a reference to use it later on
        Toolbar actionBar = mainView.actionBar;
        setSupportActionBar(actionBar);
        ActionBar actionBarReference = getSupportActionBar();

        // Habilitar el botón de navegación hacia atrás
        if (actionBarReference != null) {
            actionBarReference.setHomeButtonEnabled(true);
        }

        // Calcular insets y ajustar la vista para que se adapte al grosor de las barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(mainView.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}