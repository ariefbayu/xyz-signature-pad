package xyz.ariefbayu.android.signature_app

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signature.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class SignatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signature)

        signaturePad.setPenColor(Color.BLACK)

        clearButton.setOnClickListener {
            signaturePad.clearView()
        }
        saveButton.setOnClickListener {
            val signature = signaturePad.signatureBitmap;

            val savedPath = bitmapToFile(signature)
            Toast.makeText(this, "Signature saved into: " + savedPath.encodedPath, Toast.LENGTH_LONG).show()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun bitmapToFile(bitmap:Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images",Context.MODE_PRIVATE)
        file = File(file,"saved-signature.jpg")

        try{
            // Compress the bitmap and save in jpg format
            val stream:OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e:IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse(file.absolutePath)
    }
}
