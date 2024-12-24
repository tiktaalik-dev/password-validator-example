package dev.tiktaalik.passwordvalidator.ui.validator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import dev.tiktaalik.passwordvalidator.ResultsFragment;
import dev.tiktaalik.passwordvalidator.data.model.ValidPassword;
import dev.tiktaalik.passwordvalidator.databinding.FragmentValidatorBinding;

public class ValidatorFragment extends Fragment {

    private FragmentValidatorBinding binding;

    private static boolean checkTypedPassword(EditText passwordEditText) {
        // Recuperar el texto escrito por el usuario
        String enteredText = passwordEditText.getText().toString();

        // Crear un objeto ValidPassword con la contraseña ingresada
        ValidPassword typedText = new ValidPassword(enteredText);

        return typedText.isValidPassword();
    }

    @NonNull
    private static TextWatcher getTextWatcher(EditText passwordEditText, Button loginButton) {
        final ValidPassword[] typedText = new ValidPassword[1];
        final Integer delayMs = 500;
        final Handler typingHandler = new Handler(Looper.getMainLooper());
        final Runnable[] typingRunnable = {null};

        // Crear un objeto TextWatcher para manejar el texto escrito por el usuario
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Ignorar este método
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (typingRunnable[0] != null) {
                    typingHandler.removeCallbacks(typingRunnable[0]);
                }
                typingRunnable[0] = new Runnable() {
                    @Override
                    public void run() {

                        // Si el largo de la contraseña es correcto, establecer el botón como habilitado
                        loginButton.setEnabled(checkTypedPassword(passwordEditText));
                    }
                };

                // Manejar el texto después que el tiempo de espera ha transcurrido
                typingHandler.postDelayed(typingRunnable[0], delayMs);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Ignorar este método
            }
        };
        return textWatcher;
    }

    private static EditText.OnEditorActionListener getSendListener(EditText passwordEditText, Button loginButton) {
        TextView.OnEditorActionListener sendQueryListener = new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView passwordEditText, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    // Comprobar si la contraseña es válida
                    boolean validPassword = checkTypedPassword((EditText) passwordEditText);

                    // Si es válida, gatillar el botón
                    loginButton.performClick();
                }

                return false;
            }
        };

        return sendQueryListener;
    }

    private View.OnClickListener getClickWatcher(ProgressBar loadingProgressBar, NavController navController) {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Activar la animación de la barra de progreso
                loadingProgressBar.setVisibility(View.VISIBLE);

                // Navegar hacia el siguiente fragmento usando el action definido en el XML
                NavDirections action = ValidatorFragmentDirections.actionLoginFragmentToResultsFragment();
                navController.navigate(action);

            }
        };
        return clickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentValidatorBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        final NavController navController = NavHostFragment.findNavController(this);

        // Agregar un "oyente" para detectar cuando el usuario deja de escribir y pasárselo al manejador
        passwordEditText.addTextChangedListener(getTextWatcher(passwordEditText, loginButton));

        // Agregar un "oyente" para detectar cuando el usuario presiona la tecla "Buscar"
        passwordEditText.setOnEditorActionListener(getSendListener(passwordEditText, loginButton));

        // Agregar un "oyente" para detectar cuando el usuario hace clic en el botón
        loginButton.setOnClickListener(getClickWatcher(loadingProgressBar, navController));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}