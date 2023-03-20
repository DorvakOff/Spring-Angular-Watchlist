package com.dorvak.webapp.metier.controllers;

import com.dorvak.webapp.metier.models.JSONMovie;
import com.dorvak.webapp.metier.services.TrendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trends")
public class TrendsController {

    @Autowired
    private TrendsService trendsService;

    @GetMapping("/popular")
    public List<JSONMovie> getTrends() {
        return trendsService.getTopMovies();
    }
}
