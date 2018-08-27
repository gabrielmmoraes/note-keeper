package com.gabrielmmoraes.notekeeper

// Usando object ao invés de classe, DataManager é inicializado como um Singleton
object DataManager {
    // Atribuindo objeto de classe HashMap à variável courses (as chaves são strings e os valores objetos da classe CourseInfo)
    val courses = HashMap<String, CourseInfo>()

    // Array de objetos NoteInfo
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }

    private fun initializeCourses() {
        // Inicializando um objeto CourseInfo
        var course = CourseInfo("android_intents", "Android Programming wih Intents")

        // Guardando objeto no HashMap
        // courses[course.courseId] = course é equivalente
        courses.set(course.courseId, course)


        course = CourseInfo("android_async", "Android Async Programming and Services")
        courses.set(course.courseId, course)

        // É possível mudar a ordem dos parâmetros usando "<nome_parâmetro> = <valor_de_inicialização>"
        course = CourseInfo(title = "Java Fundamentals: The Java Language", courseId = "java_lang")
        courses.set(course.courseId, course)

        course = CourseInfo("java_core", "Java Fundamentals: The Core Platform")
        courses.set(course.courseId, course)

    }

    private fun initializeNotes(){
        // Inicializando Notes
        var course = courses["android_intents"]!!
        var note = NoteInfo(course, "Dynamic intent resolution",
                "Wow, intents allow components to be resolved at runtime")
        notes.add(note)
        note = NoteInfo(course, "Delegating intents",
                "PendingIntents are powerful; they delegate much more than just a component invocation")
        notes.add(note)

        course = courses["android_async"]!!
        note = NoteInfo(course, "Service default threads",
                "Did you know that by default an Android Service will tie up the UI thread?")
        notes.add(note)
        note = NoteInfo(course, "Long running operations",
                "Foreground Services can be tied to a notification icon")
        notes.add(note)

        course = courses["java_lang"]!!
        note = NoteInfo(course, "Parameters",
                "Leverage variable-length parameter lists")
        notes.add(note)
        note = NoteInfo(course, "Anonymous classes",
                "Anonymous classes simplify implementing one-use types")
        notes.add(note)

        course = courses["java_core"]!!
        note = NoteInfo(course, "Compiler options",
                "The -jar option isn't compatible with with the -cp option")
        notes.add(note)
        note = NoteInfo(course, "Serialization",
                "Remember to include SerialVersionUID to assure version compatibility")
        notes.add(note)
    }
}