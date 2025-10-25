package ru.urfu.CourseProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.urfu.CourseProject.entity.Client;
import ru.urfu.CourseProject.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientAccessService {
    private final ClientRepository clientRepository;

    public boolean canViewClient(Long clientId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        //Рид онли, админы и супервизоры могут видеть всех клиентов
        if(hasRole("ROLE_ADMIN") || hasRole("ROLE_SUPERVISOR") || hasRole("ROLE_READ_ONLY")){
            return true;
        }

        //обычный юзер видит только своих клиентов
        Optional<Client> client = clientRepository.findById(clientId);
        return client.map(c-> c.getCreatedBy().equals(username))
                .orElse(false);
    }

    public boolean canEditClient(Long clientId){
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //String username = auth.getName();

        return hasRole("ROLE_ADMIN") || hasRole("ROLE_SUPERVISOR");
    }

    public boolean canDeleteClients(Long clientId){
        return hasRole("ROLE_ADMIN") || hasRole("ROLE_SUPERVISOR");
    }

    public List<Client> getAccessibleClients(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if(hasRole("ROLE_ADMIN") || hasRole("ROLE_SUPERVISOR")){
            return clientRepository.findAll();
        } else if(hasRole("ROLE_USER")){
            return clientRepository.findByCreatedBy(username);
        } else return clientRepository.findAll();
    }

    private boolean hasRole(String role){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role::equals);
    }
}
