package util;

public final class TimeUtil {

    private TimeUtil() {
    }

    public static String formatHours(double hours) {

        int totalMinutes = (int) Math.round(hours * 60);

        int days = totalMinutes / (24 * 60);
        int remainingMinutes = totalMinutes % (24 * 60);

        int h = remainingMinutes / 60;
        int m = remainingMinutes % 60;

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append(" day");
        }

        if (h > 0) {
            if (!result.isEmpty()) {
                result.append(" ");
            }
            result.append(h).append(" h");
        }

        if (m > 0 || result.isEmpty()) {
            if (!result.isEmpty()) {
                result.append(" ");
            }
            result.append(m).append(" min");
        }

        return result.toString();
    }
}