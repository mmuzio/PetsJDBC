package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * JDBC- Java database connectivity
 * 
 * Important Interfaces:
 * - Connection -> allows us to connect to our db
 * - Statement -> raw SQL query
 * - Prepared Statement -> precompile the SQL string without parameters, 
 * once parameters are added, they are only treated as values, never keywords
 * - Callable Statement -> same idea as PS, also prevents SQL injection but 
 * is typically used for stored procedures (PL/SQL)
 */

import com.example.model.Pet;

public class PetDaoImpl implements PetDao {
	
	//There should be system/env variables once connected to AWS:RDS 
	//NOTE: STS WILL NOT HAVE ACCESS TO NEW ENVIRONMENT VARIABLES UNTIL YOU RESTART IT
	private static String url = "jdbc:postgresql://localhost:5432/postgres";
	// url is always of the form: jdbc:postgresql://host:port/database
	
	private static String username = "postgres";
	
	private static String password = "postgres";
	
	
	
	//CREATE

	@Override
	public void insertPet(Pet p) {
		
		try (Connection conn = DriverManager.getConnection(url, username, password)){
			
			String sql = "INSERT INTO Pets VALUES (?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// We are setting the first question mark to be the name that belongs to our pet object
			ps.setString(1, p.getName());
			
			// We are setting the second question mark to be the type that belongs to our pet object
			ps.setString(2, p.getType());
			
			// Executes the query without a result
			ps.execute();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}

	}
	
	//READ

	@Override
	public Pet selectPetByName(String name) {
		
		Pet pet = null;
		
		try (Connection conn = DriverManager.getConnection(url, username, password)){
			
			String sql = "SELECT * FROM Pets WHERE name=?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// We are setting the first question mark to be the name that belongs to our pet object
			ps.setString(1, name);
			
			//Execute query and store resultset in resultset type (object)
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				pet = new Pet(rs.getString("name"), rs.getString("type"));
				//we are iterating through our result set and populating our
				//pet object with the values that are coming from the table's columns
				//"name" and "type" on line 67 are column names
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		return pet;
	}

	@Override
	public List<Pet> selectAllPets() {
		
		List<Pet> pets = new ArrayList<Pet>();
		
		try (Connection conn = DriverManager.getConnection(url, username, password)){
			
			String sql = "SELECT * FROM PETS";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//Execute query and store resultset in resultset type (object)
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				pets.add(new Pet(rs.getString("name"), rs.getString("type")));
				//we are iterating through our result set and populating our
				//pet object with the values that are coming from the table's columns
				//"name" and "type" on line 67 are column names
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		return pets;
	}

	//UPDATE
	
	@Override
	public void updatePet(Pet p) {
		
		try (Connection conn = DriverManager.getConnection(url, username, password)){
			
			String sql = "UPDATE Pets SET type=? WHERE name=? ";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// We are setting the first question mark to be the name that belongs to our pet object
			ps.setString(2, p.getName());
			
			// We are setting the second question mark to be the type that belongs to our pet object
			ps.setString(1, p.getType());
			
			// Executes the query without a result
			ps.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}

	}

	//DELETE
	
	@Override
	public void deletePet(Pet p) {
		
		try (Connection conn = DriverManager.getConnection(url, username, password)){
			
			String sql = "DELETE FROM Pets WHERE name=?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// We are setting the first question mark to be the name that belongs to our pet object
			ps.setString(1, p.getName());
			
			// Executes the query without a result
			ps.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}

	}

}
