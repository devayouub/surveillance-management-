package management;

import java.util.ArrayList;

public class utilisateurmodel {
    private ArrayList<utilisateur> users;

    
    public utilisateurmodel() {
        this.users = new ArrayList<>();
    }

    
    public ArrayList<utilisateur> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<utilisateur> users) {
        this.users = users;
    }

   
    public void addUser(utilisateur user) {
        if (!emailExiste(user.getEmail())) {
            users.add(user);
            System.out.println(" Utilisateur ajouté : " + user.getUsername());
        } else {
            System.out.println("email " + user.getEmail() + " est déjà utilisé !");
        }
    }

    
    private boolean emailExiste(String email) {
        for (utilisateur u : users) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    
    public void removeUser(int index) {
        
            users.remove(index);
        
    }

    public void updateUser(String email, String newUsername, String newEmail, String newPassword) {
        for (utilisateur user : users) {
        	user.setEmail(newEmail);
        	user.setPassword(newPassword);
        	user.setUsername(newUsername);
        }}
    public void afficherUtilisateurs() {
        if (users.isEmpty()) {
            System.out.println(" Aucun utilisateur enregistré.");
        } else {
            System.out.println(" Liste des utilisateurs :");
            for (utilisateur u : users) {
                System.out.println("  - " + u.getUsername() + " (" + u.getEmail() + ")");
            }
        }
    }
}