package com.example.reminderpill.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivityOtpBinding

class OtpActivity : AppCompatActivity() {
    lateinit var binding: ActivityOtpBinding
    var phone : String = ""
     var code : String =  ""
    var otp : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView() {
        if (intent!=null){

            phone = intent.getStringExtra("phone").toString()
            code = intent.getStringExtra("code").toString()
            binding.txtNumber.text = phone
            binding.txtCode.text = code

        }

        binding.imgBack.setOnClickListener{
            onBackPressed()
        }

//        binding.pinview.setOnClickListener {
//            otp = it.toString()
//        }

        binding.btnVerify.setOnClickListener {
       /*
            if (otp.length == 4)
            {*/
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

       /*     }
         *//*   else{
                binding.pinview.error = "Enter valid OTP"
            }*/

        }

    }
}