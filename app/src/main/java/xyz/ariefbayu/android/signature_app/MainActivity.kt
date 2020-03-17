package xyz.ariefbayu.android.signature_app

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_SIGNATURE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signButton.setOnClickListener {
            val intent = Intent(applicationContext, SignatureActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SIGNATURE);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SIGNATURE && resultCode == Activity.RESULT_OK){
            val wrapper = ContextWrapper(applicationContext)

            //load the signature into imageview
            var file = wrapper.getDir("Images",Context.MODE_PRIVATE)
            file = File(file,"saved-signature.jpg")
            signature.setImageBitmap(fileToBitmap(Uri.fromFile(file)))
        }
    }

    private fun fileToBitmap(uri: Uri): Bitmap? {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        try{
            // Compress the bitmap and save in jpg format
            return BitmapFactory.decodeFile(uri.path)
        }catch (e: IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return null
    }
}
