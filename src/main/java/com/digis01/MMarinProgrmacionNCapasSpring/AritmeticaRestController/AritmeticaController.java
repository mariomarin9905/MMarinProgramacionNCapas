package com.digis01.MMarinProgrmacionNCapasSpring.AritmeticaRestController;

import java.text.DecimalFormat;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiAritmetica")
public class AritmeticaController {

    @GetMapping("bienvenida")
    public String bienvenida() {
        return "bienvenida";
    }

    @GetMapping("/sumar")
    public ResponseEntity<String> suma(@RequestParam int numero1, @RequestParam int numero2) {

        int resultado = numero1 + numero2;
        String respuesta = "Resultdao de la suma : " + resultado;
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/resta")
    public ResponseEntity<String> resta(@RequestParam int numero1, @RequestParam int numero2) {
        String respuesta = "Resultado de la resta : " + (numero1 - numero2);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/multiplicacion")
    public ResponseEntity multiplicacion(@RequestParam int numero1, @RequestParam int numero2) {
        return ResponseEntity.status(200).body("Resultado de la multiplicacion : " + (numero1 * numero2));
    }

    @GetMapping("/division")
    public ResponseEntity division(@RequestParam double divisor, @RequestParam double dividendo) {
        if (dividendo == 0) {
            return ResponseEntity.status(400).body("Infinity dividendo como 0");
        }
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double resultado = divisor / dividendo;
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Resultado de la division : " + formatter.format(resultado));

    }

    @PostMapping("/suma2")
    public ResponseEntity suma2(@RequestBody int[] numeros) {
        return ResponseEntity.status(200).body("Resultado de la suma2 :  " + (numeros[1] + numeros[0]));
    }

    @PostMapping("/resta2")
    public ResponseEntity resta2(@RequestBody int[] numeros) {
        return ResponseEntity.status(HttpStatus.OK).body("Resultado de la resta2 : " + (numeros[0] - numeros[1]));
    }

    @PutMapping("/multiplicacion2")
    public ResponseEntity multiplicacion2(@RequestBody int[] numeros) {
        return ResponseEntity.status(200).body("Resultado de la multiplicacion : " + (numeros[0] * numeros[1]));
    }

    record Division(double divisor, double dividendo){};
     @PatchMapping("/division2")
    public ResponseEntity division2(@RequestBody Division division) {
        if (division.dividendo == 0) {
            return ResponseEntity.status(400).body("Infinito dividendo como 0");
        }
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double resultado = division.divisor / division.dividendo;
        return ResponseEntity.status(200).body("Resultado de la division : " +formatter.format(resultado));
    }
}
