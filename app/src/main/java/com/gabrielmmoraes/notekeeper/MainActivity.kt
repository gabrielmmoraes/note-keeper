package com.gabrielmmoraes.notekeeper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        /*
         Usando classe Adapter para popular o spinner
         (não usado mais pois DataManager se tornou um Singleton a fim de ser usado em diferentes activities)
         val dm = DataManager()
        */

        // Inicializando ArrayAdapter
        val adapterCourses = ArrayAdapter<CourseInfo>(this,
                // Layout padrão para spinner (um dos dois necessários - responsável pelo campo que mostra o item selecionado)
                android.R.layout.simple_spinner_item,

                /*
                 Pegando os cursos do HashTable e transformando em lista
                 Abaixo como seria no caso de DataManager sendo uma classe
                 dm.courses.values.toList())
                */
                DataManager.courses.values.toList())

        // Segundo layout necessário para o spinner (dropdown)
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Atribuindo adapter ao elemento spinner do content_main
        spinnerCourses.adapter = adapterCourses

        // Checa se há uma instância salva da Activity
        // Retorna o valor de NOTE_POSITION, caso exista, ou POSITION_NOT_SET, caso contrário
        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
                // Se for InstanceState for NULL, retorna o conteúdo de Extra
                intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET)
            displayNote()
        // Caso uma posição não esteja definida, cria-se uma nova nota
        else {
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true

            // Caso R.id possua a ação action_next, queremos executar o código em seguida
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        // Checar se o usuário está no última index da lista de notas
        if(notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu?.findItem(R.id.action_next)
            // Caso encontre a referência
            if(menuItem != null){
                // Mudar ícone
                menuItem.icon = getDrawable(R.drawable.ic_block_white_24dp)
                // Desabilitar botão
                menuItem.isEnabled = false
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    // Quando sair da Activity executar o código abaixo
    override fun onPause() {
        super.onPause()
        saveNote()
    }

    // Quando for salvar a Instance para trocar de Activity
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // Salvar em outState o valor de notePosition
        outState?.putInt(NOTE_POSITION, notePosition)
    }

    // Passa para a próxima anotação
    private fun moveNext() {
        ++notePosition
        displayNote()

        // Chamando invalidateOptionsMenu, pois ele chama a função onPrepareOptionsMenu
        // Isso tudo será usado para checar se a lista acabou e assim evitar o usuário de acessar memória não reservada
        invalidateOptionsMenu()
    }


    private fun displayNote() {
        // Acessa lista de Notes e passa seus atributos para a Activity
        val note = DataManager.notes[notePosition]
        textNoteTitle.setText(note.title)
        textNoteText.setText(note.text)

        val coursePosition = DataManager.courses
                // Queremos apenas os valores do HashMap
                .values
                // E com esses valores pegamos o index do curso que queremos
                .indexOf(note.course)

        // Selecionar o curso do index que definimos
        spinnerCourses.setSelection(coursePosition)
    }


    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        // Pegando título do campo e salvando
        note.title = textNoteTitle.text.toString()
        // Pegando texto do campo e salvando
        note.text = textNoteText.text.toString()
        // Castando o curso selecionado no spinner como CourseInfo
        note.course = spinnerCourses.selectedItem as CourseInfo
    }

}
