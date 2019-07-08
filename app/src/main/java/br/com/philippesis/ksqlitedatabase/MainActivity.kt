package br.com.philippesis.ksqlitedatabase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.philippesis.ksqlitedatabase.data.DatabaseHandler
import br.com.philippesis.ksqlitedatabase.data.models.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var dbHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init db
        dbHandler = DatabaseHandler(this)

        // On click save button
        button_save.setOnClickListener {
            if (validation()) {
                val user = User()
                val success: Boolean
                user.firstName = editText_firstName.text.toString()
                user.lastName = editText_lastName.text.toString()
                success = dbHandler!!.addUser(user)
                if (success) {
                    Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_LONG).show()
                }
            }
        }

        // On click show button
        button_show.setOnClickListener {
            val user = dbHandler!!.getAllUsers()
            textView_show.text = user
        }

    }

    private fun validation(): Boolean {
        var validate = false

        if(editText_firstName.text.toString().isNotEmpty() && editText_lastName.text.toString().isNotEmpty()) {
            validate = true
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
        }

        return validate
    }
}
