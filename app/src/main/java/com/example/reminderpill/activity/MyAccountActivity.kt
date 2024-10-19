package com.example.reminderpill.activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivityMyAccountBinding
import com.example.reminderpill.utils.DBHelper
import com.example.reminderpill.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth

class MyAccountActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    private val SELECT_PICTURE = 1
    private var selectedImageUri: Uri? = null
    lateinit var prefManager: PrefManager
    var loginEmail: String = ""
    lateinit var dbHelper: DBHelper
    lateinit var binding: ActivityMyAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMyAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DBHelper(this, null)

        resetUserInfo()
        initView()
    }

    private fun initView() {
        binding.imgEdit.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
        }

        binding.userName.setOnClickListener {
            showEditUserInfoDialog()
        }

        binding.userPhone.setOnClickListener {
            showEditUserInfoDialog()
        }

        binding.userEmail.text = "PillMinder@gmail.com"
        binding.userPhone.text = "+91 12345 67890"
        resetUserInfo()

        prefManager = PrefManager(this)

        loginEmail = prefManager.getUserEmail().toString()
        binding.userName.text = prefManager.getUsername().toString()
        binding.userEmail.text = prefManager.getUserEmail().toString()
        binding.userPhone.text = prefManager.getUserPhone().toString()

        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            Glide.with(this@MyAccountActivity).load(firebaseUser.photoUrl)
                .into(binding.imgProfile)
            binding.userName.text = firebaseUser.displayName
            binding.userEmail.text = firebaseUser.email
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.logOut.setOnClickListener {
            handleLogout()
        }


        handleSignIn()
    }

    private fun showEditUserInfoDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_user_info, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit User Info")

        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextPhone = dialogView.findViewById<EditText>(R.id.editTextPhone)

        editTextName.setText(binding.userName.text)
        editTextPhone.setText(binding.userPhone.text)

        dialogBuilder.setPositiveButton("Save") { dialog, _ ->
            val newName = editTextName.text.toString()
            val newPhone = editTextPhone.text.toString()

            if (newName.isNotEmpty() && newPhone.isNotEmpty()) {
                // Update user info and UI
                binding.userName.text = newName
                binding.userPhone.text = newPhone
                // Save changes to the database
                val loggedInUserEmail = dbHelper.getUserByEmailFromLoggedInUser()
                if (loggedInUserEmail != null) {
                    dbHelper.updateUserDetails(loggedInUserEmail, newName, newPhone)
                }
                Toast.makeText(this, "Changes saved successfully.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter both name and phone number.", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun handleSignIn() {
        val loggedInUserEmail = dbHelper.getUserByEmailFromLoggedInUser()
        if (loggedInUserEmail.isNullOrEmpty()) {
            Toast.makeText(this, "No user logged in. Please log in first.", Toast.LENGTH_SHORT)
                .show()
        } else {
            val user = dbHelper.getUserByEmail(loggedInUserEmail)
            if (user != null) {
                binding.userName.text = user.userName
                binding.userEmail.text = user.email
                binding.userPhone.text = user.phone
            } else {
                Toast.makeText(this, "User details not found.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleLogout() {
        loginEmail = dbHelper.getUserByEmailFromLoggedInUser().toString()

        Log.e("TAG", "handleLogout: " + loginEmail)

        if (loginEmail.isNullOrEmpty()) {
            Toast.makeText(this, "User not logged in, Please LogIn First", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (dbHelper.logOutUser(loginEmail)) {
                prefManager.clearPreferences()
                Toast.makeText(
                    this@MyAccountActivity,
                    "Logged Out Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                resetUserInfo()
                val intent = Intent(this@MyAccountActivity, LogInScreenActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this@MyAccountActivity,
                    "Logout Failed. Please Try Again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            selectedImageUri = data?.data
            if (selectedImageUri != null) {
                binding.imgProfile.setImageURI(selectedImageUri)
            }
        }
    }

    private fun resetUserInfo() {
        binding.imgProfile.setImageResource(R.drawable.baseline_person_24)
        binding.userName.text = "PillMinder User"
        binding.userEmail.text = "PillMinder@gmail.com"
        binding.userPhone.text = "+91 12345 67890"
    }
}
