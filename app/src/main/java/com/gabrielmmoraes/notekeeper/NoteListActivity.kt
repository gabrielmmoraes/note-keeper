package com.gabrielmmoraes.notekeeper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter

import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.content_note_list.*

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(toolbar)

        // Mudança de activity ao apertar o botão
        // Activities são inicializadas a partir da transmissão de um Intent, que aponta para a activity que está sendo chamada
        fab.setOnClickListener { view ->

            // Inicializando o Intent
            val activityIntent = Intent(this,

                    // Passando a classe MainActivity (que aponta para a activity com o spinner)
                    // do Kotlin como .java, pois a API do android espera classes em Java
                    MainActivity::class.java)

            startActivity(activityIntent)
        }

        // Poputando listNotes com adapter (similar ao processor de popular o spinner)
        listNotes.adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                DataManager.notes)

        // Usando expressão lambda {dentro de chaves} para não precisar implementar a interface funcional (AdapterView.OnItemClickListener)
        // Formato: {parâmetros -> implementação}
        listNotes.setOnItemClickListener{parent, view, position, id ->

            // A fim de inicializar outra Activity é necessário criar um Intent
            val activityIntent = Intent(this, MainActivity::class.java)

            // Adicionando informação ao Intent com padrão chave (com o nome que quisermos) e valor
            // Esse Extra passa a posição do elemento na lista (index)
            // A partir dessa informação é possível saber qual curso queremos passar de parâmetro para a próxima Activity.
            // NOTE_POSITION é uma constante declarada no arquivo Constant.kt
            activityIntent.putExtra(NOTE_POSITION, position)
            startActivity(activityIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Passar listNotes atualizado para o listNotes da Activity (castado como ArrayAdapter)
        (listNotes.adapter as ArrayAdapter<NoteInfo>)
                // Método que informa à Activity que houve mudança no DataSet
                .notifyDataSetChanged()
    }
}
