package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParamFlowRuleNacosProvider implements DynamicRuleProvider<List<ParamFlowRule>> {
    private static Logger logger = LoggerFactory.getLogger(ParamFlowRuleNacosProvider.class);

    @Autowired
    private NacosPropertiesConfiguration nacosConfigProperties;

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<String, List<ParamFlowRule>> converter;

    @Override
    public List<ParamFlowRule> getRules(String appName) throws Exception {
        String dataID=new StringBuilder(appName).append(NacosConstants.PARAM_FLOW_DATA_ID_POSTFIX).toString();
        String rules = configService.getConfig(dataID, nacosConfigProperties.getGroupId(), 3000);
        logger.info("pull ParamFlowRule from Nacos Config:{}",rules);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}
