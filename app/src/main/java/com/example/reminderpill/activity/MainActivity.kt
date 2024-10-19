package com.example.reminderpill.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivityMainBinding
import com.example.reminderpill.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var loginName: String
    lateinit var ne : String
    lateinit var email : String
    lateinit var phone : String
    lateinit var prefManager: PrefManager

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = FirebaseAuth.getInstance()








        updateUI()
        initView()
        checkLogin()
    }

    private fun checkLogin() {
        if(prefManager.isLogIn() == false){
            val intent = Intent(this@MainActivity, LogInScreenActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onStart() {
        super.onStart()
        updateUI()
    }
    private fun updateUI() {

        val imageUriString = intent.getStringExtra("IMAGE_URI")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            binding.imgUser.setImageURI(imageUri)
        }
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            Glide.with(this@MainActivity).load(firebaseUser.photoUrl).into(binding.imgUser)

        } else {
            binding.imgUser.setImageResource(R.drawable.baseline_person_24)

        }
    }

    private fun initView() {

        prefManager = PrefManager(this)
        prefManager.setFirstTimeLaunch(false)
        loginName = prefManager.getUsername().toString()


        if (intent != null) {

            loginName = intent.getStringExtra("name").toString()
        }


        if (intent != null) {
            ne = intent.getStringExtra("username").toString()
            phone = intent.getStringExtra("phone").toString()
            email = intent.getStringExtra("email").toString()

        }


        binding.imgMenu.setOnClickListener {


            val intent = Intent(this@MainActivity, MenuActivity::class.java)

            startActivity(intent)
        }

        binding.imgUser.setOnClickListener {
            val intent = Intent(this@MainActivity, MyAccountActivity::class.java)
            startActivity(intent)
        }

        binding.btnAddmedicines.setOnClickListener {

            val intent = Intent(this@MainActivity, ReminderActivity::class.java)
            startActivity(intent)
        }
    }
}