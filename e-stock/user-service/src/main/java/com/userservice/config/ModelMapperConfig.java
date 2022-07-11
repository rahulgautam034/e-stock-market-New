package com.userservice.config;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * model mapper configuration
 *
 */
@NoArgsConstructor
@Configuration
public class ModelMapperConfig {
        /**
         * bean of model mapper
         */
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
}
