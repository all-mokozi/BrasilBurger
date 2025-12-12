package brasil.repository;

import java.util.List;

import brasil.entity.Produit;

public interface IProduitRepository {
    public void  insert( Produit b);
   Double findPriceById(int idProduit);
   List<Produit> findComposantsDisponibles();
   
}
