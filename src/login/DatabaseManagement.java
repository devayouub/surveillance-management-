
package login;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import management.ClassRoom;
import management.Cycle;
import management.Domain;
import management.DomainInfo;
import management.Exam;
import management.Module;
import management.ModuleInfo;
import management.Professor;
import management.User;
public class DatabaseManagement {
    private static final String URL = "jdbc:mysql://localhost:3306/surveillance_data_base";
    private static final String USER = "root";
    private static final String DB_PASSWORD = "ayoub2005";
    static ObservableList<User> users = FXCollections.observableArrayList();
    static ObservableList<Professor> professors = FXCollections.observableArrayList();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, DB_PASSWORD);
    }

    // Validate password: at least 8 characters, 1 uppercase letter, 1 number
    public static String isValidPassword(String user_password) {
        if (user_password.length() < 8) {
            return "Password must contain at least 8 characters.";
        }
        if (!user_password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter.";
        }
        if (!user_password.matches(".*\\d.*")) {
            return "Password must contain at least one number.";
        }
        return "Valid"; // If it passes all checks
    }

    
    public static boolean authenticateUser(String user_name , String user_password) {
    	
    	String query = "SELECT * FROM user WHERE user_name = ? AND user_password = ?";
    	
    	try (Connection conn = getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		stmnt.setString(1, user_name);
    		stmnt.setString(2, user_password);
    		
    		ResultSet rs = stmnt.executeQuery();
    		if(rs.next()) {
    			return true;
    			
    		}
    		return false;
    	}catch (SQLException e) {
    		e.printStackTrace();
			return false;
		}
    	
    	
    }
    
    public static  boolean addUser(User user) {
        // Prepare SQL insert statement (no need to include userID because it's AUTO_INCREMENT)
        String query = "INSERT INTO user (user_name, is_admin, remember_me,user_password) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmnt = conn.prepareStatement(query)) {

            // Set parameters for the insert statement
            stmnt.setString(1, user.getUsername());
            stmnt.setBoolean(2, user.getIsAdmin());
            stmnt.setBoolean(3, user.getisRemembered());
            stmnt.setString(4, user.getPassword());

         
            int result = stmnt.executeUpdate();
            if (result >= 1) {

                return true; 
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
		return false;
    }

    
    
    public static boolean removeUser(User user){
    	String query = "delete from user where ID_user = ?";
    	try(Connection conn = getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		stmnt.setInt(1,user.getUserID());
    		int rowsaffected = stmnt.executeUpdate();
    		if(rowsaffected>0) {return true;
    		}
   
    	}catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public static boolean check_if_is_admin(String user_name){
    	String query = "select from users where username = ?";
    	try(Connection conn = getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		stmnt.setString(1, user_name);
    		ResultSet rs = stmnt.executeQuery();
    		if(rs.next()) {
    			return rs.getBoolean("is_admin");
    		}
    		
    	}catch (SQLException e) {
			e.getStackTrace();
		}
    	return false;


    	
    }
    
    public static boolean RememberMeUpdater(String user_name,boolean call) {
        String query = "UPDATE user SET remember_me = ? WHERE user_name = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, call);
            stmt.setString(2,user_name);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
               return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
      
    }
    
public static boolean isUserRemembered() {
    String query = "SELECT user_name FROM user WHERE remember_me = TRUE LIMIT 1";
    try (Connection conn = getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            return true;  // User is remembered
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;  // No user remembered
}
public static String getRememberedUser() {
	String query = "SELECT user_name FROM user WHERE remember_me = TRUE LIMIT 1";
	try (Connection conn = getConnection(); 
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {
	        if (rs.next()) {
	        	 return	rs.getString(1);	              // returns remembered user

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;  // No user remembered
	}

public static void loadUsersFromDatabase( TableView UsersTable) {
    try (Connection conn = getConnection(); //  DB connection method
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT ID_user, user_name, is_admin,remember_me,user_password FROM user")) {

        while (rs.next()) {
            int id = rs.getInt("ID_user");
            String username = rs.getString("user_name");
            boolean isAdmin = rs.getBoolean("is_admin");
            boolean isRemembered = rs.getBoolean("remember_me");
            String password = rs.getString("user_password");
            users.add(new User(id, username, isAdmin, isRemembered, password));
        }
        UsersTable.setItems(users);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static ObservableList<Professor> loadProfessorsFromDatabase() {
        ObservableList<Professor> professors = FXCollections.observableArrayList();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM professor")) {

            while (rs.next()) {
                int id = rs.getInt("ID_prof");
                String firstName = rs.getString("prenom_prof");
                String lastName = rs.getString("nom_prof");
                String email = rs.getString("email_prof");
                professors.add(new Professor(id, firstName, lastName, email));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professors;
    }




    public static boolean addProfessor(Professor professor) {
    	
        if (!isValidEmailFormat(professor.getPrEmail())) {

            return false;
        }
    	

        String query = "INSERT INTO professor (nom_prof, prenom_prof,email_prof) VALUES (?, ?, ?)";


        try (Connection conn = getConnection();
                PreparedStatement stmnt = conn.prepareStatement(query)) {

            stmnt.setString(1, professor.getPrLastName());  
            stmnt.setString(2, professor.getPrFirstName()); 
            stmnt.setString(3, professor.getPrEmail());     

            int result = stmnt.executeUpdate();
            if (result >= 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void updateProfessor(Professor professor) {
        String sql = "UPDATE professor SET nom_prof = ?, prenom_prof = ?,email_prof = ? WHERE ID_prof = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, professor.getPrLastName());
            stmt.setString(2, professor.getPrFirstName());
            stmt.setString(3, professor.getPrEmail());
            stmt.setInt(4, professor.getProfId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public static boolean deleteProfessor(Professor professor) {
    String query = "DELETE FROM professor WHERE ID_prof = ?";

    try (Connection conn = getConnection();
            PreparedStatement stmnt = conn.prepareStatement(query)) {

        stmnt.setInt(1, professor.getProfId());
        int rowsAffected = stmnt.executeUpdate();

        if (rowsAffected > 0) {
            return true;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public static boolean isValidEmailFormat(String email) {
    if (email == null) {
        return false;
    }
    else {
    	if(email.equals("")) {
    	return true;
    	}
    //  to check if email follows the format string@string.string
    String emailformat = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    return email.matches(emailformat);
}
    }
public static String addModuleToDomainSemester(String domainname, int semesterNo, Module module) {
    boolean result1 = false;
    boolean result2 = false;

    try (Connection conn = getConnection()) {
        conn.setAutoCommit(false); // Start transaction

        // Step 1: Check if module exists
        String selectModuleSQL = "SELECT mnémonique FROM module WHERE mnémonique = ?";
        try (PreparedStatement selectStmt = conn.prepareStatement(selectModuleSQL)) {
            selectStmt.setString(1, module.getUniqueName());
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    return "module exists";
                }
            }
        }

        // Step 2: Insert the module
        String insertModuleSQL = "INSERT INTO module (mnémonique) VALUES (?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertModuleSQL)) {
            insertStmt.setString(1, module.getUniqueName());
            int result = insertStmt.executeUpdate();
            result1 = (result >= 1);
        }

        // Step 3: Retrieve domain ID
        int domainId = -1;
        String selectDomainSQL = "SELECT ID_spécialité FROM spécialité WHERE nom_spécialité = ?";
        try (PreparedStatement domainStmt = conn.prepareStatement(selectDomainSQL)) {
            domainStmt.setString(1, domainname);
            try (ResultSet rs = domainStmt.executeQuery()) {
                if (rs.next()) {
                    domainId = rs.getInt("ID_spécialité");
                } else {
                    conn.rollback();
                    return "domain not found";
                }
            }
        }

        // Step 4: Insert into Semester_Module table
        String insertSemesterModuleSQL = "INSERT INTO Semester_Module (domain_id, semester_no, module_id) VALUES (?, ?, ?)";
        try (PreparedStatement semesterStmt = conn.prepareStatement(insertSemesterModuleSQL)) {
            semesterStmt.setInt(1, domainId);
            semesterStmt.setInt(2, semesterNo);
            semesterStmt.setString(3, module.getUniqueName());
            int rowsInserted = semesterStmt.executeUpdate();
            result2 = (rowsInserted > 0);
        }

        conn.commit(); // Commit if all succeeded
    } catch (SQLException e) {
        e.printStackTrace();
        return "failed";
    }

    return (result1 && result2) ? "success" : "failed";
}






public static boolean deleteModule(Module module) {
    String deleteFromSemesterModule = "DELETE FROM semester_module WHERE module_id = ?";
    String deleteFromModule = "DELETE FROM module WHERE mnémonique = ?";

    try (Connection conn = getConnection()) {
        conn.setAutoCommit(false); // Begin transaction

        try (PreparedStatement stmt1 = conn.prepareStatement(deleteFromSemesterModule);
             PreparedStatement stmt2 = conn.prepareStatement(deleteFromModule)) {

            // Delete from semester_module
            stmt1.setString(1, module.getUniqueName());
            stmt1.executeUpdate();

            // Delete from module
            stmt2.setString(1, module.getUniqueName());
            int rowsDeleted = stmt2.executeUpdate();

            conn.commit(); // Commit both deletions
            return rowsDeleted > 0;

        } catch (SQLException e) {
            conn.rollback(); // Roll back if either deletion fails
            e.printStackTrace();
            return false;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public static ObservableList<String> getCycles() {
    ObservableList<String> cycles = FXCollections.observableArrayList();
    String query = "SELECT * FROM Cycle";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String name = rs.getString("nom_cycle");
            cycles.add(name);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return cycles;
}
public static Cycle getCycle(String cycleName) {
    String query = "SELECT * FROM Cycle WHERE nom_cycle = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, cycleName); // Set the parameter BEFORE executing

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("ID_cycle"); // Adjust column names to match your schema
                String name = rs.getString("nom_cycle");

                return new Cycle(id, name);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
public static ObservableList<String> getDomaines(String cyclename) {
    ObservableList<String> domaines = FXCollections.observableArrayList();
    Cycle cycle = getCycle(cyclename);
    String query = "SELECT nom_spécialité FROM spécialité WHERE cycle_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, cycle.getId()); // Set parameter before execution
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("nom_spécialité"); // Correct column name
                domaines.add(name);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return domaines;
}
public static ObservableList<Domain> getDomainesAsAWhole(String cyclename) {
    ObservableList<Domain> domaines = FXCollections.observableArrayList();
    Cycle cycle = getCycle(cyclename);
    String query = "SELECT * FROM spécialité WHERE cycle_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, cycle.getId()); // Set parameter before execution
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Domain domain = new Domain(rs.getInt("ID_spécialité"),rs.getString("nom_spécialité"),rs.getInt("cycle_id"));; // Correct column name
                domaines.add(domain);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return domaines;
}


    
    
    
public static List<String> getModulesBySemesterAndDomain(int semesterNumber, int domainId) {
    List<String> modules = new ArrayList<>();

    String query = "SELECT m.module_name FROM Module m JOIN Semester_Module sm ON m.module_id = sm.module_id WHERE sm.semester_no = ? AND sm.domain_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, semesterNumber);
        stmt.setInt(2, domainId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            modules.add(rs.getString("module_name"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return modules;
}

public static ObservableList<ModuleInfo> loadModuleInfo() {
    ObservableList<ModuleInfo> data = FXCollections.observableArrayList();

    String query = "SELECT c.nom_cycle, d.nom_spécialité, sm.semester_no, m.mnémonique FROM cycle c JOIN spécialité d ON c.ID_cycle = d.cycle_id JOIN Semester_Module sm ON d.ID_spécialité = sm.domain_id JOIN Module m ON sm.module_id = m.mnémonique        ORDER BY c.nom_cycle, d.nom_spécialité, sm.semester_no    ";
    	

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String cycle = rs.getString("nom_cycle");
            String domain = rs.getString("nom_spécialité");
            int semester = rs.getInt("semester_no");
            String module = rs.getString("mnémonique");

            data.add(new ModuleInfo(cycle, domain, semester, module));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return data;
}

    
public static boolean updateModule(String oldKey, Module module) {
    String selectSemesterModule = "SELECT domain_id, semester_no FROM semester_module WHERE module_id = ?";
    String deleteFromSemesterModule = "DELETE FROM semester_module WHERE module_id = ?";
    String updateModule = "UPDATE module SET mnémonique = ? WHERE mnémonique = ?";
    String insertIntoSemesterModule = "INSERT INTO semester_module (domain_id, semester_no, module_id) VALUES (?, ?, ?)";

    try (Connection conn = getConnection()) {
        conn.setAutoCommit(false); // Start transaction

        List<int[]> cachedSemesterModules = new ArrayList<>();

        // Step 1: Read and cache current semester_module rows
        try (PreparedStatement selectStmt = conn.prepareStatement(selectSemesterModule)) {
            selectStmt.setString(1, oldKey);
            try (ResultSet rs = selectStmt.executeQuery()) {
                while (rs.next()) {
                    int domainId = rs.getInt("domain_id");
                    int semesterNo = rs.getInt("semester_no");
                    cachedSemesterModules.add(new int[]{domainId, semesterNo});
                }
            }
        }

        // Step 2: Delete old semester_module entries
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteFromSemesterModule)) {
            deleteStmt.setString(1, oldKey);
            deleteStmt.executeUpdate();
        }

        // Step 3: Update module mnémonique
        try (PreparedStatement updateStmt = conn.prepareStatement(updateModule)) {
            updateStmt.setString(1, module.getUniqueName());
            updateStmt.setString(2, oldKey);
            updateStmt.executeUpdate();
        }

        // Step 4: Insert cached semester_module rows with new module_id
        try (PreparedStatement insertStmt = conn.prepareStatement(insertIntoSemesterModule)) {
            for (int[] row : cachedSemesterModules) {
                insertStmt.setInt(1, row[0]); // domain_id
                insertStmt.setInt(2, row[1]); // semester_no
                insertStmt.setString(3, module.getUniqueName()); // new module_id
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        }

        conn.commit();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public static boolean addDomain(Domain domain) {
    String insertQuery = "INSERT INTO spécialité (nom_spécialité, cycle_id) VALUES (?,?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
        stmt.setString(1, domain.getDomainName());
        stmt.setInt(2, domain.getCycle());

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public static boolean deleteDomain(Domain domain) {
    String deleteSemesterModules = "DELETE FROM semester_module WHERE domain_id = ?";
    String deleteDomain = "DELETE FROM spécialité WHERE ID_spécialité = ?";

    try (Connection conn = getConnection()) {
        conn.setAutoCommit(false); // Begin transaction

        try (PreparedStatement stmt1 = conn.prepareStatement(deleteSemesterModules);
             PreparedStatement stmt2 = conn.prepareStatement(deleteDomain)) {

            stmt1.setInt(1, domain.getId());
            stmt1.executeUpdate();

            stmt2.setInt(1,domain.getId());
            int rows = stmt2.executeUpdate();

            conn.commit();
            return rows > 0;

        } catch (SQLException e) {
            conn.rollback(); // Rollback on failure
            e.printStackTrace();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

public static ObservableList<DomainInfo> getAllDomaines() {
    ObservableList<DomainInfo> domaines = FXCollections.observableArrayList();

    String query = "SELECT s.ID_spécialité, s.nom_spécialité, c.nom_cycle " +
                   "FROM spécialité s " +
                   "JOIN Cycle c ON s.cycle_id = c.ID_cycle";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("ID_spécialité");
            String domainName = rs.getString("nom_spécialité");
            String cycleName = rs.getString("nom_cycle");

            DomainInfo info = new DomainInfo(cycleName, domainName);

            // Set the ID using reflection, since setId is private
            Field idField = DomainInfo.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(info, id);

            domaines.add(info);
        }

    } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
        e.printStackTrace();
    }

    return domaines;
}

public static String getModuleIdsByDomainAndDateTime(String domainName, int semesterNo, LocalDate examDate, String examHeure) {
    
    String query = "SELECT sm.module_id " +
                   "FROM semester_module sm " +
                   "JOIN spécialité s ON sm.domain_id = s.ID_spécialité " +
                   "JOIN exam e ON e.mnémonique = sm.module_id " + // Join with the exam table
                   "WHERE s.nom_spécialité = ? AND sm.semester_no = ? " +
                   "AND e.exam_date = ? AND e.exam_heure = ?"; // Conditions for exam date and time
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, domainName);
        stmt.setInt(2, semesterNo);
        stmt.setDate(3, java.sql.Date.valueOf(examDate)); // Set the exam date
        stmt.setString(4, examHeure); // Set the exam time
        
        try (ResultSet rs = stmt.executeQuery()) {
            // Return the first module_id that matches
            if (rs.next()) {
                return rs.getString("module_id");
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return null; // Return null if no match found
}


    
public static int getSemesterByExamDateTimeAndDomain(LocalDate examDate, String examHeure, int domainId) {
    String query = "SELECT sm.semester_no " +
                   "FROM exam e " +
                   "JOIN semester_module sm ON e.mnémonique = sm.module_id " +
                   "WHERE e.exam_date = ? AND e.exam_heure = ? AND sm.domain_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setDate(1, java.sql.Date.valueOf(examDate));
        stmt.setString(2, examHeure);
        stmt.setInt(3, domainId);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("semester_no");
            } else {
                System.err.println("No semester found for the given date/time/domain");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0; // or -1 if you want to indicate "not found"
}

public static Integer getSpecialiteIdByCycleAndName(int cycleId, String nomSpecialite) {
    String query = "SELECT ID_spécialité FROM spécialité WHERE cycle_id = ? AND nom_spécialité = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, cycleId);
        stmt.setString(2, nomSpecialite);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("ID_spécialité");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace(); // Or handle the exception as needed
    }

    return null; // Return null if not found
}
public static Exam getExamByDetails(LocalDate examDate, String examHeure, String mnemonique) {
    Exam exam = null;

    String query = "SELECT * FROM exam WHERE exam_date = ? AND exam_heure = ? AND mnémonique = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setDate(1, java.sql.Date.valueOf(examDate));
        stmt.setString(2, examHeure);
        stmt.setString(3, mnemonique);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                exam = new Exam(rs.getDate("exam_date").toLocalDate(), 
                		rs.getString("exam_heure"), 
                		new Module( rs.getString("mnémonique")),
                		rs.getInt("ID_exam"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return exam;
}
public static ObservableList<Professor> getUnassignedSurveillants2(int examId) {
    ObservableList<Professor> professors = FXCollections.observableArrayList();

    String query =
        "SELECT DISTINCT p.ID_prof, p.nom_prof, p.prenom_prof, p.email_prof " +
        "FROM examiner e " +
        "JOIN professor p ON e.ID_prof = p.ID_prof " +
        "WHERE e.ID_exam = ? " +
        "AND e.ID_prof NOT IN ( " +
        "    SELECT s.ID_prof FROM surveillance s WHERE s.exam = ? " +
        ") " +
        "ORDER BY p.nom_prof, p.prenom_prof";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, examId);
        stmt.setInt(2, examId);  // For the subquery

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Professor prof = new Professor(
                    rs.getInt("ID_prof"),
                    rs.getString("prenom_prof"),
                    rs.getString("nom_prof"),
                    rs.getString("email_prof")
                );
                professors.add(prof);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return professors;
}

public static boolean doesSalleExist(String salleName) {
    String query = "SELECT 1 FROM salle WHERE nom_salle = ? LIMIT 1";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, salleName);
        
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next(); // If there's at least one result, the salle exists
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return false; // Return false if there is an error or no result
}
public static boolean addSalle(String salleName, int min,int max) {
    String query = "INSERT INTO salle VALUES (?,?,?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, salleName);
        stmt.setInt(2, min);
        stmt.setInt(3, max);
        int rowsAffected = stmt.executeUpdate();
        System.out.println("done");
        return rowsAffected > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
public static boolean deleteSalleByName(String salleName) {
    String query = "DELETE FROM salle WHERE nom_salle = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, salleName);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
public static List<ClassRoom> loadAllSalles() {
    List<ClassRoom> salles = new ArrayList<>();
    String query = "SELECT nom_salle,minProf,maxProf FROM salle";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
        	String name = rs.getString("nom_salle");
            int min = rs.getInt("minProf");
            int max = rs.getInt("maxProf");
          if(min==1) {
            ClassRoom salle = new ClassRoom(name,"Salle");
      	  salles.add(salle);

        }
          if(min==2) {
              ClassRoom salle = new ClassRoom(name,"Amphi");
        	  salles.add(salle);

          }
          

    }
    }catch (SQLException e) {
        e.printStackTrace();
    }
return salles;
}
public static String getRoomStatus(String nomSalle) {
    String query = "SELECT COUNT(s.nom_salle) AS rep_count, " +
                  "sal.minProf, sal.maxProf " +
                  "FROM salle sal " +
                  "LEFT JOIN surveillance s ON sal.nom_salle = s.nom_salle " +
                  "WHERE sal.nom_salle = ? " +
                  "GROUP BY sal.minProf, sal.maxProf";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, nomSalle);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int repCount = rs.getInt("rep_count");
                int minProf = rs.getInt("minProf");
                int maxProf = rs.getInt("maxProf");

                if (repCount < minProf) return "red";
                else if (repCount < maxProf) return "yellow";
                else return "green";
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "error";
}

public static ObservableList<String> getSallesWithStatusByExamId(int examId) {
    ObservableList<String> sallesWithStatus = FXCollections.observableArrayList();

    String query = "SELECT DISTINCT es.nom_salle " +
                   "FROM examen_salle es " +
                   "WHERE es.id_exam = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, examId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String salle = rs.getString("nom_salle");
                String status = getRoomStatus(salle);

                // Add status label with room name (e.g., "yellow - RoomA")
                sallesWithStatus.add(status+"-"+salle );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return sallesWithStatus;
}

public static ObservableList<String> getAvailableSalles(int examId) {
    ObservableList<String> availableSalles = FXCollections.observableArrayList();

    // Query all rooms assigned to this exam by its ID
    String examRoomsQuery = "SELECT DISTINCT es.nom_salle " +
                            "FROM examen_salle es " +
                            "WHERE es.id_exam = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(examRoomsQuery)) {

        stmt.setInt(1, examId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String salle = rs.getString("nom_salle");
                String status = getRoomStatus(salle);

                if (status.equals("red") || status.equals("yellow")) {
                    availableSalles.add(status + " - " + salle);
                }
                // Fully occupied rooms (green) are excluded
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return availableSalles;
}

public static void insertSurveillance(int examId, int professorId, String nom) {
    String query = "INSERT INTO surveillance (ID_prof, nom_salle,exam) VALUES (?, ?, ?)";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

      
        statement.setInt(1, professorId);
        statement.setString(2, nom);
        statement.setInt(3, examId);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Surveillance row inserted successfully.");
        }

    } catch (SQLException e) {
        System.err.println("Error inserting surveillance row: " + e.getMessage());
    }
}

public static Map<String, Integer[]> getRoomCapacities(int examId) {
    Map<String, Integer[]> roomCapacities = new HashMap<>();

    String query = """
        SELECT s.nom_salle, s.minProf, s.maxProf
        FROM examen_salle es
        JOIN salle s ON es.nom_salle = s.nom_salle
        WHERE es.id_exam = ?
    """;

    try (Connection conn = getConnection();  // Replace with your connection method
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, examId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String roomName = rs.getString("nom_salle");
                int minProf = rs.getInt("minProf");
                int maxProf = rs.getInt("maxProf");
                roomCapacities.put(roomName, new Integer[]{minProf, maxProf});
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return roomCapacities;
}

public static boolean markProfessorPresent(int profId, int examId) {
    String query = "UPDATE examiner SET is_present = TRUE WHERE ID_prof = ? AND ID_exam= ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, profId);
        stmt.setInt(2, examId);
        int rowsUpdated = stmt.executeUpdate();

        return rowsUpdated > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public static ObservableList<String> getModulesByDomainAndSemester(Domain Domain,int Semestre){
	 ObservableList<String> Modules = FXCollections.observableArrayList();
	    String query = "SELECT module_id FROM semester_module WHERE domain_id =? and semester_no = ? ";

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setInt(1,Domain.getId());
	        stmt.setInt(2,Semestre);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                String name = rs.getString("module_id"); // Correct column name
	                Modules.add(name);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return Modules;

}
public static ObservableList<Professor> getAllProfessors(String name) {
    ObservableList<Professor> professors = FXCollections.observableArrayList();

    String query = "select * from professor where nom_prof like ? or prenom_prof like ?";
       
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
    	stmt.setString(1, "%" + name + "%");
    	stmt.setString(2, "%" + name + "%");

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Professor prof = new Professor(
                    rs.getInt("ID_prof"),
                    rs.getString("prenom_prof"),
                    rs.getString("nom_prof"),
                    rs.getString("email_prof")
                );
                professors.add(prof);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return professors;
}

 public ObservableList<ClassRoom> getAllClassrooms(Domain Domain){
	    ObservableList<ClassRoom> classrooms = FXCollections.observableArrayList();

	 String query = "select * from salle where Domain_id = ?";
	 try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	    	stmt.setInt(1,Domain.getId());


	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	            	ClassRoom classRoom = new ClassRoom(
	                    rs.getString("nom_salle"),
	                    rs.getInt("Domain_id")
	                );
	            	classrooms.add(classRoom);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return classrooms;


}
 public static boolean InsertIntoExam(Exam exam) {
	    String query = "INSERT INTO exam VALUES (?, ?, ?)";
	    try (Connection conn = getConnection();
	         PreparedStatement insertStmt = conn.prepareStatement(query)) {

	        insertStmt.setString(1, exam.getHour());
	        insertStmt.setDate(2, Date.valueOf(exam.getDate())); 
	        insertStmt.setString(3, exam.getModule().getUniqueName());

	        int result = insertStmt.executeUpdate();
	        return (result >= 1);
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}



}
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
    
    
    
    
    
    
    

