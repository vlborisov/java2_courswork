package suites;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.sportmaster.city.City;

import static ru.sportmaster.utils.DataGeneratorUtil.generateRandomCity;

public class BaseTestSuit {

    public static City city;

    @BeforeMethod(alwaysRun = true)
    public static void SetUp(){
        city = generateRandomCity();
    }

    @AfterMethod(alwaysRun = true)
    public static void afterMethod(){
        city = null;
    }
}
