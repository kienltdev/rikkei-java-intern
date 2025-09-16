package intern.rikkei.warehousesystem.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import intern.rikkei.warehousesystem.constant.ErrorCodes;
import intern.rikkei.warehousesystem.exception.ApiErrorResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        String message = messageSource.getMessage("error.accessDenied", null, request.getLocale());
        Object originalUri = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        String path = (originalUri != null) ? originalUri.toString() : request.getRequestURI();

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.FORBIDDEN.value())
                .code(ErrorCodes.ACCESS_DENIED)
                .message(message)
                .path(path)
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        OutputStream responseStream = response.getOutputStream();
        objectMapper.writeValue(responseStream, apiErrorResponse);
        responseStream.flush();
    }
}
