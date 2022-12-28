package com.adapters.outbound.http;

import com.adapters.outbound.EnvNames;
import com.adapters.outbound.JsonUtils;
import com.domain.ports.management.farm.CounterGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
public class Counter implements CounterGateway {

  private final RestTemplate restTemplate;
  private final String counterUrl;
  private final String simpsonEndpoint;
  private final String averageEndpoint;

  private final String durationEndpoint;
  private final String durationPeriodSumEndpoint;
  private final String simpsonPeriodSumEndpoint;

  public Counter(
      @Value("${" + EnvNames.COUNTER_URL + "}") String counterUrl,
      @Value("${" + EnvNames.COUNTER_SIMPSON_ENDPOINT + "}") String simpsonEndpoint,
      @Value("${" + EnvNames.COUNTER_AVERAGE_ENDPOINT + "}") String averageEndpoint,
      @Value("${" + EnvNames.COUNTER_DURATION_ENDPOINT + "}") String durationEndpoint,
      @Value("${" + EnvNames.DURATION_PERIOD_SUM_ENDPOINT + "}") String durationPeriodSumEndpoint,
      @Value("${" + EnvNames.SIMPSON_PERIOD_SUM_ENDPOINT + "}") String simpsonPeriodSumEndpoint
  ) {
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    this.restTemplate = new RestTemplate();
    this.restTemplate.setRequestFactory(requestFactory);
    this.counterUrl = counterUrl;
    this.simpsonEndpoint = simpsonEndpoint;
    this.averageEndpoint = averageEndpoint;
    this.durationEndpoint = durationEndpoint;
    this.durationPeriodSumEndpoint = durationPeriodSumEndpoint;
    this.simpsonPeriodSumEndpoint = simpsonPeriodSumEndpoint;
  }

  public Double countAverage(List<Double> data) {
    try {
      return request(counterUrl.concat(averageEndpoint), JsonUtils.getObjectMapper().writeValueAsString(data));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public Double countDuration(Map<Date, Boolean> data) {
    try {
      return request(counterUrl.concat(durationEndpoint), JsonUtils.getObjectMapper().writeValueAsString(data));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public Double countSimpson(Map<Date, Double> data) {
    try {
      return request(counterUrl.concat(simpsonEndpoint), JsonUtils.getObjectMapper().writeValueAsString(data));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public Double countPeriodFarmWorkingHoursStatisticsSum(String farmId, Date startDate, Date endDate) {
    try {
      Map<String, Date> body = Map.of("start_date", startDate, "end_date", endDate);
      return request(counterUrl.concat(durationPeriodSumEndpoint),
          JsonUtils.getObjectMapper().writeValueAsString(body));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public Double countPeriodFarmEnergyStatisticsSum(String farmId, Date startDate, Date endDate) {
    try {
      Map<String, Date> body = Map.of("start_date", startDate, "end_date", endDate);
      return request(counterUrl.concat(simpsonPeriodSumEndpoint), JsonUtils.getObjectMapper().writeValueAsString(body));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private Double request(String url, String data) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return sendRequest(
        url,
        httpHeaders,
        data
    ).getBody();
  }

  private ResponseEntity<Double> sendRequest(String url, HttpHeaders headers, String body) {
    HttpEntity<String> request = new HttpEntity<>(body, headers);
    return restTemplate.postForEntity(
        url,
        request,
        Double.class
    );
  }
}