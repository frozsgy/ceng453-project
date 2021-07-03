package group10.server.config;

import group10.server.entity.Role;
import group10.server.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Config class that is responsible of the Role logic of Players.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@Component
public class DataLoader implements ApplicationRunner {

    /**
     * Auto-wired role repository. Responsible for interacting with role table.
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Creates the role mechanism during run.
     *
     * @param args - Application arguments of Spring
     * @throws Exception - gets thrown if an exception happens at role repository
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
        for (String r : roles) {
            if (!roleRepository.existsByName(r)) {
                Role insertRole = new Role();
                insertRole.setName(r);
                roleRepository.save(insertRole);
            }
        }

    }
}