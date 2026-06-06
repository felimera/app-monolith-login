package com.project.app_login_back.insfraestructure.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController {

    // Intercepta cualquier ruta que no sea un archivo físico (como .js o .css) ni un endpoint de la API
    @GetMapping(value = "{path:[^\\.]*}")
    public String redirect() {
        // Redirige el flujo al index.html para que el enrutador de Angular tome el control
        return "forward:/index.html";
    }
}
