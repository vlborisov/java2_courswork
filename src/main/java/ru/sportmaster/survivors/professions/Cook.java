package ru.sportmaster.survivors.professions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sportmaster.enums.Profession;
import ru.sportmaster.enums.Resource;
import ru.sportmaster.survivors.Scavenger;
import ru.sportmaster.survivors.Survivor;

import static ru.sportmaster.utils.DataGeneratorUtil.randomNumber;

@Getter
@Setter
@ToString
public class Cook extends Survivor implements Scavenger {

    public Cook(String name, int age) {
        super(name, age, Profession.COOK);
    }

    @Override
    public int gatherResources(Resource resource) {
        return randomNumber(2, 4);
    }
}
