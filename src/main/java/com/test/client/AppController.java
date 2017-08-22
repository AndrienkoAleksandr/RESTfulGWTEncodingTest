package com.test.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.*;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

/**
 * Created by user on 12/29/16.
 */
public class AppController {

    private static final String BASE_URL = GWT.getHostPageBaseURL() + "rest/echo";

    @Inject
    public AppController() {
    }

    public void go(final Element element) {
        final Map<Integer, Character> unhandledChars = new HashMap<>();

        doRequest((char)0, unhandledChars, new CallBack() {

            private int index = 1;

            @Override
            public void doTask() {
                if (index < 65536) {
                    char character = (char)index;
                    index++;
                    doRequest(character, unhandledChars, this);
                } else {
                   printResult(unhandledChars, element);
                }
            }
        });
    }

    private void printResult(Map<Integer, Character> unhandledChars, final Element element) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        for (Map.Entry<Integer, Character> charEntry: unhandledChars.entrySet()) {
            safeHtmlBuilder.appendHtmlConstant(charEntry.getValue() + " Number index: " + charEntry.getKey());
            safeHtmlBuilder.appendHtmlConstant("<br>");
        }
        safeHtmlBuilder.appendHtmlConstant("AMOUNT unhandled " + unhandledChars.size());
        element.setInnerSafeHtml(safeHtmlBuilder.toSafeHtml());
    }

    private void doRequest(final char character, final Map<Integer, Character> unhandedChars, final CallBack callBack) {
        GWT.log(character + " url: " + BASE_URL +"/" + character + " index: " + (int)character);
        try {
//            final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, BASE_URL +"/" + character);
//            final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, BASE_URL +"/" + URL.encode(character + ""));
//            final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, BASE_URL +"/" + UriUtils.encode(character + ""));
            final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, BASE_URL +"/" + (UriUtils.encodeAllowEscapes(character + "")));
//            final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, BASE_URL +"/" + (URL.encodePathSegment(character + "")));

            requestBuilder.sendRequest(null, new RequestCallback() {
//                @Override
                public void onResponseReceived(Request request, Response response) {
                    String responseText = response.getText();
                    if (responseText.equals("pong") || !responseText.equals(valueOf(character))) {
                        unhandedChars.put((int)character, character);
                    }
                    callBack.doTask();
                }

                @Override
                public void onError(Request request, Throwable throwable) {
                    GWT.log("Failed to connect ", throwable);
                    unhandedChars.put((int)character, character);
                    callBack.doTask();
                }
            });
        } catch (Exception e) {
            GWT.log("Failed to connect to server.");
            unhandedChars.put((int)character, character);
            callBack.doTask();
        }
    }

    /** This callBack should be launched anyway in success and on failure or any exception*/
    private interface CallBack {
        void doTask();
    }
}

