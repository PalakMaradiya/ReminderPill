package com.example.reminderpill.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.reminderpill.R
import com.example.reminderpill.adapter.RefillAdapter
import com.example.reminderpill.databinding.ActivityRefillBinding
import com.example.reminderpill.utils.RefillModal

class RefillActivity : AppCompatActivity() {

    lateinit var list : ArrayList<RefillModal>
    lateinit var refillAdapter: RefillAdapter
    lateinit var manager: GridLayoutManager
    lateinit var refillModal: RefillModal
    lateinit var binding: ActivityRefillBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRefillBinding.inflate(layoutInflater)
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


        list = ArrayList()

        refillAdapter = RefillAdapter(this@RefillActivity, list)
        manager = GridLayoutManager(this@RefillActivity,1,GridLayoutManager.VERTICAL,false)
        binding.rcvRefill.adapter = refillAdapter
        binding.rcvRefill.layoutManager = manager
        refillAdapter.notifyDataSetChanged()
        preparePaintings()

    }

    private fun preparePaintings() {

        refillModal = RefillModal("Amphotericin B", R.drawable.ic_medi , "21 Stoke")
        list.add(refillModal)

        refillModal = RefillModal("Amphotericin B", R.drawable.ic_syrup , "20 Stoke")
        list.add(refillModal)

        refillModal = RefillModal("Amphotericin B", R.drawable.ic_dropper , "18 Stoke")
        list.add(refillModal)

        refillModal = RefillModal("Amphotericin B", R.drawable.ic_tablets , "20 Stoke")
        list.add(refillModal)


        refillModal = RefillModal("Amphotericin B", R.drawable.ic_ampoule , "18 Stoke")
        list.add(refillModal)
    }
}