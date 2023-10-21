package com.example.aplicacionsegundocorte;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<String> notesList = new ArrayList<>();
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        FloatingActionButton fabAddNote = findViewById(R.id.fabAddNote);

        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(notesList);
        recyclerViewNotes.setAdapter(adapter);

        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

        private List<String> notes;

        NotesAdapter(List<String> notes) {
            this.notes = notes;
        }

        @Override
        public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
            return new NoteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NoteViewHolder holder, int position) {
            String note = notes.get(position);
            holder.bind(note);
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        class NoteViewHolder extends RecyclerView.ViewHolder {

            TextView noteTextView;

            NoteViewHolder(View itemView) {
                super(itemView);
                noteTextView = itemView.findViewById(R.id.noteTextView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            showOptionsDialog(position);
                        }
                    }
                });
            }

            void bind(String note) {
                String[] parts = note.split("\n\n", 2);
                String title = parts[0]; // Tomamos solo el título
                noteTextView.setText(title);
            }

            private void showOptionsDialog(final int position) {
                CharSequence options[] = new CharSequence[]{"Ver", "Editar"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Elige una opción");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) { // Ver
                            Intent viewIntent = new Intent(MainActivity.this, ThirdActivity.class);
                            viewIntent.putExtra("noteDetail", notesList.get(position));
                            startActivity(viewIntent);
                        } else if (which == 1) { // Editar
                            Intent editIntent = new Intent(MainActivity.this, SecondActivity.class);
                            editIntent.putExtra("notePosition", position);
                            editIntent.putExtra("noteDetail", notesList.get(position));
                            startActivity(editIntent);
                        }
                    }
                });
                builder.show();
            }
        }
    }
}