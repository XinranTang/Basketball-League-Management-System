package sample;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField sqlText;
    public ChoiceBox cb;
    public Label warning;
    public TextArea viewList;
    public ImageView image;
    private Connection conn;

    public void getView(ActionEvent actionEvent) throws SQLException {
        warning.setText("");
        if (cb.getSelectionModel().getSelectedItem().toString().equals("write my own query using MySQL")) {
            String stmt = sqlText.getText();
            if (stmt != null && stmt.length() != 0) {
                PreparedStatement p = conn.prepareStatement(stmt);
                executeQuery(p);
            } else {
                warning.setText("Please enter some queries!");
            }
        } else if (cb.getSelectionModel().getSelectedItem() == null || cb.getSelectionModel().getSelectedItem().toString().length() == 0) {
            warning.setText("Please choose your management!");
        } else if (cb.getSelectionModel().getSelectedItem().toString().equals("show the score table for this year's points")) {
            String stmt =
                    "select team_name as Name, (win_number+draw_number+loss_number) as Played, win_number as Win,draw_number as Draw, loss_number as Loss, (win_number*2+draw_number) as Points " +
                            "from Team;";
            PreparedStatement p = conn.prepareStatement(stmt);
            executeQuery(p);
        } else if (cb.getSelectionModel().getSelectedItem().toString().equals("show the score table for this year's and next year's points")) {
            String stmt =
                    "select team_name as Name, (win_number+draw_number+loss_number) as Played, win_number as Win,draw_number as Draw, loss_number as Loss, (win_number*2+draw_number) as Points, (win_number*3+draw_number*2-loss_number*1) as 'Next Year\\'s Points' " +
                            "from Team;";
            PreparedStatement p = conn.prepareStatement(stmt);
            executeQuery(p);
        } else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for a match using away team name, home team name and date")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select * from basketballmatch " +
                                "where away_team = ? and home_team = ? and match_date = ?;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                p.setString(2, paramList[1]);
                p.setString(3, paramList[2]);
                executeQuery(p);
            }
        } else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for all the matches on one day")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select * from basketballmatch " +
                                "where match_date = ?;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                executeQuery(p);
            }
        } else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for all the matches between two teams")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select * from basketballmatch " +
                                "where (away_team = ? and home_team = ?) or (away_team = ? and home_team = ?);";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                p.setString(2, paramList[1]);
                p.setString(3, paramList[1]);
                p.setString(4, paramList[0]);
                executeQuery(p);
            }
        } else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for attributes of a player by ID")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select * from player " +
                                "where ID = ?;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                executeQuery(p);
            }
        }
        else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for all players with the same family name")) {
            String stmt =
                    "select p1.ID as ID_1,p1.p_family_name as p_family_name_1,p1.p_given_name as p_given_name_1,p1.team_name as team_1 ,p2.ID as ID_2,p2.p_family_name as family_name_2,p2.p_given_name as given_name_2 ,p2.team_name as team_2 from player p1,player p2 " +
                            "where p1.p_family_name = p2.p_family_name and p1.ID<p2.ID;";
            PreparedStatement p = conn.prepareStatement(stmt);
            executeQuery(p);
        } else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for players that works for a certain team by a team name")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select ID,p_family_name,p_given_name from player " +
                                "where team_name =  ?;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                executeQuery(p);
            }
        } else if (cb.getSelectionModel().getSelectedItem().toString().equals("According to coach name, look for his players buying year & price")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select ID,p_family_name,p_given_name,price, buy_year from player " +
                                "where c_family_name = ? and c_given_name = ?;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                p.setString(2, paramList[1]);
                executeQuery(p);
            }
        } else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for a team's manager/coach and his attributes")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select c_family_name as 'family name' , c_given_name as 'given name' from team " +
                                "where team_name = ?";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                executeQuery(p);
            }
        }else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for all satisfied players that has Estimated Value within a range")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select ID, p_family_name,p_given_name from player " +
                                "where estimated_value>? and estimated_value<?;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                p.setString(2, paramList[1]);
                executeQuery(p);
            }
        }else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for all the teams in one city")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select team_name,street_name ,win_number,draw_number,loss_number from team " +
                                "where team.city_name = ?;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                executeQuery(p);
            }
        }else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for all of a team's matches as home team")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select * from basketballmatch " +
                                "where home_team = ?;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                executeQuery(p);
            }
        }else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for a team's players whose age is within a range")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select p_family_name,p_given_name " +
                                "from player " +
                                "where team_name = ? and age>? and age<?;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                p.setString(2, paramList[1]);
                p.setString(3, paramList[2]);
                executeQuery(p);
            }
        }else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for players bought by a coach/manager order by prices in descending order")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select  p_family_name,p_given_name " +
                                "from player " +
                                "where c_family_name = ? and c_given_name = ? " +
                                "order by price desc;";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                p.setString(2, paramList[1]);
                executeQuery(p);
            }
        }
        else if (cb.getSelectionModel().getSelectedItem().toString().equals("Look for the youngest player in a team")) {
            if (sqlText.getText() == null || sqlText.getText().length() == 0) {
                warning.setText("Please enter some parameters for queries!");
            } else {
                String stmt =
                        "select p_family_name,p_given_name ,age " +
                                "from player " +
                                "where team_name = ? and age <= all(select age from player where team_name = ?);";
                PreparedStatement p = conn.prepareStatement(stmt);
                p.clearParameters();
                String[] paramList = sqlText.getText().split(",");
                p.setString(1, paramList[0]);
                p.setString(2, paramList[0]);
                executeQuery(p);
            }
        }        else if (cb.getSelectionModel().getSelectedItem().toString().equals("Get the average age for every team")) {

                String stmt =
                        "select team_name,avg(age) " +
                                "from player " +
                                "group by team_name;";
                PreparedStatement p = conn.prepareStatement(stmt);
                executeQuery(p);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image imageSource = new Image("file:C:\\Users\\gmf\\Desktop\\数据库实验\\BasketballLeageManageSystem\\641.png");
        image.setImage(imageSource);
        cb.setItems(FXCollections.observableArrayList(
                "show the score table for this year's points", "show the score table for this year's and next year's points", "Look for a match using away team name, home team name and date",
                "Look for all the matches on one day", "Look for all the matches between two teams", "Look for attributes of a player by ID",
                "Get the average age for every team","Look for the youngest player in a team","Look for players bought by a coach/manager order by prices in descending order",
                "Look for a team's players whose age is within a range","Look for all of a team's matches as home team","Look for all the teams in one city",
                "Look for all satisfied players that has Estimated Value within a range","Look for a team's manager/coach and his attributes",
                "According to coach name, look for his players buying year & price","Look for players that works for a certain team by a team name",
                "Look for all players with the same family name",
                new Separator(), "write my own query using MySQL")
        );
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver could not be loaded");
            System.exit(0);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "basketball_league", "root", "205173");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeQuery(PreparedStatement p) {
        try {
            ResultSet rs = p.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            StringBuilder stringBuilder = new StringBuilder();
            List<List<String>> data = new ArrayList<>();
            List<String> temp = new ArrayList<>();
            for (int j = 0; j < rs.getMetaData().getColumnCount(); j++) {
                temp.add(rs.getMetaData().getColumnName(j+1));
            }
            data.add(temp);
            while (rs.next()) {
                temp = new ArrayList<>();
                for (int i = 1; i <= col; i++) {
                    temp.add(rs.getString(i) );
                }
                data.add(temp);
            }
            viewList.setText(new TableFormat(data).println(10));

        } catch (MySQLSyntaxErrorException e) {
            warning.setText(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
