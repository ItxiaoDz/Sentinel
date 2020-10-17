package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParamFlowRuleNacosPublisher implements DynamicRulePublisher<List<ParamFlowRule>> {

    @Autowired
    private NacosPropertiesConfiguration nacosPropertiesConfiguration;

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<List<ParamFlowRule>, String> converter;

    @Override
    public void publish(String appName, List<ParamFlowRule> rules) throws Exception {
        AssertUtil.notEmpty(appName, "appName cannot be empty");
        if (rules == null) {
            return;
        }
        String dataID=new StringBuilder(appName).append(NacosConstants.PARAM_FLOW_DATA_ID_POSTFIX).toString();
        configService.publishConfig(dataID, nacosPropertiesConfiguration.getGroupId(), converter.convert(rules));
    }
}
