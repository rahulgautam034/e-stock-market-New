package com.stockmarket.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.stockmarket.exception.ExceptionMessage;
import com.stockmarket.exception.StockException;
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
    public Exception decode(String methodKey, Response response) {
        String message = null;
        Reader reader = null;

        try {
            reader = response.body().asReader();
            //Easy way to read the stream and get a String object
            String result = CharStreams.toString(reader);
            //use a Jackson ObjectMapper to convert the Json String into a
            //Pojo
            ObjectMapper mapper = new ObjectMapper();
            //just in case you missed an attribute in the Pojo
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //init the Pojo
            ExceptionMessage exceptionMessage = mapper.readValue(result,
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
        throw new StockException(message);
    }
}
