package com.example.envagemobileapplication.Activities.DashBoard.SettingsDetails

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.envagemobileapplication.Activities.DashBoard.MainActivity
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.BuildConfig
import com.example.envagemobileapplication.Models.RequestModels.UpdateStatusPayload
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GtLogdinUserDetailsRsp.GetLoggedinUserDetails
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateJobsStatusResponse.UpdateJobsStatusResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateProfileResultResponse.UpdateProfileResultResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.ActivityPersonalInformationBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PersonalInformationActivity : AppCompatActivity() {
    lateinit var bodyofpf: MultipartBody.Part
    lateinit var loader: Loader
    private val GALLERY_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    lateinit var currentPhotoPath: String
    lateinit var imagefilforapi: File
    lateinit var payloadList: MutableList<UpdateStatusPayload>
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var binding: ActivityPersonalInformationBinding
    lateinit var genderlist: ArrayList<String>
    private val MULTIPLE_PERMISSIONS = 10
    var permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonalInformationBinding.inflate(layoutInflater)

        initViews()
        clickListeners()
        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {

                    binding.cvCameragallery.visibility = View.GONE
                    val selectedImageUri: Uri? = data?.data
                    val contentResolver = this.contentResolver
                    val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                    val fileSize = inputStream?.available() ?: 0

                    if (fileSize > 5 * 1024 * 1024) { // 5 MB in bytes
                        // Show an error message
                        Toast.makeText(
                            this,
                            "Please upload an image in defined limit (5 MB).",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val uri: Uri? =
                            selectedImageUri

                        val imageFile: File? = uriToFile(contentResolver, uri!!)
                        Log.i("imagefile", imageFile.toString())
                        imagefilforapi = imageFile!!
                        Picasso.get().load(selectedImageUri).into(binding.ivProfilePic)
                        val requestBody =
                            imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "imagePath",
                                imageFile.name,
                                requestBody
                            )

                        UpdateProfilePic(bodyofpf)
                    }


                }

                CAMERA_REQUEST_CODE -> {

                    val uri = Uri.parse(currentPhotoPath)
                    CropImage.activity(uri)
                        .setRequestedSize(512, 512)
                        .start(this)

                    val imageFile: File? = File(currentPhotoPath)
                    Log.i("imagefile", imageFile.toString())
                    imagefilforapi = imageFile!!
                    Picasso.get().load(uri).into(binding.ivProfilePic)
                    val requestBody =
                        imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    bodyofpf =
                        MultipartBody.Part.createFormData(
                            "imagePath",
                            imageFile.name,
                            requestBody
                        )
                    binding.cvCameragallery.visibility = View.GONE

                    UpdateProfilePic(bodyofpf)

                }

                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    loader.show()
                    val result = CropImage.getActivityResult(data)
                    if (resultCode == Activity.RESULT_OK) {
                        val resultUri = result.uri
                        val file = resultUri.path
                        // mChatListner.sendImageMessage(file!!)

                        Glide.with(this)
                            .load(resultUri)
                            .circleCrop()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(binding.ivProfilePic)

                        val imagePath = File(file)


                        imagefilforapi = imagePath

                        val requestBody =
                            imagefilforapi
                                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "profileImage",
                                imagefilforapi.name,
                                requestBody
                            )


                        binding.cvCameragallery.visibility = View.GONE
                        val delayMillis = 3000L // Delay between transitions in milliseconds
                        val handler = Handler()
                        handler.postDelayed({
                            UpdateProfilePic(bodyofpf)
                        }, delayMillis)


                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        result.error.printStackTrace()
                    }

                }


            }
        }
    }

    private fun clickListeners() {
        binding.spinnergender.setOnClickListener {
            if (binding.spinnergender.isPopupShowing) {
                binding.spinnergender.dismissDropDown()
            } else {
                binding.spinnergender.requestFocus()
                binding.spinnergender.showDropDown()
            }
        }
        binding.updateRecord.setOnClickListener {
            callUpdateClientApi()
        }
        binding.ivCamera.setOnClickListener {
            if (checkPermissions()) {
                binding.cvCameragallery.visibility = View.VISIBLE
            }
        }
        binding.cvTakePhoto.setOnClickListener {
            openCamera()
            binding.cvGallery.setBackgroundResource(com.example.envagemobileapplication.R.drawable.searchbg)
            binding.cvGallery.setBackgroundColor(resources.getColor(com.example.envagemobileapplication.R.color.white))
            binding.tvGallery.setTextColor(resources.getColor(com.example.envagemobileapplication.R.color.black))
            val drawableLeft: Drawable? =
                ContextCompat.getDrawable(
                    this,
                    com.example.envagemobileapplication.R.drawable.ic_select_from_gallery
                )
            binding.tvGallery.setCompoundDrawablesWithIntrinsicBounds(
                drawableLeft,
                null,
                null,
                null
            )
            binding.cvTakePhoto.setBackgroundResource(com.example.envagemobileapplication.R.drawable.btn_black_radius)
            binding.tvCamera.setTextColor(resources.getColor(com.example.envagemobileapplication.R.color.white))
            val drawable = ContextCompat.getDrawable(
                this,
                com.example.envagemobileapplication.R.drawable.ic_select_camera
            )

            // Set the color you want for the SVG drawable
            val color =
                ContextCompat.getColor(this, com.example.envagemobileapplication.R.color.white)

            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, color)
                binding.tvCamera.setCompoundDrawablesWithIntrinsicBounds(
                    wrappedDrawable,
                    null,
                    null,
                    null
                )
            }


        }
        binding.cvGallery.setOnClickListener {

            openGallery()
            binding.cvTakePhoto.setBackgroundResource(com.example.envagemobileapplication.R.drawable.searchbg)
            binding.cvTakePhoto.setBackgroundColor(resources.getColor(com.example.envagemobileapplication.R.color.white))
            binding.tvCamera.setTextColor(resources.getColor(com.example.envagemobileapplication.R.color.black))
            val drawableLeft: Drawable? =
                ContextCompat.getDrawable(
                    this,
                    com.example.envagemobileapplication.R.drawable.ic_select_camera
                )
            binding.tvCamera.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)

            binding.cvGallery.setBackgroundResource(com.example.envagemobileapplication.R.drawable.btn_black_radius)

            binding.tvGallery.setTextColor(resources.getColor(com.example.envagemobileapplication.R.color.white))
            val drawable =
                ContextCompat.getDrawable(
                    this,
                    com.example.envagemobileapplication.R.drawable.ic_select_from_gallery
                )
            ContextCompat.getDrawable(
                this,
                com.example.envagemobileapplication.R.drawable.ic_select_from_gallery
            )

            // Set the color you want for the SVG drawable
            val color =
                ContextCompat.getColor(this, com.example.envagemobileapplication.R.color.white)

            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, color)
                binding.tvGallery.setCompoundDrawablesWithIntrinsicBounds(
                    wrappedDrawable,
                    null,
                    null,
                    null
                )
            }
        }
        binding.ivCross.setOnClickListener {
            global.showDialog(this, this)
        }
        binding.ivCancel.setOnClickListener {
            global.showDialog(this, this)
        }
    }

    private fun initViews() {
        tokenManager = TokenManager(this)
        loader = Loader(this)
        payloadList = ArrayList()
        genderlist = ArrayList()
        genderlist.add("Male")
        genderlist.add("Female")
        genderlist.add("Other")

        if (global.loggedinuserDetails != null) {
            if (global.loggedinuserDetails!!.firstName != null) {
                binding.etFirstname.setText(global.loggedinuserDetails!!.firstName)
            }
            if (global.loggedinuserDetails!!.lastName != null) {
                binding.etLastname.setText(global.loggedinuserDetails!!.lastName)
            }
            if (global.loggedinuserDetails!!.email != null) {
                binding.etEmail.setText(global.loggedinuserDetails!!.email)
            }

            if (global.loggedinuserDetails!!.contact != null) {
                binding.etPhone.setText(global.loggedinuserDetails!!.contact)
            }
            if (global.loggedinuserDetails!!.userName != null) {
                binding.etUsername.setText(global.loggedinuserDetails!!.userName)
            }
            if (global.loggedinuserDetails!!.gender != null) {
                binding.spinnergender.setText(global.loggedinuserDetails!!.gender)
            }
            if (global.loggedinuserDetails!!.department.departmentName != null) {
                binding.etDepartment.setText(global.loggedinuserDetails!!.department.departmentName)
            }
            if (global.loggedinuserDetails!!.designation.designationName != null) {
                binding.etDesignation.setText(global.loggedinuserDetails!!.designation.designationName)
            }
            if (global.loggedinuserDetails!!.address != null) {
                binding.etAddress.setText(global.loggedinuserDetails!!.address)
            }
            if (global.loggedinuserDetails!!.roleName != null) {
                binding.etRole.setText(global.loggedinuserDetails!!.roleName)
            }
            if (global.loggedinuserDetails!!.imagePath != null) {
                try {
                    Picasso.get().load(global.loggedinuserDetails!!.imagePath)
                        .into(binding.ivProfilePic)
                }
                catch (e:Exception){

                }

            }

        }

        val adapter = customadapter(
            this,
            R.layout.simple_spinner_item,
            genderlist
        )
        binding.spinnergender.setAdapter(adapter)



        binding.TIfirstname.isEnabled = false
        binding.TIlastname.isEnabled = false
        binding.TIemailAdress.isEnabled = false
        binding.Tiusername.isEnabled = false
        binding.TIDepartment.isEnabled = false
        binding.TIDesignation.isEnabled = false
        binding.TIRole.isEnabled = false
    }

    private fun callUpdateClientApi() {
        try {

            loader.show()
            val phone = UpdateStatusPayload("add", "/contact", binding.etPhone.text.toString())
            val gender =
                UpdateStatusPayload("add", "/gender", binding.spinnergender.text.toString())
            val address = UpdateStatusPayload("add", "/address", binding.etAddress.text.toString())
            payloadList.add(phone)
            payloadList.add(gender)
            payloadList.add(address)


            var tokenmanager: TokenManager = TokenManager(this)
            var token = tokenmanager.getAccessToken()
            var clientid = global.loggedinuserDetails!!.userId

            ApiUtils.getAPIService(this).updateUserProfile(
                token.toString(), payloadList, clientid!!
            )
                .enqueue(object : Callback<UpdateJobsStatusResponse> {
                    override fun onResponse(
                        call: Call<UpdateJobsStatusResponse>,
                        response: Response<UpdateJobsStatusResponse>
                    ) {
                        if (response.body() != null) {

                            loader.hide()

                            if (response.body()!!.data.alreadyExist.equals(true)) {

                                Snackbar.make(
                                    binding.root,
                                    "Client already exists.",
                                    Snackbar.LENGTH_LONG
                                ).apply {

                                    setTextColor(
                                        ContextCompat.getColor(
                                            this@PersonalInformationActivity,
                                            com.example.envagemobileapplication.R.color.white
                                        )
                                    )
                                    setBackgroundTint(
                                        ContextCompat.getColor(
                                            this@PersonalInformationActivity,
                                            com.example.envagemobileapplication.R.color.orange
                                        )
                                    )
                                    show() // Show the Snackbar
                                }
                            } else {
                                getLoggedinUserDetils()

                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<UpdateJobsStatusResponse>,
                        t: Throwable
                    ) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    fun getLoggedinUserDetils() {

        try {
            var tokenmanager: TokenManager = TokenManager(this)
            var token = tokenmanager.getAccessToken()
            ApiUtils.getAPIService(this).GetLoggedInUserDetails(
                token.toString(),
            )
                .enqueue(object : Callback<GetLoggedinUserDetails> {
                    override fun onResponse(
                        call: Call<GetLoggedinUserDetails>,
                        response: Response<GetLoggedinUserDetails>
                    ) {
                        if (response.body() != null) {
                            global.loggedinuserDetails = response.body()!!.data
                            val toast = Toast.makeText(
                                this@PersonalInformationActivity,
                                "Client information has been updated successfully",
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                        }
                    }

                    override fun onFailure(
                        call: Call<GetLoggedinUserDetails>,
                        t: Throwable
                    ) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun checkPermissions(): Boolean {

        if (Build.VERSION.SDK_INT >= 33) {
            permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        }

        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }

        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    private fun openCamera() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = getImageFile()
        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            ) else Uri.fromFile(file)
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            openCameraActivityResultLauncher.launch(pictureIntent)
        } else {
            startActivityForResult(pictureIntent, CAMERA_REQUEST_CODE)
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        galleryIntent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/jpg", "image/png")
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)

    }

    private fun getImageFile(): File {
        val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"
        val storageDir: File = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            this.filesDir
        } else {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "Camera"
            )
        }
        val file = File.createTempFile(
            imageFileName, ".jpg", storageDir
        )
        currentPhotoPath = "file:" + file.absolutePath
        currentPhotoPath = "file:" + file.absolutePath
        return file
    }

    fun getStreamByteFromImage(imageFile: File): ByteArray? {
        var photoBitmap = BitmapFactory.decodeFile(imageFile.path)
        val stream = ByteArrayOutputStream()
        val imageRotation = getImageRotation(imageFile)
        if (imageRotation != 0) photoBitmap = getBitmapRotatedByDegree(photoBitmap, imageRotation)
        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        return stream.toByteArray()
    }

    fun createTempImageFile(imageByteArray: ByteArray): File {
        val tempFile = File.createTempFile("temp_image", ".jpg", this.cacheDir)
        try {
            val fileOutputStream = FileOutputStream(tempFile)
            fileOutputStream.write(imageByteArray)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tempFile
    }

    private fun getImageRotation(imageFile: File): Int {
        var exif: ExifInterface? = null
        var exifRotation = 0
        try {
            exif = ExifInterface(imageFile.path)
            exifRotation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return if (exif == null) 0 else exifToDegrees(exifRotation)
    }

    private fun getBitmapRotatedByDegree(bitmap: Bitmap, rotationDegree: Int): Bitmap {
        val matrix = Matrix()
        matrix.preRotate(rotationDegree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun exifToDegrees(rotation: Int): Int {
        if (rotation == ExifInterface.ORIENTATION_ROTATE_90) return 90 else if (rotation == ExifInterface.ORIENTATION_ROTATE_180) return 180 else if (rotation == ExifInterface.ORIENTATION_ROTATE_270) return 270
        return 0
    }

    private fun UpdateProfilePic(bodyofpf: MultipartBody.Part) {
        loader.show()

        if (imagefilforapi != null) {
            var tokenmanager: TokenManager = TokenManager(this)
            var token = tokenmanager.getAccessToken()
            var clientID = global.loggedinuserDetails!!.userId

            try {


                ApiUtils.getAPIService(this).UpdateUserProfilepic(
                    token.toString(), clientID!!, bodyofpf
                )
                    .enqueue(object : Callback<UpdateProfileResultResponse> {
                        override fun onResponse(
                            call: Call<UpdateProfileResultResponse>,
                            response: Response<UpdateProfileResultResponse>
                        ) {
                            if (response.body() != null) {

                                loader.hide()
                                //      getClientHeaderSummary()
                                Toast.makeText(
                                    this@PersonalInformationActivity,
                                    "Profile Image updated successfully",
                                    Toast.LENGTH_LONG
                                )
                                    .show()

                                tokenManager.saveProfiepic(response.body()!!.data.blob.uri.toString())
                                try {
                                    Picasso.get().load(tokenManager.getProfilePic())
                                        .into(MainActivity.binding.leftDrawerMenu.ivProfile)
                                } catch (e: Exception) {
                                }
                                binding.cvCameragallery.visibility = View.GONE
                            }
                        }

                        override fun onFailure(
                            call: Call<UpdateProfileResultResponse>,
                            t: Throwable
                        ) {
                            loader.hide()
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                loader.hide()
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        } else {
            loader.hide()
            Toast.makeText(this, "imageofpf is null", Toast.LENGTH_LONG).show()
        }
        /*   var name = "testtttxffdsfdsfdsfdsffdst"
           var websiteurl = "www.url.com"
           var description = stringToBinary("testing userrr")*/


    }

    @SuppressLint("Range")
    fun uriToFile(contentResolver: ContentResolver, uri: Uri): File? {
        try {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                it.moveToFirst()
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val inputStream = contentResolver.openInputStream(uri)
                val file = File(Constants.context!!.cacheDir, displayName)

                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                    return file
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        return null
    }

    var openCameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val uri = Uri.parse(currentPhotoPath)
                val resultUri = uri
                val file = resultUri.path
                // Check if the file size is less than or equal to 3MB (3 * 1024 * 1024 bytes)
                val imagePath = File(file)

                var imagebytearray = getStreamByteFromImage(imagePath)
                val tempImageFile = createTempImageFile(imagebytearray!!)
                // Compress the image using BitmapFactory
                val options = BitmapFactory.Options()
                options.inSampleSize = 2 // Adjust the sample size as needed
                val bitmap = BitmapFactory.decodeFile(imagePath.absolutePath, options)

                val compressedImageFile =
                    File(this.externalCacheDir, "compressed_image.jpg")
                val outputStream = FileOutputStream(compressedImageFile)

                // Compress the Bitmap to a file
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)

                if (tempImageFile.length() <= 5 * 1024 * 1024) {


                    // The image is within the size limit
                    Glide.with(this)
                        .load(resultUri)
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.ivProfilePic)

                    imagefilforapi = tempImageFile

                    val requestBody =
                        imagefilforapi.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    bodyofpf = MultipartBody.Part.createFormData(
                        "imagePath",
                        imagefilforapi.name,
                        requestBody
                    )

                    UpdateProfilePic(bodyofpf)

                } else {
                    Toast.makeText(
                        this,
                        "Please upload an image in defined limit (5 MB).",
                        Toast.LENGTH_SHORT
                    ).show()
                    // The image size exceeds 3MB, handle this as per your requirements (e.g., show an error message)
                    // You can add a Toast or Snackbar to inform the user about the size limit.
                }
            } catch (e: Exception) {
                // Handle any exceptions that may occur
            }
        }
    }


}
