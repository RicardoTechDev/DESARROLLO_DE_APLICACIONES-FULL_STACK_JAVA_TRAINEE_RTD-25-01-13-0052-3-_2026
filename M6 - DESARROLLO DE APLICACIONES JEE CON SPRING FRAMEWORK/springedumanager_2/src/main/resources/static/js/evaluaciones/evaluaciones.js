    /*
     * Guardamos las referencias
     * a los elementos del modal.
     */

    const idEvaluacion = document.getElementById('idEvaluacion');
    const nombreEvaluacion = document.getElementById('nombreEvaluacion');
    const estudianteEvaluacion = document.getElementById('estudianteEvaluacion');
    const cursoEvaluacion = document.getElementById('cursoEvaluacion');
    const notaEvaluacion = document.getElementById('notaEvaluacion');
    const tituloModalEvaluacion = document.getElementById('tituloModalEvaluacion');

    function nuevaEvaluacion() {
        /*
         * Cambiamos el título.
         */
        tituloModalEvaluacion.textContent = 'Nueva Evaluación';
        /*
         * ID 0 significa:
         *
         * nuevo registro.
         */
        idEvaluacion.value = 0;
        /*
         * Limpiamos los campos.
         */
        nombreEvaluacion.value = '';
        estudianteEvaluacion.value = '';
        cursoEvaluacion.value = '';
        notaEvaluacion.value = '';
    }


    function editarEvaluacion(boton) {
        /*
         * Recuperamos los datos almacenados
         * dentro de los atributos data-*.
         */
        const id = boton.dataset.id;
        const nombre = boton.dataset.nombre;
        const estudiante = boton.dataset.estudiante;
        const curso = boton.dataset.curso;
        const nota = boton.dataset.nota;

        /*
         * Cambiamos el título.
         */
        tituloModalEvaluacion.textContent = 'Editar Evaluación';

        /*
         * Cargamos los datos existentes
         * dentro del formulario.
         */
        idEvaluacion.value = id;
        nombreEvaluacion.value = nombre;
        estudianteEvaluacion.value = estudiante;
        cursoEvaluacion.value = curso;
        notaEvaluacion.value = nota;
    }
