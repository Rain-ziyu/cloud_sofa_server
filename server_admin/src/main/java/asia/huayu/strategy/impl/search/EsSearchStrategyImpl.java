package asia.huayu.strategy.impl.search;

import asia.huayu.model.dto.ArticleSearchDTO;
import asia.huayu.strategy.SearchStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static asia.huayu.constant.CommonConstant.*;
import static asia.huayu.enums.ArticleStatusEnum.PUBLIC;

@Log4j2
@Service("esSearchStrategyImpl")
public class EsSearchStrategyImpl implements SearchStrategy {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<ArticleSearchDTO> searchArticle(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        return search(buildQuery(keywords));
    }

    private NativeSearchQueryBuilder buildQuery(String keywords) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("articleTitle", keywords))
                        .should(QueryBuilders.matchQuery("articleContent", keywords)))
                .must(QueryBuilders.termQuery("isDelete", FALSE))
                .must(QueryBuilders.termQuery("status", PUBLIC.getStatus()));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        return nativeSearchQueryBuilder;
    }

    private List<ArticleSearchDTO> search(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("articleTitle");
        titleField.preTags(PRE_TAG);
        titleField.postTags(POSTAG);
        HighlightBuilder.Field contentField = new HighlightBuilder.Field("articleContent");
        contentField.preTags(PRE_TAG);
        contentField.postTags(POSTAG);
        contentField.fragmentSize(50);
        nativeSearchQueryBuilder.withHighlightFields(titleField, contentField);
        try {
            SearchHits<ArticleSearchDTO> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), ArticleSearchDTO.class);
            return search.getSearchHits().stream().map(hit -> {
                ArticleSearchDTO article = hit.getContent();
                List<String> titleHighLightList = hit.getHighlightFields().get("articleTitle");
                if (CollectionUtils.isNotEmpty(titleHighLightList)) {
                    article.setArticleTitle(titleHighLightList.get(0));
                }
                List<String> contentHighLightList = hit.getHighlightFields().get("articleContent");
                if (CollectionUtils.isNotEmpty(contentHighLightList)) {
                    article.setArticleContent(contentHighLightList.get(contentHighLightList.size() - 1));
                }
                return article;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

}

