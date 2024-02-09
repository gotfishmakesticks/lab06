package g313.mirenkov.lab06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NoteEditor extends AppCompatActivity {

    EditText ed_title;
    EditText ed_content;
    String title, content;
    int requestCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        ed_title = findViewById(R.id.note_name);
        ed_content = findViewById(R.id.note_content);
        Intent intent = getIntent();
        requestCode = intent.getIntExtra("requestCode", 78);
        if (requestCode == 56) {
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            ed_title.setText(title);
            ed_content.setText(content);
        }
    }

    public void on_create(View v) {
        title = ed_title.getText().toString();
        content = ed_content.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        setResult(requestCode, intent);
        finish();
    }

    public void on_exit(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
}