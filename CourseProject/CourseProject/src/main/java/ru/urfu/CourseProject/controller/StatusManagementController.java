package ru.urfu.CourseProject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.urfu.CourseProject.entity.Client;
import ru.urfu.CourseProject.model.ClientStatus;
import ru.urfu.CourseProject.service.ClientService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/status-management")
@RequiredArgsConstructor
public class StatusManagementController {
    private final ClientService clientService;

    // Страница управления статусами
    @GetMapping
    public String statusManagementPage(Model model) {
        List<Client> clients = clientService.findClientsForStatusManagement();
        model.addAttribute("clients", clients);
        return "status-management/list";
    }

    // Обновление статуса клиента
    @PostMapping("/update-status/{id}")
    public String updateClientStatus(@PathVariable Long id,
                                     @RequestParam ClientStatus newStatus,
                                     RedirectAttributes redirectAttributes) {
        try {
            clientService.updateClientStatus(id, newStatus);
            redirectAttributes.addFlashAttribute("success",
                    "Статус клиента успешно обновлен на: " + newStatus.getDisplayName());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Ошибка при обновлении статуса: " + e.getMessage());
        }
        return "redirect:/status-management";
    }

    // Быстрое действие "Следующий статус"
    @PostMapping("/next-status/{id}")
    public String nextStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Client client = clientService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Клиент не найден"));

            ClientStatus nextStatus = getNextStatus(client.getStatus(), client.isDeliveryService());
            if (nextStatus != null) {
                clientService.updateClientStatus(id, nextStatus);
                redirectAttributes.addFlashAttribute("success",
                        "Статус обновлен на: " + nextStatus.getDisplayName());
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Невозможно перевести в следующий статус");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Ошибка при обновлении статуса: " + e.getMessage());
        }
        return "redirect:/status-management";
    }

    @ModelAttribute("statusManagementController")
    public StatusManagementController getController() {
        return this;
    }

    // Получение следующего статуса
    private ClientStatus getNextStatus(ClientStatus current, boolean hasDelivery) {
        if (hasDelivery) {
            return switch (current) {
                case ACCEPTED_FOR_PROCESSING -> ClientStatus.UNDER_REVIEW;
                case UNDER_REVIEW -> ClientStatus.RETURNED_TO_OFFICE;
                case RETURNED_TO_OFFICE -> ClientStatus.SENT_FOR_DELIVERY;
                case SENT_FOR_DELIVERY -> ClientStatus.RECEIVED_BY_CLIENT;
                default -> null;
            };
        } else {
            return switch (current) {
                case ACCEPTED_FOR_PROCESSING -> ClientStatus.UNDER_REVIEW;
                case UNDER_REVIEW -> ClientStatus.RETURNED_TO_OFFICE;
                case RETURNED_TO_OFFICE -> ClientStatus.RECEIVED_BY_CLIENT;
                default -> null;
            };
        }
    }

    // Метод для получения отображаемого имени следующего статуса
    public String getNextStatusDisplayName(ClientStatus currentStatus, boolean hasDelivery) {
        ClientStatus nextStatus = getNextStatus(currentStatus, hasDelivery);
        return nextStatus != null ? nextStatus.getDisplayName() : "Завершено";
    }
}
