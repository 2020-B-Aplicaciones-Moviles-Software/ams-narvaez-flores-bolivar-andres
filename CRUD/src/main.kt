import java.io.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList



fun main() {

    val fileEstudiantes:String = "D:/EPN/Aplicaciones Moviles/GitHub/ams-narvaez-flores-bolivar-andres/ListaEstudiantes.txt"
    val fileMaterias:String = "D:/EPN/Aplicaciones Moviles/GitHub/ams-narvaez-flores-bolivar-andres/Materias.txt"

    val listaEstudiantes = ArrayList<String>()
    val listaMaterias = ArrayList<String>()

    println("******Bienvenido seleccione una opción:******")
    println("1. Estudiantes")
    println("2. Materias")

    val opcion = Scanner(System.`in`)
    var op: Int = opcion.nextInt()

    when (op) {
        1 -> {
            println("******Bienvenido a ESTUDIANTES******")
            println("Lista de estudiantes: ")
            println("")
            imprimirListaAlumnos(listaEstudiantes)
            try {
                //listarEstudiantes(fileEstudiantes)
            }catch (e:Exception){
                print(e.message)
            }

            println("")
            println("***MENU DE OPCIONES***")
            println("1. Actualizar Estudiante")
            println("2. Ingresar Estudiante")
            println("3. Eliminar Estudiante")
            println("4. Mostrar los estudiantes por materia")
            println("")
            val opcion = Scanner(System.`in`)
            var op: Int = opcion.nextInt()

            when(op){
                1 -> {
                    println("Actualizar Alumno")
                    listarEstudiantes(fileEstudiantes)
                    println("Ingrese el iD del estudiante a actualizar: ")
                    val opcion = Scanner(System.`in`)
                    val opA = opcion.nextLine()
                    val est:String = busquedaEstID(fileEstudiantes, opA)
                    val idEst = token(est)
                    if (!est.equals("vacio")){
                        print("${est}\n")
                    }
                    println("Ingrese los nuevos datos:")
                    println("")
                    println("Nombre y Apellido del Alumno: ")
                    var nom:String = readLine().toString()
                    println("Fecha Nacimiento: ")
                    var date:String = readLine().toString()
                    println("Calificacion: ")
                    var cal:String = readLine().toString()
                    println("ID Materia: ")
                    var mat:String = readLine().toString()

                    var alumno:String = "$nom;$date;$cal;$mat"

                    actualizarEst(alumno, fileEstudiantes, idEst[0])
                    println("Estudiante actualizado")
                    return main()
                }
                2 -> {
                    println("Ingresar Alumno")
                    println("")

                    println("Nombre y Apellido del Alumno: ")
                    var nom:String = readLine().toString()
                    println("Fecha Nacimiento: ")
                    var date:String = readLine().toString()
                    println("Calificacion: ")
                    var cal:String = readLine().toString()
                    println("ID Materia: ")
                    var mat:String = readLine().toString()

                    var alumno:String = "$nom;$date;$cal;$mat"
                    //ingresarEstudiante(alumno.toString())
                    ingresarAlumno(alumno, fileEstudiantes)
                    println("Estudiante ingresado con éxito")
                    return main()
                }
                3 -> {
                    println("Ingrese el ID del alumno a elimnar")
                    val scan = Scanner(System.`in`)
                    val del = scan.nextLine()
                    val est:String = busquedaEstID(fileEstudiantes, del)
                    if(!est.equals("vacio")){
                        eliminarEstudiante(est, fileEstudiantes)
                        println("Estudiante eliminado con éxito")
                        return main()
                    }else{
                        println("No existe el alumno")
                        return main()
                    }
                }
                4 -> {
                    println("******Estudiantes por Materia******")
                    listarEstudiantes(fileEstudiantes)
                    println("Ingrese el ID de la Materia")
                    val scan = Scanner(System.`in`)
                    val ide = scan.nextInt()
                    println("Estos son los estudiantes de la Materia ${ide}")
                    mostrarMatxEst(fileEstudiantes, fileMaterias, ide.toString())
                    return main()
                }
            }
        }
        2 -> {
            println("******Bienvenido a MATERIAS******")
            println("Lista de Materias: ")
            println("")
            //imprimirMaterias(fileMaterias)
            imprimirListaMaterias(listaMaterias)
            println("")
            println("***MENU DE OPCIONES***")
            println("1. Actualizar Materia")
            println("2. Ingresar Materia")
            println("3. Eliminar Materia")
            println("")
            val opcion = Scanner(System.`in`)
            var op1: Int = opcion.nextInt()

            when(op1){
                1 -> {
                    println("Actualizar Materia")
                    imprimirMaterias(fileMaterias)
                    println("Ingrese el Id de la materia a actualizar: ")
                    val opcion = Scanner(System.`in`)
                    val opA = opcion.nextLine()
                    val mat:String = busquedaMatID(fileMaterias, opA)
                    val idMat = token(mat)
                    if (!mat.equals("vacio")){
                        print("${mat}\n")
                    }
                    println("Ingrese los nuevos datos:")
                    println("Nombre de la Materia: ")
                    var nom:String = readLine().toString()
                    println("Codigo: ")
                    var cod:String = readLine().toString()
                    println("Horas: ")
                    var hor:String = readLine().toString()
                    println("Profesor: ")
                    var prof:String = readLine().toString()

                    var materia:String = "$nom;$cod;$hor;$prof"

                    actualizarMat(materia, fileMaterias, idMat[0])
                    println("Materia actualizada")
                    return main()
                }
                2 -> {
                    println("Ingresar Materia")
                    println("")

                    println("Nombre de la Materia: ")
                    var nom:String = readLine().toString()
                    println("Codigo: ")
                    var cod:String = readLine().toString()
                    println("Horas: ")
                    var hor:String = readLine().toString()
                    println("Profesor: ")
                    var prof:String = readLine().toString()

                    var materia:String = "$nom;$cod;$hor;$prof"

                    ingresarMateria(materia, fileMaterias)
                    println("Materia ingresada con éxito")
                    return main()

                }
                3 -> {
                    println("Ingrese el ID de la Materia a elimnar")
                    val scan = Scanner(System.`in`)
                    val del = scan.nextLine()
                    val mat:String = busquedaMatID(fileMaterias, del)
                    if(!mat.equals("vacio")){
                        eliminarMateria(mat, fileMaterias)
                        println("Materia eliminada con éxito")
                        return main()
                    }else{
                        print("No existe la materia")
                        return main()
                    }
                }
            }
        }
        else -> {
            println("Seleccione la opción correcta")
            return main()
        }
    }
}


