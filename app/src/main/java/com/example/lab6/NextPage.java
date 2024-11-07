package com.example.lab6;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class NextPage extends Activity {

    // Объявление переменных для текстового поля и кнопки
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;

    // Константы для кода запроса
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_page); // Установка макета для этой активности

        // Инициализация элементов интерфейса
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        // Установка обработчика клика для кнопки
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Вызов метода для распознавания речи при нажатии на кнопку
                promptSpeechInput();
            }
        });
    }

    // Метод для запуска распознавания речи
    private void promptSpeechInput() {
        // Создание нового намерения для распознавания речи
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Установка модели языка для распознавания
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Установка языка по умолчанию
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        // Установка подсказки для пользователя
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));

        try {
            // Запуск активности для распознавания речи
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            // Обработка случая, когда распознавание речи не поддерживается
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Обработка результата распознавания речи
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Проверка, какой запрос завершен
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                // Проверка, успешен ли результат
                if (resultCode == RESULT_OK && null != data) {
                    // Получение результатов распознавания речи
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    // Установка первого результата в текстовое поле
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }
        }
    }
}
