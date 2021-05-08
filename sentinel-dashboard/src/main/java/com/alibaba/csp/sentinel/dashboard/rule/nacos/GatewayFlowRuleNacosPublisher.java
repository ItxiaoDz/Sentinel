package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayFlowRuleNacosPublisher implements DynamicRulePublisher<List<GatewayFlowRuleEntity>> {

    @Autowired
    private NacosPropertiesConfiguration nacosPropertiesConfiguration;

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<List<GatewayFlowRuleEntity>, String> converter;

    @Override
    public void publish(String appName, List<GatewayFlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(appName, "appName cannot be empty");
        if (rules == null) {
            return;
        }
        String dataID=new StringBuilder(appName).append(NacosConstants.GATEWAY_FLOW_DATA_ID_POSTFIX).toString();
        configService.publishConfig(dataID, nacosPropertiesConfiguration.getGroupId(), converter.convert(rules));
    }
}
