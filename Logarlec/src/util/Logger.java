package util;

public class Logger {
    private static int indentLevel = 0;
    private static int startedCount = 0;

    public static void started(Object obj, String methodName, Object... params) {
        String indent = generateIndent();
        System.out.println(indent + "STARTED: " + obj + "." + methodName + "(" + arrayToString(params) + ")");
        indentLevel++;
        startedCount++;
    }

    public static void finished(Object obj, String methodName, Object... params) {
        indentLevel--;
        String indent = generateIndent();
        System.out.println(indent + "FINISHED: " + obj + "." + methodName + "(" + arrayToString(params) + ")");
    }

    private static String generateIndent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    private static String arrayToString(Object[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static int getStartedCount() {
        return startedCount;
    }
}