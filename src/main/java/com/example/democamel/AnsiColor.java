package com.example.democamel;

enum AnsiColor {
    //Color end string, color reset
    RESET("\u001B[0m"),

    // Regular Colors. Normal color, no bold, background color etc.
    BLACK("\u001B[0;30m"),    // BLACK
    RED("\u001B[0;31m"),      // RED
    GREEN("\u001B[0;32m"),    // GREEN
    YELLOW("\u001B[0;33m"),   // YELLOW
    BLUE("\u001B[0;34m"),     // BLUE
    MAGENTA("\u001B[0;35m"),  // MAGENTA
    CYAN("\u001B[0;36m"),     // CYAN
    WHITE("\u001B[0;37m"),    // WHITE

    // Bold
    BLACK_BOLD("\u001B[1;30m"),   // BLACK
    RED_BOLD("\u001B[1;31m"),     // RED
    GREEN_BOLD("\u001B[1;32m"),   // GREEN
    YELLOW_BOLD("\u001B[1;33m"),  // YELLOW
    BLUE_BOLD("\u001B[1;34m"),    // BLUE
    MAGENTA_BOLD("\u001B[1;35m"), // MAGENTA
    CYAN_BOLD("\u001B[1;36m"),    // CYAN
    WHITE_BOLD("\u001B[1;37m"),   // WHITE

    // Underline
    BLACK_UNDERLINED("\u001B[4;30m"),     // BLACK
    RED_UNDERLINED("\u001B[4;31m"),       // RED
    GREEN_UNDERLINED("\u001B[4;32m"),     // GREEN
    YELLOW_UNDERLINED("\u001B[4;33m"),    // YELLOW
    BLUE_UNDERLINED("\u001B[4;34m"),      // BLUE
    MAGENTA_UNDERLINED("\u001B[4;35m"),   // MAGENTA
    CYAN_UNDERLINED("\u001B[4;36m"),      // CYAN
    WHITE_UNDERLINED("\u001B[4;37m"),     // WHITE

    // Background
    BLACK_BACKGROUND("\u001B[40m"),   // BLACK
    RED_BACKGROUND("\u001B[41m"),     // RED
    GREEN_BACKGROUND("\u001B[42m"),   // GREEN
    YELLOW_BACKGROUND("\u001B[43m"),  // YELLOW
    BLUE_BACKGROUND("\u001B[44m"),    // BLUE
    MAGENTA_BACKGROUND("\u001B[45m"), // MAGENTA
    CYAN_BACKGROUND("\u001B[46m"),    // CYAN
    WHITE_BACKGROUND("\u001B[47m"),   // WHITE

    // High Intensity
    BLACK_BRIGHT("\u001B[0;90m"),     // BLACK
    RED_BRIGHT("\u001B[0;91m"),       // RED
    GREEN_BRIGHT("\u001B[0;92m"),     // GREEN
    YELLOW_BRIGHT("\u001B[0;93m"),    // YELLOW
    BLUE_BRIGHT("\u001B[0;94m"),      // BLUE
    MAGENTA_BRIGHT("\u001B[0;95m"),   // MAGENTA
    CYAN_BRIGHT("\u001B[0;96m"),      // CYAN
    WHITE_BRIGHT("\u001B[0;97m"),     // WHITE

    // Bold High Intensity
    BLACK_BOLD_BRIGHT("\u001B[1;90m"),    // BLACK
    RED_BOLD_BRIGHT("\u001B[1;91m"),      // RED
    GREEN_BOLD_BRIGHT("\u001B[1;92m"),    // GREEN
    YELLOW_BOLD_BRIGHT("\u001B[1;93m"),   // YELLOW
    BLUE_BOLD_BRIGHT("\u001B[1;94m"),     // BLUE
    MAGENTA_BOLD_BRIGHT("\u001B[1;95m"),  // MAGENTA
    CYAN_BOLD_BRIGHT("\u001B[1;96m"),     // CYAN
    WHITE_BOLD_BRIGHT("\u001B[1;97m"),    // WHITE

    // High Intensity backgrounds
    BLACK_BACKGROUND_BRIGHT("\u001B[0;100m"),     // BLACK
    RED_BACKGROUND_BRIGHT("\u001B[0;101m"),       // RED
    GREEN_BACKGROUND_BRIGHT("\u001B[0;102m"),     // GREEN
    YELLOW_BACKGROUND_BRIGHT("\u001B[0;103m"),    // YELLOW
    BLUE_BACKGROUND_BRIGHT("\u001B[0;104m"),      // BLUE
    MAGENTA_BACKGROUND_BRIGHT("\u001B[0;105m"),   // MAGENTA
    CYAN_BACKGROUND_BRIGHT("\u001B[0;106m"),      // CYAN
    WHITE_BACKGROUND_BRIGHT("\u001B[0;107m");     // WHITE

    private final String code;

    AnsiColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
