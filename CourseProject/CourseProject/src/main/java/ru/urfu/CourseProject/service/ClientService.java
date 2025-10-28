package ru.urfu.CourseProject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.urfu.CourseProject.entity.Client;
import ru.urfu.CourseProject.model.ClientStatus;
import ru.urfu.CourseProject.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public void createClient(Client client) {
        // Устанавливаем создателя клиента
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        client.setCreatedBy(username);
        clientRepository.save(client);
    }

    @Transactional
    public void updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();


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
        client.setUpdatedBy(username);

        clientRepository.save(client);
    }

    public void updateClientStatus(Long id, ClientStatus newStatus){
        Optional<Client> client = clientRepository.findById(id);

        if(client.isPresent()){
            Client clientInfo = client.get();
            if (!isValidStatusTransition(clientInfo.getStatus(), newStatus, clientInfo.isDeliveryService())) {
                throw new RuntimeException("Недопустимый переход статуса: " +
                        clientInfo.getStatus().getDisplayName() + " → " + newStatus.getDisplayName());
            }

            clientInfo.setStatus(newStatus);
            clientRepository.save(clientInfo);
        }
    }


    private boolean isValidStatusTransition(ClientStatus current, ClientStatus next, boolean hasDelivery) {
        // Определяем допустимые переходы статусов
        if (hasDelivery) {
            // С доставкой: полный workflow
            return switch (current) {
                case ACCEPTED_FOR_PROCESSING -> next == ClientStatus.UNDER_REVIEW;
                case UNDER_REVIEW -> next == ClientStatus.RETURNED_TO_OFFICE;
                case RETURNED_TO_OFFICE -> next == ClientStatus.SENT_FOR_DELIVERY;
                case SENT_FOR_DELIVERY -> next == ClientStatus.RECEIVED_BY_CLIENT;
                case RECEIVED_BY_CLIENT -> false; // конечный статус
            };
        } else {
            // Без доставки: пропускаем этап доставки
            return switch (current) {
                case ACCEPTED_FOR_PROCESSING -> next == ClientStatus.UNDER_REVIEW;
                case UNDER_REVIEW -> next == ClientStatus.RETURNED_TO_OFFICE;
                case RETURNED_TO_OFFICE -> next == ClientStatus.RECEIVED_BY_CLIENT;
                case SENT_FOR_DELIVERY, RECEIVED_BY_CLIENT -> false; // конечные статусы
            };
        }
    }

    public List<Client> findClientsForStatusManagement() {
        // Возвращаем клиентов, у которых статус не конечный
        return clientRepository.findByStatusNotIn(
                List.of(ClientStatus.RECEIVED_BY_CLIENT)
        );
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