//FUNCIONES PARA IMPRIMIR LISTAS
fun imprimirListaAlumnos(
    estudiantesList:ArrayList<String>
){

    val estudiantesFile = File("D:/EPN/Aplicaciones Moviles/GitHub/ams-narvaez-flores-bolivar-andres/ListaEstudiantes.txt")

    estudiantesFile.useLines {
            lines -> lines.forEach {
        estudiantesList.add(it)
    }
    }
    estudiantesList.forEach {
            line ->
        println("$line")
    }
}
fun imprimirListaMaterias(
    materiasList:ArrayList<String>
){

    val materiasFile = File("D:/EPN/Aplicaciones Moviles/GitHub/ams-narvaez-flores-bolivar-andres/Materias.txt")

    materiasFile.useLines {
            lines -> lines.forEach {
        materiasList.add(it)
    }
    }
    materiasList.forEach {
            line ->
        println("$line")
    }
}

fun listarEstudiantes(file:String){
    val lineas:List<String> = File(file).readLines()
    var i:Int = 0
    while (i < lineas.size){
        var est = token(lineas[i])
        var estAl = AlumnoCl(
            est[0].toInt(),
            est[1],
            est[2],
            est[3],
            est[4].toInt()
        ).toString()
        println(estAl)
        i++
    }
}
fun imprimirMaterias(file:String){
    val lineas:List<String> = File(file).readLines()
    var i:Int = 0
    while (i < lineas.size){
        var mat = token(lineas[i])
        var matAl = Materias(
            mat[0].toInt(),
            mat[1],
            mat[2],
            mat[3].toInt(),
            mat[4]
        ).toString()
        println(matAl)
        i++
    }
}

fun token(
    entrada:String
): List<String> {
    val linea = entrada.split(";")
    return linea
}

// FUNCIONES PARA INGRESAR
fun ingresarMateria(
    ent:String,
    file: String
){
    var mat = token(ent)
    var id: Int = idMat(file)
    var matN = Materias(
        id,
        mat[0],
        mat[1],
        mat[2].toInt(),
        mat[3]
    ).ingresarMateria(file)
}

