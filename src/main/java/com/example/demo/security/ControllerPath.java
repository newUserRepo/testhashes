package com.example.demo.security;

import com.example.demo.util.TypesFields;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//redireccion a google en caso de error 404 not found!!!
@RestController
public class ControllerPath implements ErrorController {

    @ExceptionHandler(RuntimeException.class)
    @RequestMapping(method = RequestMethod.GET)
    private void getError(final RuntimeException ex, final HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(getErrorPath());
    }

    @Override
    public String getErrorPath() {
        return TypesFields.ERROR;
    }
}
