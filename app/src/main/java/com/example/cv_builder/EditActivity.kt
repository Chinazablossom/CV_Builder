package com.example.cv_builder

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.cv_builder.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val devRoles = resources.getStringArray(R.array.DeveloperRoles)
        val locations = resources.getStringArray(R.array.locationsArray)
        val schools = resources.getStringArray(R.array.universities)
        val arrayAdapterDevRoles = ArrayAdapter(this@EditActivity, R.layout.roleslist, devRoles)
        val arrayAdapterLocations = ArrayAdapter(this@EditActivity, R.layout.roleslist, locations)
        val arrayAdapterSchools = ArrayAdapter(this@EditActivity, R.layout.roleslist, schools)
        viewBinding.devRoleIdEditText.setAdapter(arrayAdapterDevRoles)
        viewBinding.locationIdEditText.setAdapter(arrayAdapterLocations)
        viewBinding.educationIdEditText.setAdapter(arrayAdapterSchools)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val sharedPreferences = getSharedPreferences("CV_BUILDER", MODE_PRIVATE)
        viewBinding.doneTxt.setOnClickListener {
            val editor = sharedPreferences.edit()
            val userName = viewBinding.fullNameIdEditText.text.toString()
            val developerRole = viewBinding.devRoleIdEditText.text.toString()
            val location = viewBinding.locationIdEditText.text.toString()
            val email = viewBinding.emailIdEditText.text.toString()
            val education = viewBinding.educationIdEditText.text.toString()
            val slackUserName = viewBinding.slackIdEditText.text.toString()
            val gitHubUsername = viewBinding.gitHubEtIdEditText.text.toString()
            val bio = viewBinding.bioIdEditText.text.toString()

            editor.apply {
                putString("USER_NAME", userName)
                putString("DEV_ROLE", developerRole)
                putString("USER_LOCATION", location)
                putString("USER_EMAIL", email)
                putString("USER_EDU", education)
                putString("USER_SLACK", slackUserName)
                putString("USER_GIT", gitHubUsername)
                putString("USER_BIO", bio)
                apply()
            }
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        val userName = sharedPreferences.getString("USER_NAME", getString(R.string.userName))
        val email = sharedPreferences.getString("USER_EMAIL", getString(R.string.email))
        val slackUserName = sharedPreferences.getString("USER_SLACK", getString(R.string.userName))
        val gitHubUsername =
            sharedPreferences.getString("USER_GIT", getString(R.string.gituserName))
        val bio = sharedPreferences.getString("USER_BIO", getString(R.string.bio))
        viewBinding.fullNameIdEditText.setText(userName)
        viewBinding.emailIdEditText.setText(email)
        viewBinding.slackIdEditText.setText(slackUserName)
        viewBinding.gitHubEtIdEditText.setText(gitHubUsername)
        viewBinding.bioIdEditText.setText(bio)

        viewBinding.backId.setOnClickListener {
            finish()
        }


    }


    /*  private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
          if (keyCode == KeyEvent.KEYCODE_ENTER) {
              val nextFocusView = view.focusSearch(View.FOCUS_DOWN)
              nextFocusView?.requestFocus()
              return true
          }
          return false
      }*/

}