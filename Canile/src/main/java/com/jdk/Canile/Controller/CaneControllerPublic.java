package com.jdk.Canile.Controller;

import com.jdk.Canile.Entity.Cane;
import com.jdk.Canile.Service.CaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class CaneControllerPublic {

    @Autowired
    private final  CaneService caneService;

    public CaneControllerPublic(CaneService caneService){
        this.caneService = caneService;
    }

    @GetMapping
    public List<Cane> caniDaAdottare(){
        return caneService.findByAdottatoFalse();
    }

}
