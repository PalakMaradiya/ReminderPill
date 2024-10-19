package com.example.reminderpill.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.reminderpill.R
import com.example.reminderpill.adapter.MedicineAdapter
import com.example.reminderpill.databinding.ActivityMedicineBinding
import com.example.reminderpill.utils.MedicineModal

class MedicineActivity : AppCompatActivity() {

    lateinit var medicineAdapter: MedicineAdapter
    lateinit var medicineList: ArrayList<MedicineModal>
    lateinit var manager: LinearLayoutManager
    lateinit var medicineModal: MedicineModal
    lateinit var binding: ActivityMedicineBinding
    var SELECT_PICTURE = 100
    var selectedImageUri: Int = 0
    private var dialogView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {


        binding.btnAdd.setOnClickListener {

            showAddMedicineDialog()
        }

        binding.imgBack.setOnClickListener {

            onBackPressed()
        }

        binding.imgUser.setOnClickListener {

            var i = Intent(this@MedicineActivity, MyAccountActivity::class.java)
            startActivity(i)
        }
        medicineList = ArrayList()


        medicineAdapter =
            MedicineAdapter(this@MedicineActivity, medicineList, onItemClick = { img ->

                var i = Intent(this@MedicineActivity, DetailsActivity::class.java)
                i.putExtra("img", img)
                startActivity(i)

            })
        manager = LinearLayoutManager(this@MedicineActivity, LinearLayoutManager.VERTICAL, false)
        binding.rcvMedicine.adapter = medicineAdapter
        binding.rcvMedicine.layoutManager = manager
        medicineAdapter.notifyDataSetChanged()





        preparePaintings()
    }
    private fun showAddMedicineDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_medicine, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setTitle("Add New Medicine")

        val etMedicineName = dialogView.findViewById<EditText>(R.id.etMedicineName)
        val btnChooseImage = dialogView.findViewById<Button>(R.id.btnChooseImage)

        btnChooseImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
        }

        builder.setPositiveButton("Add") { dialog, which ->
            val medicineName = etMedicineName.text.toString().trim()
            if (medicineName.isNotBlank() && selectedImageUri != null) {
                val newMedicine = MedicineModal(medicineName, selectedImageUri!!)
                medicineAdapter.addMedicine(newMedicine)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please enter medicine name and select an image", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data.toString().toInt()
            if (selectedImageUri != null) {
                val imgSelected = dialogView?.findViewById<ImageView>(R.id.imgSelcted)
                Glide.with(this)
                    .load(selectedImageUri)
                    .into(imgSelected!!)
            }
        }
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



