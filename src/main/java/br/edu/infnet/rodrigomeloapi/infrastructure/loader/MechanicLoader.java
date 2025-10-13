package br.edu.infnet.rodrigomeloapi.infrastructure.loader;

import br.edu.infnet.rodrigomeloapi.application.service.MechanicService;
import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class MechanicLoader implements ApplicationRunner {

    private final MechanicService mechanicService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var resource = new ClassPathResource("mechanics.txt");
        if (!resource.exists()) {
            log.warn("mechanics.txt not found on classpath. Skipping MechanicLoader.");
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
                if (p.length < 8) {
                    log.warn("Skipping mechanics line {}: expected 8 cols, got {} -> {}", lineNo, p.length, line);
                    skipped++;
                    continue;
                }

                try {
                    String name = p[0].trim();
                    String email = p[1].trim();
                    String cpf = p[2].trim();
                    String phone = p[3].trim();
                    String emp = p[4].trim();
                    String spec = p[5].trim();
                    double salary = p[6].isBlank() ? 0.0 : Double.parseDouble(p[6].trim());
                    boolean active = p[7].isBlank() ? true : Boolean.parseBoolean(p[7].trim());

                    Mechanic m = new Mechanic();
                    m.setName(name);
                    m.setEmail(email);
                    m.setCpf(cpf);
                    m.setPhone(phone);
                    m.setEmployeeNumber(emp);
                    m.setSpecialty(spec);
                    m.setSalary(salary);
                    m.setActive(active);

                    mechanicService.save(m);
                    loaded++;
                } catch (Exception e) {
                    log.warn("Skipping mechanics line {} due to parse error: {}", lineNo, e.getMessage());
                    skipped++;
                }
            }
        }

        log.info("MechanicLoader finished. Loaded={}, Skipped={}", loaded, skipped);
    }
}
