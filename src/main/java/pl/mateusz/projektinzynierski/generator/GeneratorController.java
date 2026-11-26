package pl.mateusz.projektinzynierski.generator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/generator")
public class GeneratorController {

    private final TrafficGenerator generator;

    public GeneratorController(TrafficGenerator generator) {
        this.generator = generator;
    }

    @PostMapping("/run")
    public Map<String, Object> run(@RequestParam(defaultValue = "200") int normal,
                                   @RequestParam(defaultValue = "3") int attacks,
                                   @RequestParam(defaultValue = "8") int attempts) {
        List<String> attackIps = generator.run(normal, attacks, attempts);
        return Map.of("normalEvents", normal, "attackIps", attackIps);
    }
}