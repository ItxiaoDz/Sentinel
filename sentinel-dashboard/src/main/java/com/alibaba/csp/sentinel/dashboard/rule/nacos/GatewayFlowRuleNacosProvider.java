package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GatewayFlowRuleNacosProvider implements DynamicRuleProvider<List<GatewayFlowRuleEntity>> {
    private static Logger logger = LoggerFactory.getLogger(GatewayFlowRuleNacosProvider.class);

    @Autowired
    private NacosPropertiesConfiguration nacosConfigProperties;

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<String, List<GatewayFlowRuleEntity>> converter;

    @Override
    public List<GatewayFlowRuleEntity> getRules(String appName) throws Exception {
        String dataID=new StringBuilder(appName).append(NacosConstants.GATEWAY_FLOW_DATA_ID_POSTFIX).toString();
        String rules = configService.getConfig(dataID, nacosConfigProperties.getGroupId(), 3000);
        logger.info("pull gateway flow rule entity from Nacos Config:{}",rules);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}
