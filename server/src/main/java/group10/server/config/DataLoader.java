package group10.server.config;

import group10.server.entity.Role;
import group10.server.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

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