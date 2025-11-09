package br.edu.infnet.rodrigomeloapi.infrastructure.loader;

import br.edu.infnet.rodrigomeloapi.application.service.ClientService;
import br.edu.infnet.rodrigomeloapi.application.service.MechanicService;
import br.edu.infnet.rodrigomeloapi.application.service.ServiceOrderService;
import br.edu.infnet.rodrigomeloapi.domain.model.Client;
import br.edu.infnet.rodrigomeloapi.domain.model.Mechanic;
import br.edu.infnet.rodrigomeloapi.domain.model.ServiceOrder;
import br.edu.infnet.rodrigomeloapi.domain.model.PartUsed;
import br.edu.infnet.rodrigomeloapi.infrastructure.repository.jpa.springdata.JpaServiceOrderRepository;
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
@Order(50) // Ensures it runs after other loaders
@RequiredArgsConstructor
public class ServiceOrderLoader implements ApplicationRunner {

    private final ServiceOrderService serviceOrderService;
    private final ClientService clientService;
    private final MechanicService mechanicService;
    // Using JPA repo here for the loader-specific finder method
    private final JpaServiceOrderRepository jpaServiceOrderRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var resource = new ClassPathResource("service_orders.txt");
        if (!resource.exists()) {
            log.warn("service_orders.txt not found. Skipping ServiceOrderLoader.");
            return;
        }

        int loaded = 0, skipped = 0, lineNo = 0;
        try (var is = resource.getInputStream();
             var br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    skipped++;
                    continue;
                }
                
                // Format: clientCpf,mechanicEmployeeNumber,entryDate,estimatedExitDate,problemDescription,totalValue,[part_desc,part_val,part_qty]...
                String[] p = line.split(",", -1);
                if (p.length < 6) {
                    log.warn("Skipping service_orders line {}: expected >=6 cols, got {} -> {}", lineNo, p.length, line);
                    skipped++;
                    continue;
                }

                try {
                    String clientCpf = p[0].trim();
                    String mechanicId = p[1].trim();
                    LocalDateTime entryDate = LocalDateTime.parse(p[2].trim());
                    LocalDateTime estimatedExitDate = LocalDateTime.parse(p[3].trim());
                    String problemDesc = p[4].trim();
                    double totalValue = Double.parseDouble(p[5].trim());

                    // Duplicate check (to avoid reloading)
                    if (jpaServiceOrderRepository.findByEntryDateAndClientCpf(entryDate, clientCpf).isPresent()) {
                        skipped++;
                        continue;
                    }

                    // F4, Section 4 Requirement: Dynamic association using Query Methods
                    Client client = clientService.findByCpf(clientCpf)
                            .orElseThrow(() -> new Exception("Client with CPF " + clientCpf + " not found."));
                    
                    Mechanic mechanic = mechanicService.findById(Long.valueOf(mechanicId))
                            .orElseThrow(() -> new Exception("Mechanic with Employee# " + mechanicId + " not found."));

                    ServiceOrder so = new ServiceOrder();
                    so.setClient(client);
                    so.setMechanic(mechanic);
                    so.setEntryDate(entryDate);
                    so.setEstimatedExitDate(estimatedExitDate);
                    so.setProblemDescription(problemDesc);
                    so.setTotalValue(totalValue);

                    // Load parts (optional fields at the end of the line)
                    if (p.length > 6) {
                        for (int i = 6; i < p.length; i += 3) {
                            if (i + 2 < p.length) {
                                String partDesc = p[i].trim();
                                double partVal = Double.parseDouble(p[i + 1].trim());
                                int partQty = Integer.parseInt(p[i + 2].trim());
                                if (!partDesc.isEmpty()) {
                                    PartUsed part = new PartUsed(null, partDesc, partVal, partQty, so);
                                    so.getPartsUsed().add(part);
                                }
                            }
                        }
                    }

                    serviceOrderService.save(so);
                    loaded++;

                } catch (Exception e) {
                    log.warn("Skipping service_orders line {} due to error: {}", lineNo, e.getMessage());
                    skipped++;
                }
            }
        }
        log.info("ServiceOrderLoader finished. Loaded={}, Skipped={}", loaded, skipped);
    }
}