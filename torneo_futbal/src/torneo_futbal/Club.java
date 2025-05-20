package torneo_futbal;

public class Club {
	
	private String nombre;
	
	public Club(String nombre) {
        this.nombre = nombre;
      
    }
    
    
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return nombre ;
    }

}
