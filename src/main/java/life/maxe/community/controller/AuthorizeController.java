package life.maxe.community.controller;

import life.maxe.community.dto.AccessTokenDTO;
import life.maxe.community.dto.GithubUser;
import life.maxe.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import okhttp3.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class AuthorizeController {

    /*自动把实例化好的东西放在这里*/
    @Autowired
    private GithubProvider githubProvider;

    /*
    * 为了能够在不同的项目环境读取这些变量，所以放进了application里面
    * */
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        if(user!=null){
//            登录成功，写cookie 和 session
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
//            登录失败，重新登录
            return "redirect:/";
        }
    }
}
