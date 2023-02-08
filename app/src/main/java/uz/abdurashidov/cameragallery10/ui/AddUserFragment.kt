package uz.abdurashidov.cameragallery10.ui

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import uz.abdurashidov.cameragallery10.BuildConfig
import uz.abdurashidov.cameragallery10.databinding.FragmentAddUserBinding
import uz.abdurashidov.cameragallery10.db.MyDbHelper
import uz.abdurashidov.cameragallery10.models.User
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddUserFragment : Fragment() {

    private val binding by lazy { FragmentAddUserBinding.inflate(layoutInflater) }
    lateinit var photoUri:Uri
    lateinit var currentImagePath: String
    lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        myDbHelper= MyDbHelper(binding.root.context)

        binding.apply {
            image.setOnClickListener {
                val imageFile = createImageFile()
                photoUri = FileProvider.getUriForFile(
                    binding.root.context,
                    BuildConfig.APPLICATION_ID,
                    imageFile
                )
                println("mageFile: $imageFile, photoUri = $photoUri")
                getTakeImageContent.launch(photoUri)

            }

            save.setOnClickListener {
                val user=User(
                    name = binding.name.text.toString(),
                    surname = binding.surname.text.toString(),
                    image = photoUri.toString()
                )
                myDbHelper.addUser(user)
            }
        }

        return binding.root
    }


    private val getTakeImageContent =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            println("getTakeImageContent ishlayapti")
            if (it) {
                println(photoUri)
                binding.image.setImageURI(photoUri)
                val inputStream = requireActivity().contentResolver?.openInputStream(photoUri)
                val file = File(requireActivity().filesDir, "image.jpg")
                val fileOutputStream = FileOutputStream(file)
                inputStream?.copyTo(fileOutputStream)
                inputStream?.close()
                fileOutputStream.close()
                val absolutePath = file.absolutePath
                Toast.makeText(binding.root.context, "$absolutePath", Toast.LENGTH_SHORT).show()
            }
        }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val externalFilesDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        println("createImageFile ishlayapti")
        return File.createTempFile("JPEG_$format", ".jpg", externalFilesDir).apply {
            currentImagePath = absolutePath
        }
    }

}