package br.com.sudosu.appdenoticiasadmin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.sudosu.appdenoticiasadmin.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnPub.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val desc = binding.edtDesc.text.toString()
            val pubDt = binding.edtPubDt.text.toString()
            val author = binding.edtAuthor.text.toString()

            if (title.isEmpty() || desc.isEmpty() || pubDt.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos",Toast.LENGTH_SHORT).show()
            }else{
                saveNews(title, desc, pubDt, author,)
            }
        }
    }

    private fun saveNews(
        titulo: String,
        desc: String,
        pubDt: String,
        author: String,
    ){

        val mapNews = hashMapOf(
            "titulo" to titulo,
            "desc" to desc,
            "pubDt" to pubDt,
            "author" to author,
        )

        db.collection("noticias").document("noticia")
            .set(mapNews)
            .addOnCompleteListener{ tarefa ->
                if (tarefa.isSuccessful){
                    Toast.makeText(this, "Notícia publicada com sucesso!",Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            }
            .addOnFailureListener {  }
    }

    private fun clearFields(){
        binding.edtTitle.setText("")
        binding.edtDesc.setText("")
        binding.edtPubDt.setText("")
        binding.edtAuthor.setText("")
    }
}