package partFour.youn.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import partFour.youn.dto.MovieDTO;
import partFour.youn.dto.PageRequestDTO;
import partFour.youn.service.MovieService;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes){
        System.out.println("movieDTO = " + movieDTO);

        Long mno = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("msg",mno);

        return "redirect:/movie/list";
    }
    @GetMapping("/list")
    public void list(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        System.out.println("requestDTO = " + requestDTO);
        model.addAttribute("result",movieService.getList(requestDTO));
    }
}
