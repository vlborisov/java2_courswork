package ru.sportmaster.city;

import lombok.Data;
import ru.sportmaster.enums.Resource;
import ru.sportmaster.survivors.Scavenger;
import ru.sportmaster.survivors.Survivor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
public class City {

    private String cityName;
    private List<Survivor> survivors;
    private Map<Resource, Integer> resources;

    public City(String name, List<Survivor> survivors, Map<Resource, Integer> resources) {
        this.cityName = name;
        this.survivors = survivors;
        this.resources = resources;
    }

    /**
     * Регистрация выжившего
     *
     */
    public void addSurvivors(Survivor... survivors) {
        this.survivors.addAll(Arrays.stream(survivors).toList());
    }

    /**
     * Отправить всех жителей за определенным ресурсом
     *
     */
    public void collectResources(Resource resource) {
        if (this.survivors.isEmpty()) {
            throw new RuntimeException("В городе нет выживших");
        } else {
            int collectedResourceCount = collectResourcesBySurvivors(this.survivors, resource);
            this.resources.put(resource, getResources().get(resource)+ collectedResourceCount);
        }
    }

    /**
     * Отправить определенных жителей за определенным ресурсом
     *
     */
    public void collectResources(List<Survivor> collectorsList, Resource resource) {
        if (collectorsList.isEmpty()) {
            throw new RuntimeException("Список выживших пуст");
        } else if (collectorsList.size() > this.survivors.size()) {
            throw new RuntimeException("Кол-во собирателей превышает кол-во жителей города");
        } else if (!this.survivors.containsAll(collectorsList)) {
            throw new RuntimeException("Нет таких выживших в городе");
        } else {
            int collectedResourceCount = collectResourcesBySurvivors(collectorsList, resource);
            this.resources.put(resource, getResources().get(resource)+ collectedResourceCount);
        }
    }

    /**
     * сбор ресурсов выжившими
     *
     */
    private static int collectResourcesBySurvivors(List<Survivor> survivors, Resource resource) {
        return survivors.stream()
                .map(Scavenger.class::cast)
                .mapToInt(scavenger -> scavenger.gatherResources(resource))
                .sum();
    }
}
