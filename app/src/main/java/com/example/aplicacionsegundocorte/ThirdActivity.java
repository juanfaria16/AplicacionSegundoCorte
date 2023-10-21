package com.example.aplicacionsegundocorte;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView tvNoteDetail = findViewById(R.id.tvNoteDetail);
        Button btnBackToMain = findViewById(R.id.btnBackToMain);

        // Recibimos la nota seleccionada desde MainActivity
        Intent intent = getIntent();
        String noteDetail = intent.getStringExtra("noteDetail");
        tvNoteDetail.setText(noteDetail);

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar MainActivity y finalizar las otras actividades en la pila
                Intent mainIntent = new Intent(ThirdActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                finish();
            }
        });
    }
}