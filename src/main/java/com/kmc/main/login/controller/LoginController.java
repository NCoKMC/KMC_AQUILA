package com.kmc.main.login.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmc.main.login.dto.SignupRequestDto;
import com.kmc.main.user.dto.UserDto;
import com.kmc.main.user.service.UserService;

@Controller
@RequestMapping("/")
public class LoginController {
	@Autowired
    private UserService userService;


//	@PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String login(@RequestBody LoginDto request) {
//        return "/login";
//    }


    @GetMapping(value = "/signup")
    public String signup() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return "/signup";
        return "redirect:/index";
    }

    @PostMapping("/signup")
    public String saveUser(UserDto userVo) { // 회원 가입
        try {
        	//System.out.print(reqMap.toString());
            userService.signup(userVo);
        } catch (DuplicateKeyException e) {
            return "redirect:/signup?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signup?error_code=-99";
        }
        return "redirect:/login";
    }

    @PostMapping("/auth")
    public String auth(UserDto userVo) { // 회원 가입
        try {
        	//System.out.print(reqMap.toString());
            userService.signup(userVo);
        } catch (DuplicateKeyException e) {
            return "redirect:/signup?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signup?error_code=-99";
        }
        return "redirect:/login";
    }

	@GetMapping("/login")
	public String login() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
        	return "/loginPage";
        }

        return "redirect:/index";
	}

	@GetMapping("/index")
	public String index(Model model) throws Exception {
		String id =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // token에 저장되어 있는 인증된 사용자의 id 값

        UserDto userVo = userService.getUserByKmcCd(id+"");
        userVo.setPassword(null); // password는 보이지 않도록 null로 설정
        model.addAttribute("user", userVo);
		return "/index";
	}

	@GetMapping("/gantt")
	public String demo() throws Exception {

		return "gantt";
	}
}
