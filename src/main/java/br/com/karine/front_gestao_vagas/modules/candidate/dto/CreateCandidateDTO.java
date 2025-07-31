package br.com.karine.front_gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCandidateDTO {
    
    private String username;
    private String name;
    private String password;
    private String email;
    private String description;
}
