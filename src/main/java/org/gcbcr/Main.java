package org.gcbcr;

public class Main {
    public static void main(String[] args) {
        FuturepediaScraper scraper = new FuturepediaScraper("src/main/resources/chromedriver.exe");
        SoftwareDescription softwareDescription = scraper.getSoftwareInfo("Lately");
        MarkdownWriter.buildMarkdownStr(softwareDescription);
    }
}