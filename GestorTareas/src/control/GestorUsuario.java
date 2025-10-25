package control;

import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;

/** clase que tendrá una lista de usuarios y métodos para 
 * trabajar con ellos: agregar, eliminar, buscar, listar, etc. */
public class GestorUsuario {
        private List<Usuario> usuarios;

    public GestorUsuario() {
        usuarios = new ArrayList<>();
    }

}
