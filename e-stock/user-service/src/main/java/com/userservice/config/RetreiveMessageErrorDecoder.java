package com.userservice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.userservice.exception.CompanyException;
import com.userservice.exception.ExceptionMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;

@NoArgsConstructor
@Slf4j
public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(final String methodKey,final Response response) {
        String message = null;
        Reader reader = null;

        try {
            reader = response.body().asReader();
            //Easy way to read the stream and get a String object
            final String result = CharStreams.toString(reader);
            //use a Jackson ObjectMapper to convert the Json String into a
            //Pojo
            final ObjectMapper mapper = new ObjectMapper();
            //just in case you missed an attribute in the Pojo
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //init the Pojo
            final ExceptionMessage exceptionMessage = mapper.readValue(result,
                    ExceptionMessage.class);

            message = exceptionMessage.getMessage();

        } catch (IOException e) {

            log.error("error while decode: {}",e.getMessage());
        }finally {

            //It is the responsibility of the caller to close the stream.
            try {

                if (reader != null){
                    reader.close();
                }

            } catch (IOException e) {
                log.error("error while decode: {}",e.getMessage());
            }
        }
        throw new CompanyException(message);
    }
}
