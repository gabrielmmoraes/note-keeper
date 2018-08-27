package com.gabrielmmoraes.notekeeper

// Declarando CourseInfo como classe que possui os atributos imutáveis (val) Id de curso e título
// (inicializados pelo construtor)

// Ela é declarada como Data Class pois possui apenas atributos.
// Essa declaração nos permite usar métodos como toString, equals e copy (criados automaticamente
// pelo Kotlin)
data class CourseInfo (val courseId: String, val title: String) {
    // Dando override no toString() para retornar o título do curso
    override fun toString(): String {
        return title
    }
}

// Declarando NoteInfo como classe que é relacionada a um objeto da classe CourseInfo,
// e que possui título e texto (todos inicializados pelo construtor)

// Definindo os atributos como nullable a fim de podermos criar um NoteInfo novo com valores NULL de
// inicialização
data class NoteInfo (var course: CourseInfo? = null, var title: String? = null, var text: String? = null)

