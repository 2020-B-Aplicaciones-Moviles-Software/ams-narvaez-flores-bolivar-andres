import java.util.*
import kotlin.collections.ArrayList

fun main(){
    println("xddddd")
    //declaración de variables
    /*Ejemplo en Java Int = 12;*/
    var edadProfesor = 31
    //var edadProfesor: Int = 31
    var sueldoProfesor = 12.34
    //var sueldoProfesor: Double = 12.34
    //Duck Typing

    //VARIABLES MUTABLES / VARIABLES INMUTABLES
    //MUTABLES
    var edadCachorro: Int = 0
    edadCachorro = 1
    edadCachorro = 2
    edadCachorro = 3
    //*INMUTABLES=variables que no pueden ser reasignadas.*//
    val numeroCedula = 1721132445
    //numeroCedula = 12

    //TIPOS DE VARIABLES
    val nombreProf: String = "Andrex Narvaez"
    val sueldo: Double = 12.2
    val estadoCivil: Char = 'S'
    val fechaNac: Date = Date()

    //CONDICIONALES

    if(true){
        //verdadera
    }else{
        //cosas falsas
    }

    when(sueldo){
        12.2 -> { //Inicio bloque
            println("Sueldo normal")
        }//fin Bloque
        -12.2 -> println("Sueldo negativo")
        else -> println("Sueldo no reconocido")
    }

    val sueldoMayorAEstablecido:Int = if(sueldo > 12.2) 500 else 0
    //condicion ? bloque-true : bloque-false

    imprimirNombre("Andrex")
    //imprimirNombre()

    calcularSueldo(1000.00)
    //calcularSueldo(1000.00)
    calcularSueldo(1000.00, 14.00)
    //calcularSueldo(1000.00, 14.00)
    calcularSueldo(1000.00, 12.00, 3)
    //calcularSueldo(1000.00, 12.00, 3)

    //Named Parameters
    calcularSueldo(
            1000.00,
            //        12.00,
            calculoEspecial = 3
    )

    //Arreglos Estáticos
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    //arregloEstatico.add(12) -> NO TENEMOS AQUI, NO SE PUEDE MODIFICAR LOS ELEMENTOS DEL ARREGLOS

    //Arreglos Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //OPERADORES = Sirven para los arreglos estáticos y dinámicos
    arregloEstatico.forEach {  }
    arregloDinamico.forEach {  }
    //FOREACH
    val respuestaForEach:Unit = arregloDinamico
            .forEach{
                valorActualIteracion -> //antes de la flecha van los parametros y despue viene la ejecución del código de la función
                println("Valor: ${valorActualIteracion}")
            }
    println(respuestaForEach)
    arregloDinamico
            .forEachIndexed{ indice, valorActualIteracion ->
                println("Valor: ${valorActualIteracion} Indice: ${indice}")
            }

    //MAP -> Modifica cada uno de los elementos del arreglo
    //1) Enviamos el nuevo valor de la iteracion
    //2) Nos devuelve es un nuevo arreglo con los valores modificados

    val respuestaMap:List<Int> = arregloDinamico
            .map { valorActualIteracion ->
                //calculo1
                //calculo2
                return@map valorActualIteracion * 10
            }
    println(respuestaMap)

    val respuestaMapDos:List<Int> = arregloDinamico
            .map { it + 15}
    //        .map { i -> i + 15 }
    println(respuestaMapDos)

    //Filter -> FILTRAR EL ARREGLO
    //1) Devolver una expresion (TRUE o FALSE)
    //2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
            .filter { valorActualIteracion ->
                val mayoresACinco: Boolean = valorActualIteracion > 5
                return@filter mayoresACinco
            }
    println(respuestaFilter)

    //Any All -> Condicion -> Boolean
    //OR <-> AND
    //OR = Any
    //OR (Es falso si todos son falsos)
    //OR (Es verdadero si un es verdadero)
    //AND (Es falso si uno es falso)
    //AND (Es verdadero si todos son verdaderos)

    val respuestaAny: Boolean = arregloDinamico
            .any{
                valorActualIteracion ->
                return@any (valorActualIteracion > 5) //si esto se cumple devuelve verdadero.
            }
    println(respuestaAny) //true

    val respuestaAll: Boolean = arregloDinamico
            .all {
                valorActualIteracion ->
                return@all valorActualIteracion > 5 //
            }
    println(respuestaAll) //false

    //REDUCE
    // 1) devuelve el acumulado
    // 2) En que valor empieza
    //{1, 2, 3, 4, 5}
    // 0 = 0 + 1
    // 1 = 1 + 2
    // 3 = 3 + 3
    // 6 = 6 + 4
    // 10 = 10 + 5
    // 15

    val respuestaFilter1: Int = arregloDinamico
            .reduce { //acumulado = 0
                acumulado, valorActualIteracion ->
                return@reduce acumulado + valorActualIteracion
            }
    println(respuestaFilter1) //78

    val respuestaReduceFold = arregloDinamico
            .fold(
                    100,
                    {
                        acumulado, valorActualIteracion ->
                        return@fold acumulado -valorActualIteracion //del valor inicial resta el reduce
                    }
            )
    println(respuestaReduceFold) //22

    //arreploMutable.fold (empieza desde el principio)
    //arreploMutable.foldRight (empieza desde el final)
    //arreploMutable.reduce (empieza desde el principio)
    //arreploMutable.reduceRight (empieza desde el final)

    val vidaActual:Double = arregloDinamico
            .map { it * 2.3 } // arreglo
            .filter { it > 20 } // arreglo
            .fold(100.00, {acc, i -> acc - i}) // valor
            .also{ println(it)} //ejecutar codigo extra
    println("valor vida actual: ${vidaActual}") //3.40

    val ejemploUno = Suma(1,2)
    val ejemploDos = Suma(null,2)
    val ejemploTres = Suma(1,null)
    val ejemploCuatro = Suma(null,null)

    println(ejemploUno.sumar())
    println(Suma.historialSumas)
    println(ejemploDos.sumar())
    println(Suma.historialSumas)
    println(ejemploTres.sumar())
    println(Suma.historialSumas)
    println(ejemploCuatro.sumar())
    println(Suma.historialSumas)

}//FIN bloque main

fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}") //template Strings
}
fun calcularSueldo(
        sueldo: Double, //Requerido
        tasa: Double = 12.00, //Opcionales
        calculoEspecial: Int? = null //Variables que PUEDEN ser null //si utilizamos esto debemos hacer la verificación con un if
): Double {
    if(calculoEspecial == null){
        return sueldo * (100 / tasa)
    }else{
        return sueldo * (100 / tasa) * calculoEspecial
    }

}



abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor( //Constructor primario
            uno: Int,
            dos: Int
    ) { //Bloque de codigo constructor primario
        //this.numeroUno
        numeroUno = uno
        //this.numeroDos
        numeroDos = dos
    }
}

//instancia.numeroUNo
//instancia.numeroUNo
abstract class Numeros(
        protected var numeroUno: Int,
        protected var numeroDos: Int
) {
    init { //bloque de codigo del constructor primario
        //println("HOLA")
    }
}


class Suma(
        uno: Int, //parametros
        dos: Int //parametros
):Numeros(uno, dos){
    init {
        //this.numeroUno
        //this.numeroDos
        //X -> this.uno -> NO EXISTE
        //X -> this.dos -> NO EXISTE
    }

    constructor( //segundo constructor
            uno: Int?,
            dos: Int
    ) : this( //llamada del constructor primario
            if (uno == null) 0 else uno,
            dos
    ) {

    }

    constructor( //tercer constructor
            uno: Int,
            dos: Int?
    ) : this( //llamada del constructor primario
            uno,
            if (dos == null) 0 else dos
    ) {

    }

    constructor( //cuarto constructor
            uno: Int?,
            dos: Int?
    ) : this( //llamada del constructor primario
            if (uno == null) 0 else uno,
            if (dos == null) 0 else dos
    ) {

    }

    public fun sumar(): Int{
        //this.numeroUno
        //this.numeroDos
        val total: Int = numeroUno + numeroDos
        Suma.agregarHistorial(total)
        return total
    }

    //SINGLETON
    //existe un solo objeto por todas las clases que existan
    companion object{ //Metodos y propiedades
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(nuevaSuma: Int){
            this.historialSumas.add(nuevaSuma)
        }
    }
}

class BaseDeDatos(){ //para almacenar los datos
    companion object{
        val datos = arrayListOf<Int>()
    }
}

//BaseDeDatos.datos