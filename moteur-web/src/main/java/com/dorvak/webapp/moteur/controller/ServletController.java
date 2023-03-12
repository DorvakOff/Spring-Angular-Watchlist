package com.dorvak.webapp.moteur.controller;

import com.dorvak.webapp.moteur.dto.ServletDto;
import com.dorvak.webapp.moteur.service.ServletExecutorService;
import com.dorvak.webapp.moteur.servicelet.InputData;
import com.dorvak.webapp.moteur.servicelet.OutputData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/servlet")
public class ServletController {

    @Autowired
    private ServletExecutorService servletExecutorService;

    @PostMapping("/execute")
    @ResponseBody
    public OutputData execute(@RequestBody ServletDto dto, HttpServletRequest request) {
        InputData inputData = new InputData(dto.servletName(), dto.action(), request);
        return servletExecutorService.execute(inputData);
    }
}
