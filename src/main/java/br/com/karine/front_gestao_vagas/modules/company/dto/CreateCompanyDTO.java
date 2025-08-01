package br.com.karine.front_gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyDTO {
    
    private String username;
    private String name;
    private String email;
    private String password;
    private String website;
    private String description;
}
