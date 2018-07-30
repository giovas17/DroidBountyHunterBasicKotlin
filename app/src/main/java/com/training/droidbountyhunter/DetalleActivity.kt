package com.training.droidbountyhunter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import com.training.data.DatabaseBountyHunter
import com.training.models.Fugitivo
import com.training.network.NetworkServices
import com.training.network.onTaskListener
import com.training.utils.PictureTools
import kotlinx.android.synthetic.main.activity_detalle.*
import org.json.JSONObject


/**
 * @author Giovani Gonzalez
 * Created giovani on 7/5/18
 */
class DetalleActivity : AppCompatActivity(){

    private var UDID: String? = ""
    var fugitivo: Fugitivo? = null
    var database: DatabaseBountyHunter? = null
    private var direccionImagen: Uri? = null
    private val REQUEST_CODE_PHOTO_IMAGE = 1787

    override fun onCreate(savedInstanceState: Bundle?) {

        @SuppressLint("HardwareIds")
        UDID = Settings.Secure.getString(contentResolver,Settings.Secure.ANDROID_ID)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        fugitivo = intent.extras["fugitivo"] as Fugitivo
        // Se obtiene el nombre del fugitivo del objeto fugitivo y se usa como título
        title = fugitivo!!.name + " - " + fugitivo!!.id
        // Se identifica si es Fugitivo o capturado para el mensaje...
        if (fugitivo!!.status == 0){
            etiquetaMensaje.text = "El fugitivo sigue suelto..."
        }else{
            etiquetaMensaje.text = "Atrapado!!!"
            botonCapturar.visibility = GONE
            if (fugitivo!!.photo.isNotEmpty()){
                val bitmap = PictureTools.decodeSampledBitmapFromUri(fugitivo!!.photo,200,200)
                pictureFugitive.setImageBitmap(bitmap)
            }
        }
    }

    fun capturarFugitivoPresionado(view: View){
        database = DatabaseBountyHunter(this)
        fugitivo!!.status = 1
        if (fugitivo!!.photo.isEmpty()){
            Toast.makeText(this,
                    "Es necesario tomar la foto antes de capturar al fugitivo",
                    Toast.LENGTH_LONG).show()
            return
        }
        database!!.actualizarFugitivo(fugitivo!!)
        val services = NetworkServices(object: onTaskListener{
            override fun tareaCompletada(respuesta: String) {
                val obj = JSONObject(respuesta)
                val mensaje = obj.optString("mensaje","")
                mensajeDeCerrado(mensaje)
            }

            override fun tareaConError(codigo: Int, mensaje: String, error: String) {
                Toast.makeText(applicationContext,
                        "Ocurrio un problema en la comunicación con el WebService!!!",
                        Toast.LENGTH_LONG).show()
            }
        })
        services.execute("Atrapar",UDID)
        botonCapturar.visibility = GONE
        botonEliminar.visibility = GONE
        setResult(0)
    }

    fun eliminarFugitivoPresionado(view: View){
        database = DatabaseBountyHunter(this)
        database!!.borrarFugitivo(fugitivo!!)
        setResult(0)
        finish()
    }

    fun mensajeDeCerrado(mensaje: String){
        val builder = AlertDialog.Builder(this)
        builder.create()
        builder.setTitle("Alerta!!!")
                .setMessage(mensaje)
                .setOnDismissListener {
                    setResult(fugitivo!!.status)
                    finish()
                }.show()
    }

    fun OnFotoClick(view: View){
        if (PictureTools.permissionReadMemmory(this)){
            obtenFotoDeCamara()
        }
    }

    private fun obtenFotoDeCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        direccionImagen = PictureTools.getOutputMediaFileUri(this, MEDIA_TYPE_IMAGE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, direccionImagen)
        startActivityForResult(intent, REQUEST_CODE_PHOTO_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PHOTO_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                fugitivo!!.photo = PictureTools.currentPhotoPath
                val bitmap = PictureTools
                        .decodeSampledBitmapFromUri(PictureTools.currentPhotoPath, 200, 200)
                pictureFugitive.setImageBitmap(bitmap)
            }
        }
    }
}