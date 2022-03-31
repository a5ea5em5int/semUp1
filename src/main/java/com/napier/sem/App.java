package com.napier.sem;


import java.sql.*;
import java.util.ArrayList;

/* it is App who connect to MySql  and extract one application */
public class App
{  Connection con = null;
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " +                                  Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }


    public ArrayList<CapitalCityReport>  getCitiesBtPop() throws SQLException {

        String sql ="select c.Name,co.Name,c.Population from country as co, city as c where co.Code =c.CountryCode order by c.Population desc limit 5";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rset = pstmt.executeQuery();
       ArrayList <CapitalCityReport>  capitals = new ArrayList<CapitalCityReport>();
        while(rset.next())
        {
           capitals.add(new CapitalCityReport(rset.getString(1),rset.getString(2),rset.getString(3)));
        }
    return capitals;
    }
    public void displayCapitalCity(ArrayList<CapitalCityReport> capitals)
    {   if (capitals ==null)
        {
        System.out.println(" capital is null");
        return;
         }
         else if (capitals.size() == 0)
         {   System.out.println(" There is no country data ");
                return;

            }
         else {
            for (CapitalCityReport crp : capitals)
            {
                if (crp == null) System.out.println(" capital is null ");
                else {
                System.out.println(crp.getName() + "\t" + crp.getCountry() + "\t" + crp.getPopulation());
            }
        }
    }
    }
    public static void main(String[] args) throws SQLException {
        App app = new App();

       if(args.length < 1){
            app.connect("localhost:33060", 0);
        }else{
            app.connect(args[0], Integer.parseInt(args[1]));
        }
        ArrayList<Country> courntryList = app.getAllCountriesInfobyPopulation();
        app.display(courntryList);
        ArrayList<CapitalCityReport> creport = app.getCitiesBtPop();
        app.displayCapitalCity(creport);
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
        countries.add(null);// for testing purposes
        return countries;
    }
    public void display(ArrayList<Country> countries) {
        if (countries == null)
            System.out.println(" countries object is null");
        else if (countries.size()==0)
        {
            System.out.println(" no country");
        }
        else {
            for (Country c : countries) {
                if (c == null) System.out.println(" country object is null");
                else {
                    System.out.println(c.getName() + "\t" + c.getContinent() + "\t" + c.getCapital() + "\t" + c.getRegion() + "\t" + c.getPopulation());
                }
            }

        }
    }
}
