package com.example.envagemobileapplication.Activities.Employees.EmployeeFragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.envagemobileapplication.Adapters.CandidateSummaryEducationAdapter
import com.example.envagemobileapplication.Adapters.CandidateSummaryExperienceAdapter
import com.example.envagemobileapplication.Adapters.CandidateSummaryJobsAdapter
import com.example.envagemobileapplication.Adapters.CandidateSummarySkillsAdapter
import com.example.envagemobileapplication.Adapters.EmployeeSummaryPlacementsAdapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummarySkillsRes.CandidateSummarySkillsRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteHeadrSmaryRsp.CandidateHeaderSummaryResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSmryEtiondRes.CandidateSummaryEducationRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSumryExpRes.CandidateSummaryExperienceRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.EmployeeHeaderSummaryResponse.EmployeeHeaderSummaryResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetPlacementResponse.GetPlacementResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateProfileResultResponse.UpdateProfileResultResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.FragmentCandidateSummaryBinding
import com.example.envagemobileapplication.databinding.FragmentEmployeeProfileSummaryBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.github.dhaval2404.imagepicker.util.FileUtil.getImageFile
import com.squareup.picasso.BuildConfig
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

class EmployeeProfileSummaryF : Fragment() {

    lateinit var binding: FragmentEmployeeProfileSummaryBinding


    lateinit var longEmail:String
    lateinit var longAddress:String
    lateinit var bodyofpf: MultipartBody.Part
    lateinit var loader: Loader
    private val MULTIPLE_PERMISSIONS = 10
    private val GALLERY_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    lateinit var currentPhotoPath: String
    lateinit var imagefilforapi: File



    lateinit var candidateSummaryEducationAdapter: CandidateSummaryEducationAdapter
    lateinit var candidateSummaryExperienceAdapter: CandidateSummaryExperienceAdapter
    lateinit var candidateSummarySkillsAdapter: CandidateSummarySkillsAdapter
    lateinit var employeeSummaryPlacementsAdapter: EmployeeSummaryPlacementsAdapter

