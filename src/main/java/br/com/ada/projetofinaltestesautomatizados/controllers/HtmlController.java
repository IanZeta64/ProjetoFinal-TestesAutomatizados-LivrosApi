package br.com.ada.projetofinaltestesautomatizados.controllers;

import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/livros")
@RequiredArgsConstructor
public class HtmlController {

    private final LivroController livroController;

    @GetMapping("/livros")
    public String listarTodos(Model model){
        List<LivroResponse> response = livroController.buscarTodos();
        model.addAttribute("livros", response);
        return "livros";
    }

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/{isbn}")
    public String buscarLivroIsbn(@PathVariable("isbn") String isbn, Model model){
        LivroResponse livroResponse = livroController.buscarPorIsbn(isbn);
        model.addAttribute("livro", livroResponse);
        return "add-livro";
    }

    @GetMapping(params = "titulo")
    public String buscarLivroTitulo(@RequestParam("titulo") String titulo, Model model){
        List<LivroResponse> livroResponse = livroController.buscarPorTitulo(titulo);
        model.addAttribute("livros", livroResponse);
        return "add-livro";
    }
    @PostMapping
    public String salvarLivro(@RequestBody LivroRequest livroRequest){
        livroController.salvar(livroRequest);
        return "redirect:index";
    }
    @PostMapping("/atualizar")
    public String atualizarLivro(@RequestParam("isbn") String isbn, @RequestBody LivroRequest livroRequest) {
        livroController.atualizar(isbn, livroRequest);
        return "redirect:/livros";
    }

    @GetMapping("add-livro")
    public String novoLivro(Model model){
        model.addAttribute("livro", new LivroRequest());
        return "add-livro";
    }

    @DeleteMapping("/{isbn}")
    public String deleteAthlete(@PathVariable("isbn") String isbn){
        livroController.deletar(isbn);
        return "redirect:index";
    }

//    @PostMapping("/federation")
//    public String searchForFederation(@RequestParam("federation") String federation, Model models){
//        List<Athlete> athletes = athleteServices.findByFederation(federation);
//        models.addAttribute("athletes", athletes);
//        return  "athletes";
//    }
//
//    @PostMapping("/name")
//    public String searchForName(@RequestParam("name") String name, Model models){
//        List<Athlete> athletes = athleteServices.findByNameStartingWith(name);
//        models.addAttribute("athletes", athletes);
//        return  "athletes";
//    }
//    @PostMapping("/id")
//    public String searchForId(@RequestParam("id") String id, Model models){
//        List <Athlete> athletes = List.of(athleteServices.findById(Long.parseLong(id)));
//        models.addAttribute("athletes", athletes);
//        return  "athletes";
//    }
//
//
//    @PostMapping("/modality")
//    public String searchForModality(@RequestParam("modality") String modality, Model models){
//        List<Athlete> athletes = athleteServices.findByModality(modality);
//        models.addAttribute("athletes", athletes);
//        return  "athletes";
//    }
}
