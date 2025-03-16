package com.example.alyshevyaminovapp.presentaion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alyshevyaminovapp.R
import com.example.alyshevyaminovapp.data.DbHelper
import com.example.alyshevyaminovapp.domain.User

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registration_screen)

        val userLogin: EditText = findViewById(R.id.user_login_auth)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPass: EditText = findViewById(R.id.user_password_auth)
        val button: Button = findViewById(R.id.button_auth)
        val linkToForgetPassword: ImageButton = findViewById(R.id.button_back)

        linkToForgetPassword.setOnClickListener()
        {
            val intent = Intent(this, Authorization::class.java)
            startActivity(intent)
        }
        button.setOnClickListener()
        {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val password = userPass.text.toString().trim()
            val linkToAuthorization : ImageButton = findViewById(R.id.button_back)
            linkToAuthorization.setOnClickListener(){
                val intent = Intent(this,Authorization::class.java)
                startActivity(intent)
            }
            val listOfEdits = listOf(login, email, password)

            if (listOfEdits.any {it.isEmpty()}) {
                Toast.makeText(this,"Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
            } else {

                val user = User(login, email, password)

                val db = DbHelper(this, null)

                db.addUser(user)
                Toast.makeText(this, "Регистрация успешна, пользователь $login добавлен", Toast.LENGTH_SHORT).show()

                userLogin.text.clear()
                userEmail.text.clear()
                userPass.text.clear()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}