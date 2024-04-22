package util;

public class Logger {
    private static int indentLevel = 0;
    private static int logLevel = 1;

    public static void started(Object obj, String methodName, Object... params) {
        if(logLevel == 1){
            String indent = generateIndent();
            System.out.println(indent + "STARTED: " + obj + "." + methodName + "(" + arrayToString(params) + ")");
            indentLevel++;
        }
    }

    public static void finished(Object obj, String methodName, Object... params) {
        if(logLevel == 1){
            indentLevel--;
            String indent = generateIndent();
            System.out.println(indent + "FINISHED: " + obj + "." + methodName + "(" + arrayToString(params) + ")");
        }
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

    static public void setLogLevel(int level){
        logLevel = level;
    }
}