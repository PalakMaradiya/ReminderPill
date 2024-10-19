package com.example.reminderpill.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminderpill.R
import com.example.reminderpill.adapter.MedicineAdapter
import com.example.reminderpill.databinding.FragmentActiveBinding // Adjust the import to match your binding class
import com.example.reminderpill.utils.MedicineModal

class ActiveFragment : Fragment() {

    private var _binding: FragmentActiveBinding? = null
    private val binding get() = _binding!!

    lateinit var medicineAdapter: MedicineAdapter


    lateinit var medicineList: ArrayList<MedicineModal>
    lateinit var  manager : LinearLayoutManager
    lateinit var medicineModal: MedicineModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActiveBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

        medicineList = ArrayList()


        medicineAdapter = MedicineAdapter(requireContext(),medicineList , onItemClick = {
            // Handle item click event here
        })
        manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvActive.adapter = medicineAdapter
        binding.rcvActive.layoutManager = manager
        preparePaintings()

    }

    private fun preparePaintings() {


        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_medi)
        medicineList.add(medicineModal)

        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_syrup)
        medicineList.add(medicineModal)

        medicineModal = MedicineModal("Amphotericin B", R.drawable.ic_dropper)
        medicineList.add(medicineModal)

        medicineAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
