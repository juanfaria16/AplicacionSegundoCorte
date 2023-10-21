package com.example.aplicacionsegundocorte;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etContent;
    private int notePosition = -1; // Para almacenar la posición de la nota que se está editando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        Button btnSaveNote = findViewById(R.id.btnSaveNote);

        // Verificar si estamos editando una nota
        if (getIntent().hasExtra("notePosition") && getIntent().hasExtra("noteDetail")) {
            notePosition = getIntent().getIntExtra("notePosition", -1);
            String noteDetail = getIntent().getStringExtra("noteDetail");

            // Dividir el detalle de la nota en título y contenido
            String[] parts = noteDetail.split("\n\n", 2);
            etTitle.setText(parts[0]);
            if (parts.length > 1) {
                etContent.setText(parts[1]);
            }
        }

        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if (!title.isEmpty() && !content.isEmpty()) {
            if (notePosition != -1) { // Si estamos editando una nota
                MainActivity.notesList.set(notePosition, title + "\n\n" + content);
            } else { // Si estamos añadiendo una nueva nota
                MainActivity.notesList.add(title + "\n\n" + content);
            }
            finish(); // Cierra esta actividad y regresa a MainActivity
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }
}