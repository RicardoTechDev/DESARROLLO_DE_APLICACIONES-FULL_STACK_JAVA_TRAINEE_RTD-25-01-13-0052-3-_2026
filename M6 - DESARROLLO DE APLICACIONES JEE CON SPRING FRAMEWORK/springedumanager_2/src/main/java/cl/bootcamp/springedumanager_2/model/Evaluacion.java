package cl.bootcamp.springedumanager_2.model;

public class Evaluacion {
    private int id;
    private String nombre;
    private String estudiante;
    private String curso;


    /*
     * Nota obtenida.
     *
     * Usamos double para trabajar
     * con valores decimales:
     *
     * 5.5
     * 6.2
     * 4.8
     */
    private double nota;



    /*
     * Constructor vacío.
     */
    public Evaluacion() {
    }



    /*
     * Constructor con parámetros.
     *
     * Lo utilizaremos para crear
     * datos iniciales de ejemplo.
     */
    public Evaluacion(
            int id,
            String nombre,
            String estudiante,
            String curso,
            double nota) {


        this.id = id;

        this.nombre = nombre;

        this.estudiante = estudiante;

        this.curso = curso;

        this.nota = nota;
    }



    /*
     * GETTERS Y SETTERS
     */


    public int getId() {

        return id;
    }


    public void setId(int id) {

        this.id = id;
    }


    public String getNombre() {

        return nombre;
    }


    public void setNombre(String nombre) {

        this.nombre = nombre;
    }


    public String getEstudiante() {

        return estudiante;
    }


    public void setEstudiante(
            String estudiante) {

        this.estudiante = estudiante;
    }


    public String getCurso() {

        return curso;
    }


    public void setCurso(
            String curso) {

        this.curso = curso;
    }


    public double getNota() {

        return nota;
    }


    public void setNota(double nota) {

        this.nota = nota;
    }

}