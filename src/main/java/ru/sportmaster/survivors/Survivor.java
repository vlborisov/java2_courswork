package ru.sportmaster.survivors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sportmaster.enums.Profession;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
public abstract class Survivor {
    private String name;
    private int age;
    private Profession profession;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survivor survivor = (Survivor) o;
        return age == survivor.age && Objects.equals(name, survivor.name) && profession == survivor.profession;
    }
}
