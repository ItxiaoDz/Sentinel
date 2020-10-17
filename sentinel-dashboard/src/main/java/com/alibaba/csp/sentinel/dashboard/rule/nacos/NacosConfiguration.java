package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

@EnableConfigurationProperties(NacosPropertiesConfiguration.class)
@Configuration
public class NacosConfiguration {

    @Bean
    public Converter<List<FlowRuleEntity>,String> flowRuleEntityEncoder(){
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String,List<FlowRuleEntity>> flowRuleEntityDecoder(){
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean
    public Converter<List<ParamFlowRule>,String> paramFlowRuleEntityEncoder(){
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String,List<ParamFlowRule>> paramFlowRuleEntityDecoder(){
        return s -> JSON.parseArray(s, ParamFlowRule.class);
    }

    @Bean
    public ConfigService nacosConfigService(NacosPropertiesConfiguration nacosPropertiesConfiguration) throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, nacosPropertiesConfiguration.getServerAddr());
        properties.put(PropertyKeyConst.NAMESPACE, nacosPropertiesConfiguration.getNamespace());
        return ConfigFactory.createConfigService(properties);
    }
}
