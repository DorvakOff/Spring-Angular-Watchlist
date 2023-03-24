package com.dorvak.webapp.moteur.service;

import com.dorvak.webapp.moteur.exception.ServletExecutionException;
import com.dorvak.webapp.moteur.servicelet.InputData;
import com.dorvak.webapp.moteur.servicelet.NoAuth;
import com.dorvak.webapp.moteur.servicelet.OutputData;
import com.dorvak.webapp.moteur.servicelet.WebServlet;
import com.dorvak.webapp.moteur.utils.CharacterUtils;
import com.dorvak.webapp.moteur.utils.LoggerUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

@Service
public class ServletExecutorService {

    private final Map<String, Class<? extends WebServlet>> servlets;

    public ServletExecutorService() {
        Reflections reflections = new Reflections("com.dorvak.webapp");
        servlets = reflections.getSubTypesOf(WebServlet.class).stream()
                .collect(java.util.stream.Collectors.toMap(Class::getSimpleName, c -> c));
        LoggerUtils.info("Servlets(%d): %s", servlets.size(), servlets.keySet());
    }

    public OutputData execute(InputData inputData) {
        String servlet = inputData.getServletName();
        String action = inputData.getAction();
        OutputData outputData = new OutputData(servlet, action);

        try {
            if (CharacterUtils.isEmptyTrim(servlet)) {
                throw new ServletExecutionException("Servlet name is empty");
            }
            if (CharacterUtils.isEmptyTrim(action)) {
                throw new ServletExecutionException("Action is empty");
            }

            Class<?> servletClass = Optional.ofNullable(servlets.get(servlet)).orElseThrow(() -> new ServletExecutionException("Servlet not found: %s", servlet));

            if (servletClass.getSuperclass() == WebServlet.class) {
                Object servletInstance = servletClass.getDeclaredConstructor().newInstance();
                String methodName = "to" + CharacterUtils.capitalize(action);
                try {
                    servletClass.getMethod(methodName, InputData.class, OutputData.class).invoke(servletInstance, inputData, outputData);
                } catch (NoSuchMethodException e) {
                    throw new ServletExecutionException("Action not found: %s", methodName);
                }
            } else {
                throw new ServletExecutionException("Servlet is not a WebServlet: %s", servlet);
            }
        } catch (ServletExecutionException e) {
            outputData.setError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            outputData.setError(CharacterUtils.isEmptyTrim(e.getMessage()) ? e.getClass().getSimpleName() : e.getMessage());
        }
        return outputData;
    }

    public Map<String, Class<? extends WebServlet>> getServlets() {
        return servlets;
    }

    public boolean servletNeedAuth(String servlet, String action) {
        try {
            Class<?> servletClass = Optional.ofNullable(servlets.get(servlet)).orElseThrow(() -> new ServletExecutionException("Servlet not found: %s", servlet));
            if (servletClass.isAnnotationPresent(NoAuth.class)) {
                return false;
            }
            String methodName = "to" + CharacterUtils.capitalize(action);
            Method m = servletClass.getMethod(methodName, InputData.class, OutputData.class);
            return !m.isAnnotationPresent(NoAuth.class);
        } catch (Exception e) {
            return true;
        }
    }
}
