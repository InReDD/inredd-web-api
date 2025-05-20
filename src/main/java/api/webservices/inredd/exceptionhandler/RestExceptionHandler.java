package api.webservices.inredd.web.exception;

import api.webservices.inredd.service.exception.GroupNotEmptyException;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    // DTO simples, compatível com Java 11
    public static class ErrorDTO {
        private String error;
        private String message;

        public ErrorDTO(String error, String message) {
            this.error   = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }

    @ExceptionHandler(GroupNotEmptyException.class)
    public ResponseEntity<ErrorDTO> handleGroupNotEmpty(GroupNotEmptyException ex) {
        ErrorDTO body = new ErrorDTO(
            "GROUP_NOT_EMPTY",
            ex.getMessage()
        );
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(EntityNotFoundException ex) {
        ErrorDTO body = new ErrorDTO(
            "NOT_FOUND",
            ex.getMessage()
        );
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(body);
    }
}
