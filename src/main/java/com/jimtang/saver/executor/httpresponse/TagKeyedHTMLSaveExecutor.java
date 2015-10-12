package com.jimtang.saver.executor.httpresponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by tangz on 10/11/2015.
 */
public abstract class TagKeyedHTMLSaveExecutor extends HTMLBodySaveExecutor {

    private String enclosingTag;
    private String attr;
    private String attrValue;

    public TagKeyedHTMLSaveExecutor(String enclosingTag, String attr, String attrValue) {
        this.enclosingTag = enclosingTag;
        this.attr = attr;
        this.attrValue = attrValue;
    }

    @Override
    protected String getImageUrlFromHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements matchingTag = document.getElementsByTag(enclosingTag);
        if (matchingTag.isEmpty()) {
            throw new HTMLTraverseException(String.format(
                    "No matching enclosingTag: %s found in response body.", enclosingTag));
        }
        if (!matchingTag.hasAttr(attr)) {
            throw new HTMLTraverseException(String.format(
                    "No matching attribute: %s for enclosingTag: %s found in response body.", attr, enclosingTag));
        }
        Elements matchingAttr = matchingTag.attr(attr, attrValue);
        if (matchingAttr.isEmpty()) {
            throw new HTMLTraverseException(String.format(
                    "No matching attribute value: %s for attribute: %s found in response body.", attrValue, attr));
        }
        return super.getImageUrlFromHtml(matchingAttr.html());
    }
}
