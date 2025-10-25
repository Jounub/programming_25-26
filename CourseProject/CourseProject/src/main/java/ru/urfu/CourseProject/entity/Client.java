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
    private int passportNumber;

    //Дата рождения клиента
    @Column(name = "birthdate")
    private String birthdate;

    //Электронная почта клиента
    @Column(name = "email")
    private String email;

    //Номер телефона клиента
    @Column(name = "phone")
    private String phone;

//    @Column(name = "comment")
//    private String comment;

    // Дополнительные услуги
    @Column(name = "sms_notification")
    private boolean smsNotification;

    @Column(name = "phone_notification")
    private boolean phoneNotification;

    @Column(name = "delivery_service")
    private boolean deliveryService;

    // Город доставки (если услуга доставки активна)
    @Column(name = "delivery_city")
    private String deliveryCity;

    // Стоимость доставки (будет рассчитываться)
    @Column(name = "delivery_cost")
    private int deliveryCost;

    // Общая сумма к оплате
    @Column(name = "total_amount")
    private int totalAmount;

    // Пользователь, создавший запись
    @Column(name = "created_by")
    private String createdBy;

    // Связь с адресом
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
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
