/*
 * =========================================================
 * REFERENCIAS A LOS ELEMENTOS DEL MODAL
 * =========================================================
 *
 * Guardamos en constantes los elementos
 * del formulario que posteriormente
 * modificaremos con JavaScript.
 */


/*
 * Campo oculto que contiene
 * el ID de la evaluación.
 *
 * id = 0
 *      → nueva evaluación
 *
 * id diferente de 0
 *      → editar evaluación
 */
const idEvaluacion =
    document.getElementById(
        'idEvaluacion'
    );


/*
 * Campo correspondiente al
 * nombre de la evaluación.
 *
 * Ejemplo:
 *
 * Prueba 1
 * Proyecto Final
 * Examen
 */
const nombreEvaluacion =
    document.getElementById(
        'nombreEvaluacion'
    );


/*
 * Campo correspondiente a
 * la ponderación.
 *
 * Ejemplo:
 *
 * 30
 *
 * significa:
 *
 * 30%
 */
const ponderacionEvaluacion =
    document.getElementById(
        'ponderacionEvaluacion'
    );


/*
 * Select que contiene
 * los cursos disponibles.
 *
 * El usuario verá:
 *
 * Java
 * Python
 * Spring Boot
 *
 * pero el value será:
 *
 * curso.id
 *
 * Ejemplo:
 *
 * value="2"
 */
const cursoEvaluacion =
    document.getElementById(
        'cursoEvaluacion'
    );


/*
 * Título del modal.
 *
 * Lo cambiaremos dinámicamente entre:
 *
 * Nueva Evaluación
 *
 * y
 *
 * Editar Evaluación
 */
const tituloModalEvaluacion =
    document.getElementById(
        'tituloModalEvaluacion'
    );



/*
 * =========================================================
 * NUEVA EVALUACIÓN
 * =========================================================
 *
 * Esta función se ejecuta cuando
 * presionamos:
 *
 * + Nueva Evaluación
 *
 *
 * Su objetivo es:
 *
 * - cambiar el título;
 * - colocar ID en 0;
 * - limpiar los campos;
 * - dejar el select sin curso seleccionado.
 */
function nuevaEvaluacion() {


    /*
     * Cambiamos el título
     * del modal.
     */
    tituloModalEvaluacion.textContent =
        'Nueva Evaluación';



    /*
     * ID = 0 indica al backend
     * que estamos creando
     * un nuevo registro.
     */
    idEvaluacion.value = 0;



    /*
     * Limpiamos el nombre.
     */
    nombreEvaluacion.value = '';



    /*
     * Limpiamos la ponderación.
     */
    ponderacionEvaluacion.value = '';



    /*
     * Dejamos el select
     * sin curso seleccionado.
     *
     * Para que esto funcione
     * debemos tener una opción:
     *
     * <option value="">
     *     Seleccione un curso
     * </option>
     */
    cursoEvaluacion.value = '';

}



/*
 * =========================================================
 * EDITAR EVALUACIÓN
 * =========================================================
 *
 * Esta función recibe el botón
 * que fue presionado.
 *
 *
 * Ejemplo:
 *
 * editarEvaluacion(this)
 *
 *
 * El botón tendrá información
 * almacenada utilizando atributos:
 *
 * data-id
 * data-nombre
 * data-ponderacion
 * data-curso
 */
function editarEvaluacion(
        boton) {


    /*
     * =====================================================
     * RECUPERAR DATOS DEL BOTÓN
     * =====================================================
     *
     * dataset permite recuperar
     * los atributos data-*.
     *
     *
     * Ejemplo:
     *
     * data-id="3"
     *
     * se recupera mediante:
     *
     * boton.dataset.id
     */


    /*
     * ID de la evaluación.
     */
    const id =
        boton.dataset.id;



    /*
     * Nombre de la evaluación.
     */
    const nombre =
        boton.dataset.nombre;



    /*
     * Ponderación actual.
     */
    const ponderacion =
        boton.dataset.ponderacion;



    /*
     * ID del curso al que
     * pertenece la evaluación.
     *
     * IMPORTANTE:
     *
     * aquí recibiremos el ID,
     * no el nombre del curso.
     *
     * Ejemplo:
     *
     * Java tiene ID 2
     *
     * data-curso="2"
     */
    const curso =
        boton.dataset.curso;



    /*
     * =====================================================
     * CAMBIAR TÍTULO
     * =====================================================
     */
    tituloModalEvaluacion.textContent =
        'Editar Evaluación';



    /*
     * =====================================================
     * CARGAR DATOS EN EL FORMULARIO
     * =====================================================
     */


    /*
     * Cargamos el ID.
     */
    idEvaluacion.value =
        id;



    /*
     * Cargamos el nombre.
     */
    nombreEvaluacion.value =
        nombre;



    /*
     * Cargamos la ponderación.
     */
    ponderacionEvaluacion.value =
        ponderacion;



    /*
     * Seleccionamos automáticamente
     * el curso correspondiente.
     *
     * Ejemplo:
     *
     * curso = 2
     *
     * JavaScript buscará en el <select>
     * la opción cuyo:
     *
     * value="2"
     *
     * y la seleccionará.
     */
    cursoEvaluacion.value =
        curso;

}