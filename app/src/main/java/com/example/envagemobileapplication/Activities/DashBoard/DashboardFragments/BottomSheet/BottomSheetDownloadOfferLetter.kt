package com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.databinding.BottomSheetDownloadOfferLetterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.net.URLEncoder

class BottomSheetDownloadOfferLetter : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetDownloadOfferLetterBinding
    private val GALLERY_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 102


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetDownloadOfferLetterBinding.inflate(inflater, container, false)


        clickListeners()

        return binding.root

    }

    private fun clickListeners() {
        binding.downloadOfferLetter.setOnClickListener {
            try {
                var extraDocument =
                    Constants.offerLetterUrl
                val encodedFileName = URLEncoder.encode(extraDocument.toString(), "UTF-8")
                val fileUrl =
                    "https://staginggateway.enwage.com/api/v1/AzureStorage/download?filename=$encodedFileName"

                Log.i("fileurllatest", fileUrl)
                downloadFile(requireContext(), fileUrl)
            } catch (e: Exception) {
            }
        }

    }

    fun downloadFile(context: Context, fileUrl: String) {
        try {
            // Extract the file name from the file URL
            var fileName = Uri.parse(fileUrl).lastPathSegment ?: "file"
            var uniqueFileName = "${fileName}_${System.currentTimeMillis()}"
            Log.i("uniquefilename", uniqueFileName)
            if (!uniqueFileName.contains(".")) {
                if (fileUrl.contains(".pdf")) {
                    uniqueFileName += ".pdf"
                } else if (fileUrl.contains(".docx")) {
                    uniqueFileName += ".docx"
                } else {
                    uniqueFileName += ".doc"
                }
            }
            // Create a request for the DownloadManager
            val request = DownloadManager.Request(Uri.parse(fileUrl))
                .setTitle(uniqueFileName)
                .setDescription("Downloading file")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uniqueFileName)

            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = manager.enqueue(request)

            /*  val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
              val receiver = object : BroadcastReceiver() {
                  override fun onReceive(context: Context?, intent: Intent?) {
                      val downloadedId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                      if (downloadedId == downloadId) {
                          // Handle the downloaded file here
                       //   handleDownloadedFile(context, uniqueFileName)
                      }
                  }
              }
              context.registerReceiver(receiver, filter)*/
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle any exceptions or log messages
            // ...
        }
    }

    private fun checkPermissions(): Boolean {
        val cameraPermission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val storagePermission =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        return cameraPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val cameraPermission = Manifest.permission.CAMERA
        val storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                cameraPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(cameraPermission)
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                storagePermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(storagePermission)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsToRequest.toTypedArray(),
                CAMERA_REQUEST_CODE
            )
        } else {
            clickListeners()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                clickListeners()
            } else {

                // Handle the case where permissions are not granted
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    // Handle the selected image from the gallery
                    val selectedImageUri: Uri? = data?.data
                    // Use the selectedImageUri as needed
                }
                CAMERA_REQUEST_CODE -> {
                    // Handle the captured image from the camera
                    // The image is usually available in the data parameter of the intent.
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    // Use the imageBitmap as needed
                }
            }
        }
    }


    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }


}