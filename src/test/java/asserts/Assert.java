package asserts;

import org.assertj.core.api.Assertions;
import ru.sportmaster.survivors.Survivor;

import java.util.List;

import static utils.Dictionary.ErrorsTexts.*;

public class Assert {

    private Assert() {
    }

    //region ///////////////////////////// Equals /////////////////////////////

    public static void assertEquals(int toEquals, int equalsWith) {
        Assertions.assertThat(toEquals)
                .withFailMessage(String.format(ERROR_ASSERT_NOT_EQUALS, toEquals, equalsWith))
                .isEqualTo(equalsWith);
    }

    public static void assertEquals(Object toEquals, Object equalsWith) {
        Assertions.assertThat(toEquals)
                .withFailMessage(String.format(ERROR_ASSERT_NOT_EQUALS, toEquals, equalsWith))
                .isEqualTo(equalsWith);
    }

    //endregion

    //region ///////////////////////////// NotEquals /////////////////////////////

    public static void assertNotEquals(int toEquals, int equalsWith) {
        Assertions.assertThat(toEquals)
                .withFailMessage(ERROR_ASSERT_EQUALS)
                .isNotEqualTo(equalsWith);
    }

    //endregion

    //region ///////////////////////////// Contains /////////////////////////////

    public static void assertContains(List<Survivor> toContains, Survivor containsIn) {
        Assertions.assertThat(toContains)
                .withFailMessage(String.format(ERROR_ASSERT_CONTAINS, containsIn))
                .contains(containsIn);
    }

    public static void assertContains(List<Survivor> toContains, List<Survivor> containsIn) {
        Assertions.assertThat(toContains)
                .withFailMessage(ERROR_ASSERT_CONTAINS_ALL)
                .containsAll(containsIn);
    }

    //endregion
}
