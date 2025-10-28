package ru.urfu.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.CourseProject.entity.DeliveryPrice;
import ru.urfu.CourseProject.repository.DeliveryPriceRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeliveryController {
    @Autowired
    private DeliveryPriceRepository deliveryPriceRepository;

    @GetMapping("/api/delivery-price")
    public Map<String, Object> getDeliveryPrice(@RequestParam String city) {
        Map<String, Object> response = new HashMap<>();

        try {
            DeliveryPrice deliveryPrice = deliveryPriceRepository.findByCity(city);
            if (deliveryPrice != null) {
                response.put("price", deliveryPrice.getDeliveryCost());
                response.put("city", deliveryPrice.getCity());
            } else {
                response.put("price", 5000); // цена по умолчанию
                response.put("message", "Город не найден, применена цена по умолчанию");
            }
        } catch (Exception e) {
            response.put("price", 5000);
            response.put("message", "Ошибка при получении цены");
        }

        return response;
    }
}
