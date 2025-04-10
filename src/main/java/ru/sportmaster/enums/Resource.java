package ru.sportmaster.enums;

import lombok.Getter;

@Getter
public enum Resource {
    FOOD("Еда"),
    CONSTRUCTION_MATERIALS("Строительные материалы"),
    WEAPON("Оружие"),
    MEDICINES("Медикаменты");

    private final String value;

    Resource(String value) {
        this.value = value;
    }
}
