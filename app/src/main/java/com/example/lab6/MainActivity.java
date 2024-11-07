package com.example.lab6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends Activity {
    private EditText editText;
    private Button english, german, nextPage,speakNowButton;
    private TTSManager ttsManager = new TTSManager();
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.input_text);
        english = findViewById(R.id.english);
        german = findViewById(R.id.german);
        speakNowButton = findViewById(R.id.speak_now);
        nextPage = findViewById(R.id.next_page);

        // Устанавливаем слушателя для кнопки английского языка
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locale = Locale.US; // Установка локали на английский
                ttsManager.init(MainActivity.this, locale); // Инициализация TTSManager
                speakNowButton.setVisibility(View.VISIBLE); // Показываем кнопку

            }
        });

        // Устанавливаем слушателя для кнопки немецкого языка
        german.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locale = new Locale("de", "DE"); // Установка локали на немецкий
                ttsManager.init(MainActivity.this, locale); // Инициализация TTSManager
                speakNowButton.setVisibility(View.VISIBLE); // Показываем кнопку

            }
        });

        // Устанавливаем слушателя для кнопки озвучивания
        speakNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String text = editText.getText().toString();
                if (!text.isEmpty()) {
                    ttsManager.initQueue(text); // Озвучивание текста
                }
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent intent = new Intent(MainActivity.this, NextPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown(); // Остановка TTSManager при уничтожении активности
    }
}