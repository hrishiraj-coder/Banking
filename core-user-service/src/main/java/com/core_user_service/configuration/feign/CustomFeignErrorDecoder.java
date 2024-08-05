package com.core_user_service.configuration.feign;

import com.core_user_service.exception.SimpleBankingGlobalException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        SimpleBankingGlobalException globalException = extractBankingCoreException(response);

        return switch (response.status()) {
            case 400 -> {
                log.error("Error in request went through feign client {}", globalException.getCode() + " - "
                        + globalException.getMessage());
                yield globalException;
            }

            case 401 -> {
                log.error("Unauthorized request through feign");
                yield new Exception("Unauthorized Request through feign");
            }

            case 404 -> {
                log.error("Unidentified request through feign");
                yield new Exception("Unidentified Request through feign ");
            }

            default -> {
                log.error("Error in request went through feign client");
                yield new Exception("Common Feign Exception");
            }
        };


    }

    private SimpleBankingGlobalException extractBankingCoreException(Response response) {
        SimpleBankingGlobalException simpleBankingGlobalException = null;
        Reader reader = null;

        try {
            reader = response.body().asReader(StandardCharsets.UTF_8);
            String result = IOUtils.toString(reader);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            simpleBankingGlobalException = mapper.readValue(result, SimpleBankingGlobalException.class);
        } catch (IOException e) {
            log.error("IO Exception on reading exception message from feign client" + e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("IO Exception on reading exception message  feign client" + e);
            }
        }
        return simpleBankingGlobalException;


    }
}
