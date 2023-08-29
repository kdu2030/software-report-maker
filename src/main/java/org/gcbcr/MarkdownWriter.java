package org.gcbcr;

import net.steppschuh.markdowngenerator.image.Image;
import net.steppschuh.markdowngenerator.link.Link;
import net.steppschuh.markdowngenerator.text.emphasis.BoldText;
import net.steppschuh.markdowngenerator.text.heading.Heading;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class MarkdownWriter {

    public static void createHeading(StringBuilder mdStringBuilder, SoftwareDescription softwareDescription) {
        String softwareName = softwareDescription.getSoftwareName();
        mdStringBuilder.append(new Heading(softwareName)).append("\n");
    }

    public static void createLink(StringBuilder mdStringBuilder, SoftwareDescription softwareDescription) {
        String softwareLink = softwareDescription.getUrl();
        mdStringBuilder.append(new BoldText("URL:")).append(" ");
        mdStringBuilder.append(new Link(softwareLink, softwareLink)).append("\n").append("\n");
    }

    public static void createSummary(StringBuilder mdStringBuilder, SoftwareDescription softwareDescription) {
        mdStringBuilder.append(softwareDescription.getShortSummary()).append("\n");
    }

    public static void createImage(StringBuilder mdStringBuilder, SoftwareDescription softwareDescription) {
        String imgURL = softwareDescription.getImgUrl();
        mdStringBuilder.append(new Image(softwareDescription.getSoftwareName(), imgURL)).append("\n");
    }

    public static String handleListElement(Element parentListElement, boolean isUnordered) {
        StringBuilder listStr = new StringBuilder();
        Elements listItems = parentListElement.children();
        int orderedListIndex = 1;
        for (Element listItem : listItems) {
            if (isUnordered && listItem.tagName().equals("li")) {
                listStr.append("* " + listItem.text() + "\n");
            } else if (listItem.equals("li")) {
                listStr.append(orderedListIndex + ". " + listItem.text() + "\n");
                orderedListIndex++;
            }
        }
        return listStr.toString();
    }

    public static void createDescription(StringBuilder mdStringBuilder, SoftwareDescription softwareDescription) {
        String description = softwareDescription.getDescription();
        Document document = Jsoup.parse(description);
        Elements parentElements = document.getElementsByTag("body").get(0).children();
        Elements childrenElements = new Elements();

        for (int i = 0; i < parentElements.size(); i++) {
            if (parentElements.get(i).tagName().equals("div")) {
                childrenElements.addAll(parentElements.get(i).children());
            } else {
                childrenElements.add(parentElements.get(i));
            }
        }

        for (Element element : childrenElements) {
            if (element.tagName().matches("h[0-9]")) {
                mdStringBuilder.append("\n" + "**" + element.text() + "**" + "\n");
            } else if (element.tagName().matches("p")) {
                mdStringBuilder.append("\n");
                mdStringBuilder.append(element.text() + " \n");
            } else if (element.tagName().matches("ul")) {
                mdStringBuilder.append(handleListElement(element, true));
            }
        }
    }

    public static void createCompetitorList(StringBuilder mdStringBuilder, List<String> alternatives) {
        mdStringBuilder.append("\n**Alternative Software**\n");
        for (String alternative : alternatives) {
            mdStringBuilder.append("* " + alternative + "\n");
        }
    }

    public static String buildMarkdownStr(SoftwareDescription softwareDescription) {
        StringBuilder mdStringBuilder = new StringBuilder();
        createHeading(mdStringBuilder, softwareDescription);
        createLink(mdStringBuilder, softwareDescription);
        createSummary(mdStringBuilder, softwareDescription);
        createImage(mdStringBuilder, softwareDescription);
        createDescription(mdStringBuilder, softwareDescription);
        createCompetitorList(mdStringBuilder, softwareDescription.getAlternativeSoftwareNames());
        return mdStringBuilder.toString();
    }
}
