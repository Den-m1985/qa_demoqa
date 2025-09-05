package helpers;

import com.github.javafaker.Faker;
import data.Gender;
import data.Months;

import java.util.Random;

public class RandomGeneration {
    private final Faker faker = new Faker();

    public String getRandomFirstName() {
        return faker.name().firstName();
    }

    public String getRandomLastName() {
        return faker.name().lastName();
    }

    public String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public String getRandomGender() {
        int genderPick = new Random().nextInt(Gender.values().length);
        return String.valueOf(Gender.values()[genderPick].getGender());
    }

    public String getRandomPhone() {
        return faker.phoneNumber().subscriberNumber(10);
    }

    public String getRandomDay() {
        int day = new Random().nextInt(28) + 1;

        if (day <= 9) {
            return "0" + day;
        } else {
            return String.valueOf(day);
        }
    }

    public String getRandomMonth() {
        int monthPick = new Random().nextInt(Months.values().length);
        return String.valueOf(Months.values()[monthPick].getMonth());
    }

    public String getRandomYear() {
        return String.valueOf(faker.number().numberBetween(1921, 2007));
    }

    public String getRandomAddress() {
        return faker.address().streetAddress();
    }
}
