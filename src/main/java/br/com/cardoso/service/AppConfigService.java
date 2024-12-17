package br.com.cardoso.service;

import br.com.cardoso.model.ConfigModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppConfigService {

    private final Environment environment;
    private final ObjectMapper objectMapper;

    public AppConfigService(Environment environment, ObjectMapper objectMapper) {
        this.environment = environment;
        this.objectMapper = objectMapper;
    }

    public Map<String, String> getAllConfig() throws JsonProcessingException {
        String appConfigUrl = environment.getProperty("APP_CONFIG_URL");
        RestClient restClient = RestClient.create();
        assert appConfigUrl != null;
        String body = restClient.get().uri(appConfigUrl + "FeatureFlagsConfig").retrieve().body(String.class);

        Map<String, ConfigModel> configMap = objectMapper.readValue(body, new TypeReference<>() {
        });

        assert configMap != null;
        return configMap.entrySet()
                .stream()
                .filter(entry -> "configuration".equals(entry.getValue().type()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().value()));
    }

    public String getConfigValue(String key) {
        String appConfigUrl = environment.getProperty("APP_CONFIG_URL");
        RestClient restClient = RestClient.create();
        assert appConfigUrl != null;
        return restClient.get().uri(appConfigUrl + "FeatureFlagsConfig?flag=" + key).retrieve().body(String.class);
    }

    public String getSimpleConfig() {
        String appConfigUrl = environment.getProperty("APP_CONFIG_URL");
        RestClient restClient = RestClient.create();
        assert appConfigUrl != null;
        return restClient.get().uri(appConfigUrl + "SimpleConfig").retrieve().body(String.class);
    }
}
