package com.huayu.service;

import com.huayu.model.dto.AboutDTO;
import com.huayu.model.dto.AuroraAdminInfoDTO;
import com.huayu.model.dto.AuroraHomeInfoDTO;
import com.huayu.model.dto.WebsiteConfigDTO;
import com.huayu.model.vo.AboutVO;
import com.huayu.model.vo.WebsiteConfigVO;

public interface AuroraInfoService {

    void report();

    AuroraHomeInfoDTO getAuroraHomeInfo();

    AuroraAdminInfoDTO getAuroraAdminInfo();

    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    WebsiteConfigDTO getWebsiteConfig();

    void updateAbout(AboutVO aboutVO);

    AboutDTO getAbout();

}
