package com.digis01.MMarinProgrmacionNCapasSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("operaciones/{opcion}")
    public String Operaciones(@PathVariable String opcion, @RequestParam int numero1, @RequestParam int numero2) {
        double resultado;
        switch (opcion) {
            case "suma":
                System.out.println(opcion);
                resultado = numero1+numero2;
                System.out.println(resultado);
                break;
            case "resta":
                System.out.println(opcion);
                resultado = numero1-numero2;
                System.out.println(resultado);
                break;
            case "multiplicacion":
                System.out.println(opcion);
                resultado = numero1*numero2;
                System.out.println(resultado);
                break;
            case "division":
                System.out.println(opcion);
                resultado = numero1/numero2;
                System.out.println(resultado);
                break;
        }
        return "Operaciones";
    }
    

}
