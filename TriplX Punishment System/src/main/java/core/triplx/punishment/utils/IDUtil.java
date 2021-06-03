package core.triplx.punishment.utils;

public class IDUtil {

    public static int randomID() {
        String AlphaNumericString = "0123456789"; // ignore name of string lol
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(7);

        for (int i = 0; i < 7; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return Integer.parseInt(sb.toString());
    }

}
