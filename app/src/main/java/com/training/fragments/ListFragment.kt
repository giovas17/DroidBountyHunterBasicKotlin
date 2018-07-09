package com.training.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.training.droidbountyhunter.DetalleActivity
import com.training.droidbountyhunter.R
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * @author Giovani Gonzalez
 * Created giovani on 7/5/18
 */

const val SECTION_NUMBER : String = "section_number"

class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Se hace referencia al Fragment generado por XML en los Layouts y
        // se instancia en una View...
        return inflater.inflate(R.layout.fragment_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val modo = arguments!![SECTION_NUMBER] as Int
        // Datos dummy para la lista
        val dummyData = listOf(
                "Armando Olmos",
                "Guillermo Ortega",
                "Carlos Martinez",
                "Israel Ramirez",
                "Karen Muñoz",
                "Alejandro Rincon")
        val adaptador = ArrayAdapter<String>(context,R.layout.item_fugitivo_list,dummyData)
        listaFugitivosCapturados.adapter = adaptador
        listaFugitivosCapturados.setOnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(context, DetalleActivity::class.java)
            intent.putExtra("titulo",(view as TextView).text)
            intent.putExtra("modo", modo)
            startActivity(intent)
        }
    }

}