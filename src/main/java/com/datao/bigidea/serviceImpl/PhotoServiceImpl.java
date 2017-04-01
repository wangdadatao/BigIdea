package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.entity.BingImg;
import com.datao.bigidea.mapper.BingImgMapper;
import com.datao.bigidea.serviceImpl.service.PhotoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by 王 海涛 on 2017/4/1.
 */
@Service
public class PhotoServiceImpl extends BaseService implements PhotoService {

    @Resource
    private BingImgMapper bingImgMapper;


    @Override
    public BingImg getIndexImg() {
        return bingImgMapper.getIndexImg();
    }

    @Override
    public void captureBingImg() {
        String baseUrl = "http://www.bing.com";
        String spUrl = "//hpimagearchive.aspx?format=xml&idx=0&n=1";
        BingImg bingImg = new BingImg();

        try {
            Document document = Jsoup.connect(baseUrl + spUrl).get();
            Elements element = document.select("images>image>url");
            bingImg.setUrl(baseUrl + element.get(0).text());
            bingImg.setCreateTime(getNowTime());
            bingImg.setCopyright(document.select("copyright").get(0).text());
            bingImg.setStartDate(document.select("startdate").get(0).text());
            bingImg.setEndDate(document.select("enddate").get(0).text());
            bingImg.setFullStartdate(document.select("fullstartdate").get(0).text());
            bingImg.setUrlMd5(DigestUtils.md5Hex(bingImg.getUrl()));

            bingImgMapper.insert(bingImg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
