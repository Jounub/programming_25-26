package ru.urfu.CourseProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.urfu.CourseProject.entity.Client;
import ru.urfu.CourseProject.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> findByCurrentUser() {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //return clientRepository.findByCreatedBy(username);
        return clientRepository.findAll();
    }

    public Client createClient(Client client) {
        // Устанавливаем создателя клиента
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        client.setCreatedBy(username);
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        // Обновляем поля
        client.setSurname(clientDetails.getSurname());
        client.setName(clientDetails.getName());
        client.setPhone(clientDetails.getPhone());
        client.setEmail(clientDetails.getEmail());
        client.setStatus(clientDetails.getStatus());
        client.setServiceFee(clientDetails.getServiceFee());
        client.setSmsNotification(clientDetails.isSmsNotification());
        client.setPhoneNotification(clientDetails.isPhoneNotification());
        client.setDeliveryService(clientDetails.isDeliveryService());
        client.setDeliveryCity(clientDetails.getDeliveryCity());
        client.setDeliveryCost(clientDetails.getDeliveryCost());
        client.setTotalAmount(clientDetails.getTotalAmount());
        client.setSmsNotificationCost(clientDetails.getSmsNotificationCost());
        client.setPhoneNotificationCost(clientDetails.getPhoneNotificationCost());
        client.setNotes(clientDetails.getNotes());

        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
