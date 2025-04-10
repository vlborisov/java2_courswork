package ru.sportmaster.utils;

import com.github.javafaker.Faker;
import ru.sportmaster.city.City;
import ru.sportmaster.enums.Profession;
import ru.sportmaster.enums.Resource;
import ru.sportmaster.survivors.Survivor;
import ru.sportmaster.survivors.professions.Cook;
import ru.sportmaster.survivors.professions.Medic;
import ru.sportmaster.survivors.professions.Soldier;
import ru.sportmaster.survivors.professions.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static ru.sportmaster.enums.Resource.*;

public class DataGeneratorUtil {

    private static final Faker faker = new Faker();

    private DataGeneratorUtil() {
    }

    /**
     * Генерация кол-ва ресурсов для города
     */
    private static int recourseCountGeneratorForCity() {
        return Faker.instance().number().numberBetween(0, 400);
    }

    /**
     * Генерация чисел от min до max
     *
     * @param min
     * @param max
     */
    public static int randomNumber(int min, int max) {
        return Faker.instance().number().numberBetween(min, max + 1);
    }

    /**
     * Создание рандомного выжившего
     */
    public static Survivor generateRandomSurvivor() {
        return switch (faker.number().numberBetween(0, 4)) {
            case 0 -> new Medic(generateRandomName(), generateRandomAge());
            case 1 -> new Worker(generateRandomName(), generateRandomAge());
            case 2 -> new Soldier(generateRandomName(), generateRandomAge());
            case 3 -> new Cook(generateRandomName(), generateRandomAge());
            default -> throw new IllegalStateException("Произошла ошибка");
        };
    }

    public static Survivor generateRandomSurvivorByProfession(Profession profession) {
        return switch (profession) {
            case MEDIC -> new Medic(generateRandomName(), generateRandomAge());
            case WORKER -> new Worker(generateRandomName(), generateRandomAge());
            case SOLDIER -> new Soldier(generateRandomName(), generateRandomAge());
            case COOK -> new Cook(generateRandomName(), generateRandomAge());
        };
    }

    /**
     * Метод для генерации списка рандомных выживших
     *
     * @param survivorsCount
     */
    public static List<Survivor> generateSurvivorsList(int survivorsCount) {
        try {
            return Stream
                    .generate(DataGeneratorUtil::generateRandomSurvivor)
                    .limit(survivorsCount)
                    .toList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Произошла ошибка во время генерации выживших");
        }
    }

    /**
     * Метод для создание рандомного города
     */
    public static City generateRandomCity() {
        return new City(generateRandomCityName(), new ArrayList<>(generateSurvivorsList(randomNumber(15, 50))), generateRandomResourcesForCity());
    }

    /**
     * метод для генерации рандомного названия города
     */
    public static String generateRandomCityName() {
        return faker.country().capital();
    }

    /**
     * метод для генерации рандомного имени
     */
    public static String generateRandomName() {
        return faker.name().fullName();
    }

    /**
     * метод для генерации рандомного возраста
     */
    public static int generateRandomAge() {
        return faker.number().numberBetween(1, 121);
    }

    /**
     * Метод для генерации рандомного кол-ва ресурсов
     */
    public static HashMap<Resource, Integer> generateRandomResourcesForCity() {
        return new HashMap<>(Map.of(
                CONSTRUCTION_MATERIALS, recourseCountGeneratorForCity(),
                FOOD, recourseCountGeneratorForCity(),
                WEAPON, recourseCountGeneratorForCity(),
                MEDICINES, recourseCountGeneratorForCity()
        ));
    }
}
