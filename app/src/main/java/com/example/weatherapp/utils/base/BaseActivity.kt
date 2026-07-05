package com.example.weatherapp.utils.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.utils.MyApp
import io.github.inflationx.viewpump.ViewPumpContextWrapper

open class BaseActivity : AppCompatActivity() {
    //Calligraphy
    override fun attachBaseContext(newBase: Context?) {
        val app = newBase?.applicationContext as MyApp
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase, app.viewPump))
    }
}