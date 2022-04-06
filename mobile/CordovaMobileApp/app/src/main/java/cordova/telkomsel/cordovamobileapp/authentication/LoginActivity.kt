package cordova.telkomsel.cordovamobileapp.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import cordova.telkomsel.cordovamobileapp.MainActivity
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import cordova.telkomsel.cordovamobileapp.authentication.model.UserRequest
import cordova.telkomsel.cordovamobileapp.authentication.model.UserResponse
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var sharedpref : PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        sharedpref = PreferencesHelper(this)

        initAction()
    }

    override fun onStart() {
        super.onStart()
        if (sharedpref.getBoolean( Constant.PREF_IS_LOGIN)){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    fun initAction() {
        button_login.setOnClickListener {
            loginActivity_loading.visibility = View.VISIBLE
            login()
        }
    }

    fun login() {
        val request = UserRequest()
        request.username = inputUser.text.toString().trim()
        request.password = inputPassword.text.toString().trim()

        val retro = RetrofitInstance.getRetroClientInstance().create(RetrofitService::class.java)
        retro.login(request).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val username = inputUser.text.toString().trim()
                val password = inputPassword.text.toString().trim()
                val user = response.body()
                if(validateLogin(username, password)){
                    if(user!!.status!! == 0) {
                        saveSession( inputUser.text.toString(), inputPassword.text.toString(), user!!.full_name!!.toString())
                        Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    }else{
                        loginActivity_loading.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Login gagal", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
            }

        })
    }

    private fun saveSession( username: String, password: String, fullname: String ){
        sharedpref.put( Constant.PREF_USERNAME, username)
        sharedpref.put( Constant.PREF_PASSWORD, password)
        sharedpref.put( Constant.PREF_FULLNAME, fullname)
        sharedpref.put( Constant.PREF_IS_LOGIN, true)
    }

    fun validateLogin(username: String, password: String) : Boolean{
        if (username.isEmpty()){
                loginActivity_loading.visibility = View.GONE
            inputUser.error = "Username is required!"
            return false
        }

        if(password.isEmpty()){
            loginActivity_loading.visibility = View.GONE
            inputPassword.error = "Password is required!"
            return false
        }
        return true
    }
}