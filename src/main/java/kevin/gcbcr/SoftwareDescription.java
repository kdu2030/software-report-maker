package kevin.gcbcr;

import java.util.List;

public class SoftwareDescription {
    private String softwareName;
    private String url;
    private List<String> alternativeSoftwareNames;
    private String description;

    private String imgUrl;

    private String shortSummary;

    public SoftwareDescription(String softwareName, String url, List<String> alternativeSoftwareNames, String description, String imgUrl, String shortSummary) {
        this.softwareName = softwareName;
        this.url = url;
        this.alternativeSoftwareNames = alternativeSoftwareNames;
        this.description = description;
        this.imgUrl = imgUrl;
        this.shortSummary = shortSummary;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getAlternativeSoftwareNames() {
        return alternativeSoftwareNames;
    }

    public void setAlternativeSoftwareNames(List<String> alternativeSoftwareNames) {
        this.alternativeSoftwareNames = alternativeSoftwareNames;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShortSummary() {
        return shortSummary;
    }

    public void setShortSummary(String shortSummary) {
        this.shortSummary = shortSummary;
    }
}
