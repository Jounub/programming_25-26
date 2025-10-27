package ru.urfu.CourseProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.urfu.CourseProject.model.ClientStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CLIENTS")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    //Статус клиента в системе
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ClientStatus status;

    //Имя клиента
    @Column(name = "name")
    private String name;

    //Фамилия клиента
    @Column(name = "surname")
    private String surname;

    //Номер паспорта клиента
    @Column(name = "passport_number", unique = true)
    private String passportNumber;

    //Дата рождения клиента
    @Column(name = "birthdate")
    private String birthdate;

    //Электронная почта клиента
    @Column(name = "email")
    private String email;

    //Номер телефона клиента
    @Column(name = "phone")
    private String phone;

    // Дополнительные услуги
    @Column(name = "sms_notification")
    private boolean smsNotification;

    @Column(name = "phone_notification")
    private boolean phoneNotification;

    @Column(name = "notes")
    private String notes;

    @Column(name = "service_fee")
    private Integer serviceFee = 3500;

    @Column(name = "delivery_service")
    private boolean deliveryService;

    // Город доставки (если услуга доставки активна)
    @Column(name = "delivery_city")
    private String deliveryCity;

    // Стоимость доставки (будет рассчитываться)
    @Column(name = "delivery_cost")
    private Integer deliveryCost = 0;

    // Общая сумма к оплате
    @Column(name = "total_amount")
    private Integer totalAmount = 3500;

    @Column(name = "sms_notification_cost")
    private Integer smsNotificationCost = 0;

    @Column(name = "phone_notification_cost")
    private Integer phoneNotificationCost = 0;

    // Пользователь, создавший запись
    @Column(name = "created_by")
    private String createdBy;

    // Связь с адресом
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    // Дата создания и последнего обновления
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Автоматическое установление дат перед сохранением и обновлением
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
        // Устанавливаем начальный статус по умолчанию
        if (status == null) {
            status = ClientStatus.ACCEPTED_FOR_PROCESSING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
