package suites;

import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.sportmaster.enums.Resource;
import ru.sportmaster.survivors.Survivor;
import utils.Dictionary;

import java.util.List;

import static asserts.Assert.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.sportmaster.utils.DataGeneratorUtil.generateSurvivorsList;
import static utils.Dictionary.ErrorsTexts.ERROR_EMPTY_LIST;
import static utils.Dictionary.ErrorsTexts.ERROR_TEXT_ERROR;

public class CityTestSuit extends BaseTestSuit {

    @Test(description = "Проверка добавления нового выжившего в город", groups = {"Regress", "Smoke"})
    public void testAddNewSurvivor() {
        Survivor newSurvivor = generateSurvivorsList(1).get(0);

        step("Добавление нового выжившего в список выживших города");
        city.addSurvivors(newSurvivor);

        step("Проверка, что список выживших города содержит добавленного пользователя");
        assertContains(city.getSurvivors(), newSurvivor);
    }

    @DataProvider(name = "Resources")
    public Object[][] dataProviderForResources() {
        return new Object[][]{
                {Resource.FOOD},
                {Resource.MEDICINES},
                {Resource.CONSTRUCTION_MATERIALS},
                {Resource.WEAPON}
        };
    }

    @Test(description = "Проверка сбора ресурсов всем городом", groups = {"Regress", "Smoke"}, dataProvider = "Resources")
    public void testCollectResourcesByAllSurvivors(Resource resource) {
        int resourcesCount = city.getResources().get(resource);

        step("Сбор ресурсов всем городом");
        city.collectResources(resource);

        int newResourcesCount = city.getResources().get(resource);

        step("Проверка, что кол-во ресурсов изменилось");
        assertNotEquals(resourcesCount, newResourcesCount);
    }

    @DataProvider(name = "SurvivorsList")
    public Object[][] dataProviderFoSurvivorsLists() {
        return new Object[][]{
                {generateSurvivorsList(51), Dictionary.ErrorsTexts.ERROR_OUT_OF_BOUND_SURVIVORS},
                {generateSurvivorsList(0), ERROR_EMPTY_LIST},
                {generateSurvivorsList(5), Dictionary.ErrorsTexts.ERROR_SURVIVORS_NOT_IN_LIST}
        };
    }

    @Test(description = "Проверка ошибок при сборе ресурсов с некорректным списком выживших", groups = {"Regress"}, dataProvider = "SurvivorsList")
    public void testErrorsCollectResourcesBySurvivorsList(List<Survivor> survivors, String errorText) {
        Resource resource = Resource.FOOD;

        step(String.format("Проверка, что метод вернул ошибку: %s", errorText));
        assertThatThrownBy(() -> {city.collectResources(survivors, resource);})
                .withFailMessage(ERROR_TEXT_ERROR)
                .hasMessage(errorText);
    }

    @Test(description = "Проверка сбора ресурсов списком выживших", groups = {"Regress"})
    public void testCollectResourcesBySurvivorsList() {
        Resource resource = Resource.FOOD;
        int resourcesCount = city.getResources().get(resource);

        step("Сбор ресурса частью выживших города");
        city.collectResources(city.getSurvivors().stream().limit(5).toList(), resource);

        int newResourcesCount = city.getResources().get(resource);

        step("Проверка, что кол-во ресурса изменилось");
        assertNotEquals(resourcesCount, newResourcesCount);
    }
}
