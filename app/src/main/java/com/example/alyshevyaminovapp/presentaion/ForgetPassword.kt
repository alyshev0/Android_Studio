package com.example.alyshevyaminovapp.presentaion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.alyshevyaminovapp.R
import com.example.alyshevyaminovapp.data.DbHelper
import com.example.alyshevyaminovapp.domain.EmailSender
import com.example.alyshevyaminovapp.domain.EmailSender.Companion.sendNewPasswordByEmail

class ForgetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgetpassword_screen)
        enableEdgeToEdge()
        val db = DbHelper(this, null)
//        val linkToAuthorization: ImageButton = findViewById(R.id.button_back)
        val resetPasswordButton: Button = findViewById(R.id.button_send_message)
        val etEmail:EditText = findViewById(R.id.user_email)
//        linkToAuthorization.setOnClickListener()
//        {
//            val intent = Intent(this, Authorization::class.java)
//            startActivity(intent)
//        }
        resetPasswordButton.setOnClickListener()
        {
            val email = etEmail.text.toString().trim()
            if (email == "")
            {
                Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show()
            }
            if (db.checkUserByEmail(email))
            {
                val newPassword = EmailSender.generateNewPassword()
                if (db.updatePassword(email, newPassword))
                {
                    val user = db.getUserByEmail(email)

                    if(user != null)
                    {
                        sendNewPasswordByEmail(user.email, newPassword)
                        Toast.makeText(this, "Новый пароль обновлении пароля", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this, "Ошибка при обновлении пароля", Toast.LENGTH_SHORT).show()
                    }

                }
            }
            else
            {
                Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
            }
        }
    }
}