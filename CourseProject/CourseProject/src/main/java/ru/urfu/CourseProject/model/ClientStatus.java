package ru.urfu.CourseProject.model;

public enum ClientStatus {
    ACCEPTED_FOR_PROCESSING("Принято в обработку"),
    UNDER_REVIEW("На рассмотрении"),
    SENT_FOR_DELIVERY("Передано в доставку"),
    RECEIVED_BY_CLIENT("Получено клиентом");

    private final String displayName;

    ClientStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
