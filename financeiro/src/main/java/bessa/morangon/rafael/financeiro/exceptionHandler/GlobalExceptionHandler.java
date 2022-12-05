package bessa.morangon.rafael.financeiro.exceptionHandler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DataRegraNegocioException.class)
    public String defaultErrorHandler(DataRegraNegocioException e) throws Exception {
        return "exceptionDatasIguais";
    }

    @ExceptionHandler(value = DateTimeParseException.class)
    public String defaultErrorHandler(DateTimeParseException e) throws Exception {
        return "exceptionPrimeiraLinhaInvalida";
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public String defaultErrorHandler(FileNotFoundException e) throws Exception {
        return "exceptionArquivoNaoEncontrado";
    }

    @ExceptionHandler(value = UsuarioJaExisteException.class)
    public String defaultErrorHandler(UsuarioJaExisteException e) throws Exception {
        return "exceptionUsuarioJaExisteNoBanco";
    }

    @ExceptionHandler(value = ArrayIndexOutOfBoundsException.class)
    public String defaultErrorHandler(ArrayIndexOutOfBoundsException e) throws Exception {
        return "exceptionNãoEscolheuMesAnalise";
    }

}
