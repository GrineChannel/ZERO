
package com.zeroproject.zero

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*

class MainActivity : Activity() {

    private lateinit var messages: LinearLayout
    private lateinit var input: EditText
    private lateinit var scroll: ScrollView

    private val bgColor = Color.rgb(18, 18, 24)
    private val panelColor = Color.rgb(34, 34, 44)
    private val accent = Color.rgb(190, 155, 90)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        window.statusBarColor = bgColor
        window.navigationBarColor = bgColor

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bgColor)
            setPadding(16, 12, 16, 16)
        }

        // Верхняя панель

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 12)
        }

        val title = TextView(this).apply {
            text = "ZERO"
            textSize = 30f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val status = TextView(this).apply {
            text = "Твой персональный цифровой помощник"
            textSize = 13f
            setTextColor(accent)
            gravity = Gravity.CENTER
            setPadding(0, 4, 0, 0)
        }

        header.addView(title)
        header.addView(status)

        // Область переписки

        scroll = ScrollView(this).apply {
            isFillViewport = true
            clipToPadding = false
        }

        messages = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 8, 0, 12)
        }

        scroll.addView(
            messages,
            ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        // Нижняя панель ввода

        val inputPanel = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(8, 10, 8, 10)
            setBackgroundColor(panelColor)
        }

        input = EditText(this).apply {
            hint = "Напиши сообщение..."
            setHintTextColor(Color.GRAY)
            setTextColor(Color.WHITE)
            textSize = 16f

            setSingleLine(false)
            minLines = 3
            maxLines = 5
            gravity = Gravity.TOP or Gravity.START

            setPadding(14, 12, 14, 12)
            setBackgroundColor(Color.rgb(45, 45, 57))
        }

        val send = Button(this).apply {
            text = "➤"
            textSize = 23f
            setTextColor(Color.WHITE)
            backgroundTintList =
                ColorStateList.valueOf(accent)
            isAllCaps = false

            setOnClickListener {
                sendMessage()
            }
        }

        inputPanel.addView(
            input,
            LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                marginEnd = 8
            }
        )

        inputPanel.addView(
            send,
            LinearLayout.LayoutParams(64, 64)
        )

        // Собираем экран

        root.addView(
            header,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        root.addView(
            inputPanel,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        setContentView(root)

        addMessage(
            "ZERO",
            "Привет! Я на связи.\n\nСкоро мы подключим мою нейросеть Gemma 3 4B."
        )
    }

    private fun sendMessage() {
        val text = input.text.toString().trim()

        if (text.isEmpty()) return

        addMessage("Ты", text)
        input.text.clear()

        addMessage(
            "ZERO",
            "Я получил твоё сообщение:\n$text\n\nПока работаю в тестовом режиме."
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
                    panelColor
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
            scroll.fullScroll(View.FOCUS_DOWN)
        }
    }
}
