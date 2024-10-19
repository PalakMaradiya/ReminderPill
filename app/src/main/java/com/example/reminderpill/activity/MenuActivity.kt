package com.example.reminderpill.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuBinding.inflate(layoutInflater)
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
        binding.medicine.setOnClickListener {

            val intent = Intent(this, MedicineActivity::class.java)
            startActivity(intent)
        }

        binding.tracking.setOnClickListener {

            val intent = Intent(this, MedicineReminderActivity::class.java)
            startActivity(intent)
        }

        binding.refillMedicine.setOnClickListener {
            val intent = Intent(this, RefillActivity::class.java)
            startActivity(intent)
        }

        binding.allMedicine.setOnClickListener {

            val intent = Intent(this, AllMedicineActivity::class.java)
            startActivity(intent)
        }

        binding.setting.setOnClickListener {

            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }
}