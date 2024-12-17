package br.com.cardoso.controller;

import br.com.cardoso.service.AppConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/config")
public class AppConfigController {

    private final AppConfigService appConfigService;

    public AppConfigController(AppConfigService appConfigService) {
        this.appConfigService = appConfigService;
    }

    @GetMapping
    public Map<String, String> getAllConfig() throws JsonProcessingException {
        return appConfigService.getAllConfig();
    }

    @GetMapping("/{key}")
    public String getConfigValue(@PathVariable String key) {
        return appConfigService.getConfigValue(key);
    }

    @GetMapping("/simple")
    public String getSimpleConfig() {
        return appConfigService.getSimpleConfig();
    }
}
