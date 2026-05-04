package com.example.guessnumber


import android.app.ComponentCaller
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.guessnumber.databinding.ActivityMainBinding
import kotlin.properties.Delegates
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
companion object{
    val SETTINGS_RESULT = 1
}

    private lateinit var binding: ActivityMainBinding
    private var min = 1
    private var max = 20
    private var n = -1
    private var attmax = 10
    private var att = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        val view = binding.root
        setContentView(view)



        startGame()


        binding.buttCheck.setOnClickListener {
            if(att > 0 && binding.numberEnter.text.toString() != "" && binding.textRes.text != "Вы победили!)") {
                checkNum()
            }
        }

        binding.buttGameAgain.setOnClickListener {
            startGame()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        Log.d("ZZZ", "normaldi")
        super.onActivityResult(requestCode, resultCode, data, caller)

        if (resultCode==RESULT_OK){

            val att=data?.getIntExtra("Attempts", 10)?:10
            val max=data?.getIntExtra("MaxNum", 20)?:20
            SetSettings(att, max)
            startGame()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.xxx == item.itemId){
            val intent = Intent(this, Settings::class.java)
            intent.putExtra("Attempts", attmax)
            intent.putExtra("MaxNum", max)

            startActivityForResult(intent, SETTINGS_RESULT)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun SetSettings(attSet: Int, numSet: Int){
        attmax = attSet
        max = numSet
    }
    private fun generateNumber(): Int{
        return Random.nextInt(min, max)
    }

    private fun startGame(){
        binding.image.visibility = View.INVISIBLE
        binding.buttGameAgain.visibility = View.INVISIBLE
        n = generateNumber()
        binding.textRes.setText("")
        att = attmax
        binding.numberEnter.setHint("Введите число от $min до $max")
        binding.attText.setText("Осталось попыток: $att")
    }

    private fun checkNum(){
        if (binding.numberEnter.text.toString().toInt() == n){
            win()
        }
        else if(binding.numberEnter.text.toString().toInt() < n){
            binding.textRes.setText("Нужно число больше")
        }
        else{
            binding.textRes.setText("Нужно число меньше")
        }
        att--
        binding.attText.setText("Осталось попыток: $att")
        binding.numberEnter.setText("")

        if(att <= 0){
            lose()
        }
    }

    private fun win(){
        binding.image.visibility = View.VISIBLE
        binding.buttGameAgain.visibility = View.VISIBLE
        binding.textRes.setText("Вы победили!)")
        binding.image.setImageResource(R.drawable.win)
    }

    private fun lose(){
        binding.image.visibility = View.VISIBLE
        binding.buttGameAgain.visibility = View.VISIBLE
        binding.textRes.setText("Вы проиграли!(")
        binding.image.setImageResource(R.drawable.lose)
    }

}