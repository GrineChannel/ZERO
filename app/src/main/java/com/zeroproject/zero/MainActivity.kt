
package com.zeroproject.zero

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

class MainActivity : Activity() {

    private lateinit var messages: LinearLayout
    private lateinit var input: EditText
    private lateinit var scroll: ScrollView

    private val bgColor = Color.rgb(18, 18, 24)
    private val accent = Color.rgb(190, 155, 90)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bgColor)
            setPadding(20, 20, 20, 12)
        }

        val header = TextView(this).apply {
            text = "ZERO"
            textSize = 30f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 4)
        }

        val status = TextView(this).apply {
            text = "Твой персональный цифровой помощник"
            textSize = 13f
            setTextColor(accent)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 18)
        }

        scroll = ScrollView(this)

        messages = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 8, 0, 8)
        }

        scroll.addView(messages)

        val inputRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(0, 12, 0, 0)
        }

        input = EditText(this).apply {
            hint = "Напиши ZERO..."
            setHintTextColor(Color.GRAY)
            setTextColor(Color.WHITE)
            textSize = 15f
            setSingleLine(true)
            setPadding(16, 12, 16, 12)
            setBackgroundColor(Color.rgb(38, 38, 48))
        }

        val send = Button(this).apply {
            text = "➤"
            textSize = 20f
            setTextColor(Color.WHITE)
            setOnClickListener {
                sendMessage()
            }
        }

        inputRow.addView(
            input,
            LinearLayout.LayoutParams(0, 58, 1f)
        )

        inputRow.addView(
            send,
            LinearLayout.LayoutParams(58, 58)
        )

        root.addView(header)
        root.addView(status)

        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        root.addView(inputRow)

        setContentView(root)

        addMessage(
            "ZERO",
            "Привет! Я на связи. Пока работаю в тестовом режиме, но скоро мы подключим мою нейросеть."
        )
    }

    private fun sendMessage() {
        val text = input.text.toString().trim()

        if (text.isEmpty()) return

        addMessage("Ты", text)
        input.text.clear()

        addMessage(
            "ZERO",
            "Я получил твоё сообщение: \"$text\"\n\nСейчас мой чат работает в тестовом режиме. Следующий этап — подключение Gemma 3 4B."
        )
    }

    private fun addMessage(sender: String, text: String) {
        val message = TextView(this).apply {
            this.text = "$sender\n$text"
            textSize = 15f

            setTextColor(
                if (sender == "ZERO") Color.WHITE else accent
            )

            setPadding(16, 14, 16, 14)

            setBackgroundColor(
                if (sender == "ZERO")
                    Color.rgb(38, 38, 48)
                else
                    Color.rgb(29, 29, 38)
            )
        }

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(0, 6, 0, 6)
        messages.addView(message, params)

        scroll.post {
            scroll.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }
}
