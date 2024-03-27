package com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments.Guest.AddGuestFragments

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.envagemobileapplication.Activities.DashBoard.MainActivity
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.BuildConfig
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateProfileResultResponse.UpdateProfileResultResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.AddGuestFragmentsDataListener
import com.example.envagemobileapplication.Utils.Global
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.FragmentAddGuestBasicDetailBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class AddGuestBasicDetailF : Fragment() {
    var dataListener: AddGuestFragmentsDataListener? = null
    private var email: String = ""
    val emailRegex = Regex("^([a-zA-Z0-9_\\.-]+)@([a-zA-Z0-9\\.-]+)\\.([a-zA-Z]{2,})$")
    lateinit var bodyofpf: MultipartBody.Part
    lateinit var imagefilforapi: File
    lateinit var currentPhotoPath: String
    private val GALLERY_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    lateinit var binding: FragmentAddGuestBasicDetailBinding
    lateinit var tokenManager: TokenManager
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var loader: Loader
    lateinit var genderlist: ArrayList<String>
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddGuestFragmentsDataListener) {
            dataListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddGuestBasicDetailBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {

                    binding.cvCameragallery.visibility = View.GONE
                    val selectedImageUri: Uri? = data?.data
                    val contentResolver = requireActivity().contentResolver
                    val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                    val fileSize = inputStream?.available() ?: 0

                    if (fileSize > 5 * 1024 * 1024) { // 5 MB in bytes
                        // Show an error message
                        Toast.makeText(
                            requireActivity(),
                            "Please upload an image in defined limit (5 MB).",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val uri: Uri? =
                            selectedImageUri

                        val imageFile: File? = uriToFile(contentResolver, uri!!)
                        Log.i("imagefile", imageFile.toString())
                        imagefilforapi = imageFile!!
                        global.imagefile  = imagefilforapi
                        Picasso.get().load(selectedImageUri).into(binding.imageView10)
                        val requestBody =
                            imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "profileImage",
                                imageFile.name,
                                requestBody
                            )
                        global.profilePicGuest = bodyofpf
                        /*       UpdateProfilePic(bodyofpf)*/
                    }


                }

                CAMERA_REQUEST_CODE -> {

                    val uri = Uri.parse(currentPhotoPath)
                    CropImage.activity(uri)
                        .setRequestedSize(512, 512)
                        .start(requireActivity())

                    val imageFile: File? = File(currentPhotoPath)
                    Log.i("imagefile", imageFile.toString())
                    imagefilforapi = imageFile!!
                    Picasso.get().load(uri).into(binding.imageView10)
                    val requestBody =
                        imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    bodyofpf =
                        MultipartBody.Part.createFormData(
                            "profileImage",
                            imageFile.name,
                            requestBody
                        )
                    binding.cvCameragallery.visibility = View.GONE
                    global.profilePicGuest = bodyofpf
                    /*    UpdateProfilePic(bodyofpf)*/

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
                            .into(binding.imageView10)

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

                        global.profilePicGuest = bodyofpf
                        binding.cvCameragallery.visibility = View.GONE
                        /* val delayMillis = 3000L // Delay between transitions in milliseconds
                         val handler = Handler()
                         handler.postDelayed({
                             UpdateProfilePic(bodyofpf)
                         }, delayMillis)*/


                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        result.error.printStackTrace()
                    }

                }


            }
        }
    }

    override fun onResume() {

        if (global.guestData.firstName != null) {
            binding.etFirstName.setText(global.guestData.firstName.toString())
        }
        if (global.guestData.lastName != null) {
            binding.etLastName.setText(global.guestData.lastName.toString())
        }

        if (global.guestData.email != null) {
            binding.etEmail.setText(global.guestData.email.toString())
        }
        if (global.guestData.gender != null) {
            binding.spinnergender.setText(global.guestData.gender.toString())
        }
        if (global.guestData.department != null) {
            binding.etDepartment.setText(global.guestData.department.toString())
        }
        if (global.guestData.phonenumber != null) {
            binding.etPhone.setText(global.guestData.phonenumber.toString())
        }
        if (global.guestData.linkedinurl != null) {
            binding.etLinkedinUrl.setText(global.guestData.linkedinurl.toString())
        }
        super.onResume()
    }

    override fun onPause() {

        val firstname = binding.etFirstName.text.toString()
        val lastname = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val gender = binding.spinnergender.text.toString()
        val department = binding.etDepartment.text.toString()
        val phonenumber = binding.etPhone.text.toString()
        val linkedinurl = binding.etLinkedinUrl.text.toString()
        // Call the interface method with the data
        dataListener?.onDataReceived(
            firstname,
            lastname,
            email,
            gender,
            department,
            phonenumber,
            linkedinurl,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null

        )
        super.onPause()
    }

    private fun initViews() {
        tokenManager = TokenManager(requireContext())
        loader = Loader(requireContext())
        genderlist = ArrayList()
        genderlist.add("Male")
        genderlist.add("Female")
        genderlist.add("Other")

        val fnhint = "First Name *"
        val fnformattedHint = global.formatHintWithRedAsterisk(fnhint)
        binding.TIfirstname.hint = fnformattedHint

        val lnhint = "Last Name *"
        val lnformattedHint = global.formatHintWithRedAsterisk(lnhint)
        binding.TILastName.hint = lnformattedHint

        val emailhint = "Email *"
        val emailformattedHint = global.formatHintWithRedAsterisk(emailhint)
        binding.TIEmail.hint = emailformattedHint



        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed in this case
            }

            override fun afterTextChanged(s: Editable?) {
                val phoneNumber = s.toString()
                val formattedPhoneNumber = global.formatPhoneNumber(phoneNumber)
                if (phoneNumber != formattedPhoneNumber) {
                    binding.etPhone.removeTextChangedListener(this)
                    binding.etPhone.text =
                        Editable.Factory.getInstance().newEditable(formattedPhoneNumber)
                    binding.etPhone.setSelection(formattedPhoneNumber.length)
                    binding.etPhone.addTextChangedListener(this)
                }
            }
        })


        try {
            val adapter = customadapter(
                requireContext(),
                R.layout.simple_spinner_item,
                genderlist
            )
            binding.spinnergender.setAdapter(adapter)

        } catch (e: Exception) {

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
        binding.btnsave.setOnClickListener {

            var firstnamee = binding.etFirstName.text
            var lastname = binding.etLastName.text
            var email = binding.etEmail.text
            if (firstnamee!!.isNotEmpty() && lastname!!.isNotEmpty() && email!!.isNotEmpty()) {
                replaceFragment(AddGuestAddressDetailF())
            } else {
                if (firstnamee!!.isEmpty()) {
                    binding.TIfirstname.error = "First name is Required"
                    binding.TIfirstname.setErrorIconDrawable(null)// Set the error message
                    binding.TIfirstname.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText);
                }
                if (lastname!!.isEmpty()) {
                    binding.TILastName.error = "Last name is Required"
                    binding.TILastName.setErrorIconDrawable(null)// Set the error message
                    binding.TILastName.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText);
                }
                if (email!!.isEmpty()) {
                    binding.TILastName.error = "Last name is Required"
                    binding.TILastName.setErrorIconDrawable(null)// Set the error message
                    binding.TILastName.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText);
                }
            }

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
                    requireContext(),
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
                requireContext(),
                com.example.envagemobileapplication.R.drawable.ic_select_camera
            )

            // Set the color you want for the SVG drawable
            val color =
                ContextCompat.getColor(
                    requireContext(),
                    com.example.envagemobileapplication.R.color.white
                )

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
                    requireContext(),
                    com.example.envagemobileapplication.R.drawable.ic_select_camera
                )
            binding.tvCamera.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)

            binding.cvGallery.setBackgroundResource(com.example.envagemobileapplication.R.drawable.btn_black_radius)

            binding.tvGallery.setTextColor(resources.getColor(com.example.envagemobileapplication.R.color.white))
            val drawable =
                ContextCompat.getDrawable(
                    requireContext(),
                    com.example.envagemobileapplication.R.drawable.ic_select_from_gallery
                )
            ContextCompat.getDrawable(
                requireContext(),
                com.example.envagemobileapplication.R.drawable.ic_select_from_gallery
            )

            // Set the color you want for the SVG drawable
            val color =
                ContextCompat.getColor(
                    requireContext(),
                    com.example.envagemobileapplication.R.color.white
                )

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
        binding.ivCancel.setOnClickListener {

            global.showDialog(requireContext(), requireActivity())

        }
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable?) {

                val validatedEmail: String? =
                    validateEmail(editable.toString(), binding.etEmail)

                if (validatedEmail != null) {
                    // Use the validated email
                    email = validatedEmail

                } else {
                    email = ""
                    // Handle the case where the email is invalid
                }
            }
        })
        binding.etLinkedinUrl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                if (enteredText.isEmpty()) {
                    // Clear any previous error when the text is empty
                    binding.TILinkedinUrl.error = null
                } else if (global.isValidUrl(enteredText)) {
                    // URL is valid, you can update UI or perform other actions
                    binding.TILinkedinUrl.error = null // Clear any previous error
                } else {

                    binding.TILinkedinUrl.setError("Enter valid url");
                    binding.TILinkedinUrl.setErrorIconDrawable(null)// Set the error message
                    binding.TILinkedinUrl.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText);
                    // URL is not valid, display an error message
                    /*binding.etClientWebsite.error = "Invalid URL"*/
                }

            }
        })
    }

    private fun openCamera() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = getImageFile()
        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            FileProvider.getUriForFile(
                requireContext(),
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
            requireActivity().filesDir
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

                var imagebytearray = global.getStreamByteFromImage(imagePath)
                val tempImageFile = createTempImageFile(imagebytearray!!)
                // Compress the image using BitmapFactory
                val options = BitmapFactory.Options()
                options.inSampleSize = 2 // Adjust the sample size as needed
                val bitmap = BitmapFactory.decodeFile(imagePath.absolutePath, options)

                val compressedImageFile =
                    File(requireActivity().externalCacheDir, "compressed_image.jpg")
                val outputStream = FileOutputStream(compressedImageFile)

                // Compress the Bitmap to a file
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)

                if (tempImageFile.length() <= 5 * 1024 * 1024) {


                    // The image is within the size limit
                    Glide.with(this)
                        .load(resultUri)
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.imageView10)

                    imagefilforapi = tempImageFile

                    val requestBody =
                        imagefilforapi.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    bodyofpf = MultipartBody.Part.createFormData(
                        "profileImage",
                        imagefilforapi.name,
                        requestBody
                    )


                    global.profilePicGuest = bodyofpf
                    binding.cvCameragallery.visibility = View.GONE

                    //UpdateProfilePic(bodyofpf)

                } else {
                    binding.cvCameragallery.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Please upload an image in defined limit (5 MB).",
                        Toast.LENGTH_SHORT
                    ).show()
                    // The image size exceeds 3MB, handle this as per your requirements (e.g., show an error message)
                    // You can add a Toast or Snackbar to inform the user about the size limit.
                }
            } catch (e: Exception) {
                binding.cvCameragallery.visibility = View.GONE
                // Handle any exceptions that may occur
            }
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

    private fun checkPermissions(): Boolean {

        if (Build.VERSION.SDK_INT >= 33) {
            global.permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        }

        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in global.permissions) {
            result = ContextCompat.checkSelfPermission(requireContext(), p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }

        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                listPermissionsNeeded.toTypedArray(),
                global.MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    fun validateEmail(email: String, etBCC: EditText): String? {
        return if (!Global.emailRegex.matches(email)) {

            binding.TIEmail.error = "Invalid email address"
            binding.TIEmail.errorIconDrawable = null// Set the error message
            binding.TIEmail.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
            null
        } else {
            binding.TIEmail.error = null
            email
        }
    }

    /*  fun replaceFragment(fragment: Fragment) {
          val fragmentManager: FragmentManager = childFragmentManager
          val transaction: FragmentTransaction = fragmentManager.beginTransaction()
          transaction.replace(com.example.envagemobileapplication.R.id.cc_fragments, fragment)
          transaction.commit()
      }*/

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            com.example.envagemobileapplication.R.id.cc_fragments,
            fragment
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    @SuppressLint("Range")
    fun uriToFile(contentResolver: ContentResolver, uri: Uri): File? {
        try {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                it.moveToFirst()
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val inputStream = contentResolver.openInputStream(uri)
                val file = File(requireActivity().cacheDir, displayName)

                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                    return file
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireActivity(), e.toString(), Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        return null
    }

}