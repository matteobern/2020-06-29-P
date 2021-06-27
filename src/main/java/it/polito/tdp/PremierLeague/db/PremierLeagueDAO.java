package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
		public List<Match>getMatchesFromMonth(int month){
			String sql = "SELECT * "
					+ "FROM matches m, Teams t1, Teams t2 "
					+ "WHERE MONTH(date)=? AND m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
			List<Match> result = new ArrayList<Match>();
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, month);
				ResultSet res = st.executeQuery();
				while (res.next()) {

					
					Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
								res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
					
					
					result.add(match);

				}
				conn.close();
				return result;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
		public List<Adiacenza> getAdiacenze(int month,int time){
			String sql = "SELECT a1.matchID as m1,a2.matchID as m2,COUNT(*) as peso "
					+ "FROM Actions a1,Actions a2,Matches m1,Matches m2 "
					+ "WHERE a1.PlayerID=a2.PlayerID AND a1.TimePlayed>? AND a2.timePlayed>? AND a1.matchID>a2.matchID AND "
					+ "a1.matchID=m1.matchID AND a2.matchID=m2.matchID AND MONTH(m1.Date)= MONTH(m2.Date) AND MONTH(m1.Date)=? "
					+ "GROUP BY a1.matchID,a2.matchID";
					
			List<Adiacenza> result = new ArrayList<Adiacenza>();
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, time);
				st.setInt(2, time);
				st.setInt(3, month);
				ResultSet res = st.executeQuery();
				while (res.next()) {

					
					Adiacenza a=new Adiacenza(res.getInt("m1"),res.getInt("m2"),res.getInt("peso"));
					
					
					result.add(a);

				}
				conn.close();
				return result;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
	
}
