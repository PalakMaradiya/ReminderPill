package com.example.reminderpill.fragement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminderpill.R
import com.example.reminderpill.adapter.MedicineAdapter
import com.example.reminderpill.databinding.FragmentDeactiveBinding
import com.example.reminderpill.utils.MedicineModal

class DeactiveFragment : Fragment() {

    private var _binding: FragmentDeactiveBinding? = null
    private val binding get() = _binding!!


    lateinit var medicineAdapter: MedicineAdapter


    lateinit var medicineList: ArrayList<MedicineModal>
    lateinit var  manager : LinearLayoutManager
    lateinit var medicineModal: MedicineModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeactiveBinding.inflate(inflater, container, false)
        initView()
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {

        medicineList = ArrayList()


        medicineAdapter = MedicineAdapter(requireContext(),medicineList , onItemClick = {
            // Handle item click event here
        })
        manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvDeActive.adapter = medicineAdapter
        binding.rcvDeActive.layoutManager = manager
        preparePaintings()

    }

    private fun preparePaintings() {


        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_tablets)
        medicineList.add(medicineModal)


        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_ampoule)
        medicineList.add(medicineModal)

        medicineAdapter.notifyDataSetChanged()
    }




}