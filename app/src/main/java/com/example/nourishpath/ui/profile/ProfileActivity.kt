package com.example.nourishpath.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.nourishpath.R
import com.example.nourishpath.data.local.Profile
import com.example.nourishpath.data.local.ProfileRoomDatabase
import com.example.nourishpath.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private var selectedImageUri: Uri? = null

    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startGallery()
        } else {
            Toast.makeText(this, "Izin diperlukan untuk mengakses galeri", Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyUriToInternalStorage(uri: Uri): Uri? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val file = File(filesDir, "profile_image_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            Uri.fromFile(file)
        } catch (e: Exception) {
            Log.e("ProfileActivity", "Gagal menyalin file ke internal storage: ${e.message}")
            null
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            val internalUri = copyUriToInternalStorage(it)
            handleUri(internalUri)
            selectedImageUri?.let {
                saveUriToPreferences(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        selectedImageUri = loadUriFromPreferences()
        selectedImageUri?.let { uri ->
            binding.profileImage.setImageURI(uri)
        } ?: run {
            binding.profileImage.setImageResource(R.drawable.ic_profile)
        }

        profileViewModel.profileLiveData.observe(this) { profile ->
            profile?.let {
                selectedImageUri?.let { uri ->
                    if (uri.scheme == "content") {
                        try {
                            val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            contentResolver.takePersistableUriPermission(uri, takeFlags)
                            binding.profileImage.setImageURI(uri)
                        } catch (e: SecurityException) {
                            Log.e("ProfileActivity", "Gagal memuat gambar: ${e.message}")
                            binding.profileImage.setImageResource(R.drawable.ic_profile)
                        } catch (e: IllegalArgumentException) {
                            Log.e("ProfileActivity", "URI tidak valid: ${e.message}")
                            binding.profileImage.setImageResource(R.drawable.ic_profile)
                        }
                    } else {
                        binding.profileImage.setImageURI(uri)
                    }
                } ?: run {
                    binding.profileImage.setImageResource(R.drawable.ic_profile)
                }

                selectedImageUri = it.profileImageUri?.let { uriString ->
                    Uri.parse(uriString)
                }

                binding.edtFullname.setText(it.name)
                binding.edtAddress.setText(it.address)
                binding.edtPhone.setText(it.phone)
            }
        }
        profileViewModel.loadProfile()

        binding.profileImage.setOnClickListener { checkPermissionsAndStartGallery() }

        binding.btnSave.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val name = binding.edtFullname.text.toString()
        val address = binding.edtAddress.text.toString()
        val phone = binding.edtPhone.text.toString()

        if (name.isEmpty()) {
            binding.edtFullname.error = "Nama tidak boleh kosong"
            return
        }

        if (address.isEmpty()) {
            binding.edtAddress.error = "Alamat tidak boleh kosong"
            return
        }

        if (phone.isEmpty()) {
            binding.edtPhone.error = "Nomor telepon tidak boleh kosong"
            return
        }

        val profileImageUri = selectedImageUri

        // Simpan profil
        val profile = Profile(
            profileImageUri = profileImageUri.toString(), // Store URI as String
            name = name,
            address = address,
            phone = phone
        )
        profileViewModel.saveProfile(profile)
        Toast.makeText(this, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
    }

    private fun checkPermissionsAndStartGallery() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    startGallery()
                } else {
                    permissionRequestLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    startGallery()
                } else {
                    permissionRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
    private fun handleUri(uri: Uri?) {
        uri?.let {
            when (it.scheme) {
                "content" -> {
                    val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    try {
                        contentResolver.takePersistableUriPermission(it, takeFlags)
                        Log.d("ProfileActivity", "Izin persistable berhasil diambil untuk URI: $it")
                    } catch (e: SecurityException) {
                        Log.e("ProfileActivity", "Gagal mengambil izin persistable: ${e.message}")
                    }
                }
                "file" -> {
                    Log.d("ProfileActivity", "URI file:// tidak memerlukan izin persistable: $it")
                }
                else -> {
                    Log.e("ProfileActivity", "Skema URI tidak dikenal: ${it.scheme}")
                }
            }

            selectedImageUri = it
            binding.profileImage.setImageURI(it)
        } ?: run {
            binding.profileImage.setImageResource(R.drawable.ic_profile)
            Log.e("ProfileActivity", "URI tidak valid atau null")
        }
    }
    private fun saveUriToPreferences(uri: Uri?) {
        val sharedPref = getSharedPreferences("ProfilePrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        uri?.let {
            editor.putString("profileImageUri", it.toString())
        }
        editor.apply()
    }

    private fun loadUriFromPreferences(): Uri? {
        val sharedPref = getSharedPreferences("ProfilePrefs", MODE_PRIVATE)
        val uriString = sharedPref.getString("profileImageUri", null)
        return uriString?.let { Uri.parse(it) }
    }
}
