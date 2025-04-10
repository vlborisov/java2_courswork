package ru.sportmaster.enums;

import lombok.Getter;

@Getter
public enum Profession {
    MEDIC("Медик"),
    SOLDIER("Солдат"),
    WORKER("Рабочий"),
    COOK("Повар");

    private final String value;

    Profession(String value) {
        this.value = value;
    }
}

