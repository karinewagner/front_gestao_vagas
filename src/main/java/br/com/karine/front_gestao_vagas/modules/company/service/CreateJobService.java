package br.com.karine.front_gestao_vagas.modules.company.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.karine.front_gestao_vagas.modules.company.dto.CreateJobsDTO;

@Service
public class CreateJobService {
    
    public String execute(CreateJobsDTO createJobsDTO, String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<CreateJobsDTO> request = new HttpEntity<>(createJobsDTO, headers);

        var result = rt.postForObject("http://localhost:8080/company/job/", request, String.class);

        return result;
    }
}
