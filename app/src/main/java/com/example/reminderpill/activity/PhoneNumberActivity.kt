package com.example.reminderpill.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivityPhoneNumberBinding


class PhoneNumberActivity : AppCompatActivity() {
    lateinit var binding: ActivityPhoneNumberBinding
    var phone: String = ""
    var code: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {

        binding.imgBack.setOnClickListener {

            onBackPressed()
        }


        binding.btnsendOtp.setOnClickListener {
            phone = binding.edtNumber.getText().toString()
            code = binding.codePicker.getSelectedCountryCode()
            if (binding.edtNumber.text.toString().isEmpty()) {
                Toast.makeText(
                    this@PhoneNumberActivity,
                    "Please enter mobile number",
                    Toast.LENGTH_LONG).show()
            } else if (phone.length == 10) {
                if (Patterns.PHONE.matcher(phone).matches()) {
                    //Toast.makeText(this@PhoneNumberActivity, "MATCH", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, OtpActivity::class.java)
                    intent. putExtra("phone", phone)
                    intent. putExtra("code", code)

                    startActivity(intent)
                }
            } else {
                Toast.makeText(this@PhoneNumberActivity, "Invalid phone number length", Toast.LENGTH_LONG).show()
            }

        }

    }
}