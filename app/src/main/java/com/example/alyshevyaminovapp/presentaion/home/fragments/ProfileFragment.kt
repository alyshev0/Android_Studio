package com.example.alyshevyaminovapp.presentaion.home.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.widget.AppCompatButton
import com.example.alyshevyaminovapp.R
import com.example.alyshevyaminovapp.data.DbHelper
import com.example.alyshevyaminovapp.presentaion.home.HomeNavigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import org.w3c.dom.Text

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val frameProfileUsername = view.findViewById<TextView>(R.id.fragment_profile_username)
        val frameProfileEmail = view.findViewById<TextView>(R.id.fragment_profile_email)
        val changePasswordButton =
            view.findViewById<AppCompatButton>(R.id.fragment_profile_change_password)


        val login = arguments?.getString("login") ?: "" //
        val email = arguments?.getString("email") ?: "" //

        frameProfileUsername.text = login
        frameProfileEmail.text = email

        val profileImage = view.findViewById<ShapeableImageView>(R.id.frame_profile_picture)
        val changeImage = view.findViewById<FloatingActionButton>(R.id.change_profile_picture)

        val db = DbHelper(requireContext(), null)

        val bitmap = db.loadProfileImage(email)
        if (bitmap != null) {
            profileImage.setImageBitmap(bitmap)
        } else {
            profileImage.setImageResource(R.drawable.icons_default_avatar)
        }
        val launchGallery = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver,
                    result.data!!.data
                )

                profileImage.setImageBitmap(bitmap)


                // Сохранение изображения в DB

                val dbHelper = DbHelper(requireContext(), null)

                val email = arguments?.getString("key") ?: ""

                dbHelper.saveProfileImage(email, bitmap)


                val sharedPreferences =
                    requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

                val login = sharedPreferences.getString("login", "") ?: ""

                (requireActivity() as HomeNavigation).updateNavigationHeader(login, email)

            }

        }


        // Установка обработчика для кнопки смены изображения

        changeImage.setOnClickListener {

            val openGallery = Intent(Intent.ACTION_GET_CONTENT).setType("image/*")

            launchGallery.launch(openGallery)

        }

    }
}




