package br.edu.infnet.rodrigomeloapi.infrastructure.loader;

import br.edu.infnet.rodrigomeloapi.application.service.ClientService;
import br.edu.infnet.rodrigomeloapi.domain.model.Address;
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
import java.time.LocalDateTime;

@Slf4j
@Component
@Order(10)
@RequiredArgsConstructor
public class ClientLoader implements ApplicationRunner {

    private final ClientService clientService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var resource = new ClassPathResource("clients.txt");
        if (!resource.exists()) {
            log.warn("clients.txt not found on classpath. Skipping ClientLoader.");
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
                    if (line.toLowerCase().startsWith("name,")) continue;
                }

                String[] p = line.split(",", -1);
                if (p.length < 13) { 
                    log.warn("Skipping clients line {}: expected >=13 cols, got {} -> {}", lineNo, p.length, line);
                    skipped++;
                    continue;
                }

                try {
                    String name = p[0].trim();
                    String email = p[1].trim();
                    String cpf = p[2].trim();
                    String phone = p[3].trim();
                    String loyalty = p[4].trim();
                    String signupAtRaw = p[5].trim();

                    String zip = p[6].trim();
                    String street = p[7].trim();
                    String number = p[8].trim();
                    String complement = p[9].trim();
                    String neighborhood = p[10].trim();
                    String city = p[11].trim();
                    String state = p[12].trim();

                    Address address = Address.builder()
                            .zipCode(zip)
                            .street(street)
                            .number(number)
                            .complement(complement.isBlank() ? null : complement)
                            .neighborhood(neighborhood)
                            .city(city)
                            .state(state)
                            .build();

                    Client c = new Client();
                    c.setName(name);
                    c.setEmail(email);
                    c.setCpf(cpf);
                    c.setPhone(phone);
                    c.setLoyaltyCode(loyalty.isBlank() ? null : loyalty);
                    c.setAddress(address);
                    if (!signupAtRaw.isBlank()) {
                        c.setSignupAt(LocalDateTime.parse(signupAtRaw));
                    }
                    clientService.save(c);
                    loaded++;
                } catch (Exception e) {
                    log.warn("Skipping clients line {} due to parse error: {}", lineNo, e.getMessage());
                    skipped++;
                }
            }
        }

        log.info("ClientLoader finished. Loaded={}, Skipped={}", loaded, skipped);
    }
}
