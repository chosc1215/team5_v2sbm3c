package dev.mvc.restcontents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.restcate.RestcateProc;
import dev.mvc.restcate.RestcateProcInter;
import dev.mvc.restcate.RestcateVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class RestcontentsCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.restcate.RestcateProc")
  private RestcateProcInter restcateProc;

  @Autowired
  @Qualifier("dev.mvc.restcontents.RestcontentsProc")
  private RestcontentsProcInter restcontentsProc;
  
  public RestcontentsCont () {
    System.out.println("-> RestcontentsCont created.");
  }
  
// http://localhost:9093/restcontents/create.do?restcateno=1
  @RequestMapping(value="/restcontents/create.do", method = RequestMethod.GET)
  public ModelAndView create(int restcateno) {
    ModelAndView mav = new ModelAndView();
    
    RestcateVO restcateVO = this.restcateProc.read(restcateno);
    mav.addObject("restcateVO", restcateVO);
    
    mav.setViewName("/restcontents/create");
    
    return mav;
  }

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
