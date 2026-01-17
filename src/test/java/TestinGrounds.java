import utils.Randomizer;

public class TestinGrounds {

    public static void main(String[] args) {

        String randomStr = Randomizer.generateRandomAlphaNumericStr(10);
        String randomEmail = Randomizer.generateRandomUUIDEmail();

        System.out.println(randomStr);
        System.out.println(randomEmail);
    }
}
