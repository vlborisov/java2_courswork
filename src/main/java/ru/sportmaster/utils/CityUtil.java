package ru.sportmaster.utils;

import ru.sportmaster.city.City;
import ru.sportmaster.enums.Profession;
import ru.sportmaster.enums.Resource;
import ru.sportmaster.survivors.Survivor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityUtil {

    private CityUtil() {
    }

    /**
     * Поиск самого молодого выжившего
     */
    public static Survivor findYoungestSurvivor(City city) {
        return city.getSurvivors()
                .stream()
                .min(Comparator.comparingInt(Survivor::getAge))
                .orElseThrow(NullPointerException::new);
    }

    /**
     * Поиск самого старого выжившего
     */
    public static Survivor findOldestSurvivor(City city) {
        return city.getSurvivors()
                .stream()
                .max(Comparator.comparingInt(Survivor::getAge))
                .orElseThrow(NullPointerException::new);
    }

    /**
     * Вывести список выживших конкретной профессии
     */
    public static List<Survivor> filterSurvivorsByProfession(City city, Profession profession) {
        List<Survivor> filtratedSurvivors = city.getSurvivors()
                .stream()
                .filter(survivor -> survivor.getProfession().equals(profession))
                .toList();
        if (filtratedSurvivors.isEmpty()) {
            throw new RuntimeException("Данной профессии нет в городе");
        } else {
            return filtratedSurvivors;
        }
    }

    /**
     * Метод возвращает ресурс с наименьшими запасами
     */
    public static Map<Resource, Integer> findMostEmptyResource(City city) {
        Resource key = city.getResources()
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .orElseThrow(NullPointerException::new)
                .getKey();
        return new HashMap<>(Map.of(key, city.getResources().get(key)));
        }

    /**
     * Рассчет количества дней, на сколько поселению хватит еды
     */
    public static int calculateDaysWithFood(City city) {
        if (city.getSurvivors().isEmpty()) {
            throw new RuntimeException("В городе нет выживших");
        }
        return city.getResources().get(Resource.FOOD) / city.getSurvivors().size();
    }
}
