package com.example.cv_builder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.cv_builder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding

    companion object {
        private const val REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val sharedPreferences = getSharedPreferences("CV_BUILDER", MODE_PRIVATE)
        val userName = sharedPreferences.getString("USER_NAME", getString(R.string.userName))
        val role = sharedPreferences.getString("DEV_ROLE", getString(R.string.role))
        val location = sharedPreferences.getString("USER_LOCATION", getString(R.string.location))
        val email = sharedPreferences.getString("USER_EMAIL", getString(R.string.email))
        val school = sharedPreferences.getString("USER_EDU", getString(R.string.education))
        val slack = sharedPreferences.getString("USER_SLACK", getString(R.string.userName))
        val git = sharedPreferences.getString("USER_GIT", getString(R.string.gituserName))
        val bio = sharedPreferences.getString("USER_BIO", getString(R.string.about))

        val imageUri = sharedPreferences.getString("USER_IMAGE_URI", null)
        if (!imageUri.isNullOrEmpty()) {
            viewBinding.ImgViewId.setImageURI(Uri.parse(imageUri))
        }



        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        var isExpanded = false

        val lines = viewBinding.aboutId.layout?.lineCount
        if (lines != null) {
            if (lines >= 3) {
                viewBinding.seeMoreTextView.visibility = View.VISIBLE
            } else {
                viewBinding.seeMoreTextView.visibility = View.GONE
            }
        }
        viewBinding.seeMoreTextView.setOnClickListener {
            isExpanded = !isExpanded

            if (isExpanded) {
                viewBinding.aboutId.maxLines = Integer.MAX_VALUE
                viewBinding.seeMoreTextView.text = getString(R.string.SeeLess)
            } else {
                viewBinding.aboutId.maxLines = 3
                viewBinding.seeMoreTextView.text = getString(R.string.SeeMore)
            }
        }



        viewBinding.TxtUserName.text = userName
        viewBinding.TxtNiche.text = role
        viewBinding.LocationTxt.text = location
        viewBinding.TxtEmailId.text = email
        viewBinding.EducationTxt.text = school
        viewBinding.slackUserNameTxtId.text = slack
        viewBinding.TxtGitHandle.text = git
        viewBinding.aboutId.text = bio

        viewBinding.editCvId.setOnClickListener {
            startActivity(Intent(this@MainActivity, EditActivity::class.java))

        }

        viewBinding.changeImgId.setOnClickListener {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
                startActivityForResult(it, 1)
            }
        }


    }

    override fun onBackPressed() {
        finishAffinity()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                val updatedInfo = data?.getStringExtra("UPDATED_INFO")
                val uri = data?.data
                if (uri != null) {
                    viewBinding.ImgViewId.setImageURI(uri)
                    val sharedPreferences = getSharedPreferences("CV_BUILDER", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("USER_IMAGE_URI", uri.toString())
                    editor.apply()
                }
            }
        }
    }


}









