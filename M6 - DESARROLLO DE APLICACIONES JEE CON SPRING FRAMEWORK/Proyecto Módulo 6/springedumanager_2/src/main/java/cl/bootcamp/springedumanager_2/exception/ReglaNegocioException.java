package cl.bootcamp.springedumanager_2.exception;

public class ReglaNegocioException extends RuntimeException{
	public ReglaNegocioException(String mensaje) {
		super(mensaje);
	}
}
