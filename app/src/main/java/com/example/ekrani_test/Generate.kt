package com.example.ekrani_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.view.View
import com.example.ekrani_test.databinding.ActivityGenerateBinding
import kotlin.text.trim as trim1



class Generate : AppCompatActivity() {
    lateinit var bindingClass2: ActivityGenerateBinding
    internal var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate)
        bindingClass2 = ActivityGenerateBinding.inflate(layoutInflater)
        setContentView(bindingClass2.root)
    }
 fun OnClickBack(view: View){
     finish()
 }

    fun onClickGenerate(view:View){
        val editT1 = bindingClass2.editTextSN.text.toString()
        val editT2 = bindingClass2.editTextRef.text.toString()
        val editT3 = bindingClass2.editTextLot.text.toString()
        var editT4 = bindingClass2.editTextDate.text.toString()
        var editT5 = bindingClass2.editTextDate2.text.toString()

        val Per = bindingClass2.editTextSN.text.toString() +"|"+ bindingClass2.editTextLot.text.toString() +"|"+ bindingClass2.editTextRef.text.toString() +"|"+ bindingClass2.editTextDate.text.toString() +"|"+ bindingClass2.editTextDate2.text.toString()


        if (editT1.trim1 { it <= ' ' }.length == 0 || editT2.trim1 { it <= ' ' }.length == 0 || editT3.trim1 { it <= ' ' }.length == 0 || editT4.trim1 { it <= ' ' }.length == 0) {
            Toast.makeText(this@Generate, "Заполните основные поля", Toast.LENGTH_SHORT).show()
        } else {
            try {
                bitmap = TextToImageEncode(Per)
                bindingClass2.imageViewQrCode.setImageBitmap(bitmap)
                //give read write permission

            } catch (e: WriterException) {
                e.printStackTrace()
            }

        }

    }
    @Throws(WriterException::class)
    private fun TextToImageEncode(Value: String): Bitmap? {
        val bitMatrix: BitMatrix
        val QRcodeWidth = 500
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                QRcodeWidth, QRcodeWidth, null
            )

        } catch (Illegalargumentexception: IllegalArgumentException) {

            return null
        }

        val bitMatrixWidth = bitMatrix.getWidth()

        val bitMatrixHeight = bitMatrix.getHeight()

        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.black)
                else
                    resources.getColor(R.color.white)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }
}