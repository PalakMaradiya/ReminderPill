package com.example.reminderpill.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.example.reminderpill.R
import com.example.reminderpill.adapter.SliderAdapter
import com.example.reminderpill.databinding.ActivityFirstIntroScreenBinding
import com.example.reminderpill.utils.PrefManager
import com.example.reminderpill.utils.SliderData

class FirstIntroScreenActivity : AppCompatActivity() {

    lateinit var sliderAdapter: SliderAdapter
    lateinit var sliderList: ArrayList<SliderData>
    lateinit var binding: ActivityFirstIntroScreenBinding
    lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prefManager = PrefManager(this)


        binding = ActivityFirstIntroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        initView()
    }

    private fun launchLoginScreen() {
        startActivity(Intent(this, LogInScreenActivity::class.java))
        finish()
        prefManager.setFirstTimeLaunch(false)
    }

    private fun initView() {


        if(prefManager.isLogIn()!!){

                val intent = Intent(this@FirstIntroScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            prefManager.setFirstTimeLaunch(false)

        }


        binding.btnSkip.setOnClickListener {
            launchLoginScreen()
        }
        sliderList = ArrayList()

        sliderList.add(
            SliderData(
                "it's very difficult to take care of ownself , \n find your virtual nurse",
                "Your Virtual Nurse",
                R.drawable.illustration
            )
        )

        sliderList.add(
            SliderData(
                "we will always remind your medication \n schedule, It is now our responsibility.",
                "Health Reminder",
                R.drawable.illustration_2
            )
        )


        sliderList.add(
            SliderData(
                "we are not your regular doctor but we are\n more than a doctor.",
                "Your Caring Partners",
                R.drawable.illustration_03
            )
        )

        sliderAdapter = SliderAdapter(this, sliderList)
        binding.viewPager.adapter = sliderAdapter
        binding.viewPager.addOnPageChangeListener(viewListener)

        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < sliderList.size - 1) {
                binding.viewPager.currentItem = currentItem + 1

            }

        }
    }

    var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {

            if (position == 0) {
                binding.btnSkip.visibility = View.VISIBLE
                binding.btnLetsGo.visibility = View.GONE
                binding.btnNext.visibility = View.VISIBLE
                binding.idTVSlideOne.setTextColor(resources.getColor(R.color.blue))
                binding.idTVSlideTwo.setTextColor(resources.getColor(R.color.grey))
                binding.idTVSlideThree.setTextColor(resources.getColor(R.color.grey))

            } else if (position == 1) {

                binding.btnSkip.visibility = View.VISIBLE
                binding.btnLetsGo.visibility = View.GONE
                binding.btnNext.visibility = View.VISIBLE
                binding.idTVSlideOne.setTextColor(resources.getColor(R.color.grey))
                binding.idTVSlideTwo.setTextColor(resources.getColor(R.color.blue))
                binding.idTVSlideThree.setTextColor(resources.getColor(R.color.grey))



                binding.btnSkip.setOnClickListener {
                    val intent =
                        Intent(this@FirstIntroScreenActivity, LogInScreenActivity::class.java)
                    startActivity(intent)
                    prefManager.setFirstTimeLaunch(false)
                }
            } else {

                binding.btnNext.visibility = View.GONE
                binding.btnLetsGo.visibility = View.VISIBLE
                binding.btnSkip.visibility = View.GONE
                binding.idTVSlideOne.setTextColor(resources.getColor(R.color.grey))
                binding.idTVSlideTwo.setTextColor(resources.getColor(R.color.grey))
                binding.idTVSlideThree.setTextColor(resources.getColor(R.color.blue))


                binding.btnLetsGo.setOnClickListener {
                    val intent =
                        Intent(this@FirstIntroScreenActivity, LogInScreenActivity::class.java)
                    startActivity(intent)
                    prefManager.setFirstTimeLaunch(false)
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }
}