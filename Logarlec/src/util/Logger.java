package util;



public class Logger {
    public enum LogLevel {
        DISABLED,
        CALL_FLOWS,
        INPUT_HANDLER,
        ALL
    }
    private static int indentLevel = 0;
    /**
     * 3 log level van:
     * - DISABLED, egyaltalan nincsenek logok
     * - CALL_FLOWS, fuggvenyek call flow logok
     * - INPUT_HANDLER, InputHandler logok
     * - ALL, az osszes logolas
     */
    private static LogLevel logLevel = LogLevel.DISABLED;

    /**
     * Egyszeruen csak kilogol egy info uzenetet
     * @param message a logolando uzenet
     */
    public static void commandLog(String message){
        if (logLevel == LogLevel.INPUT_HANDLER || logLevel == LogLevel.ALL) System.out.println(message);
    }

    public static void started(Object obj, String methodName, Object... params) {
        if(logLevel == LogLevel.CALL_FLOWS || logLevel == LogLevel.ALL){
            String indent = generateIndent();
            System.out.println(indent + "STARTED: " + obj + "." + methodName + "(" + arrayToString(params) + ")");
            indentLevel++;
        }
    }

    public static void finished(Object obj, String methodName, Object... params) {
        if(logLevel == LogLevel.CALL_FLOWS || logLevel == LogLevel.ALL){
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

    static public void setLogLevel(LogLevel level){
        logLevel = level;
    }

    static public void setLogLevel(String level){
        switch (level){
            case "DISABLED":
                logLevel = LogLevel.DISABLED;
                break;
            case "INPUT_HANDLER":
                logLevel = LogLevel.INPUT_HANDLER;
                break;
            case "CALL_FLOWS":
                logLevel = LogLevel.CALL_FLOWS;
                break;
            case "ALL":
                logLevel = LogLevel.ALL;
                break;
        }
    }
}