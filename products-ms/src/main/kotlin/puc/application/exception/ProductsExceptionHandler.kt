package puc.application.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import puc.domain.products.services.exceptions.ProductNotFoundException
import java.time.LocalDate

@RestControllerAdvice
class ProductsExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ProblemDetail {
        val problemDetail: ProblemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, e.localizedMessage)
        problemDetail.title = "Fill the fields correctly"
        problemDetail.detail = "${e.message}"
        problemDetail.setProperty("timestamp", LocalDate.now())
        return problemDetail
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(e: ProductNotFoundException): ProblemDetail {
        val problemDetail: ProblemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.localizedMessage)
        problemDetail.title = "Product not found"
        problemDetail.detail = "${e.message}"
        problemDetail.setProperty("timestamp", LocalDate.now())
        return problemDetail
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(e: ResponseStatusException): ProblemDetail {
        val problemDetail: ProblemDetail = ProblemDetail.forStatusAndDetail(e.statusCode, e.localizedMessage)
        problemDetail.title = e.body.title
        problemDetail.detail = e.body.detail
        problemDetail.setProperty("timestamp", LocalDate.now())
        return problemDetail
    }
}