package com.example.reminderpill.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminderpill.R
import com.example.reminderpill.adapter.MedicineAdapter
import com.example.reminderpill.databinding.ActivityMedicineReminderBinding
import com.example.reminderpill.utils.MedicineModal


class MedicineReminderActivity : AppCompatActivity() {

    lateinit var medicineAdapter: MedicineAdapter
    lateinit var medicineList: ArrayList<MedicineModal>
    lateinit var  manager : LinearLayoutManager
    lateinit var medicineModal: MedicineModal
    lateinit var binding: ActivityMedicineReminderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMedicineReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(com.example.reminderpill.R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initview()
    }

    private fun initview() {

        binding.imgBack.setOnClickListener {

            onBackPressed()
        }

        medicineList = ArrayList()


        medicineAdapter = MedicineAdapter(this@MedicineReminderActivity, medicineList , onItemClick = {img->

//            var i = Intent(this@MedicineReminderActivity, DetailsActivity::class.java)
//            i.putExtra("img", img)
//            startActivity(i)

        })
        manager = LinearLayoutManager(this@MedicineReminderActivity, LinearLayoutManager.VERTICAL, false)
        binding.rcvMediRim.adapter = medicineAdapter
        binding.rcvMediRim.layoutManager = manager
        medicineAdapter.notifyDataSetChanged()

        preparePaintings()


    }

    private fun preparePaintings() {

        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_medi)
        medicineList.add(medicineModal)

        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_syrup)
        medicineList.add(medicineModal)

        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_dropper)
        medicineList.add(medicineModal)

        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_tablets)
        medicineList.add(medicineModal)


        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_ampoule)
        medicineList.add(medicineModal)
    }


}

