package br.edu.infnet.rodrigomeloapi.infrastructure.loader;

import br.edu.infnet.rodrigomeloapi.application.service.BikeService;
import br.edu.infnet.rodrigomeloapi.application.service.ClientService;
import br.edu.infnet.rodrigomeloapi.domain.model.Bike;
import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(30)
@RequiredArgsConstructor
public class ClientBikeLoader implements ApplicationRunner {

    private final ClientService clientService;
    private final BikeService bikeService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var resource = new ClassPathResource("client-bikes.txt");
        if (!resource.exists()) {
            log.warn("client-bikes.txt not found. Skipping ClientBikeLoader.");
            return;
        }

        int loaded = 0, skipped = 0, lineNo = 0;
        try (var is = resource.getInputStream();
             var br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty()) { skipped++; continue; }

                if (first) { // header
                    first = false;
                    if (line.toLowerCase().startsWith("cpf,")) continue;
                }

                // cpf,model,brand,manufactureYear,serialNumber,type
                String[] p = line.split(",", -1);
                if (p.length < 6) {
                    log.warn("Skipping line {}: expected 6 cols, got {} -> {}", lineNo, p.length, line);
                    skipped++;
                    continue;
                }

                String cpf = p[0].trim();
                String model = p[1].trim();
                String brand = p[2].trim();
                String yearStr = p[3].trim();
                String serial = p[4].trim();
                String type = p[5].trim();

                try {
                    var clientOpt = clientService.findByCpf(cpf);
                    if (clientOpt.isEmpty()) {
                        log.warn("Skipping line {}: client with CPF {} not found", lineNo, cpf);
                        skipped++;
                        continue;
                    }
                    Client ownerStub = new Client();
                    ownerStub.setId(clientOpt.get().getId()); // FK direta

                    int manufactureYear = Integer.parseInt(yearStr);

                    Bike b = Bike.builder()
                            .model(model)
                            .brand(brand)
                            .manufactureYear(manufactureYear)
                            .serialNumber(serial.isBlank() ? null : serial)
                            .type(type.isBlank() ? null : type)
                            .build();
                    b.setOwner(ownerStub);

                    bikeService.save(b);
                    loaded++;
                } catch (Exception e) {
                    log.warn("Skipping line {} due to parse/validation error: {}", lineNo, e.getMessage());
                    skipped++;
                }
            }
        }

        log.info("ClientBikeLoader finished. Loaded={}, Skipped={}", loaded, skipped);
    }
}
