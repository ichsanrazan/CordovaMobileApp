package cordova.telkomsel.cordovamobileapp.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cordova.telkomsel.cordovamobileapp.MainActivity
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.authentication.model.UserRequest
import cordova.telkomsel.cordovamobileapp.authentication.model.UserResponse
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        initAction()
    }

    fun initAction() {
        button_login.setOnClickListener {
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
                if(validateLogin(username, password)){
                    if(response.body()!!.status!! == 0) {
                        Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    }else{
                        Toast.makeText(this@LoginActivity, "Login gagal", Toast.LENGTH_SHORT).show()
                    }
                }


                //startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
            }

        })
    }

    fun validateLogin(username: String, password: String) : Boolean{
        if (username.isEmpty()){
            inputUser.error = "Email is required!"
            return false
        }

        if(password.isEmpty()){
            inputPassword.error = "Password is required!"
            return false
        }
        return true
    }
}