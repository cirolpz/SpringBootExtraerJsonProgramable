package org.example.controllers;

import org.example.models.Dispo_alquiler_diaria;
import org.example.services.Dispo_alquiler_diariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController

public class Dispo_alquiler_diariaController {
    private final Dispo_alquiler_diariaService dispoAlquilerDiariaService;
    @Autowired

    public Dispo_alquiler_diariaController(Dispo_alquiler_diariaService dispoAlquilerDiariaService){
        this.dispoAlquilerDiariaService=dispoAlquilerDiariaService;
    }

    @GetMapping("/dispoAlquilerDiaria")
    public List<Dispo_alquiler_diaria> getAllBooks(){
        return dispoAlquilerDiariaService.getAllDispo_alquiler_diaria();
    }


}
