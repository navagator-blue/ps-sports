package com.ps.sapesports.web.error

import org.slf4j.LoggerFactory
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class ErrorAttributes : DefaultErrorAttributes() {

    override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): MutableMap<String, Any> {
        val errorAttributes = super.getErrorAttributes(request, options)
        addErrorDetails(errorAttributes, request)
        return errorAttributes
    }

    private fun addErrorDetails(errorAttributes: MutableMap<String, Any>, request: ServerRequest) {
        when (val throwable = getError(request)) {
            is IllegalArgumentException -> {
                logger.error("Encountered exception: ", throwable)
                errorAttributes["message"] = throwable.message ?: "Unexpected error"
                errorAttributes["status"] = BAD_REQUEST.value()
                errorAttributes["error"] = BAD_REQUEST.reasonPhrase
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ErrorAttributes::class.java)
    }

}