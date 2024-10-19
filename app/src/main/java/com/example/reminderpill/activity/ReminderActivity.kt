package com.example.reminderpill.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminderpill.R
import com.example.reminderpill.adapter.TypeAdapter
import com.example.reminderpill.databinding.ActivityReminderBinding
import com.example.reminderpill.utils.TypeModal

class ReminderActivity : AppCompatActivity() {
    lateinit var typeAdapter: TypeAdapter
    lateinit var typeModal: TypeModal
    lateinit var manager: LinearLayoutManager
    lateinit var typelist: ArrayList<TypeModal>
    lateinit var binding: ActivityReminderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {

        binding.imgBack.setOnClickListener{

            onBackPressed()
        }
        typelist = ArrayList()
        preparePaintings()
        typeAdapter = TypeAdapter(this@ReminderActivity, typelist)
        manager = LinearLayoutManager(this@ReminderActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rcv.adapter = typeAdapter
        binding.rcv.layoutManager = manager
        typeAdapter.notifyDataSetChanged()

        binding.btnAddReminder.setOnClickListener {
            val intent = Intent(this@ReminderActivity, MedicineActivity::class.java)
            startActivity(intent)
        }


    }

    private fun preparePaintings() {
        typeModal = TypeModal("Tablets", R.drawable.ic_medi)
        typelist.add(typeModal)

        typeModal = TypeModal("Syrups", R.drawable.ic_syrup)
        typelist.add(typeModal)

        typeModal = TypeModal("Drops", R.drawable.ic_dropper)
        typelist.add(typeModal)

        typeModal = TypeModal("Capsule", R.drawable.ic_tablets)
        typelist.add(typeModal)


        typeModal = TypeModal("Ampoules", R.drawable.ic_ampoule)
        typelist.add(typeModal)
    }
}