package brasil.dto;

import java.util.ArrayList;
import java.util.List;


public class MenuDTO extends ProduitDTO {
    
    
    private List<ComposantDTO> composants = new ArrayList<>();

   

    public List<ComposantDTO> getComposants() {
        return composants;
    }

    public void setComposants(List<ComposantDTO> composants) {
        this.composants = composants;
    }
    
    public void addComposant(ComposantDTO composant) {
        this.composants.add(composant);
    }
}