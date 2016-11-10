package com.datao.bigidea.utils.contentextractor.net;

import com.datao.bigidea.utils.contentextractor.model.CrawlDatum;

public interface Requester {
     public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception;
}