fun ingresarAlumno(
    ent: String,
    file: String
){
    var est = token(ent)
    var id: Int = idEst(file)
    var estN = AlumnoCl(
        id,
        est[0],
        est[1],
        est[2],
        est[3].toInt()
    ).crearAlumno(file)
}
fun ingresarEstudiante(str: String){

    try {
        var fo = FileWriter(
            "D:/EPN/Aplicaciones Moviles/GitHub/ams-narvaez-flores-bolivar-andres/ListaEstudiantes.txt",
            true
        )
        fo.write(str)
        fo.close()
    }catch (ex:Exception){
        print(ex.message)
    }
}

//FUNCIONES PARA ACTUALIZAR
fun actualizarEst(
    entrada:String,
    file: String,
    id: String
){
    var est = token(entrada)
    var nEst = AlumnoCl(
        id.toInt(),
        est[0],
        est[1],
        est[2],
        est[3].toInt()
    ).actualizarEst(file)
}

fun actualizarMat(
    ent: String,
    file: String,
    id: String
){
    var mat = token(ent)
    var nEst = Materias(
        id.toInt(),
        mat[0],
        mat[1],
        mat[2].toInt(),
        mat[3]
    ).actualizarMat(file)
}

//FUNCIONES PARA BUSCAR
fun buscarEstudiante(
    list:ArrayList<String>,
    fd:String
){
    list.forEachIndexed{
            i, line ->
        if(i == fd.toInt()){
            println("$line")
        }
    }
}

fun buscarMateriasID(
    file:String,
    id: String
):String{
    val lineas:List<String> = File(file).readLines()
    var i: Int = 0
    while (i < lineas.size){
        var mat = token(lineas[i])
        if(mat[0].equals(id)){
            return lineas.get(i)
        }
        i++
    }
    return "vacio"
}

fun idEst(
    file:String
):Int{
    val linea:List<String> = File(file).readLines()
    var id = token(linea[(linea.size - 1)])
    return (id[0].toInt() + 1)
}

fun idMat(
    file: String
):Int{
    val linea:List<String> = File(file).readLines()
    var id = token(linea[(linea.size - 1)])
    return (id[0].toInt() + 1)
}
fun busquedaEstID(
    list:String,
    id:String
):String{
    val lineas: List<String> = File(list).readLines()
    var i:Int = 0
    while(i < lineas.size){
        var est =token(lineas[i])
        if (est[0].equals(id)){
            return lineas.get(i)
        }
        i++
    }
    return "vacio"
}

fun busquedaMatID(
    list: String,
    id: String
):String{
    val lineas: List<String> = File(list).readLines()
    var i:Int = 0
    while(i < lineas.size){
        var mat =token(lineas[i])
        if (mat[0].equals(id)){
            return lineas.get(i)
        }
        i++
    }
    return "vacio"
}

//FUNCIONES PARA MOSTRAR
fun mostrarMatxEst(
    fileS: String,
    fileM:String,
    id: String
){
    val linea:List<String> =File(fileS).readLines()
    var i:Int = 0
    while (i<linea.size){
        var est = token(linea[i])
        if (est[4].equals(id)){
            var estA = AlumnoCl(
                est[0].toInt(),
                est[1],
                est[2],
                est[3],
                est[4].toInt()
            )
            var mat = token(busquedaEstID(fileM,est[4]))
            println(estA.toString()+";" + "${mat[1]};${mat[2]}")
        }
        i++
    }
}
fun mostrarEstID(
    fileE:String,
    fileS:String,
    id:String
){
    var est = token(busquedaEstID(fileE, id))
    var estA = AlumnoCl(
        est[0].toInt(),
        est[1],
        est[2],
        est[3],
        est[4].toInt()
    )
    var Mat = token(buscarMateriasID(fileS, est[4]))
    println(estA.toString() + "${Mat[1]} ${Mat[2]}")
}

//FUNCIONES PARA ELIMINAR
fun eliminarEstudiante(
    entrada: String,
    list:String
){
    var est = token(entrada)
    var deleteStudent = AlumnoCl(
        est[0].toInt(), est[1],
        est[2], est[3], est[4].toInt()
    ).eliminarEst(list)
}

fun eliminarMateria(
    ent: String,
    list: String
){
    var est = token(ent)
    var deleteStudent = Materias(
        est[0].toInt(), est[1],
        est[2], est[3].toInt(), est[4]
    ).eliminarMat(list)
}
