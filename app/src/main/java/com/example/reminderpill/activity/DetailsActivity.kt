package com.example.reminderpill.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    var img : String = ""
    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
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

            img = intent.getStringExtra("img").toString()

            binding.imgMedicine.setImageResource(img.toInt())


        }

        binding.imgBack.setOnClickListener {

            onBackPressed()
        }

        binding.btnEditSchedule.setOnClickListener {

            val intent = Intent(this, EditScheduleActivity::class.java)
            intent.putExtra("img", img)
            startActivity(intent)
        }

    }
}