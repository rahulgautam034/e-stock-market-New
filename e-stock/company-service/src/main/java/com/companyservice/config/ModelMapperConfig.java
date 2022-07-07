package com.companyservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * model mapper configuration
 *
 */
@Configuration
public class ModelMapperConfig {
        /**
         * bean of model mapper
         */
        @Bean
        public ModelMapper modelMapper() {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper;
        }
}
