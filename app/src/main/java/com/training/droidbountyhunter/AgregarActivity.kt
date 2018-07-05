package com.training.droidbountyhunter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author Giovani Gonzalez
 * Created giovani on 7/5/18
 */
class AgregarActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)
        title = "Nuevo Fugitivo"
    }
}

