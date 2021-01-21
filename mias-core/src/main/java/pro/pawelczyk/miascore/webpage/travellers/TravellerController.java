package pro.pawelczyk.miascore.webpage.travellers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/travellers")
@Controller
public class TravellerController {

    private final TravellerService travellerService;

    @GetMapping
    public String listBreweries(Model model) {
        model.addAttribute("travellers", travellerService.getAllTravellers());
        return "travellers/listTravellers";
    }

}
