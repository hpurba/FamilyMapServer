package service.services;

import DAO.Database;
import DAO.PersonDAO;
import DAO.UserDAO;
import DAO.authorizationTokenDAO;
import model.User;
import service.response.FillResponse;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * URL Path: /fill/[username]/{generations}
 * Example: /fill/susan/3
 * Description: Populates the server's database with generated data for the specified user name.
 * The required "username" parameter must be a user already registered with the server. If there is
 * any data in the database already associated with the given user name, it is deleted. The
 * optional generations parameter lets the caller specify the number of generations of ancestors
 * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
 * persons each with associated events).
 * HTTP Method: POST
 * Auth Token Required: No
 * Request Body: None
 * Errors: Invalid username or generations parameter, Internal server error
 */
public class Fill {

    public FillResponse execute(String username, int generations) throws SQLException {

        FillResponse response = new FillResponse();

        try {
            Database db = new Database();
            Connection conn = db.openConnection();

            UserDAO user_dao = new UserDAO();
            User user = user_dao.find(username);

            if(user == null) {
                response.setMessage("Username incorrect.");
                response.setSuccess(false);
            }
            else if (generations < 0) {
                response.setMessage("generations must be 1 or more.");
                response.setSuccess(false);
            }
            else {
                // Username exists and the correct number of Generations is provided



            }







//            PersonDAO person_dao = new PersonDAO();
//            authorizationTokenDAO token_dao = new authorizationTokenDAO();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // get user data from username

        // delete data for specific user (if they already exist with info)

        // create 4 generations of family, or different based on number of generations

        response.setMessage("Successfully");
        return response;
    }
}