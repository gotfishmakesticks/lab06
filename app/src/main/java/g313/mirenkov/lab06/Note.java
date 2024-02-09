package g313.mirenkov.lab06;

import androidx.annotation.NonNull;

public class Note {
    String title;
    String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @NonNull
    public String toString() {
        return title;
    }
}
