import java.io.File
import java.io.PrintWriter

class AlumnoCl constructor(

    var id:Int,
    var nombre:String,
    var fecha:String,
    var nota:String,
    var materia:Int
) {

    override fun toString(): String {
        return "$id;$nombre;$fecha;$nota;$materia"
    }

    fun crearAlumno(archivo:String){
        File(archivo).appendText("\n$id;$nombre;$fecha;$nota;$materia")
    }

    fun eliminarEst(archivo:String){

        var list: MutableList<String> = File(archivo).readLines().toMutableList()
        var i:Int = 0
        while (i < list.size){
            var a = token(list[i])
            if (a[0].equals(id.toString())){
                list[i] = "vacio"
                break
            }
            i++
        }
        val escribir = PrintWriter(archivo)
        escribir.print("")
        escribir.close()
        i=0
        while (i < list.size){
            if (!list[i].equals("vacio")){
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

    fun actualizarEst(archivo: String){
        var est:MutableList<String> = File(archivo).readLines().toMutableList()
        var i: Int=0
        while (i < est.size){
            var a = token(est[i])
            if (a[0].equals(id.toString())){
                est[i] = "${id};${nombre};${fecha};${nota};${materia}"
                break
            }
            i++
        }
        val write = PrintWriter(archivo)
        write.print("")
        write.close()
        i=0
        while (i < est.size){
            if (i !=0)File(archivo).appendText("\n${est[i]}")
            else File (archivo).appendText("${est[i]}")
            i++
        }
    }
}