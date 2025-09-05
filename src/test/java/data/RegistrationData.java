package data;

import helpers.RandomGeneration;

public class RegistrationData {
    private final RandomGeneration helper = new RandomGeneration();

    public static final String SUBJECT = "Computer Science";
    public static final String FIRST_HOBBY = "Sports";
    public static final String SECOND_HOBBY = "Reading";
    public static final String THIRD_HOBBY = "Music";
    public static final String STATE = "Haryana";
    public static final String CITY = "Karnal";

    public String firstName = helper.getRandomFirstName();
    public String lastName = helper.getRandomLastName();
    public String email = helper.getRandomEmail();
    public String gender = helper.getRandomGender();
    public String mobilePhone = helper.getRandomPhone();
    public String dayOfBirth = helper.getRandomDay();
    public String monthOfBirth = helper.getRandomMonth();
    public String yearOfBirth = helper.getRandomYear();
    public String address = helper.getRandomAddress();
}
