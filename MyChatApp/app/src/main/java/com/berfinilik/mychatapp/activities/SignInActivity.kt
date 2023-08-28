@file:Suppress("DEPRECATION")

package com.berfinilik.mychatapp.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.berfinilik.mychatapp.MainActivity
import com.berfinilik.mychatapp.R
import com.berfinilik.mychatapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException



class SignInActivity : AppCompatActivity() {

    lateinit var name: String
    lateinit var email: String
    lateinit var password: String
    lateinit private var fbauth: FirebaseAuth
    lateinit private var pds: ProgressDialog

    lateinit var binding : ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        fbauth = FirebaseAuth.getInstance()

        if (fbauth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
        pds = ProgressDialog(this)
        binding.signInTextToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginButton.setOnClickListener {

            email = binding.loginetemail.text.toString()
            password = binding.loginetpassword.text.toString()

            if (binding.loginetemail.text.isEmpty()){
                Toast.makeText(this, "Email Giriniz", Toast.LENGTH_SHORT).show()
            }
            if (binding.loginetpassword.text.isEmpty()){
                Toast.makeText(this, "Şifre Giriniz", Toast.LENGTH_SHORT).show()
            }
            if (binding.loginetemail.text.isNotEmpty() && binding.loginetpassword.text.isNotEmpty()){
                signIn(password, email)
            }
        }
    }
    private fun signIn(password: String, email: String) {
        pds.show()
        pds.setMessage("Giriş Yapılıyor")
        fbauth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful){
                pds.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                pds.dismiss()
                Toast.makeText(applicationContext, "Geçersiz kimlik bilgileri", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {exception->
            when (exception){
                is FirebaseAuthInvalidCredentialsException->{
                    Toast.makeText(applicationContext, "Geçersiz kimlik bilgileri", Toast.LENGTH_SHORT).show()
                }
                else-> {

                    Toast.makeText(applicationContext, "Yetkilendirme başarısız oldu", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")//belirli uyarıları devre dışı bırakır
    override fun onBackPressed() {
        super.onBackPressed()

        pds.dismiss()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        pds.dismiss()

    }

}