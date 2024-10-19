package com.example.reminderpill.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivitySignUpScreenBinding
import com.example.reminderpill.utils.DBHelper
import com.example.reminderpill.utils.PrefManager
import com.example.reminderpill.utils.UserModal
import com.google.android.material.snackbar.Snackbar
import java.net.URL

class SignUpScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpScreenBinding
    lateinit var userName: String
    lateinit var pass: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var img : URL
    lateinit var confirmPass: String
    lateinit var prefManager: PrefManager
    lateinit var db : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefManager = PrefManager(this)
        db = DBHelper(this, null)
        initView()
    }

    private fun initView() {

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSignUp.setOnClickListener {
            binding.user.error = null
            binding.email.error = null
            binding.number.error = null
            binding.pass.error = null
            binding.Cpass.error = null

             userName = binding.txtuser.text.toString()
             email = binding.txtEmail.text.toString()
             phone = binding.txtNumber.text.toString()
             pass = binding.txtPassword.text.toString()
             confirmPass = binding.txtConfirmPass.text.toString()

            val dbHelper = DBHelper(this, null)

            when {
                userName.isEmpty() -> {
                    binding.user.error = "Username is required"
                    showSnackbar("Enter your name")
                }
                email.isEmpty() -> {
                    binding.email.error = "Email is required"
                    showSnackbar("Enter your email")
                }
                !isEmailValid(email) -> {
                    binding.email.error = "Invalid email address"
                    showSnackbar("Enter a valid email address")
                }
                phone.isEmpty() -> {
                    binding.number.error = "Phone number is required"
                    showSnackbar("Enter your phone number")
                }
                !isValidPhoneNumber(phone) -> {
                    binding.number.error = "Invalid phone number"
                    showSnackbar("Enter a valid phone number")
                }
                pass.isEmpty() -> {
                    binding.pass.error = "Password is required"
                    showSnackbar("Enter your password")
                }
                !isPasswordValid(pass) -> {
                    binding.pass.error = "Password is not valid"
                    showSnackbar("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
                }
                confirmPass.isEmpty() -> {
                    binding.Cpass.error = "Confirm password is required"
                    showSnackbar("Confirm your password")
                }
                pass != confirmPass -> {
                    binding.Cpass.error = "Password does not match"
                    showSnackbar("Password does not match")
                }
                dbHelper.doesUserExist(userName) -> {
                    binding.user.error = "Username already exists"
                    showSnackbar("Username already exists. Please choose a different username or log in.")
                }
                else -> {
                    val newUser = UserModal(userName, email, pass, phone, "")
                    if (dbHelper.addUser(newUser)) {
                        dbHelper.logInUserByEmail(email)
                        Toast.makeText(this@SignUpScreenActivity, "Account created successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignUpScreenActivity, LogInScreenActivity::class.java)
                        prefManager.setLogin(true)
                        prefManager.setUsername(userName)
                        prefManager.setUserEmail(email)
                        prefManager.setUserPhone(phone)
                        startActivity(intent)
                        finish()
                    } else {
                        showSnackbar("Failed to sign up. Please try again.")
                    }
                }
            }
        }

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this@SignUpScreenActivity, LogInScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        val phonePattern = "^[0-9]{10}$"
        return phone.matches(phonePattern.toRegex())
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(emailPattern.toRegex())
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        val view = snackbar.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        val topMargin = (resources.displayMetrics.heightPixels * 0.05).toInt()
        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params.setMargins(30, topMargin, 30, 0)
        view.layoutParams = params
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.pink_bg))
        snackbar.show()
    }
}
