package com.training.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.training.droidbountyhunter.R
import com.training.models.Fugitivo
import com.training.utils.PictureTools

/**
 * @author Giovani Gonzalez
 * Created giovani on 8/20/18
 */
class FugitivoAdapter(context: Context, var datos: Array<Fugitivo>) : ArrayAdapter<Fugitivo>(context, R.layout.item_fugitivo_list,datos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        view = if(convertView == null) {
            val inflater = LayoutInflater.from(context)
            inflater.inflate(R.layout.item_fugitivo_list,parent,false)
        }else{
            convertView
        }

        val fugitivo = datos[position]

        val pictureView = view!!.findViewById<ImageView>(R.id.imagenFugitivo)
        if (fugitivo.photo.isNotEmpty()) {
            pictureView.setImageBitmap(PictureTools.decodeSampledBitmapFromUri(fugitivo.photo, 200, 200))
        }

        val nameView = view.findViewById<TextView>(R.id.nombreFugitivoTextView)
        nameView.text = fugitivo.name

        val dateView = view.findViewById<TextView>(R.id.fechaDeCaptura)
        dateView.text = fugitivo.date

        return view
    }

    override fun getCount(): Int {
        return datos.size
    }
}