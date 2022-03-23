package com.napier.sem;


import java.sql.*;
import java.util.ArrayList;

/* it is App who connect to MySql  and extract one application */
public class App
{  Connection con = null;
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database

        int retries = 100;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected to world sql");
                // Wait a bit
                Thread.sleep(10000);
                // Exit for loop
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }


    }
    public static void main(String[] args) throws SQLException {
        App app= new App();
        app.connect();
        ArrayList<Country> courntryList = app.getAllCountriesInfobyPopulation();
        app.display(courntryList);
        app.disConnect();

    }
    public void disConnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
    public ArrayList<Country> getAllCountriesInfobyPopulation() throws SQLException {
        String sql ="select Name,Continent,Region,Capital,Population from country order by Population desc";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rset = pstmt.executeQuery();
        ArrayList<Country>  countries = new ArrayList<Country>();
        while(rset.next())
        {
            Country c= new Country(rset.getString(1),rset.getString(2),rset.getString(3),rset.getString(4),rset.getFloat(5));
            countries.add(c);

        }
        return countries;
    }
    public void display(ArrayList<Country> countries)
    {   for(Country c: countries)
      {
            System.out.println(c.getName()+"\t"+c.getContinent()+"\t"+c.getCapital()+"\t"+c.getRegion()+"\t"+c.getPopulation());
         }

    }
}
