package com.amk.weatherforall.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.amk.weatherforall.R
import com.amk.weatherforall.services.Settings
import com.amk.weatherforall.viewModels.BottomNavigationViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton


class SettingsFragment : Fragment() {

    private val settings = Settings

    private lateinit var mView: View
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var buttonSignIn: SignInButton
    private lateinit var signOutButton: Button

    companion object {

        const val RC_SIGN_IN = 40404
        const val TAG = "GoogleAuth"
        fun getInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mView = view
        initView(mView)
        requestResult(view)
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(context ?: return, gso)

        buttonSignIn = view.findViewById(R.id.signInButton)
        buttonSignIn.setOnClickListener {
            signIn()
        }

        signOutButton = view.findViewById(R.id.sign_out_button)
        signOutButton.setOnClickListener {
            signOut()
        }
    }


    override fun onStart() {
        super.onStart()
        enableSign()
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(context ?: return)
        if (account != null) {
            disableSign()
            updateUI(account.email)
        }
    }

    override fun onResume() {
        super.onResume()
        initView(mView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {

        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java) ?: return
            disableSign()
            updateUI(account.email)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun initView(view: View) {
        selectTemperatureMode(view)

        val showWindCheckBox: CheckBox = view.findViewById(R.id.show_wind_checkBox)
        showWindCheckBox.isChecked = settings.showWind

        val showPressureCheckBox: CheckBox = view.findViewById(R.id.show_pressure_checkBox)
        showPressureCheckBox.isChecked = settings.showPressure
    }

    private fun updateUI(idToken: String?) {
        val token: TextView = mView.findViewById(R.id.account_text_view)
        token.text = idToken ?: ""
    }


    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener(activity ?: return) {
            updateUI("email")
            enableSign()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun selectTemperatureMode(view: View) {

        val temperatureRadioGroup: RadioGroup =
            view.findViewById(R.id.select_temperature_mode_radio_button)
        val temperatureC: RadioButton = view.findViewById(R.id.display_in_C_radioButton)
        val temperatureF: RadioButton = view.findViewById(R.id.display_in_F_radioButton)

        if (settings.temperatureC) {
            temperatureC.isChecked = true
        } else {
            temperatureF.isChecked = true
        }

        temperatureRadioGroup.setOnCheckedChangeListener { _, radioButtonId ->
            settings.previousTemperatureC = settings.temperatureC
            when (radioButtonId) {
                temperatureC.id -> settings.temperatureC = true
                temperatureF.id -> settings.temperatureC = false
            }

        }
    }

    private fun requestResult(view: View) {
        val okButton: Button = view.findViewById(R.id.ok_button_setting)
        okButton.setOnClickListener {
            val showWindCheckBox: CheckBox = view.findViewById(R.id.show_wind_checkBox)
            settings.showWind = showWindCheckBox.isChecked

            val showPressureCheckBox: CheckBox = view.findViewById(R.id.show_pressure_checkBox)
            settings.showPressure = showPressureCheckBox.isChecked

            closeFragment()

        }

        val cancelButton: Button = view.findViewById(R.id.cancel_button_setting)
        cancelButton.setOnClickListener {
            settings.temperatureC = settings.previousTemperatureC
            closeFragment()
        }
    }

    private fun closeFragment() {
        val bottomNavigationViewModel: BottomNavigationViewModel = ViewModelProviders.of(
            activity ?: return
        ).get(
            BottomNavigationViewModel::class.java
        )
        bottomNavigationViewModel.selectItemBottom(R.id.navigation_home)

        runFragments(activity ?: return, FragmentsNames.MainFragment)

    }

    private fun enableSign() {
        buttonSignIn.isEnabled = true
        signOutButton.isEnabled = false
    }

    private fun disableSign() {
        buttonSignIn.isEnabled = false
        signOutButton.isEnabled = true
    }


}