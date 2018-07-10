package com.training.droidbountyhunter

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.training.data.DatabaseBountyHunter
import com.training.models.Fugitivo
import kotlinx.android.synthetic.main.activity_agregar.*

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

    fun guardarFugitivoPresionado(view: View){
        val nombre = nombreFugitivoTextView.text.toString()
        if (nombre.isNotEmpty()){
            val database = DatabaseBountyHunter(this)
            database.insertarFugitivo(Fugitivo(0,nombre,0))
            setResult(0)
            finish()
        }else{
            AlertDialog.Builder(this)
                    .setTitle("Alerta")
                    .setMessage("Favor de capturar el nombre del fugitivo.")
                    .show()
        }
    }
}

