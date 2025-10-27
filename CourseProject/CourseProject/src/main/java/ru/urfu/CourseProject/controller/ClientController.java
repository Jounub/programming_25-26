package ru.urfu.CourseProject.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.urfu.CourseProject.entity.Address;
import ru.urfu.CourseProject.entity.Client;
import ru.urfu.CourseProject.entity.DeliveryPrice;
import ru.urfu.CourseProject.model.ClientStatus;
import ru.urfu.CourseProject.repository.DeliveryPriceRepository;
import ru.urfu.CourseProject.service.ClientAccessService;
import ru.urfu.CourseProject.service.ClientService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientAccessService clientAccessService;
    private final DeliveryPriceRepository deliveryPriceRepository;

    // Список клиентов (с учетом прав доступа)
    @GetMapping("/list")
    public String listClients(Model model) {
        List<Client> clients = clientAccessService.getAccessibleClients();

        model.addAttribute("clients", clients);
        model.addAttribute("canEdit", clientAccessService.hasRole("ROLE_ADMIN") || clientAccessService.hasRole("ROLE_SUPERVISOR"));
        return "clients/list";
    }

    // Просмотр конкретного клиента
    @GetMapping("/view/{id}")
    public String viewClient(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        if (!clientAccessService.canViewClient(id)) {
            redirectAttributes.addFlashAttribute("error", "Доступ запрещен");
            return "redirect:/clients/list";
        }

        Client client = clientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));
        model.addAttribute("client", client);
        model.addAttribute("canEdit", clientAccessService.canEditClient(id));
        return "clients/view";
    }

    // Форма создания нового клиента (только для USER)
    @GetMapping("/new")
    public String newClientForm(Model model) {
        Client client = new Client();
        client.setAddress(new Address());
        model.addAttribute("client", client);
        model.addAttribute("statuses", ClientStatus.values());
        //Флаг для определения режима (создание/редактирование)
        model.addAttribute("isEdit", false); 
        return "clients/form";
    }

    // Создание клиента (только для USER, ADMIN, SUPERVISOR)
    @PostMapping("/create")
    public String createClient(
                               @RequestParam(required = false) Boolean smsNotification,
                               @RequestParam(required = false) Boolean phoneNotification,
                               @RequestParam(required = false) Boolean deliveryService,
                               @RequestParam(required = false) String deliveryCity,
                               @RequestParam String city,
                               @RequestParam String street,
                               @RequestParam String building,
                               @RequestParam(required = false) String apartment,
                               @ModelAttribute Client client,
                               RedirectAttributes redirectAttributes) {

        // Устанавливаем адрес
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setBuilding(building);
        address.setApartment(apartment);
        client.setAddress(address);

        int serviceFee = 3500;
        int smsCost = 0;
        int phoneCost = 0;
        int deliveryCost = 0;

        //база
        int calculatedTotalAmount;
        Map<String, Integer> costBreakdown = new HashMap<>();
        costBreakdown.put("Сервисный сбор", 3500);

        // SMS уведомление
        if (Boolean.TRUE.equals(smsNotification)) {
            smsCost = 200;
            costBreakdown.put("SMS-информирование", smsCost);
        }

        // Телефонное оповещение
        if (Boolean.TRUE.equals(phoneNotification)) {
            phoneCost = 250;
            costBreakdown.put("Телефонное оповещение", phoneCost);
        }

        if (Boolean.TRUE.equals(deliveryService) && city != null && !city.trim().isEmpty()) {
            try {
                DeliveryPrice deliveryPrice = deliveryPriceRepository.findByCity(deliveryCity);
                deliveryCost = (deliveryPrice != null) ? deliveryPrice.getDeliveryCost() : 5000;
            } catch (Exception e) {
                deliveryCost = 5000;
            }
        }

        calculatedTotalAmount = serviceFee + smsCost + phoneCost + deliveryCost;

        try {
            client.setTotalAmount(calculatedTotalAmount);
            client.setDeliveryCost(deliveryCost);
            client.setServiceFee(serviceFee);
            client.setSmsNotificationCost(smsCost);
            client.setPhoneNotificationCost(phoneCost);
            clientService.createClient(client);
            redirectAttributes.addFlashAttribute("success", "Клиент успешно создан");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании клиента: " + e.getMessage());
        }
        return "redirect:/clients/list";
    }

    // Форма редактирования клиента (только для SUPERVISOR и ADMIN)
    @GetMapping("/edit/{id}")
    public String editClientForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        if (!clientAccessService.canEditClient(id)) {
            redirectAttributes.addFlashAttribute("error", "У вас нет прав для редактирования клиента");
            return "redirect:/clients/list";
        }

        Client client = clientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        // Если адрес не установлен, создаем новый
        if (client.getAddress() == null) {
            client.setAddress(new Address());
        }

        model.addAttribute("client", client);
        model.addAttribute("statuses", ClientStatus.values());
        model.addAttribute("isEdit", true);
        return "clients/form";
    }

     //Обновление клиента (только для SUPERVISOR и ADMIN)
    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id,
                               @RequestParam(required = false) Boolean smsNotification,
                               @RequestParam(required = false) Boolean phoneNotification,
                               @RequestParam(required = false) Boolean deliveryService,
                               @RequestParam(required = false) String deliveryCity,
                               @RequestParam String city,
                               @RequestParam String street,
                               @RequestParam String building,
                               @RequestParam(required = false) String apartment,
                               @ModelAttribute Client client,
                               RedirectAttributes redirectAttributes) {
        if (!clientAccessService.canEditClient(id)) {
            redirectAttributes.addFlashAttribute("error", "У вас нет прав для редактирования клиента");
            return "redirect:/clients/list";
        }

        try {
            Address address = client.getAddress();
            if (address == null) {
                address = new Address();
            }
            address.setCity(city);
            address.setStreet(street);
            address.setBuilding(building);
            address.setApartment(apartment);
            client.setAddress(address);

            // Пересчитываем стоимость при обновлении
            int serviceFee = 3500;
            int smsCost = Boolean.TRUE.equals(smsNotification) ? 200 : 0;
            int phoneCost = Boolean.TRUE.equals(phoneNotification) ? 250 : 0;
            int deliveryCost = 0;

            if (Boolean.TRUE.equals(deliveryService) && deliveryCity != null && !deliveryCity.trim().isEmpty()) {
                try {
                    DeliveryPrice deliveryPrice = deliveryPriceRepository.findByCity(deliveryCity);
                    deliveryCost = (deliveryPrice != null) ? deliveryPrice.getDeliveryCost() : 5000;
                } catch (Exception e) {
                    deliveryCost = 5000;
                }
            }

            int calculatedTotalAmount = serviceFee + smsCost + phoneCost + deliveryCost;

            client.setSmsNotification(Boolean.TRUE.equals(smsNotification));
            client.setPhoneNotification(Boolean.TRUE.equals(phoneNotification));
            client.setDeliveryService(Boolean.TRUE.equals(deliveryService));

            client.setSmsNotificationCost(smsCost);
            client.setPhoneNotificationCost(phoneCost);
            client.setDeliveryCity(deliveryCity);
            client.setDeliveryCost(deliveryCost);
            client.setServiceFee(serviceFee);
            client.setTotalAmount(calculatedTotalAmount);

            clientService.updateClient(id, client);
            redirectAttributes.addFlashAttribute("success", "Клиент успешно обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении клиента: " + e.getMessage());
        }
        return "redirect:/clients/list";
    }

    // Удаление клиента (только для SUPERVISOR и ADMIN)
    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!clientAccessService.canDeleteClient(id)) {
            redirectAttributes.addFlashAttribute("error", "У вас нет прав для удаления клиента");
            return "redirect:/clients/list";
        }

        try {
            clientService.deleteClient(id);
            redirectAttributes.addFlashAttribute("success", "Клиент успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении клиента: " + e.getMessage());
        }
        return "redirect:/clients/list";
    }
}