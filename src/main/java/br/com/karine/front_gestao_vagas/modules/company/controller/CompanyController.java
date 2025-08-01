package br.com.karine.front_gestao_vagas.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.karine.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.karine.front_gestao_vagas.modules.company.dto.CreateJobsDTO;
import br.com.karine.front_gestao_vagas.modules.company.service.CompanyService;
import br.com.karine.front_gestao_vagas.modules.company.service.CreateCompanyService;
import br.com.karine.front_gestao_vagas.modules.company.service.CreateJobService;
import br.com.karine.front_gestao_vagas.utils.FormatErrorMessage;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CreateCompanyService createCompanyService;

    @Autowired
    private CreateJobService createJobService;

    @GetMapping("/login")
    public String login(Model model) {
        return "company/login";
    }

    @PostMapping("/signIn")
    public String singIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password) {

        try {
            var token = this.companyService.login(username, password);
            var grants = token.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);
            auth.setDetails(token.getAccess_token());

            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("token", token);
            
            return "redirect:/company/jobs";

        } catch (HttpClientErrorException e) {
            
            redirectAttributes.addFlashAttribute("error_message", "Usuário/senha incorreta.");
            
            return "redirect:/company/login";
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("company", new CreateCompanyDTO());
        return "company/create";
    }

    @PostMapping("/create")
    public String save(Model model, CreateCompanyDTO company) {
        try {
            this.createCompanyService.execute(company);
        } catch (HttpClientErrorException e) {
            model.addAttribute("error_message", 
                FormatErrorMessage.formatErrorMessage(e.getResponseBodyAsString()));
            model.addAttribute("company", company);
            return "company/create";
        }

        return "company/login";
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String jobs(Model model) {
        model.addAttribute("jobs", new CreateJobsDTO());
        return "company/jobs";
    }

    @PostMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String createJobs(Model model, CreateJobsDTO createJobsDTO) {

        this.createJobService.execute(createJobsDTO, getToken());
        return "redirect:/company/jobs";
    }

        private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }
}
