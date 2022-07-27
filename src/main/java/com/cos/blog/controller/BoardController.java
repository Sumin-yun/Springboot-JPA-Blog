package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

  // 컨트롤로에서 세션을 어떻게 찾는지?
  // @AuthenticationPrincipal PrincipalDetail principal
  @GetMapping({"", "/"})
  public String index() {  
    return "index"; 
  }

}
