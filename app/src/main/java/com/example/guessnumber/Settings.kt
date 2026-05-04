package com.example.guessnumber

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.guessnumber.databinding.ActivityMainBinding
import com.example.guessnumber.databinding.ActivitySettingsBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivitySettingsBinding

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val args = intent.extras
        var max = args?.getInt("MaxNum")?:20
        var attmax = args?.getInt("Attempts")?:10

        updateText(attmax, max)

        binding.saveButton.setOnClickListener {
            if (binding.maxNumber.text.toString() != "" && binding.attemptNumber.text.toString() != "") {
                max = binding.maxNumber.text.toString().toInt()
                attmax = binding.attemptNumber.text.toString().toInt()

                val intent = Intent()
                intent.putExtra("Attempts", attmax)
                intent.putExtra("MaxNum", max)
                setResult(RESULT_OK, intent)
                finish()
            }
            else{
                Toast.makeText(this, "Не оставляйте поля пустыми!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun updateText(maxatt: Int, maxNum: Int){
        binding.attemptNumber.setText(maxatt.toString())
        binding.maxNumber.setText(maxNum.toString())
    }


}