    var permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeProfileSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = Loader(requireContext())
        networkCalls()
        clickListeners()
    }

    private fun clickListeners() {
        binding.ivCamera.setOnClickListener {

            if (checkPermissions()) {
                binding.cvCameragallery.visibility = View.VISIBLE
            }


            binding.cvTakePhoto.setOnClickListener {
                openCamera()
                binding.cvGallery.setBackgroundResource(R.drawable.searchbg)
                binding.cvGallery.setBackgroundColor(resources.getColor(R.color.white))
                binding.tvGallery.setTextColor(resources.getColor(R.color.black))
                val drawableLeft: Drawable? =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)
                binding.tvGallery.setCompoundDrawablesWithIntrinsicBounds(
                    drawableLeft,
                    null,
                    null,
                    null
                )
                binding.cvTakePhoto.setBackgroundResource(R.drawable.btn_black_radius)
                binding.tvCamera.setTextColor(resources.getColor(R.color.white))
                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_camera)

                // Set the color you want for the SVG drawable
                val color = ContextCompat.getColor(requireContext(), R.color.white)

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
                binding.cvTakePhoto.setBackgroundResource(R.drawable.searchbg)
                binding.cvTakePhoto.setBackgroundColor(resources.getColor(R.color.white))
                binding.tvCamera.setTextColor(resources.getColor(R.color.black))
                val drawableLeft: Drawable? =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_camera)
                binding.tvCamera.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)

                binding.cvGallery.setBackgroundResource(R.drawable.btn_black_radius)

                binding.tvGallery.setTextColor(resources.getColor(R.color.white))
                val drawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)

                // Set the color you want for the SVG drawable
                val color = ContextCompat.getColor(requireContext(), R.color.white)

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
        }
    }



    private fun networkCalls() {
        getEmployeeHeaderSummary()
        getEmployeeSummaryEducation()
        getEmployeeSummaryExperience()
        getEmployeeSummarySkills()
        getEmployeeSummaryPlacements()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //    checkPermissions()
                    showNoPermissionDialog()
                    //  Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {

                    binding.cvCameragallery.visibility = View.GONE
                    val selectedImageUri: Uri? = data?.data
                    val contentResolver = requireContext().contentResolver
                    val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                    val fileSize = inputStream?.available() ?: 0

                    if (fileSize > 5 * 1024 * 1024) { // 5 MB in bytes
                        // Show an error message
                        Toast.makeText(
                            requireContext(),
                            "Please upload an image in defined limit (5 MB).",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val uri: Uri? =
                            selectedImageUri

                        val imageFile: File? = uriToFile(contentResolver, uri!!)
                        Log.i("imagefile", imageFile.toString())
                        imagefilforapi = imageFile!!

                        Picasso.get().load(selectedImageUri)
                            .placeholder(R.drawable.upload_pic_bg)
                            .transform(CircleTransformation()).into(binding.ivEmployeeProfile)

                        Picasso.get().load(selectedImageUri).into(binding.ivEmployeeProfile)
                        val requestBody =
                            imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "profileImage",
                                imageFile.name,
                                requestBody
                            )

//                        UpdateProfilePic(bodyofpf)
                    }

                }

                CAMERA_REQUEST_CODE -> {

                    Toast.makeText(context, "its clicked", Toast.LENGTH_SHORT).show()

                    val uri = Uri.parse(currentPhotoPath)
                    CropImage.activity(uri)
                        .setRequestedSize(512, 512)
                        .start(requireActivity())

                    val imageFile: File? = File(currentPhotoPath)
                    Log.i("imagefile", imageFile.toString())
                    imagefilforapi = imageFile!!
                    Picasso.get().load(uri).into(binding.ivEmployeeProfile)
                    binding.cvCameragallery.visibility = View.GONE
                    val requestBody =
                        imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    bodyofpf =
                        MultipartBody.Part.createFormData(
                            "profileImage",
                            imageFile.name,
                            requestBody)

//                    UpdateProfilePic(bodyofpf)

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
                            .into(binding.ivEmployeeProfile)

                        val imagePath = File(file)

                        imagefilforapi = imagePath
                        binding.cvCameragallery.visibility = View.GONE

                        val requestBody =
                            imagefilforapi
                                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "profileImage",
                                imagefilforapi.name,
                                requestBody
                            )

                        val delayMillis = 3000L // Delay between transitions in milliseconds
                        val handler = Handler()
                        handler.postDelayed({
//                            UpdateProfilePic(bodyofpf)
                        }, delayMillis)

                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        result.error.printStackTrace()
                    }
                }
            }
        }
    }


    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        galleryIntent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/jpg", "image/png")
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    private fun openCamera() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = getImageFile()
        try {
            val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                FileProvider.getUriForFile(
                    requireActivity(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    file
                ) else Uri.fromFile(file)

            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

//

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                openCameraActivityResultLauncher.launch(pictureIntent)
            } else {
                startActivityForResult(pictureIntent, CAMERA_REQUEST_CODE)
            }
        }catch (e:Exception){
            Log.d("sjdhjsdf",e.toString())
        }

    }

    fun createTempImageFile(imageByteArray: ByteArray): File {
        val tempFile = File.createTempFile("temp_image", ".jpg", requireActivity().cacheDir)
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

    private fun exifToDegrees(rotation: Int): Int {
        if (rotation == ExifInterface.ORIENTATION_ROTATE_90) return 90 else if (rotation == ExifInterface.ORIENTATION_ROTATE_180) return 180 else if (rotation == ExifInterface.ORIENTATION_ROTATE_270) return 270
        return 0
    }

    fun getStreamByteFromImage(imageFile: File): ByteArray? {
        var photoBitmap = BitmapFactory.decodeFile(imageFile.path)
        val stream = ByteArrayOutputStream()
        val imageRotation = getImageRotation(imageFile)
        if (imageRotation != 0) photoBitmap = getBitmapRotatedByDegree(photoBitmap, imageRotation)
        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        return stream.toByteArray()
    }

    private fun getBitmapRotatedByDegree(bitmap: Bitmap, rotationDegree: Int): Bitmap {
        val matrix = Matrix()
        matrix.preRotate(rotationDegree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

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
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
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
                    File(requireContext().externalCacheDir, "compressed_image.jpg")
                val outputStream = FileOutputStream(compressedImageFile)

                // Compress the Bitmap to a file
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)

                if (tempImageFile.length() <= 5 * 1024 * 1024) {


                    // The image is within the size limit
                    Glide.with(this)
                        .load(resultUri)
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.ivEmployeeProfile)

                    imagefilforapi = tempImageFile

                    val requestBody =
                        imagefilforapi.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    bodyofpf = MultipartBody.Part.createFormData(
                        "profileImage",
                        imagefilforapi.name,
                        requestBody
                    )

//                    UpdateProfilePic(bodyofpf)

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please upload an image in defined limit (5 MB).",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } catch (e: Exception) {
                // Handle any exceptions that may occur
            }
        }
    }

    private fun getImageFile(): File {
        val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"
        val storageDir: File = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            requireContext().filesDir
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
        return file
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
            result = ContextCompat.checkSelfPermission(requireActivity(), p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }

        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                listPermissionsNeeded.toTypedArray(),
                MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    private fun showNoPermissionDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("App does not have access to your Camera and Gallery. Please enable Camera and Gallery permissions from the settings and select Always.")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Ok") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()

        }


        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }


    fun setupSkillsAdapter(
        candidatelist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummarySkillsRes.Datum>
    ) {

        binding!!.rvSkillsEmployeeHS.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext()
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvSkillsEmployeeHS.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        candidateSummarySkillsAdapter = CandidateSummarySkillsAdapter(
            requireContext(),
            candidatelist,
            childFragmentManager
        )
        binding!!.rvSkillsEmployeeHS.adapter = candidateSummarySkillsAdapter
    }


    fun setupPlacementsAdapter(
        candidatelist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetPlacementResponse.Datum>
    ) {

        binding!!.rvPlacementEmployees.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext()
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvPlacementEmployees.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        employeeSummaryPlacementsAdapter = EmployeeSummaryPlacementsAdapter(
            requireContext(),
            candidatelist,
            childFragmentManager
        )
        binding!!.rvPlacementEmployees.adapter = employeeSummaryPlacementsAdapter
    }

    fun setupEducationAdapter(
        candidatelist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSmryEtiondRes.Datum>
    ) {

        binding!!.rvEducationEmployee.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(
            requireContext()
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvEducationEmployee.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        candidateSummaryEducationAdapter = CandidateSummaryEducationAdapter(
            requireContext(),
            candidatelist,
            childFragmentManager
        )
        binding!!.rvEducationEmployee.adapter = candidateSummaryEducationAdapter

    }

    fun setupExperienceAdapter(
        candidatelist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSumryExpRes.Datum>
    ) {

        binding!!.rvExperienceEmployee.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext()
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvExperienceEmployee.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        candidateSummaryExperienceAdapter = CandidateSummaryExperienceAdapter(
            requireContext(),
            candidatelist,
            childFragmentManager
        )
        binding!!.rvExperienceEmployee.adapter = candidateSummaryExperienceAdapter
    }



    private fun getEmployeeSummaryPlacements() {
        loader.show()
        val tokenManager = TokenManager(requireContext())
        val token = tokenManager.getAccessToken()
        val employeeGUID = Constants.employeeGUID

        try {

            ApiUtils.getAPIService(requireContext()).GetEmployeeSummaryPlacement(
                token.toString(), employeeGUID!!
            )
                .enqueue(object : Callback<GetPlacementResponse> {
                    override fun onResponse(
                        call: Call<GetPlacementResponse>,
                        response: Response<GetPlacementResponse>
                    ) {

                        if(response.body() != null){

                            loader.hide()

                            var arraylist :ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetPlacementResponse.Datum> = ArrayList()


                            response?.body()?.data?.let { arraylist.addAll(it) }


                            if(arraylist.size >0){
                                binding.tvNoPlacements.visibility = View.GONE
                                setupPlacementsAdapter(arraylist)
                            }

                        }else{
                            loader.hide()

                        }
                    }

                    override fun onFailure(call: Call<GetPlacementResponse>, t: Throwable) {

                    }
                })

        }catch (e:Exception){
            loader.hide()
            Log.d("candidateMSg",e.toString())

        }
    }

    private fun getEmployeeHeaderSummary() {
        loader.show()
        val tokenManager = TokenManager(requireContext())
        val token = tokenManager.getAccessToken()
        val employeeId = Constants.employeeGUID

        try {

            ApiUtils.getAPIService(requireContext()).GetEmployeeHeaderSummary(
                token.toString(), employeeId!!
            )
                .enqueue(object : Callback<EmployeeHeaderSummaryResponse> {
                    override fun onResponse(
                        call: Call<EmployeeHeaderSummaryResponse>,
                        response: Response<EmployeeHeaderSummaryResponse>
                    ) {



                        if(response.body() != null){
                            loader.hide()

                            try {
                                var candidateData = response?.body()?.data
                                loader.hide()

                                if(candidateData?.employeeSocialMedia?.get(0)?.url.toString() != null && candidateData?.employeeSocialMedia?.get(0)?.isActive != false){
                                    binding.ivFb.visibility = View.VISIBLE
                                }
                                if(candidateData?.employeeSocialMedia?.get(1)?.url.toString() != null && candidateData?.employeeSocialMedia?.get(1)?.isActive != false){
                                    binding.ivTwitter.visibility = View.VISIBLE
                                }
                                if(candidateData?.employeeSocialMedia?.get(2)?.url.toString() != null && candidateData?.employeeSocialMedia?.get(2)?.isActive != false){
                                    binding.ivLinkedIn.visibility = View.VISIBLE
                                }
                                if(candidateData?.employeeSocialMedia?.get(3)?.url.toString() != null && candidateData?.employeeSocialMedia?.get(3)?.isActive != false){
                                    binding.ivInstagram.visibility = View.VISIBLE
                                }

                                if(candidateData?.employeeInfo?.firstName !=null && candidateData?.employeeInfo?.lastName!=null){
                                    binding.employeeName.text = candidateData?.employeeInfo?.firstName + " " + candidateData?.employeeInfo?.lastName
                                }else{
                                    binding.employeeName.text = "Not Provided"
                                }


                                if(candidateData?.employeeInfo?.ssnNumber != null){

                                    try {
                                        val ssn = candidateData?.employeeInfo?.ssnNumber.toString()
                                        val maskedSSN = maskSSN(ssn)
                                        binding.employeeSSN.text = "SSN: "+maskedSSN
                                    } catch (e: IllegalArgumentException) {
                                        println("Error: ${e.message}")
                                    }

                                }else{
                                    binding.employeeSSN.text = "SSN: Not Provided"
                                }

                                if(!candidateData?.employeeInfo?.profileImage.isNullOrBlank()){
                                    Picasso.get().load(candidateData?.employeeInfo?.profileImage)
                                        .placeholder(R.drawable.upload_pic_bg)
                                        .transform(CircleTransformation()).into(binding.ivEmployeeProfile)
                                }


                                if(candidateData?.employeeInfo?.personalEmail != null){
                                    longEmail = candidateData?.employeeInfo?.personalEmail.toString()
                                    binding.eEmail.text = candidateData?.employeeInfo?.personalEmail.toString()
                                }else{
                                    binding.eEmail.text = "Not Provided"
                                }

                                if(candidateData?.employeeInfo?.phoneNumber !=null){
                                    val inputPhoneNumber = candidateData?.employeeInfo?.phoneNumber.toString()
                                    val formattedPhoneNumber =  formatToUSAPhoneNumber(inputPhoneNumber)
                                    binding.ePhone.text = formattedPhoneNumber
                                }else{
                                    binding.ePhone.text = "Not Provided"
                                }

                                if(!candidateData?.employeeInfo?.addressLine1.isNullOrBlank() && !candidateData?.employeeInfo?.addressLine2.isNullOrBlank() ){
                                    longAddress = candidateData?.employeeInfo?.addressLine1.toString() +", "+candidateData?.employeeInfo?.addressLine2.toString()
                                    binding.eAddress.text = candidateData?.employeeInfo?.addressLine1 +", "+candidateData?.employeeInfo?.addressLine2
                                }else if(!candidateData?.employeeInfo?.addressLine1.isNullOrBlank() && candidateData?.employeeInfo?.addressLine2.isNullOrBlank()){
                                    longAddress = candidateData?.employeeInfo?.addressLine1.toString()
                                    binding.eAddress.text = candidateData?.employeeInfo?.addressLine1
                                }else if(candidateData?.employeeInfo?.addressLine1.isNullOrBlank() && !candidateData?.employeeInfo?.addressLine2.isNullOrBlank()){
                                    longAddress = candidateData?.employeeInfo?.addressLine2.toString()
                                    binding.eAddress.text = candidateData?.employeeInfo?.addressLine2
                                }
                                else{
                                    binding.eAddress.text = "Not Provided"
                                }
                            }catch (e:Exception){
                                Log.d("headersummaryex",e.toString())
                            }

                        }else{
                            loader.hide()

                        }
                    }

                    override fun onFailure(call: Call<EmployeeHeaderSummaryResponse>, t: Throwable) {
                        Log.d("headersummaryfailure",t.toString())
                    }
                })


        }catch (e:Exception){
            loader.hide()
            Log.d("candidateMSg",e.toString())
        }

    }



//    private fun UpdateProfilePic(bodyofpf: MultipartBody.Part) {
//        loader.show()
//
//        if (imagefilforapi != null) {
//            var tokenmanager: TokenManager = TokenManager(requireContext())
//            var token = tokenmanager.getAccessToken()
//            var candidateId = Constants.candidateId
//
//            try {
//
//
//                ApiUtils.getAPIService(requireContext()).UpdateCandidateProfilepic(
//                    token.toString(), candidateId!!, bodyofpf
//                )
//                    .enqueue(object : Callback<UpdateProfileResultResponse> {
//                        override fun onResponse(
//                            call: Call<UpdateProfileResultResponse>,
//                            response: Response<UpdateProfileResultResponse>
//                        ) {
//                            if (response.body() != null) {
//
//                                loader.hide()
//                                getCandidateHeaderSummary()
//                                Toast.makeText(
//                                    requireContext(),
//                                    "Profile Image updated successfully",
//                                    Toast.LENGTH_LONG
//                                )
//                                    .show()
//                            }
//                        }
//
//                        override fun onFailure(
//                            call: Call<UpdateProfileResultResponse>,
//                            t: Throwable
//                        ) {
//                            loader.hide()
//                            Log.i("exceptionddsfdsfds", t.toString())
//
//                        }
//                    })
//            } catch (ex: java.lang.Exception) {
//                loader.hide()
//                Toast.makeText(requireContext(), ex.toString(), Toast.LENGTH_LONG).show()
//                Log.i("exceptionddsfdsfds", ex.toString())
//            }
//        } else {
//            loader.hide()
//            Toast.makeText(requireContext(), "imageofpf is null", Toast.LENGTH_LONG).show()
//        }
//        /*   var name = "testtttxffdsfdsfdsfdsffdst"
//           var websiteurl = "www.url.com"
//           var description = stringToBinary("testing userrr")*/
//
//
//    }


    fun formatToUSAPhoneNumber(inputPhoneNumber: String): String {
        val digitsOnly = inputPhoneNumber.replace(Regex("\\D"), "")

        if (digitsOnly.length == 10) {
            return digitsOnly.substring(0, 3) + "-" +
                    digitsOnly.substring(3, 6) + "-" +
                    digitsOnly.substring(6)
        } else {
            // Return the input as is if it doesn't match the expected format
            return digitsOnly
        }
    }


    fun maskSSN(ssn: String): String {
        // Check if the provided SSN has exactly 9 digits
        if (ssn.length != 9 || !ssn.all { it.isDigit() }) {
            throw IllegalArgumentException("Invalid SSN. Please provide a 9-digit number.")
        }

        // Mask the SSN and return the result
        val maskedSSN = "*****${ssn.takeLast(4)}"

        return maskedSSN
    }


    private fun getEmployeeSummarySkills() {
        loader.show()
        val tokenManager = TokenManager(requireContext())
        val token = tokenManager.getAccessToken()
        val employeeGUID = Constants.employeeGUID

        try {

            ApiUtils.getAPIService(requireContext()).GetEmployeeSummarySkills(
                token.toString(), employeeGUID!!
            )
                .enqueue(object : Callback<CandidateSummarySkillsRes> {
                    override fun onResponse(
                        call: Call<CandidateSummarySkillsRes>,
                        response: Response<CandidateSummarySkillsRes>
                    ) {

                        if(response.body() != null){

                            loader.hide()

                            var arraylist :ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummarySkillsRes.Datum> = ArrayList()


                            response?.body()?.data?.let { arraylist.addAll(it) }

                            if(arraylist.size >0){
                                binding.tvNoskills.visibility = View.GONE
                                setupSkillsAdapter(arraylist)
                            }

                        }else{
                            loader.hide()

                        }
                    }

                    override fun onFailure(call: Call<CandidateSummarySkillsRes>, t: Throwable) {

                    }
                })


        }catch (e:Exception){
            loader.hide()
            Log.d("candidateMSg",e.toString())

        }
    }

    private fun getEmployeeSummaryExperience() {
        loader.show()
        val tokenManager = TokenManager(requireContext())
        val token = tokenManager.getAccessToken()

        val employeeGUID = Constants.employeeGUID

        try {

            ApiUtils.getAPIService(requireContext()).GetEmployeeSummaryExperience(
                token.toString(), employeeGUID!!
            )
                .enqueue(object : Callback<CandidateSummaryExperienceRes> {
                    override fun onResponse(
                        call: Call<CandidateSummaryExperienceRes>,
                        response: Response<CandidateSummaryExperienceRes>
                    ) {

                        if(response.body() != null){
                            loader.hide()


                            var arraylist :ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSumryExpRes.Datum> = ArrayList()
                            response?.body()?.data?.let { arraylist.addAll(it) }

                            if(arraylist.size >0){

                                binding.tvNoExperience.visibility = View.GONE
                                setupExperienceAdapter(arraylist)

                            }

                        }else{
                            loader.hide()

                        }
                    }

                    override fun onFailure(call: Call<CandidateSummaryExperienceRes>, t: Throwable) {
                        // Handle the failure
                        loader.hide()

                    }
                })

        }catch (e:Exception){
            loader.hide()
            Log.d("candidateMSg",e.toString())

        }
    }

    private fun getEmployeeSummaryEducation() {
        loader.show()
        val tokenManager = TokenManager(requireContext())
        val token = tokenManager.getAccessToken()

        val employeeGUID = Constants.employeeGUID

        try {

            ApiUtils.getAPIService(requireContext()).GetEmployeeSummaryEducation(
                token.toString(), employeeGUID!!
            )
                .enqueue(object : Callback<CandidateSummaryEducationRes> {
                    override fun onResponse(
                        call: Call<CandidateSummaryEducationRes>,
                        response: Response<CandidateSummaryEducationRes>
                    ) {


                        if(response.body() != null){

                            loader.hide()

                            var arraylist :ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSmryEtiondRes.Datum> = ArrayList()


                            response?.body()?.data?.let { arraylist.addAll(it) }

                            if(arraylist.size >0){
                                binding.tvNoEducation.visibility = View.GONE
                                setupEducationAdapter(arraylist)
                            }

                        }else{
                            loader.hide()

                        }
                    }

                    override fun onFailure(call: Call<CandidateSummaryEducationRes>, t: Throwable) {
                        // Handle the failure

                    }
                })


        }catch (e:Exception){
            loader.hide()
            Log.d("candidateMSg",e.toString())

        }
    }



}