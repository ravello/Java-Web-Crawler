import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

    public static void main (String[] args) {
        String url = "https://en.wikipedia.org/";  // input any url
        crawl(1, url, new ArrayList<String>());  // run crawl function
    }

    private static void crawl (int levels, String url, ArrayList<String> visited) {  // private helper function that returns void. Is also recursive.
        if (levels <= 5) {  // level set at 5, could be more or less.
            Document doc = request(url, visited);  // get the document from the url, pass in the ArrayList parameter

            if (doc != null) {  // if the Document is not null
                for (Element link: doc.select("a[href]")) {  // for every link anchor (CSS)
                    String next_link = link.absUrl("href");
                    if (!visited.contains(next_link)) {  // if we haven't visited the link yet
                        crawl(++levels, next_link, visited);  // crawl there too
                    }
                }
            }
        }
    }

    private static Document request (String url, ArrayList<String> v) {  // private helper function that returns a Document type
        try {
            Connection con = Jsoup.connect(url);  // create a connection from the url given
            Document doc = con.get();  // use getter on the connection for the document

            if (con.response().statusCode() == 200) {  // if it succeeds
                System.out.println("Link: " + url);  // just print what the url is
                System.out.println(doc.title());  // and its document title
                v.add(url);  // add the url to the ArrayList

                return doc;  // return the document
            } else {
                return null; // if the status code is a failure, just return a null document
            }
        }
        catch (IOException e) {  // catch the exception, try block didn't succeed
            return null;  // return a null document
        }
    }
}
