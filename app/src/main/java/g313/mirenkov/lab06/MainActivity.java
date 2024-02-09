package g313.mirenkov.lab06;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Note> adp;
    ListView cycler;
    int index = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adp = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1);
        cycler = findViewById(R.id.cycler);
        cycler.setAdapter(adp);
        cycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }
        });
    }

    public void on_new_note(View v) {
        Intent intent = new Intent(this, NoteEditor.class);
        intent.putExtra("requestCode", 78);
        startActivityForResult(intent, 78);
    }

    public void on_edit_note(View v) {
        if (index < 0) {
            return;
        }
        Intent intent = new Intent(this, NoteEditor.class);
        Note note = adp.getItem(index);
        intent.putExtra("title", note.title);
        intent.putExtra("content", note.content);
        intent.putExtra("requestCode", 56);
        startActivityForResult(intent, 56);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Toast.makeText(this, String.format("Buy %d", resultCode), Toast.LENGTH_LONG).show();
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        String title = data.getStringExtra("title");
        String content = data.getStringExtra("content");
        Note note = new Note(title, content);
        Note[] notes = get_notes();
        if (resultCode == 56) {
            if (index >= 0) {
                notes[index] = note;
            }
        update_notes(notes);
        }
        else {
            adp.add(note);
        }
        adp.notifyDataSetChanged();
    }

    public void on_note_delete(View v) {
        if (index < 0) {
            return;
        }
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Note deletion");
        ad.setMessage("Are you sure you want to delete this note?");
        ad.setPositiveButton(android.R.string.yes, (dialog, which) -> delete_note());
        ad.setNegativeButton(android.R.string.no, null);
        ad.setIcon(android.R.drawable.ic_dialog_alert);
        ad.show();
    }
    public void delete_note() {
        if (index < 0) {
            return;
        }
        Note[] notes = get_notes();
        Note[] new_notes = new Note[notes.length - 1];
        for (int i = 0, j = 0; i < new_notes.length; i++, j++) {
            if (i == index) {
                j++;
            }
            new_notes[i] = notes[j];
        }
        update_notes(new_notes);
        adp.notifyDataSetChanged();
        index = -1;
    }
    public Note[] get_notes() {
        Note[] notes = new Note[adp.getCount()];
        for (int i = 0; i < notes.length; i++) {
            notes[i] = adp.getItem(i);
        }
        return notes;
    }

    public void update_notes(Note[] notes) {
        adp.clear();
        for (Note value : notes) {
            adp.add(value);
        }
    }
}