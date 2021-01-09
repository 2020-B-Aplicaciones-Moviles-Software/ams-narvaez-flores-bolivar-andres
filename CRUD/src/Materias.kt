import java.io.File
import java.io.PrintWriter

class Materias constructor(
    var id: Int,
    var nombre:String,
    var codigo:String,
    var horas: Int,
    var nomProf: String
){
    override fun toString(): String {
        return "$id;$nombre;$codigo;$horas;$nomProf"
    }

    fun ingresarMateria(archivo:String){
        File(archivo).appendText("\n$id;$nombre;$codigo;$horas;$nomProf")
    }

    fun eliminarMat(archivo:String){

        var list: MutableList<String> = File(archivo).readLines().toMutableList()
        var i:Int = 0
        while (i < list.size){
            var a = token(list[i])
            if (a[0].equals(id.toString())){
                list[i] = "empty"
                break
            }
            i++
        }
        val escribir = PrintWriter(archivo)
        escribir.print("")
        escribir.close()
        i=0
        while (i < list.size){
            if (!list[i].equals("empty")){
                if (i != 0){
                    File(archivo).appendText("\n${list[i]}")
                }else{
                    File(archivo).appendText("${list[i]}")
                }
            }
            i++
        }
        println(list)
    }

    fun actualizarMat(archivo: String){
        var mat:MutableList<String> = File(archivo).readLines().toMutableList()
        var i: Int=0
        while (i < mat.size){
            var a = token(mat[i])
            if (a[0].equals(id.toString())){
                mat[i] = "${id};${nombre};${codigo};${horas};${nomProf}"
                break
            }
            i++
        }
        val escribir = PrintWriter(archivo)
        escribir.print("")
        escribir.close()
        i = 0
        while (i < mat.size){
            if (i !=0)File(archivo).appendText("\n${mat[i]}")
            else File (archivo).appendText("${mat[i]}")
            i++
        }
    }
}