package logicielslibres.fr.voyage3;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class ActiviteTexte extends AppCompatActivity {

    private static final String TAG = "ActiviteTexte";
    private static final int REQUEST_PERMISSION_WRITE = 1001;

    private EditText date;
    private EditText titre;
    private EditText texte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texte);

        // Vérifiez les permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE);
        }

        // Récupération des id
        date = findViewById(R.id.date);
        titre = findViewById(R.id.titre);
        texte = findViewById(R.id.texte);

        // Ajouter le listener pour le champ de date
        date.setOnClickListener(v -> showDatePickerDialog(date));

        // Configuration du bouton précédent
        Button boutonQuitter = findViewById(R.id.boutonQuitter);
        boutonQuitter.setOnClickListener(v -> validerDonnees());
    }

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

    public void validerDonnees() {
        // Transformation en chaîne de caractères
        String texteDate = date.getText().toString();
        String texteTitre = titre.getText().toString();
        String texteTexte = texte.getText().toString();

        // Vérification des valeurs
        if (texteDate == null || texteDate.isEmpty() || texteTitre == null || texteTitre.isEmpty() || texteTexte == null || texteTexte.isEmpty()) {
            String messageErreur = "Au moins un des champs n'a pas été saisi.";
            Toast.makeText(ActiviteTexte.this, messageErreur, Toast.LENGTH_LONG).show();
        } else {
            // appel au "model"
            NombreMots nombreMots = new NombreMots(texteTexte);
            int nbreMots = nombreMots.compterMots(); //comptage des mots du texte
            int nbreOccurences = nombreMots.occurence(texteTitre); // occurence du titre dans le texte
            // Enregistrement des données
            String nomDeFichier = texteTitre + ".txt";
            // on va enregistrer le fichier dans le dossier Documents d'Android
            File fichier = new File("/storage/emulated/0/Documents", nomDeFichier); //chemin de Documents dans l'arborescence android
            try (FileWriter writer = new FileWriter(fichier)) {
                writer.write("Date: " + texteDate + "\n");
                writer.write("Titre: " + texteTitre + "\n");
                writer.write("Texte: " + texteTexte + "\n");
                writer.write("Nombre de mots: " + nbreMots+ "\n");
                writer.write("Nombre d'ocurences du titre dans le texte: " + nbreOccurences);
                writer.flush();
                Toast.makeText(this, "Fichier enregistré avec succès.", Toast.LENGTH_LONG).show();
                finishAffinity();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erreur lors de l'enregistrement du fichier.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
