package org.bsuir.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsuir.model.Producer;
import org.bsuir.service.ProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping(value = "/all")
    public ResponseEntity getAll(){
        log.info("GET ALL PRODUCERS");
        return ResponseEntity.ok(producerService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        producerService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody Producer producer){
        producerService.save(producer);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(producerService.findById(id).orElseThrow(NullPointerException::new));
    }

    @PostMapping(value = "/save-all")
    public ResponseEntity saveAll(@RequestBody List<Producer> producers){
        log.info("SAVE PRODUCERS");
        return ResponseEntity.ok(producerService.saveAll(producers));
    }
}
