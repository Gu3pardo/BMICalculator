package guepardoapps.bmicalculator.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import com.jjoe64.graphview.GraphView
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

fun GraphView.takeSnapshot(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}

fun GraphView.takeSnapshotAndShare(context: Context, imageName: String, title: String) {
    val bytes = ByteArrayOutputStream()
    val image = takeSnapshot()
    image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

    val path = MediaStore.Images.Media.insertImage(context.contentResolver, image, imageName, null)
            ?: throw SecurityException("Could not get path from MediaStore. Please check permissions.")

    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "image/*"
    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path))

    try {
        context.startActivity(Intent.createChooser(intent, title))
    } catch (ex: android.content.ActivityNotFoundException) {
        ex.printStackTrace()
    }
}