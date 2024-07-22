package logicielslibres.fr.voyage1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ActiviteTexte extends AppCompatActivity {

    private static final String TAG = "ActivitePrescription";

    private EditText date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texte);

        date = findViewById(R.id.date);
        // Ajouter le listener pour le champ de dateDeDebut
        date.setOnClickListener(v -> showDatePickerDialog(date));

        // Configuration du bouton précédent
        Button boutonPrecedent = findViewById(R.id.boutonPrecedent);
        boutonPrecedent.setOnClickListener(v -> finishAffinity());
    }

    // Calendrier pour la date
    private void showDatePickerDialog(EditText dateField) {
        // Obtenez la date actuelle pour initialiser le DatePickerDialog
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ActiviteTexte.this,
                (view, year1, month1, dayOfMonth) -> {
                    // Format de la date sélectionnée
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    dateField.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}

