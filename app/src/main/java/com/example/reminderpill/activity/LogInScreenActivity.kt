package com.example.reminderpill.activity

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivityLogInScreenBinding
import com.example.reminderpill.utils.DBHelper
import com.example.reminderpill.utils.PrefManager
import com.example.reminderpill.utils.UserModal
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LogInScreenActivity : AppCompatActivity() {

    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: ActivityLogInScreenBinding
    lateinit var prefManager: PrefManager
    lateinit var email: String
    lateinit var password: String
    lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLogInScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefManager = PrefManager(this)

        dbHelper = DBHelper(this,null)
        initView()
        checkLogin()
    }

    private fun checkLogin() {
        if (prefManager.isLogIn() == true) {
            val username = prefManager.getUsername()
            if (username != null) {
                val savedUser = dbHelper.getUserByEmail(username)
                if (savedUser != null) {
                    val intent = Intent(this@LogInScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    return
                }
            }
        }
    }

    private fun initView() {


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("535217268669-0qf26rvgjm7c9olnpfqph9v54oaf2kr9.apps.googleusercontent.com")
            .requestEmail().build()

        binding.btnLogIn.setOnClickListener {
            binding.user.error = null
            binding.layoutPassword.error = null
            email = binding.txtuser.text.toString()
            password = binding.txtPassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.user.error = "Email is required"
                    showSnackbar("Enter your email")
                }

                password.isEmpty() -> {
                    binding.layoutPassword.error = "Password is required"
                    showSnackbar("Enter your password")
                }

                !isPasswordValid(password) -> {
                    binding.layoutPassword.error = "Password is not valid"
                    showSnackbar("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
                }

                else -> {
                    if (dbHelper.verifyUserCredentialsByEmail(email, password)) {
                        dbHelper.logInUserByEmail(email)
                        showSnackbar("Login successfully")
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LogInScreenActivity, MainActivity::class.java)
                        prefManager.setUsername(email)
                        prefManager.setLogin(true)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "User not exist Please SignUp First", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LogInScreenActivity, SignUpScreenActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }




        binding.imgBack.setOnClickListener {
            prefManager.setUsername("")
            prefManager.setLogin(false)
            finish()
        }
        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this@LogInScreenActivity, SignUpScreenActivity::class.java)
            startActivity(intent)
        }
        binding.phoneNumberLogin.setOnClickListener {
            val intent = Intent(this@LogInScreenActivity, PhoneNumberActivity::class.java)
            startActivity(intent)
        }

        googleSignInClient = GoogleSignIn.getClient(this@LogInScreenActivity, googleSignInOptions)
        binding.GoogleLogin.setOnClickListener {
            val intent: Intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 100)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(
                Intent(
                    this@LogInScreenActivity, MainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
        return password.matches(passwordPattern.toRegex())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val signInAccountTask: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            if (signInAccountTask.isSuccessful) {
                try {
                    val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                    if (googleSignInAccount != null) {
                        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(
                            googleSignInAccount.idToken, null
                        )
                        firebaseAuth.signInWithCredential(authCredential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    showSnackbar("Login Successfully")
                                    val i =
                                        Intent(this@LogInScreenActivity, MainActivity::class.java)
                                    startActivity(i)
                                } else {
                                    showSnackbar(
                                        task.exception!!.message ?: "Authentication failed"
                                    )
                                }
                            }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }
    }



    private fun saveUserToDatabase(userName: String, userPassword: String) {
        val user = UserModal(
            userName = "",
            email = userName,
            password = userPassword,
            phone = "",
            imageUrl = ""
        )
        dbHelper.addUser(user)
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)

        val snackbarView = snackbar.view

        val drawable = resources.getDrawable(R.drawable.snackbar_bg) as GradientDrawable

        snackbarView.background = drawable
        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        val topMargin = (resources.displayMetrics.heightPixels * 0.05).toInt()
        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params.setMargins(30, topMargin, 30, 0)
        snackbarView.layoutParams = params
        snackbar.show()
    }
}
