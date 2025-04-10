package com.example.alyshevyaminovapp.domain.Services

import android.util.Log
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSender {
    companion object{
        private  const val TAB = "EmailSender"

        fun generateNewPassword(): String {
            val allowedChars = ('A' .. 'Z') +  ('a' .. 'z') + ('0' .. '9')
            return (1..8)
                .map {allowedChars.random()}
                .joinToString ("")

        }

        fun sendNewPasswordByEmail(email:String,newPassword: String){
            val subject = "Сброс пароля"
            val body = "Ваш новый пароль: $newPassword"

            Thread{
                EmailSender().sendEmail(email,subject,body)
            }.start()
        }
    }
    fun sendEmail(to:String,subject: String,body:String){
        val username = "androidstudio@mdktest.ru" // Username SMTP
        val password = "piwrrlvaoxgfhexx" // Key SMTP


        val props = java.util.Properties().apply {
            put("mail.smtp.auth","true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.yandex.ru") // Яндекс Почта
            put("mail.smtp.port", "587")
        }
        val session = Session.getInstance(props,object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                return javax.mail.PasswordAuthentication(username, password)
            }
        })
        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(username))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
                setSubject(subject)
                setText(body)

            }
            Transport.send(message)

            Log.d(TAB, "EMail send ycpechno to $to")
        }catch (e:MessagingException){
            Log.e(TAB, "Failed to  send email",e)
        }
    }
}