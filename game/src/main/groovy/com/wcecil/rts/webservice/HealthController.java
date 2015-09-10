package com.wcecil.rts.webservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	
    @RequestMapping("/alive")
    public Long greeting(){
        return System.currentTimeMillis();
    }
}
