package br.edu.infnet.rodrigomeloapi.infrastructure.loader;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import br.edu.infnet.rodrigomeloapi.application.service.BikeService;
import br.edu.infnet.rodrigomeloapi.domain.model.Bike;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class BikeLoader implements ApplicationRunner {

    private final BikeService bikeService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var resource = new ClassPathResource("bikes.txt");
        if (!resource.exists()) {
            log.warn("bikes.txt not found on classpath. Skipping initial load.");
            return;
        }

        int loaded = 0, skipped = 0, lineNo = 0;

        try (var is = resource.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty()) { skipped++; continue; }

                // Skip header (first line) if it contains non-numeric year
                if (first) {
                    first = false;
                    if (line.toLowerCase().startsWith("model,")) {
                        continue; // header
                    }
                }

                String[] parts = line.split(",");
                if (parts.length < 5) {
                    log.warn("Skipping line {}: expected 5 columns, got {} -> {}", lineNo, parts.length, line);
                    skipped++;
                    continue;
                }

                try {
                    String model = parts[0].trim();
                    String brand = parts[1].trim();
                    int year = Integer.parseInt(parts[2].trim());
                    String serial = parts[3].trim();
                    String type = parts[4].trim();

                    Bike bike = Bike.builder()
                            .model(model)
                            .brand(brand)
                            .manufactureYear(year)
                            .serialNumber(serial)
                            .type(type)
                            .build();

                    bikeService.save(bike);
                    loaded++;
                } catch (Exception e) {
                    log.warn("Skipping line {} due to parse error: {}", lineNo, e.getMessage());
                    skipped++;
                }
            }
        }

        log.info("BikeLoader finished. Loaded={}, Skipped={}", loaded, skipped);
    }
}