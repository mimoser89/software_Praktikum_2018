import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.Roof;
import project.RoofService;

@Controller   
public class RoofController {
	
  @Autowired
	private RoofService roofService;

	@RequestMapping(value="/addroof", method = RequestMethod.GET)
	public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView();
    Roof roof = new Roof();
    modelAndView.addObject("roof", roof);
    modelAndView.setViewName("addroof");
    return modelAndView;
  }

  @RequestMapping(value="/addroof", method = RequestMethod.POST)
  public ModelAndView createNewRoof(@Valid Roof roof, BindingResult bindingResult) {
    ModelAndView mav = new ModelAndView();
    Roof roofExists = roofService.findRoofByAddress(roof.getAddress());
    
    if (roofExists != null) {
      bindingResult.rejectValue("...", "error.roof", "This roof has already been registered");
    }
    if(bindingResult.hasErrors()) {
      mav.setViewName("addroof");
    } else {
      roofService.saveRoof(roof);
      mav.addObject("successMessage", "Roof has been registered successfully");
      mav.addObject("roof", new Roof());
      mav.setViewName("addroof");
    }
    return mav;
  }

}