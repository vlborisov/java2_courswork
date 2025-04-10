package suites;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.sportmaster.enums.Profession;
import ru.sportmaster.enums.Resource;
import ru.sportmaster.survivors.Survivor;
import ru.sportmaster.survivors.professions.Medic;
import ru.sportmaster.utils.CityUtil;
import utils.Dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static asserts.Assert.assertEquals;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.sportmaster.enums.Resource.FOOD;
import static ru.sportmaster.utils.CityUtil.calculateDaysWithFood;
import static ru.sportmaster.utils.CityUtil.filterSurvivorsByProfession;
import static ru.sportmaster.utils.DataGeneratorUtil.generateRandomName;
import static utils.Dictionary.ErrorsTexts.ERROR_TEXT_ERROR;

public class CityUtilTestSuit extends BaseTestSuit {

    @Test(description = "Проверка поиска самого молодого выжившего", groups = {"Regress", "Smoke"})
    public void testFindYoungestSurvivor() {
        Survivor survivor = new Medic(generateRandomName(), 1);
        city.addSurvivors(survivor);

        step("Поиск самого молодого выжившего");
        int youngestAge = CityUtil.findYoungestSurvivor(city).getAge();

        step(String.format("Проверка, что возраст самого старого выжившего равен %s", survivor.getAge()));
        assertEquals(youngestAge, survivor.getAge());
    }

    @Test(description = "Проверка поиска самого старого выжившего", groups = {"Regress", "Smoke"})
    public void testFindOldestSurvivor() {
        Survivor survivor = new Medic(generateRandomName(), 120);
        city.addSurvivors(survivor);

        step("Поиск самого молодого выжившего");
        int oldestAge = CityUtil.findOldestSurvivor(city).getAge();

        step(String.format("Проверка, что возраст самого старого выжившего равен %s", survivor.getAge()));
        assertEquals(oldestAge, survivor.getAge());
    }

    @DataProvider(name = "Professions")
    public Object[][] dataProviderForProfessions() {
        return new Object[][]{
                {Profession.COOK},
                {Profession.MEDIC},
                {Profession.SOLDIER},
                {Profession.WORKER}
        };
    }

    @Test(description = "Проверка работы фильтра по профессии", dataProvider = "Professions", groups = {"Regress"})
    public void testFilterSurvivorsByProfession(Profession profession) {
        step(String.format("Фильтрация списка выживших по профессии '%s'", profession.getValue()));
        List<Survivor> filtratedSurvivorsList = filterSurvivorsByProfession(city, profession);

        step("Получение списка профессий в отфильтрованном списке выживших");
        List<Profession> professionInList = filtratedSurvivorsList.stream().map(Survivor::getProfession).distinct().toList();

        step("Проверка, что в отфильтрованном списке только одна профессия");
        assertEquals(professionInList.size(), 1);

        step(String.format("Проверка, что в отфильтрованном списке выжившие с профессией '%s'", profession.getValue()));
        assertEquals(professionInList.get(0), profession);
    }

    @Test(description = "Проверка ошибки фильтрации при передаче пустого списка", groups = {"Regress"})
    public void testErrorEmptyListInFilterSurvivorsByProfession() {
        city.getSurvivors().removeAll(city.getSurvivors());
        Profession profession = Profession.WORKER;
        String errorText = Dictionary.ErrorsTexts.ERROR_NO_PROFESSION;

        step(String.format("Проверка, что метод вернул ошибку: %s", errorText));
        assertThatThrownBy(() -> filterSurvivorsByProfession(city, profession))
                .withFailMessage(ERROR_TEXT_ERROR)
                .hasMessage(errorText);
    }

    @Test(description = "Проверка поиска ресурса с наименьшими запасами", groups = {"Regress", "Smoke"})
    public void testFindFindMostEmptyResource() {
        Resource resource = FOOD;
        city.getResources().put(resource, 0);

        step("Поиск ресурса с наименьшим запасами");
        Map<Resource, Integer> resourcesWithMinimumValue = CityUtil.findMostEmptyResource(city);

        step("Проверка, что нашелся ресурс с наименьшим запасами");
        assertEquals(resourcesWithMinimumValue, new HashMap<>(Map.of(resource, city.getResources().get(resource))));
    }

    @Test(description = "Проверка расчета количества дней, на сколько городу хватит еды", groups = {"Regress", "Smoke"})
    public void testCalculateDaysWithFood() {
        int survivorsCount = city.getSurvivors().size();
        int foodCount = city.getResources().get(FOOD);

        step("Расчет, на сколько дней городу хватит еды");
        int daysCount = calculateDaysWithFood(city);

        step("Проверка, что корректно рассчиталось кол-во дней, на сколько городу хватит еды");
        assertEquals(daysCount, foodCount / survivorsCount);
    }

    @Test(description = "Проверка ошибки расчета количества сытых дней при передаче пустого списка", groups = {"Regress"})
    public void testErrorEmptyListInCalculateDaysWithFood() {
        String errorText = Dictionary.ErrorsTexts.ERROR_NO_SURVIVORS_IN_CITY;
        city.getSurvivors().removeAll(city.getSurvivors());

        step(String.format("Проверка, что метод вернул ошибку: %s", errorText));
        assertThatThrownBy(() -> calculateDaysWithFood(city))
                .withFailMessage(ERROR_TEXT_ERROR)
                .hasMessage(errorText);
    }

}



