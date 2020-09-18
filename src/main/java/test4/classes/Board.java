package test4.classes;

public class Board {

    private final String header;
    private final String categoryLeftAlignFormat;
    private final String separator;
    private final String scoreLeftAlignFormatRow;
    private final String footer;

    public Board(){
        header = "+--------------------------------+--------------------------------+--------------------------------+--------------------------------+--------------------------------+--------------------------------+";
        categoryLeftAlignFormat = "| %-30s | %-30s | %-30s | %-30s | %-30s | %-30s |%n";
        separator = "+--------------------------------+--------------------------------+--------------------------------+--------------------------------+--------------------------------+--------------------------------+";
        scoreLeftAlignFormatRow = "| %-30s | %-30s | %-30s | %-30s | %-30s | %-30s |%n";
        footer = "+--------------------------------+--------------------------------+--------------------------------+--------------------------------+--------------------------------+--------------------------------+";

    }

    public String getHeader() {
        return header;
    }

    public String getCategoryLeftAlignFormat() {
        return categoryLeftAlignFormat;
    }

    public String getSeparator() {
        return separator;
    }

    public String getScoreLeftAlignFormatRow() {
        return scoreLeftAlignFormatRow;
    }

    public String getFooter() {
        return footer;
    }




}
