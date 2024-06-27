package com.banking.transactions.utils.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime

class ValidationException(override val message: String?): Exception(message)

data class ApiError(
    private val _message: Any?,
    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    val code: Int = status.value(),
    val timestamp: LocalDateTime = LocalDateTime.now()
){
    val message: String
        get() = (_message ?: "Something went wrong").toString()
}

@RestControllerAdvice
class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(exception: MethodArgumentNotValidException): Any {
        val errors: MutableMap<String, Any?> = HashMap()
        errors["status"] = "BAD_REQUEST"
        errors["code"] = 400
        exception.bindingResult.allErrors.forEach { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        }
        return errors
    }

    @ExceptionHandler(ValidationException::class)
    fun validationExceptionHandler(exception: Exception): ResponseEntity<ApiError> {
        val error = ApiError(exception.message, status = HttpStatus.BAD_REQUEST)
        return ResponseEntity(error, error.status)
    }
}
