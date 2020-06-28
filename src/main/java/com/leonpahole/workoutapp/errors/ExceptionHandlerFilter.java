package com.leonpahole.workoutapp.errors;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonpahole.workoutapp.dto.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {

            HttpStatus exStatus = HttpStatus.INTERNAL_SERVER_ERROR;

            if (ex instanceof JwtException) {
                exStatus = HttpStatus.UNAUTHORIZED;
            }

            ApiError apiError = new ApiError(exStatus);
            apiError.setMessage(ex.getMessage());

            response.setStatus(apiError.getStatus().value());
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(convertErrorToJson(apiError));
        }
    }

    public String convertErrorToJson(ApiError error) throws JsonProcessingException {
        if (error == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return mapper.writeValueAsString(error);
    }
}
