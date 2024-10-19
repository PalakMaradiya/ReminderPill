package com.example.reminderpill.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivityAllMedicineBinding
import com.example.reminderpill.fragement.DeactiveFragment
import com.example.reminderpill.fragment.ActiveFragment

class AllMedicineActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllMedicineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAllMedicineBinding.inflate(layoutInflater)
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
            finish()
        }

        replaceFragment(ActiveFragment())
        binding.txtActive.setTextColor(resources.getColor(R.color.white))
        binding.txtActive.setBackgroundResource(R.drawable.green_btn)
        binding.txtActive.setOnClickListener {

            binding.txtDeactive.setBackgroundResource(R.drawable.gray_btn)
            binding.txtDeactive.setTextColor(resources.getColor(R.color.black))
            binding.txtActive.setBackgroundResource(R.drawable.green_btn)
            binding.txtActive.setTextColor(resources.getColor(R.color.white))


            replaceFragment(ActiveFragment())


        }

        binding.txtDeactive.setOnClickListener {
            binding.txtActive.setBackgroundResource(R.drawable.gray_btn)
            binding.txtActive.setTextColor(resources.getColor(R.color.black))
            binding.txtDeactive.setBackgroundResource(R.drawable.red_btn)
            binding.txtDeactive.setTextColor(resources.getColor(R.color.white))
            replaceFragment(DeactiveFragment())

        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}