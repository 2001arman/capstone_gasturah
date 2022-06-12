package com.gasturah.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gasturah.databinding.ActivityRegisterBinding
import com.gasturah.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)

        binding.tvLogin.setOnClickListener{
            finish()
        }

        binding.btnRegister.setOnClickListener {
            checkPassword()
        }

        setupView()
    }

    private fun checkPassword(){
        val password  : String= binding.inputPassword.text.toString()
        val username : String = binding.inputUsername.text.toString()
        val name : String = binding.inputName.text.toString()
        if(username.isEmpty()){
            binding.inputUsername.error = "Username tidak boleh kosong"
        } else if(password.isEmpty()){
            Toast.makeText(this@RegisterActivity, "Password tidak boleh kosong",
                Toast.LENGTH_LONG).show();
        } else if(name.isEmpty()){
            binding.inputName.error = "Nama tidak boleh kosong"
        } else{
            register(username, password, name)
        }

    }

    fun register(username: String, password: String, name: String) {
        showLoading(true)
        ApiConfig.getApiService().register(username, name, password).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                showLoading(false)

                if (response.isSuccessful) {
                    if(response.body()!!.status == "success"){
                        Toast.makeText(this@RegisterActivity, "${response.body()!!.msg}",
                            Toast.LENGTH_LONG).show();
                        finish()
                    } else{
                        Toast.makeText(this@RegisterActivity, "${response.body()!!.msg}",
                            Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this@RegisterActivity, "${response.body()}",
                        Toast.LENGTH_LONG).show();
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@RegisterActivity, "Error : ${t.message}",
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