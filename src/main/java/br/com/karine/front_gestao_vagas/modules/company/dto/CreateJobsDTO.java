package br.com.karine.front_gestao_vagas.modules.company.dto;

import lombok.Data;

@Data
public class CreateJobsDTO {
    
    private String description;
    private String level;
    private String benefits;
}
