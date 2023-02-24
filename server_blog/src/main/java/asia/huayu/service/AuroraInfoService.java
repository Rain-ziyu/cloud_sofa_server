package asia.huayu.service;

import asia.huayu.model.dto.AboutDTO;
import asia.huayu.model.dto.AuroraHomeInfoDTO;
import asia.huayu.model.dto.WebsiteConfigDTO;

public interface AuroraInfoService {

    void report();

    AuroraHomeInfoDTO getAuroraHomeInfo();


    WebsiteConfigDTO getWebsiteConfig();


    AboutDTO getAbout();

}
