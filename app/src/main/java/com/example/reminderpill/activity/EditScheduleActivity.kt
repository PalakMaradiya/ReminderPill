package com.example.reminderpill.activity

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reminderpill.R
import com.example.reminderpill.databinding.ActivityEditScheduleBinding
import com.example.reminderpill.utils.DBHelper
import com.example.reminderpill.utils.ScheduleModal

class EditScheduleActivity : AppCompatActivity() {

    var img: String = ""
    lateinit var dbHelper: DBHelper
    lateinit var schedules: MutableList<ScheduleModal>
    lateinit var binding: ActivityEditScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initview()
    }

    private fun initview() {

        dbHelper = DBHelper(this, null)
        schedules = dbHelper.getAllSchedules().toMutableList()

        if (intent != null) {
            img = intent.getStringExtra("img").toString()
            binding.imgMedicine.setImageResource(img.toInt())
        }

        binding.btnSaveSchedule.setOnClickListener {
            saveScheduleChanges()
        }

        binding.txtDecrapstion.setOnClickListener {
            showEditDescriptionDialog()
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.Duration.setOnClickListener {
            showEditDurationDialog()
        }

        binding.frequency.setOnClickListener {
            showEditFrequencyDialog()
        }

        binding.intakes.setOnClickListener {
            showEditIntakeDialog()
        }

        binding.refil.setOnClickListener {
            showEditRefillDialog()
        }

        binding.addCard.setOnClickListener {
            showAddScheduleDialog()
        }
    }

    // Function to save changes to the schedule
    private fun saveScheduleChanges() {
        val currentDescription = binding.txtDecrapstion.text.toString()
        val currentDuration = binding.txtDays.text.toString()
        val currentFrequency = binding.txtDaily.text.toString()
        val currentIntake = binding.txtTimes.text.toString()
        val currentRefill = binding.txtStoke.text.toString()

        if (currentDescription.isNotEmpty() && currentDuration.isNotEmpty() && currentFrequency.isNotEmpty() &&
            currentIntake.isNotEmpty() && currentRefill.isNotEmpty()
        ) {

            schedules.forEach { schedule ->
                schedule.description = currentDescription
                schedule.duration = currentDuration
                schedule.frequency = currentFrequency
                schedule.intake = currentIntake
                schedule.refill = currentRefill

                dbHelper.updateSchedule(schedule)
            }

            Toast.makeText(this, "All changes saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please fill in all details.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAddScheduleDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_schedule, null)
        val editTextScheduleName = dialogView.findViewById<EditText>(R.id.editTextScheduleName)

        val builder = AlertDialog.Builder(this)
            .setTitle("Add New Schedule")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->
                val scheduleName = editTextScheduleName.text.toString().trim()
                if (scheduleName.isNotEmpty()) {
                    addNewScheduleCard(scheduleName)
                    val newSchedule = ScheduleModal(0, scheduleName, "", "", "", "")
                    dbHelper.addSchedule(newSchedule)
                    schedules.add(newSchedule)
                    Toast.makeText(this, "Schedule Added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please Enter a schedule name", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun addNewScheduleCard(scheduleName: String) {
        val cardView = layoutInflater.inflate(
            R.layout.item_schedule_card,
            binding.scheduleContainer,
            false
        ) as CardView
        val scheduleNameTextView = cardView.findViewById<TextView>(R.id.scheduleName)
        scheduleNameTextView.text = scheduleName
        binding.scheduleContainer.addView(cardView)
    }

    private fun showEditDescriptionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_description, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextDescription)
        editText.setText(binding.txtDecrapstion.text)

        val builder = AlertDialog.Builder(this)
            .setTitle("Edit Description")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newDescription = editText.text.toString().trim()
                if (newDescription.isNotEmpty()) {
                    binding.txtDecrapstion.text = newDescription
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun showEditFrequencyDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_frequency, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextFrequency)
        editText.setText(binding.txtDaily.text)

        val builder = AlertDialog.Builder(this)
            .setTitle("Edit Frequency")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newFrequency = editText.text.toString().trim()
                if (newFrequency.isNotEmpty()) {
                    binding.txtDaily.text = newFrequency
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun showEditIntakeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_intake, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextIntake)
        editText.setText(binding.txtTimes.text)

        val builder = AlertDialog.Builder(this)
            .setTitle("Edit Intake")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newIntake = editText.text.toString().trim()
                if (newIntake.isNotEmpty()) {
                    binding.txtTimes.text = newIntake
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun showEditRefillDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_refill, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextRefill)
        editText.setText(binding.txtStoke.text)

        val builder = AlertDialog.Builder(this)
            .setTitle("Edit Refill")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newRefill = editText.text.toString().trim()
                if (newRefill.isNotEmpty()) {
                    binding.txtStoke.text = newRefill
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun showEditDurationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_duration, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextDuration)
        editText.setText(binding.txtDays.text)

        val builder = AlertDialog.Builder(this)
            .setTitle("Edit Duration")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newDuration = editText.text.toString().trim()
                if (newDuration.isNotEmpty()) {
                    binding.txtDays.text = newDuration
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }
}
