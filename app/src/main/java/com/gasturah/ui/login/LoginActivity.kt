package com.gasturah.ui.login

import ApiConfig
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gasturah.MainActivity
import com.gasturah.data.util.ModelPreferencesManager
import com.gasturah.databinding.ActivityLoginBinding
import com.gasturah.model.UserModel
import com.gasturah.response.LoginResponse
import com.gasturah.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ModelPreferencesManager.with(this)

        showLoading(false)
        binding.btnLogin.setOnClickListener{
            login(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())
        }

        binding.tvRegister.setOnClickListener {
            var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent);
        }

        setupView()
    }

    fun login(email: String, password: String) {
        showLoading(true)
        ApiConfig.getApiService().login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                showLoading(false)

                if (response.isSuccessful) {
                    if(response.body()!!.status == "success"){
                        var username = response.body()!!.content.username
                        var name = response.body()!!.content.name
                        var profile_picture = response.body()!!.content.profilePicture
                        var level = response.body()!!.content.level
                        val user = UserModel(username, name, profile_picture, level)

                        ModelPreferencesManager.put(user, "user")

                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent);
                        finish()
                    } else{
                        Toast.makeText(this@LoginActivity, "${response.body()!!.msg}",
                            Toast.LENGTH_LONG).show();
                    }

                } else {
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, "${response.body()}",
                        Toast.LENGTH_LONG).show();

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "${t.message}",
                    Toast.LENGTH_LONG).show();
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if(isLoading){
                loginGroup.visibility = View.INVISIBLE
                loginLoadingGroup.visibility = View.VISIBLE
            } else{
                loginGroup.visibility = View.VISIBLE
                loginLoadingGroup.visibility = View.INVISIBLE
            }

        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAnimation() {

    }

    private fun setupAction() {

    }
}