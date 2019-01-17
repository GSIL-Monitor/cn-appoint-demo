package com.jd.appoint.service.order.es;

import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jd.appoint.domain.enums.EncryptTypeEnum;
import com.jd.appoint.vo.page.Direction;
import com.jd.appoint.vo.page.Sort;
import com.jd.fastjson.PropertyNamingStrategy;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by shaohongsen on 2018/5/15.
 */
@Service
public class EsQueryBuilder {
    private static final List<SortBuilder> DEFAULT_SORTS = Arrays.asList(new FieldSortBuilder("id").order(SortOrder.DESC));

    public QueryBuilder buildQuery(Map<String, String> searchMap) {
        if (searchMap == null || searchMap.isEmpty()) {
            // 没有查询条件，用存在id代替，id必然存在
            return QueryBuilders.existsQuery("id");
        } else {
            BoolQueryBuilder baseQuery = QueryBuilders.boolQuery();
            searchMap.entrySet().stream()
                    .filter(entry -> !Strings.isNullOrEmpty(entry.getValue()))//null的不参与筛选
                    .map(entry -> buildItemQuery(entry))//构建基本筛选条件
                    .forEach(queryBuilder -> baseQuery.must(queryBuilder));//多个条件用且的关系
            return baseQuery;
        }
    }

    public String toUnderScore(String alias) {
        String key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, alias);
        return key;
    }


    public String toLowerCamel(String alias) {
        String key = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, alias);
        return key;
    }

    /**
     * staffName.EQ 邵
     *
     * @param entry
     * @return
     */
    private QueryBuilder buildItemQuery(Map.Entry<String, String> entry) {
        String key = entry.getKey();
        //array[0]  属性名，array[2]操作符
        //remark.EQ
        String[] array = key.split("\\.");
        if (array.length != 2) {
            throw new IllegalArgumentException("search key error:" + key);
        }
        String filedName = toUnderScore(array[0]);
        Operator operator = Operator.valueOf(array[1]);
        String value = entry.getValue();
        return operator.getQuery(filedName, value);
    }

    public List<SortBuilder> buildSorts(List<Sort> sorts) {
        if (sorts == null || sorts.isEmpty()) {
            return DEFAULT_SORTS;
        }
        List<SortBuilder> result = sorts.stream()
                .filter(sort -> sort != null && !Strings.isNullOrEmpty(sort.getFieldName()) && sort.getDirection() != null)
                .map(sort -> {
                    String fieldName = toUnderScore(sort.getFieldName());
                    SortBuilder sortBuilder = new FieldSortBuilder(fieldName);
                    if (Direction.ASC == sort.getDirection()) {
                        sortBuilder.order(SortOrder.ASC);
                    } else {
                        sortBuilder.order(SortOrder.DESC);
                    }
                    return sortBuilder;
                })
                .collect(Collectors.toList());
        return result;
    }

    enum Operator {
        EQ("等于") {
            @Override
            QueryBuilder getQuery(String fieldName, String value) {
                return QueryBuilders.termQuery(fieldName, value);
            }
        },
        CONTAIN("包含") {
            @Override
            QueryBuilder getQuery(String fieldName, String value) {
                return QueryBuilders.wildcardQuery(fieldName, "*" + value + "*");
            }
        },
        GTE("大于等于") {
            @Override
            QueryBuilder getQuery(String fieldName, String value) {
                return QueryBuilders.rangeQuery(fieldName).gte(value);
            }
        },
        GT("大于") {
            @Override
            QueryBuilder getQuery(String fieldName, String value) {
                return QueryBuilders.rangeQuery(fieldName).gt(value);
            }
        },
        LT("小于") {
            @Override
            QueryBuilder getQuery(String fieldName, String value) {
                return QueryBuilders.rangeQuery(fieldName).lt(value);
            }
        },
        LTE("小于等于") {
            @Override
            QueryBuilder getQuery(String fieldName, String value) {
                return QueryBuilders.rangeQuery(fieldName).lte(value);
            }
        },
        IN("IN") {
            @Override
            QueryBuilder getQuery(String fieldName, String value) {
                List<String> valueList = Splitter.on(",").omitEmptyStrings().splitToList(value);
                return QueryBuilders.termsQuery(fieldName, valueList);
            }
        };

        private String remark;

        Operator(String remark) {
            this.remark = remark;
        }

        abstract QueryBuilder getQuery(String fieldName, String value);
    }

}
