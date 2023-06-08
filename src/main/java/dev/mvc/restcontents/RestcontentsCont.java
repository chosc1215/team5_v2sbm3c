package dev.mvc.restcontents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.admin.AdminProcInter;

import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class RestcontentsCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;

  @Autowired
  @Qualifier("dev.mvc.restcontents.RestcontentsProc")
  private RestcontentsProcInter restcontentsProc;
  
  public RestcontentsCont () {
    System.out.println("-> RestcontentsCont created.");
  }
  
// http://localhost:9093/restcontents/create.do?restcateno=1
  //@RequestMapping(value="/restcontents/create.do", method = RequestMethod.GET)
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
